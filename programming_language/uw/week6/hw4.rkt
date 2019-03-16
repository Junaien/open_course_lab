#lang racket

(provide (all-defined-out))

(define sequence
        (letrec ([f (lambda (low high stride)
                            (if (or (> low high) (not (> stride 0)))
                                null
                                (cons low (f (+ low stride) high stride))))])
                f))


(define (string-append-map xs suffix)
        (map (lambda (e)
                     (string-append e suffix))
              xs))

(define (list-nth-mod xs n)
  (letrec ([f (lambda (xs n)
                      (if (= n 0)
                          (car xs)
                          (f (cdr xs) (- n 1))))])
        (cond [(< n 0) (error "list-nth-mod: negative number")]
              [(null? xs) (error "list-nth-mod: empty list")]
              [#t (f xs (remainder n (length xs)))])))

(define (stream-for-n-steps stream  n)
        (if (= 1 n)
            (cons (car (stream)) null)
            (cons (car (stream)) (stream-for-n-steps (cdr (stream)) (- n 1)))))
  
        


(define (funny-number-stream)
  (letrec ([f (lambda (n)
                (if (= 0 (remainder n 5))
                    (cons (* -1 n) (lambda () (f (+ 1 n))))
                    (cons n (lambda () (f (+ 1 n))))))])
    (f 1)))

(define dan-then-dog
        (letrec ([f-dan (lambda () (cons "dan.jpg" f-dog))]
                 [f-dog (lambda () (cons "dog.jpg" f-dan))])
                f-dan))

(define (stream-add-zero stream)
  (lambda ()
    (cons (cons 0 (car (stream))) (stream-add-zero (cdr (stream))))))

(define (cycle-lists xs ys)
        (letrec ([f (lambda (n)
                            (lambda ()
                            (cons (cons (list-nth-mod xs n) (list-nth-mod ys n))
                                  (f (+ n 1)))))])
                (f 0)))


(define (vector-assoc v vec)
        (letrec ([f (lambda (v vec index)
                            (cond [(null? vec) #f]
                                  [(not (vector? vec)) #f]
                                  [(= index (vector-length vec)) #f]
                                  [#t (let ([element (vector-ref vec index)])
                                            (cond [(and (pair? element) (equal? (car element) v)) element]
                                                  [#t (f v vec (+ index 1))]))]))])
               (f v vec 0)))
                                                  
                                                 

(define (cached-assoc xs n)
     (letrec ([memo (mcons 0 (make-vector n))])
             (lambda (v)
                     (letrec ([p (vector-assoc v (mcdr memo))])
                             (if p
                                 (begin                                
                                   p)
                                 (let ([new-ans (assoc v xs)])
                                      (if (and new-ans (> n 0))
                                          (begin
                                            (vector-set! (mcdr memo) (mcar memo) new-ans)
                                            (set-mcar! memo (remainder (+ 1 (mcar memo)) n))
                                            new-ans)
                                          new-ans)))))))
                                          
                                 
                                 
                                 
