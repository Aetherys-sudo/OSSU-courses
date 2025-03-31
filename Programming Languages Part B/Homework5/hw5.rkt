;; Programming Languages, Homework 5

#lang racket
(provide (all-defined-out)) ;; so we can put tests in a second file

;; definition of structures for MUPL programs - Do NOT change
(struct var  (string) #:transparent)  ;; a variable, e.g., (var "foo")
(struct int  (num)    #:transparent)  ;; a constant number, e.g., (int 17)
(struct add  (e1 e2)  #:transparent)  ;; add two expressions
(struct ifgreater (e1 e2 e3 e4)    #:transparent) ;; if e1 > e2 then e3 else e4
(struct fun  (nameopt formal body) #:transparent) ;; a recursive(?) 1-argument function
(struct call (funexp actual)       #:transparent) ;; function call
(struct mlet (var e body) #:transparent) ;; a local binding (let var = e in body) 
(struct apair (e1 e2)     #:transparent) ;; make a new pair
(struct fst  (e)    #:transparent) ;; get first part of a pair
(struct snd  (e)    #:transparent) ;; get second part of a pair
(struct aunit ()    #:transparent) ;; unit value -- good for ending a list
(struct isaunit (e) #:transparent) ;; evaluate to 1 if e is unit else 0

;; a closure is not in "source" programs but /is/ a MUPL value; it is what functions evaluate to
(struct closure (env fun) #:transparent) 

;; Problem 1
(define (racketlist->mupllist lst)
  (cond [(null? lst) (aunit)]
        [(list? (car lst)) (apair (racketlist->mupllist (car lst)) (racketlist->mupllist (cdr lst)))]
        [#t (apair (car lst) (racketlist->mupllist (cdr lst)))]))    

;; Problem 2

(define (mupllist->racketlist lst)
  (cond [(aunit? lst) null]
        [(apair? lst)
         (if (apair? (apair-e1 lst))
             (cons (mupllist->racketlist (apair-e1 lst)) (mupllist->racketlist (apair-e2 lst)))
             (cons (apair-e1 lst) (mupllist->racketlist (apair-e2 lst))))]))

;; lookup a variable in an environment
;; Do NOT change this function
(define (envlookup env str)
  (cond [(null? env) (error "unbound variable during evaluation" str)]
        [(equal? (car (car env)) str) (cdr (car env))]
        [#t (envlookup (cdr env) str)]))

;; Do NOT change the two cases given to you.  
;; DO add more cases for other kinds of MUPL expressions.
;; We will test eval-under-env by calling it directly even though
;; "in real life" it would be a helper function of eval-exp.
(define (eval-under-env e env)
  (cond [(int? e) e]
        
        [(aunit? e) e]
        
        [(closure? e) e]

        [(var? e) 
         (envlookup env (var-string e))]
        
        [(isaunit? e) (if (aunit? (eval-under-env (isaunit-e e) env)) (int 1) (int 0))]
        
        [(add? e) 
         (let ([v1 (eval-under-env (add-e1 e) env)]
               [v2 (eval-under-env (add-e2 e) env)])
           (if (and (int? v1)
                    (int? v2))
               (int (+ (int-num v1) 
                       (int-num v2)))
               (error "MUPL addition applied to non-number")))]
        
        [(ifgreater? e)
         (let ([v1 (eval-under-env (ifgreater-e1 e) env)]
               [v2 (eval-under-env (ifgreater-e2 e) env)])
           (if (and (int? v1)
                    (int? v2))
               (if (> (int-num v1)
                      (int-num v2))
                   (eval-under-env (ifgreater-e3 e) env)
                   (eval-under-env (ifgreater-e4 e) env))
               (error "MUPL ifgreater applied to non-number")))]
        
        [(fun? e) (closure env e)]
        
        [(call? e) (let ([func (eval-under-env (call-funexp e) env)]
                         [param (eval-under-env (call-actual e) env)])
                     (if (closure? func)
                         (if (false? (fun-nameopt (closure-fun func)))
                             (eval-under-env (fun-body (closure-fun func))
                                             (cons (cons (fun-formal (closure-fun func)) param)
                                                   (closure-env func)))
                             (eval-under-env (fun-body (closure-fun func))
                                             (cons (cons (fun-formal (closure-fun func)) param) (cons (cons (fun-nameopt (closure-fun func)) func) (closure-env func)))))
                         (error "first argument of call is not a closure")))]
        
        [(mlet? e) (eval-under-env (mlet-body e) (cons (cons (mlet-var e) (eval-under-env (mlet-e e) env)) env))]
        
        [(apair? e) (if (and
                         (or (int? (apair-e1 e))
                             (closure? (apair-e1 e))
                             (aunit? (apair-e1 e)))
                         (or (int? (apair-e2 e))
                             (closure? (apair-e2 e))
                             (aunit? (apair-e2 e))))
                        e      
                        (apair (eval-under-env (apair-e1 e) env) (eval-under-env (apair-e2 e) env)))]
        
        [(fst? e) (let ([pr (eval-under-env (fst-e e) env)])
                    (if (apair? pr) (apair-e1 pr) (error "fst must be applied to a pair")))]
        
        [(snd? e) (let ([pr (eval-under-env (snd-e e) env)])
                    (if (apair? pr) (apair-e2 pr) (error "snd must be applied to a pair")))]

        [#t (error (format "bad MUPL expression: ~v" e))]))


;; Do NOT change
(define (eval-exp e)
  (eval-under-env e null))
        
;; Problem 3
(define (ifaunit e1 e2 e3) (ifgreater (isaunit e1) (int 0)
                                      (mlet "e2" e2
                                            (var "e2"))
                                      (mlet "e3" e3
                                            (var "e3"))))

(define (mlet* lstlst e2)
  (letrec ([build-mlet (lambda (lst)
                         (if (null? lst)
                             (mlet "e2" e2
                                   (var "e2"))
                             (mlet (car (car lst)) (cdr (car lst)) (build-mlet (cdr lst)))))])
    (build-mlet lstlst)))
                             

(define (ifeq e1 e2 e3 e4)
  (mlet "e1" e1
        (mlet "e2" e2
              (ifgreater (var "e1")
                         (var "e2")
                         (mlet "e4" e4
                               (var "e4"))
                         (ifgreater (var "e2")
                                    (var "e1")
                                    (mlet "e4" e4
                                          (var "e4"))
                                    (mlet "e3" e3
                                          (var "e3")))))))
              
                             
;; Problem 4

(define mupl-map 
   (fun "mupl-map" "fn"
        (fun "list-parse" "lst"
             (ifaunit (var "lst")
                 (aunit)
                 (apair (call (var "fn") (fst (var "lst"))) (call (var "list-parse") (snd (var "lst"))))))))


(define mupl-mapAddN 
  (mlet "map" mupl-map
        (fun "map-add-n" "n"
             (call (var "map") (fun #f "x"
                                    (add (var "x") (var "n")))))))


;; Challenge Problem

(struct fun-challenge (nameopt formal body freevars) #:transparent) ;; a recursive(?) 1-argument function

;; We will test this function directly, so it must do
;; as described in the assignment
(define (compute-free-vars e) "CHANGE")

;; Do NOT share code with eval-under-env because that will make
;; auto-grading and peer assessment more difficult, so
;; copy most of your interpreter here and make minor changes
(define (eval-under-env-c e env) "CHANGE")


;; Do NOT change this
(define (eval-exp-c e)
  (eval-under-env-c (compute-free-vars e) null))
