#lang racket

(require "ep.rkt")

(require rackunit)

(define tests
  (test-suite
   "Sample tests for Assignment 5"
   (check-equal? (tree-height (btree-leaf)) 0 "tree-height test")

   (check-equal? (tree-height (btree-node 1 (btree-leaf) (btree-leaf))) 1 "tree-height test")

   (check-equal? (tree-height (btree-node 1 (btree-node 1 (btree-node 1 (btree-leaf) (btree-leaf)) (btree-leaf)) (btree-node 1 (btree-leaf) (btree-leaf)))) 3 "tree-height test")

   (check-equal? (sum-tree (btree-leaf)) 0 "tree-height test")

   (check-equal? (sum-tree (btree-node 4 (btree-leaf) (btree-leaf))) 4 "tree-height test")

   (check-equal? (sum-tree (btree-node 4 (btree-node 4 (btree-node 4 (btree-leaf) (btree-leaf)) (btree-leaf)) (btree-node 1 (btree-leaf) (btree-leaf)))) 13 "tree-height test")


   (check-equal? (prune-at-v (btree-leaf) 2) (btree-leaf) "tree-height test")

   (check-equal? (prune-at-v (btree-node 4 (btree-leaf) (btree-leaf)) 4) (btree-leaf) "tree-height test")

   (check-equal? (prune-at-v (btree-node 2 (btree-leaf) (btree-leaf)) 4) (btree-node 2 (btree-leaf) (btree-leaf)) "tree-height test")

   (check-equal? (prune-at-v (btree-node 4
                                         (btree-node 4
                                                     (btree-node 2 (btree-leaf)
                                                                 (btree-leaf))
                                                     (btree-leaf))
                                         (btree-node 1 (btree-leaf) (btree-leaf))) 2)
                 (btree-node 4
                             (btree-node 4 (btree-leaf) (btree-leaf))
                             (btree-node 1 (btree-leaf) (btree-leaf))) "tree-height test")

   (check-equal? (well-formed-tree? (btree-leaf)) #t "well formed test")

   (check-equal? (well-formed-tree? (btree-node 4 (btree-leaf) (btree-leaf))) #t "well formed test")

   (check-equal? (well-formed-tree? (btree-node 4
                                                (btree-node 4
                                                            (btree-node 2 (btree-leaf)
                                                                        (btree-leaf))
                                                            (btree-leaf))
                                                (btree-node 1 (btree-leaf) (btree-leaf)))) #t "well formed test")

   (check-equal? (fold-tree (lambda (x y) (+ x y)) 0 (btree-leaf)) 0 "fold tree")

   (check-equal? (fold-tree (lambda (x y) (+ x y)) 0 (btree-node 4 (btree-leaf) (btree-leaf))) 4 "fold tree")

   (check-equal? (fold-tree (lambda (x y) (* x y)) 1 (btree-node 4
                                                                 (btree-node 4
                                                                             (btree-node 2 (btree-leaf)
                                                                                         (btree-leaf))
                                                                             (btree-leaf))
                                                                 (btree-node 1 (btree-leaf) (btree-leaf)))) 32 "fold tree")


   (check-equal? (fold-tree (lambda (x y) (+ x y 1)) 7 (btree-node 4 (btree-node 5 (btree-leaf) (btree-leaf)) (btree-leaf))) 18 "fold tree")
   

   (check-equal? (((fold-tree-curr (lambda (x y) (+ x y))) 0) (btree-leaf)) 0 "fold tree")

   (check-equal? (((fold-tree-curr (lambda (x y) (+ x y))) 0) (btree-node 4 (btree-leaf) (btree-leaf))) 4 "fold tree")

   (check-equal? (((fold-tree-curr (lambda (x y) (* x y))) 1) (btree-node 4
                                                                 (btree-node 4
                                                                             (btree-node 2 (btree-leaf)
                                                                                         (btree-leaf))
                                                                             (btree-leaf))
                                                                 (btree-node 1 (btree-leaf) (btree-leaf)))) 32 "fold tree")


   (check-equal? (((fold-tree-curr (lambda (x y) (+ x y 1))) 7) (btree-node 4 (btree-node 5 (btree-leaf) (btree-leaf)) (btree-leaf))) 18 "fold tree")

   (check-equal? (crazy-sum (list 1 2 3)) 6 "crazy sum")

   (check-equal? (crazy-sum (list 1 - * 4 3)) 12 "crazy sum")
              
   (check-equal? (crazy-sum (list 10 * 6 / 5 - 3)) 9 "crazy sum")

   (check-equal? (either-fold (lambda (x y) (+ x y 1)) 7 (btree-node 4 (btree-node 5 (btree-leaf) (btree-leaf)) (btree-leaf))) 18 "fold either")
   
   (check-equal? (either-fold (lambda (x y) (+ x y)) 0 (list 1 2 3 4 5)) 15 "fold either")

   (check-equal? (flatten (list 1 2 (list (list 3 4) 5 (list (list 6) 7 8)) 9 (list 10))) (list 1 2 3 4 5 6 7 8 9 10) "flatten")

   (check-equal? (flatten (list 1 2 3 4 5 6 7 8 9 10)) (list 1 2 3 4 5 6 7 8 9 10) "flatten")


 
))

(require rackunit/text-ui)
;; runs the test
(run-tests tests)
