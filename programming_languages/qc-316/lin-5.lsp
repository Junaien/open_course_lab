;Main Problems
;Solution to Problem 1
;N is any positive interger
;L is any list, maybe NIL
(defun INDEX (N L)
  (cond ((< N 1)   'ERROR)
       ((endp L)  'ERROR)
       ((= N 1)   (car L))
       (T         (INDEX (- N 1) (cdr L)))));

 ;Solution to Problem 2
 ;L is any non empty list of real numbers
 (defun MIN-FIRST (L)
  (if (endp L)
       NIL
      (let ((X (MIN-FIRST (cdr L))))
           (if (or (endp X) (<= (car L) (car X)))
                L
               (cons (car X) (cons (car L) (cdr X)))))))

;Solution to Problem 3
;L is any list of real numbers
(defun SSORT (L)
  (if  (endp L)
        NIL
       (let ((X (MIN-FIRST L)))
            (cons (car X) (SSORT (cdr X))))))

;Solution to Problem 4
;L is any list of real numbers
(defun PARTITION (L P)
  (if   (endp L)
       (list NIL NIL)
       (let ((X (PARTITION (cdr L) P)))
                (if (< (car L) P)
                    (list (cons (car L) (car X)) (cadr X))
                    (list (car X) (cons (car L) (cadr X)))))))

(defun QSORT (L)
  (if (endp L)
      NIL
     (let* ((PL    (PARTITION (cdr L) (car L)))
           (left   (QSORT (car  PL)))
           (right  (QSORT (cadr PL))))
           (append left (cons (car L) right)))))

;Solution to Problem 5
;L1 L2 are a list of real numbers in ascending order
(defun MERGE-LISTS (L1 L2)
  (cond ((endp L1) L2)
       ((endp L2) L1)
       ((< (car L1) (car L2)) (cons (car L1) (MERGE-LISTS (cdr L1) L2)))
       ((cons (car L2) (MERGE-LISTS L1 (cdr L2))))))

;Solution to Problem 6
;L is a list of real numbers
(defun SPLIT-LIST (L)
  (cond ((endp L)        (list  NIL            NIL))
        ((endp (cdr L))  (list (list (car L))  NIL))
        (T               (let  ((X (SPLIT-LIST (cddr L))))
                               (list (cons (car  L) (car  X))
                                     (cons (cadr L) (cadr X)))))))

(defun MSORT (L)
  (cond ((endp L)         NIL)
        ((endp (cdr L))   L)
        (T               (let* ((SL     (SPLIT-LIST   L))
                               (left    (MSORT (car   SL)))
                               (right   (MSORT (cadr  SL))))
                         (MERGE-LISTS left right)))))

;Solution to Problem 7
;L is a list of elements
(defun REMOVE-ADJ-DUPL (L)
  (if (endp L)
       L
      (let ((X (REMOVE-ADJ-DUPL (cdr L))))
           (cond    ((endp X)                 (cons (car L) X))
                    ((equal (car L) (car X))   X)
                    (T                        (cons (car L) X))))))

 ;Solution to Problem 8
 ;L is a list of elements
 (defun UNREPEATED-ELTS (L)
   (cond ((endp L)    NIL)
         ((or (endp  (cdr L))  (not (equal (car L) (cadr L))))
                     (cons (car L) (UNREPEATED-ELTS (cdr L))))
         ((or (endp  (cddr L)) (not (equal (car L) (caddr L))))
                     (UNREPEATED-ELTS (cddr L)))
         (T          (UNREPEATED-ELTS (cdr L)))))

 ;Solution to Problem 9
 ;L is a list of elements
 (defun REPEATED-ELTS (L)
  (cond ((endp L)    NIL)
       ((or (endp (cdr L))  (not (equal (car L) (cadr L))))
                    (REPEATED-ELTS (cdr L)))
       ((or (endp (cddr L)) (not (equal (car L) (caddr L))))
                    (cons (car L) (REPEATED-ELTS (cddr L))))
       (T           (REPEATED-ELTS (cdr L)))))

;Solution to Problem 10
;L is a list of elements
(defun COUNT-REPETITIONS (L)
  (if   (endp L)
         NIL
        (let ((X  (COUNT-REPETITIONS (cdr L))))
              (if (or  (endp (cdr L)) (not (equal (car L) (cadr L))))
                       (cons (list 1 (car L)) X)
                       (cons (list (+ 1 (caar X)) (car L)) (cdr X))))))




;Solution to Problem 11
;L is a list of real numbers
(defun SUBSET (f L)
  (cond ((endp L)              NIL)
        ((funcall f (car L))  (cons (car L) (SUBSET f (cdr L))))
        (T                    (SUBSET f (cdr L)))))

;Solution to Problem 12
;L is a list of real numbers
(defun OUR-SOME (f L)
  (cond ((endp L)              NIL)
        ((funcall f (car L))   L)
        (T                    (OUR-SOME f (cdr L)))))
(defun OUR-EVERY (f L)
  (cond ((endp L)              T)
        ((funcall f (car L))  (OUR-EVERY f (cdr L)))
        (T                     NIL)))

;Solution to Problem 13
;P is the comparator
;L is a list of elements
(defun PARTITION1 (L P PREDICATE)
  (if  (endp L)
       (list NIL NIL)
       (let ((X (PARTITION1 (cdr L) P PREDICATE)))
                (if (funcall PREDICATE (car L)    P)
                    (list (cons (car L) (car X)) (cadr X))
                    (list (car X) (cons (car L)  (cadr X)))))))


(defun QSORT1 (P L)
  (if (endp L)
       NIL
      (let* ((PL     (PARTITION1 (cdr L) (car L) P))
             (left   (QSORT1 P (car  PL)))
             (right  (QSORT1 P (cadr PL))))
             (append left (cons (car L) right)))))

;Solution to Problem 14
;f is function applied
;L is a list of elements
(defun appliedIth (f i L)
  (cond   ((endp L)     NIL)
          ((<= i 0)     NIL)
          ((=  i 1)    (cons  (funcall f (car L)) (cdr L)))
          (T           (cons  (car L) (appliedIth f (- i 1) (cdr L))))))

(defun FOOHELPER (f i L LENGTH)
  (cond ((endp L)       NIL)
        ((> i LENGTH)   NIL)
        (T             (cons (appliedIth f i L) (FOOHELPER f (+ i 1) L LENGTH)))))


(defun FOO (f L)
  (FOOHELPER f 1 L (LENGTH L)))

;Solution to Problem 15
;L is a list of addable elements
(defun TR-ADD (L acc)
  (if (endp L )
       acc
      (TR-ADD (cdr L) (+ acc (car L)))))
(defun TR-MUL (L acc)
  (if (endp L )
       acc
      (TR-MUL (cdr L) (* acc (car L)))))
(defun TR-FAC (num acc)
  (if (= 1 num)
       acc
      (TR-FAC (- num 1) (* acc num))))
;n is an integer such that n > 1
(defun SLOW-PRIMEP (n)
  (if (<= n 1)
       NIL
      (= (- n 1) (mod (TR-FAC (- n 1) 1) n))))

;Solution to Problem 16
;M is non empty Matrix
(defun TRANSPOSE1 (M)
  (cond ((endp M)         NIL)
        ((endp (cdr M))  (mapcar #'list   (car M)))
        (T               (mapcar #'cons   (car M) (TRANSPOSE1 (cdr M))))))

(defun TRANSPOSE2 (M)
  (cond ((endp (car  M))    NIL)
        ((endp (cdar M))   (cons (mapcar #'car M) NIL))
        (T                 (cons (mapcar #'car M) (TRANSPOSE2 (mapcar #'cdr M))))))
(defun TRANSPOSE3 (M)
  (apply #'mapcar #'list M))
