#lang racket
(provide (all-defined-out))

(define incr
  (letrec ([f (lambda (x) (cons x (lambda () (f (+ x 1)))))])
    (lambda () (f 1))))

(define ones (lambda () (cons 1 ones)))

;; returns a list of the same length where the first element is the total of the first and last el, second the second and the second last, etc
(define (palindromic xs)
  (cond [(null? xs) null]
        [#t
         (letrec ([rev (reverse xs)]
                  [fn (lambda (xs ys)
                        (cond [(null? xs) null]
                              [#t (cons (+ (car xs) (car ys)) (fn (cdr xs) (cdr ys)))]))])
           (fn xs rev))]))


;; define a stream of fibbonaci numbers
(define fibonacci
  (letrec ([f (lambda (x y)
                (cons (+ x y)
                      (lambda () (f y (+ x y)))))])
    (lambda () (f 0 1))))


;; take a function and a stream and apply the function to the stream elements until the function returns #f
(define (stream-until f s)
  (letrec ([pr (s)])
    (if (f (car pr))
        (cons (car pr) (stream-until f (cdr pr)))
        null)))

;; define a map function for a stream
(define (stream-map f s)
  (letrec ([fn (lambda (s)
                 (let ([pr (s)])
                   (cons (f (car pr)) (lambda () (fn (cdr pr))))))])
    (lambda () (fn s))))

;; takes in two streams and returns a stream that pairs the elements of the 2 streams
(define (stream-zip s1 s2)
  (letrec ([fn (lambda (s1 s2)
                 (let ([pr1 (s1)]
                       [pr2 (s2)])
                   (cons (cons (car pr1) (car pr2)) (lambda () (fn (cdr pr1) (cdr pr2))))))])
    (lambda () (fn s1 s2))))


;; takes in a list of streams and produces a new stream that takes an element from each stream
(define (interleave los)
  (letrec ([iterate-stream (lambda (s)
                             (cdr (s)))]

           [iterate-stream-list (lambda (lst res-lst)
                                  (if (null? lst)
                                      (iterate-stream-list (reverse res-lst) null)
                                      (cons (car ((car lst))) (lambda () (iterate-stream-list (cdr lst) (cons (iterate-stream (car lst)) res-lst))))))]

           )
    (lambda () (iterate-stream-list los null))))


;; takes a stream and an int and returns a stream where the first elements of the initial stream are packed and represent the first el of the current stream
(define (pack n s)
  (letrec ([build-stream (lambda (s n0 lst)
                           (let ([pr (s)])
                             (if (<= n0 0)
                                 (cons (reverse lst) (lambda () (build-stream s n null)))
                                 (build-stream (cdr pr) (- n0 1) (cons (car pr) lst)))))])
    (lambda () (build-stream s n null))))


(define-syntax perform
  (syntax-rules (if unless)
    [(perform e1 if e2)
     (if e2
         e1
         e2)]
    [(perform e1 unless e2)
     (if e2
         e2
         e1)]))
       
(define x 10)
(define y 20)

(perform (+ x y) if (< x y))
(perform (+ x y) unless (< x y))
    
                             
           

        
    