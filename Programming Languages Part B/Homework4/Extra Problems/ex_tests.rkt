#lang racket
(require "ex.rkt")
(require rackunit)

(define tests
  (test-suite "tests for extra problems"
              (check-equal? (palindromic (list 1 2 4 8)) (list 9 6 6 9) "test palindromic 1")
              (check-equal? (palindromic null) null "test palindromic 2")
              (check-equal? (palindromic (list 1)) (list 2) "test palindromic 3")
              (check-equal? (palindromic (list 1 2)) (list 3 3) "test palindromic 3")

              (check-equal? (stream-until (lambda (x) (< x 10)) fibonacci) (list 1 2 3 5 8) "test steam until")
              (check-equal? (stream-until (lambda (x) (< x 5)) incr) (list 1 2 3 4) "test steam until")

              (check-equal? (stream-until (lambda (x) (< x 10)) (stream-map (lambda (x) (+ x 1)) fibonacci)) (list 2 3 4 6 9) "test steam until")

              (check-equal? (stream-until (lambda (x) (< (car x) 10)) (stream-zip incr fibonacci)) (list (cons 1 1) (cons 2 2) (cons 3 3) (cons 4 5) (cons 5 8) (cons 6 13) (cons 7 21) (cons 8 34) (cons 9 55)) "test steam zip")

              (check-equal? (stream-until (lambda (x) (< x 6)) (interleave (list incr ones fibonacci))) (list 1 1 1 2 1 2 3 1 3 4 1 5 5 1) "test steam zip")

              (check-equal? (stream-until (lambda (x) (< x 6)) (interleave (list incr))) (list 1 2 3 4 5) "test steam zip")

              (check-equal? (stream-until (lambda (x) (< x 6)) (interleave (list incr))) (list 1 2 3 4 5) "test steam zip")

              (check-equal? (stream-until (lambda (x) (if (member 21 x)
                                                          #f
                                                          #t)) (pack 5 incr)) (list (list 1 2 3 4 5) (list 6 7 8 9 10) (list 11 12 13 14 15) (list 16 17 18 19 20)) "test steam zip")

                                                
              ))         

(require rackunit/text-ui)
;; runs the test
(run-tests tests)