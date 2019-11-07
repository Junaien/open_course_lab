    def gauss_jordan_solve(self, B, freevar=False):
        """
        Solves ``Ax = B`` using Gauss Jordan elimination.

        There may be zero, one, or infinite solutions.  If one solution
        exists, it will be returned. If infinite solutions exist, it will
        be returned parametrically. If no solutions exist, It will throw
        ValueError.

        Parameters
        ==========

        B : Matrix
            The right hand side of the equation to be solved for.  Must have
            the same number of rows as matrix A.

        freevar : List
            If the system is underdetermined (e.g. A has more columns than
            rows), infinite solutions are possible, in terms of arbitrary
            values of free variables. Then the index of the free variables
            in the solutions (column Matrix) will be returned by freevar, if
            the flag `freevar` is set to `True`.

        Returns
        =======

        x : Matrix
            The matrix that will satisfy ``Ax = B``.  Will have as many rows as
            matrix A has columns, and as many columns as matrix B.

        params : Matrix
            If the system is underdetermined (e.g. A has more columns than
            rows), infinite solutions are possible, in terms of arbitrary
            parameters. These arbitrary parameters are returned as params
            Matrix.

        Examples
        ========

        >>> from sympy import Matrix
        >>> A = Matrix([[1, 2, 1, 1], [1, 2, 2, -1], [2, 4, 0, 6]])
        >>> B = Matrix([7, 12, 4])
        >>> sol, params = A.gauss_jordan_solve(B)
        >>> sol
        Matrix([
        [-2*tau0 - 3*tau1 + 2],
        [                 tau0],
        [           2*tau1 + 5],
        [                 tau1]])
        >>> params
        Matrix([
        [tau0],
        [tau1]])
        >>> taus_zeroes = { tau:0 for tau in params }
        >>> sol_unique = sol.xreplace(taus_zeroes)
        >>> sol_unique
         Matrix([
        [2],
        [0],
        [5],
        [0]])


        >>> A = Matrix([[1, 2, 3], [4, 5, 6], [7, 8, 10]])
        >>> B = Matrix([3, 6, 9])
        >>> sol, params = A.gauss_jordan_solve(B)
        >>> sol
        Matrix([
        [-1],
        [ 2],
        [ 0]])
        >>> params
        Matrix(0, 1, [])

        >>> A = Matrix([[2, -7], [-1, 4]])
        >>> B = Matrix([[-21, 3], [12, -2]])
        >>> sol, params = A.gauss_jordan_solve(B)
        >>> sol
        Matrix([
        [0, -2],
        [3, -1]])
        >>> params
        Matrix(0, 2, [])

        See Also
        ========

        lower_triangular_solve
        upper_triangular_solve
        cholesky_solve
        diagonal_solve
        LDLsolve
        LUsolve
        QRsolve
        pinv

        References
        ==========

        .. [1] https://en.wikipedia.org/wiki/Gaussian_elimination

        """
        from sympy.matrices import Matrix, zeros

        aug = self.hstack(self.copy(), B.copy())
        B_cols = B.cols
        row, col = aug[:, :-B_cols].shape

        # solve by reduced row echelon form
        A, pivots = aug.rref(simplify=True)
        A, v = A[:, :-B_cols], A[:, -B_cols:]
        pivots = list(filter(lambda p: p < col, pivots))
        rank = len(pivots)

        # Bring to block form
        permutation = Matrix(range(col)).T

        for i, c in enumerate(pivots):
            permutation.col_swap(i, c)


        # check for existence of solutions
        # rank of aug Matrix should be equal to rank of coefficient matrix
        if not v[rank:, :].is_zero:
            raise ValueError("Linear system has no solution")

        # Get index of free symbols (free parameters)
        free_var_index = permutation[
                         len(pivots):]  # non-pivots columns are free variables

        # Free parameters
        # what are current unnumbered free symbol names?
        name = _uniquely_named_symbol('tau', aug,
            compare=lambda i: str(i).rstrip('1234567890')).name
        gen = numbered_symbols(name)
        tau = Matrix([next(gen) for k in range((col - rank)*B_cols)]).reshape(
            col - rank, B_cols)

        # Full parametric solution
        V = A[:rank,:]
        for c in reversed(pivots):
            V.col_del(c)
        vt = v[:rank, :]
        free_sol = tau.vstack(vt - V * tau, tau)

        # Undo permutation
        sol = zeros(col, B_cols)
        for k in range(col):
            sol[permutation[k], :] = free_sol[k,:]

        if freevar:
            return sol, tau, free_var_index
        else:
            return sol, tau