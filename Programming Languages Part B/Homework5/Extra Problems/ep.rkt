#lang racket

(provide (all-defined-out)) ;; so we can put tests in a second file

(struct btree-leaf () #:transparent)
(struct btree-node (value left right) #:transparent)

(define (tree-height bt)
  (if (btree-leaf? bt)
      0
      (+ 1 (max (tree-height (btree-node-left bt)) (tree-height (btree-node-right bt))))))


(define (sum-tree bt)
  (if (btree-leaf? bt)
      0
      (+ (btree-node-value bt) (sum-tree (btree-node-left bt)) (sum-tree (btree-node-right bt)))))


(define (prune-at-v bt v)
  (if (btree-leaf? bt)
      bt
      (if (equal? (btree-node-value bt) v)
          (btree-leaf)
          (btree-node (btree-node-value bt) (prune-at-v (btree-node-left bt) v) (prune-at-v (btree-node-right bt) v)))))


(define (well-formed-tree? bt)
  (if (btree-leaf? bt)
      #t
      (and (well-formed-tree? (btree-node-left bt))
           (well-formed-tree? (btree-node-right bt)))))

(define (fold-tree f acc bt)
  (if (btree-leaf? bt)
      acc
      (letrec ([left-res (fold-tree f acc (btree-node-left bt))]
               [curr-acc (f left-res (btree-node-value bt))]
               [right-res (fold-tree f curr-acc (btree-node-right bt))])
        right-res)))

(define (fold-tree-curr f)
  (lambda (acc)
    (lambda (bt)
      (letrec ([loop-helper (lambda (acc bt)
                      (if (btree-leaf? bt)
                          acc
                          (loop-helper (f (loop-helper acc
                                                       (btree-node-left bt))
                                          (btree-node-value bt))
                               (btree-node-right bt))))])
        (loop-helper acc bt)))))


(define (crazy-sum lst)
  (letrec ([helper (lambda (acc f lst)
                     (cond [(null? lst) acc]
                            [(number? (car lst)) (helper (f acc (car lst)) f (cdr lst))]
                            [(procedure? (car lst)) (helper acc (car lst) (cdr lst))]
                            [#t (helper acc f (cdr lst))]))])
    (helper 0 + lst)))


(define (either-fold f acc str)
  (cond [(list? str) (foldl f acc str)]
        [(or (btree-leaf? str)
             (btree-node? str))
         (fold-tree f acc str)]
        [#t (error "Not a list or a tree")]))


(define (flatten lst)
  (cond [(null? lst) null]
        [(list? (car lst)) (append (flatten (car lst)) (flatten (cdr lst)))]
        [#t (cons (car lst) (flatten (cdr lst)))]))


  
  
                                        



                
      
                   
  
