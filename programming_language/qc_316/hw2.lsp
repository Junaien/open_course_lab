;1a: can a atom be a variable?
;yes, there can be symbol v for variable name

;1b: can a number be a variable?
;no, numbers are not symbols

;1c: can a list be a variable?
;no: list are actual data but not thing holds data

;1d: can an atom have no value?
;yes some variable can have no value when evaluated<<<<<<<<<<<<
;1e: can a atom have more than one value at the same time
;no

;1f can a variable have ifself as a value?
;no, if so it can't be evaluated

;1g can a list (A B C) be the value of two different variables?
;yes, you can have two reference to same (A B C)

;2 how many elements the list has
;(a)single atom
;(b)list of 4
;(c) can be a symbol atom
;(d) negither
;(e) list of 2
;(f) neither
;(g) list of 3
;(h) list of 2

;3use setf or setq to assign x
(setf X '(A B C))
(append X X)

;4what is the data type of the expression 'A?
;cons<<<<<<<<<<<<<<<<<<

;5
;a
(setf y '(A B))
;b
'(D (car y))

;6
(defun SQR (x)
         (list (* 4 x) (* x x)))
;7
(defun QUADRATIC (A B C)
                 (let ((part (sqrt (- (* B B) (* 4 A C)))))
                      (list (/ (- (- B) part) (* 2 A))
                            (/ (+ (- B) part) (* 2 A)))))

;8
(defun AREA (r)
            (* PI r r))

;9
(defun FTOC (f)
            (* (- f 32) 5/9))
;10
; (defun ROTATE-LEFT (lst)
;                    (labels ((helperf (lambda (l ans)
;                                          (case (null l)
;                                                (T (cons ans NIL))
;                                                (NIL (cons (car l) (helperf (cdr l) ans)))))))
;                         (if lst
;                             (helperf (cdr lst) (car lst))
;                             NIL)))
(defun ROTATE-LEFT (l)
                   (if   l
                         (append (cdr l) (list (car l)))
                         (NIL NIL)))
;11
(defun DIST (l1 l2)
            (sqrt (+ (expt  (- (first l1) (first l2)) 2)
                     (expt  (- (second l1) (second l2)) 2))))
;12
(defun head (l) (car l))
(defun tail (l) (cdr l))
;13
(defun switch (l) (list (second l) (first l)))
;14 70

;15
;a)
(caddr '(A B X D))
;b)
(car (cadadr '(A (B (X D)))))
;c)
(caadr (cadaar '(((A (B (X) D))))))

;16
;a)
(cons 'A (cons 'B (cons 'X (cons 'D NIL))))
;b)
(cons 'A (cons (cons 'B (cons (cons 'X (cons 'D NIL)) NIL)) NIL))
;c)
(cons (cons (cons 'A (cons (cons 'B (cons (cons 'X NIL) (cons 'D NIL))) NIL)) NIL) NIL)

;17
(setf E
'((90 91 92 93 94 95 96 97 98 99) (+ 3 4 –) (9 19 29 39 49 59 69 79 89 99)))
(list (cons (list (caar e) (cadar e)) (cddar e)) (append '(A B) (cddr (caddr e))))
;18
(list (cons (caar e) (cons 'A (cddar e)))  (cadadr e) (caddr (caddr e)) (cons (caddr (cadr e)) (cddr (caddr e))))
;19
(list (append (car e) (cons (cadadr e) NIL)) (append (cadr e) (cddr (caddr e))))
;20
(list (cons 'A (cdar e)) (list (caar e) (list (cadr (caddr e)) (caddr (caddr e))) (cddr (cdaddr e))))
