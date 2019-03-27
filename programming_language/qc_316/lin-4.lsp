;Main Problems
;Solution to Problem 1
;L must be a list, NIL is valid argument
(defun SUM (L)
           (if (endp L)
               0
               (+ (car L) (SUM (cdr L)))))

;Solution to Problem 2
;L must be a list, NIL is valid argument
(defun NEG-NUMS (L)
                (cond ((endp L)           L)
                      ((>= (car L) 0)    (NEG-NUMS (cdr L)))
                      (T                 (cons (car L)(NEG-NUMS (cdr L))))))

;Solution to Problem 3
;L must be a list of numbers, NIL is valid
;N any number
(defun INC-LIST-2 (L N)
                  (if (endp L)
                       NIL
                       (cons (+ (car L) N) (INC-LIST-2 (cdr L) N))))

;Solution to Problem 4
;L is any list of real numbers in ascending order, L could be NIL
;N any real number
(defun INSERT (N L)
                 (cond ((or (endp L)
                            (<= N (car L)))   (cons N L))
                       (T                     (cons (car L) (INSERT N (cdr L))))))


;Solution to Problem 5
;L is list of real numbers in ascending order, L could be NIL
(defun ISORT (L)
             (if (endp L)
                  L
                 (insert (car L) (ISORT (cdr L)))))

;Solution to Problem 6
;L is any list, L could be NIL
(defun SPLIT-LIST (L)
                  (cond ((endp L)        (list  NIL            NIL))
                        ((endp (cdr L))  (list (list (car L))  NIL))
                        (T
                           (let ((X (SPLIT-LIST (cddr L))))
                                (list (cons (car L) (car X))
                                      (cons (cadr L) (cadr X)))))))


;Solution to Problem 7
;L is any  list of real number, L could be NIL
;P is real number
(defun PARTITION (L P)
                 (if   (endp L)
                       (list NIL NIL)
                       (let ((X (PARTITION (cdr L) P)))
                                (if (< (car L) P)
                                    (list (cons (car L) (car X)) (cadr X))
                                    (list (car X) (cons (car L) (cadr X)))))))

;Solution to Problem 8
;L is any  list of real number, L could be NIL
;E is an element of L
(defun POS (E  L)
           (cond ((endp L)            0)
                 ((equal E (car L))   1)
                 (T   (let ((X (POS E (cdr L))))
                           (if (= 0 X)
                                0
                               (+ 1 X))))))

;Solution to Problem 9
;N is non-negative integer
(defun SPLIT-NUMS (N)
                  (if  (= 0 N)
                       (list (list 0) NIL)
                       (let ((X (SPLIT-NUMS (- N 1))))
                            (if (evenp N)
                                (list (cons N (car X))  (cadr X))
                                (list (car X)           (cons N (cadr X)))))))

;Solution to Problem 10
;L1 L2 are sets, L1, L2 could be NIL
(defun SET-UNION (L1 L2)
                 (cond ((endp L1)              L2)
                       ((endp L2)              L1)
                       ((member (car L1) L2)  (SET-UNION (cdr L1) L2))
                       (T                     (cons (car L1) (SET-UNION (cdr L1) L2)))))


;Solution to Problem 11
;L are set, L could be NIL
;x is atom
(defun SET-REMOVE (X L)
                  (cond ((endp L)             L)
                        ((equal X (car L))   (cdr L))
                        (T                   (cons (car L) (SET-REMOVE X (cdr L))))))

;Solution to Problem 12
;s1, s2 are sets of atom, s1, s2 could be NIL
(defun SET-EXCL-UNION (S1  S2)
                      (cond ((endp S1)  S2)
                            ((endp S2)  S1)
                            (T          (let* ((X  (SET-EXCL-UNION (cdr S1) S2))
                                               (P  (POS (car S1) X)))
                                              (if (= 0 P)
                                                  (cons (car S1) X)
                                                  (SET-REMOVE (car S1) X))))))
;Solution to Problem 13
;e is any list of number or symbol
(defun SINGLETONS (e)
                  (if (endp e)
                        NIL
                      (let ((X (SINGLETONS (cdr e))))
                           (if (member (car e) (cdr e))
                               (SET-REMOVE (car e) X)
                               (cons (car e) X)))))
