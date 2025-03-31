#lang racket
;; Programming Languages Homework 5 Simple Test
;; Save this file to the same directory as your homework file
;; These are basic tests. Passing these tests does not guarantee that your code will pass the actual homework grader

;; Be sure to put your homework file in the same folder as this test file.
;; Uncomment the line below and, if necessary, change the filename
(require "hw5.rkt")

(require rackunit)

(define tests
  (test-suite
   "Sample tests for Assignment 5"
   
   ;; check racketlist to mupllist with normal list
   (check-equal? (racketlist->mupllist (list (int 3) (int 4))) (apair (int 3) (apair (int 4) (aunit))) "racketlist->mupllist test")

   (check-equal? (racketlist->mupllist (list (int 3) (cons (int 4)
                                                           (cons (int 5) null)))) (apair
                                                                                   (int 3)
                                                                                   (apair
                                                                                    (apair
                                                                                     (int 4)
                                                                                     (apair (int 5) (aunit)))
                                                                                    (aunit))) "racketlist->mupllist test")

   (check-equal? (racketlist->mupllist (list (int 3) (list (int 4) (int 5)) (list "hi" "ay" 1))) (apair (int 3)
                                                                                                        (apair (apair (int 4)
                                                                                                                      (apair (int 5) (aunit)))
                                                                                                               (apair (apair "hi" (apair "ay" (apair 1 (aunit))))
                                                                                                                      (aunit)))) "racketlist->mupllist test")

   (check-equal? (racketlist->mupllist (list (int 3) (list (int 4) (int 5)))) (apair (int 3) (apair (apair (int 4) (apair (int 5) (aunit))) (aunit))) "racketlist->mupllist test")
   
   ;   ;; check mupllist to racketlist with normal list
   (check-equal? (mupllist->racketlist (apair (int 3) (apair (int 4) (aunit)))) (list (int 3) (int 4)) "racketlist->mupllist test")

   (check-equal? (mupllist->racketlist (apair (int 3)
                                              (apair (apair (int 4)
                                                            (apair (int 5) (aunit)))
                                                     (apair (apair "hi" (apair "ay" (apair 1 (aunit))))
                                                            (aunit))))) (list (int 3) (list (int 4) (int 5)) (list "hi" "ay" 1)) "racketlist->mupllist test")

   (check-equal? (mupllist->racketlist (apair (int 3) (apair (apair (int 4) (apair (int 5) (aunit))) (aunit)))) (list (int 3) (list (int 4) (int 5))) "racketlist->mupllist test")

   (check-equal? (mupllist->racketlist (apair
                                        (int 3)
                                        (apair
                                         (apair
                                          (int 4)
                                          (apair (int 5) (aunit)))
                                         (aunit))) ) (list (int 3) (cons (int 4)
                                                                         (cons (int 5) null))) "racketlist->mupllist test")

   ;;;;;;;;;;;;;;;;;;;;;;;;


   ;; var case
   (check-equal? (eval-under-env (var "x") (list (cons "x" (int 2)))) (int 2) "var test")

   (check-equal? (eval-under-env (var "x") (list (cons "xy" (int 2)) (cons "x" (int 2)) (cons "x" (int 5)))) (int 2) "var test")

   
   ;; int case
   (check-equal? (eval-under-env (int 15) (list (cons "x" (int 2)))) (int 15) "var test")

   (check-equal? (eval-under-env (int 15) null) (int 15) "var test")

   (check-equal? (eval-exp (int 15)) (int 15) "var test")

   (check-equal? (eval-exp (int 15)) (int 15) "var test")


   ;; aunit test
   (check-equal? (eval-under-env (aunit) (list (cons "x" (int 2)))) (aunit) "var test")

   (check-equal? (eval-exp (aunit)) (aunit) "var test")


   ;; isaunit test
   (check-equal? (eval-under-env (isaunit (aunit)) (list (cons "x" (int 2)))) (int 1) "var test")

   (check-equal? (eval-exp (isaunit (int 15))) (int 0) "var test")
   
   (check-equal? (eval-exp (isaunit (closure '() (fun #f "x" (aunit))))) (int 0) "isaunit test")
   
   
   ;; ifgreater case
   (check-equal? (eval-exp (ifgreater (int 3) (int 4) (int 3) (int 2))) (int 2) "ifgreater test")
   
   (check-equal? (eval-exp (ifgreater (mlet "x" (int 14) (add (var "x") (int 3))) (int 4) (int 17) (int 2))) (int 17) "ifgreater test")

   (check-equal? (eval-exp (ifgreater (mlet "x" (int 14) (add (var "x") (int 3))) (int 55) (int 17) (int 2))) (int 2) "ifgreater test")

   (check-equal? (eval-under-env (ifgreater (mlet "x" (var "y") (add (var "x") (int 3))) (int 3) (int 18) (int 2)) (list (cons "y" (int 15)))) (int 18) "ifgreater test")

   (check-equal? (eval-under-env (ifgreater (mlet "x" (var "y") (add (var "x") (int 3))) (int 55) (int 17) (int 2)) (list (cons "y" (int 15)) (cons "x" (int 65)))) (int 2) "ifgreater test")

   (check-equal? (eval-under-env (ifgreater (mlet "x" (var "x") (add (var "x") (int 3))) (int 55) (int 17) (int 2)) (list (cons "yy" (int 15)) (cons "x" (int 65)))) (int 17) "ifgreater test")
   
  
   ;; mlet test
   (check-equal? (eval-exp (mlet "x" (int 1) (add (int 5) (var "x")))) (int 6) "mlet test")

   (check-equal? (eval-exp (mlet "x" (int 1) (add (int 5) (var "x")))) (int 6) "mlet test")

   (check-equal? (eval-exp (mlet "x" (int 1) (add (int 5) (var "x")))) (int 6) "mlet test")

   (check-equal? (eval-exp (mlet "x" (int 1) (add (int 5) (var "x")))) (int 6) "mlet test")


   ;; apair test
   (check-equal? (eval-exp (apair (apair (int 1) (int 2)) (mlet "x" (int 15) (add (var "x") (int 15))))) (apair (apair (int 1) (int 2)) (int 30)) "fst test")

   
   ;; fst test
   (check-equal? (eval-exp (fst (apair (int 1) (int 2)))) (int 1) "fst test")

   (check-equal? (eval-exp (fst (apair (ifgreater (int 3) (int 4) (int 3) (int 2)) (int 2)))) (int 2) "fst test")

   (check-equal? (eval-exp (fst (fst (apair (apair (int 5) (int 6)) (int 3))))) (int 5) "fst test")

      
   ;;snd test
   (check-equal? (eval-exp (snd (apair (int 1) (int 2)))) (int 2) "snd test")

   (check-equal? (eval-exp (snd (apair (int 1) (ifgreater (int 3) (int 4) (int 3) (int 44))))) (int 44) "snd test")

   (check-equal? (eval-exp (snd (fst (apair (apair (int 5) (int 6)) (int 3))))) (int 6) "fst test")


   ;; fun test
   (check-equal? (eval-exp (fun #f "x" (add (var "x") (int 15)))) (closure null (fun #f "x" (add (var "x") (int 15)))) "snd test")
   (check-equal? (eval-exp (fun "rec" "x" (ifgreater (var "x") (int 5) (int 10) (int 20)))) (closure null (fun "rec" "x" (ifgreater (var "x") (int 5) (int 10) (int 20)))) "snd test")
   (check-equal? (eval-under-env (fun #f "x" (add (var "x") (int 15))) (list (cons "y" (int 15)))) (closure (list (cons "y" (int 15))) (fun #f "x" (add (var "x") (int 15))))  "snd test")
   (check-equal? (eval-under-env (fun "rec" "x" (ifgreater (var "x") (int 5) (int 10) (int 20))) (list (cons "y" (int 15)) (cons "x" (int 65)))) (closure (list (cons "y" (int 15)) (cons "x" (int 65))) (fun "rec" "x" (ifgreater (var "x") (int 5) (int 10) (int 20))))  "snd test")
   
   
   ;; call test
   (check-equal? (eval-exp (call (closure '() (fun #f "x" (add (var "x") (int 7)))) (int 1))) (int 8) "call test")

   
   (check-equal? (eval-exp (call
                            (fun "rec" "x" (ifgreater (var "x") (int 0)
                                                      (add (int 1)
                                                           (call (var "rec")
                                                                 (add (var "x") (int -1))))
                                                      (int 0))) (int 5))) (int 5) "call test")
   
   ;; ifaunit test
   (check-equal? (eval-exp (ifaunit (int 1) (int 2) (int 3))) (int 3) "ifaunit test")

   (check-equal? (eval-exp (ifaunit (aunit) (add (int 1) (int 5)) (int 3))) (int 6) "ifaunit test")

   ;; mlet* test
   (check-equal? (eval-exp (mlet* (list (cons "x" (int 10))) (var "x"))) (int 10) "mlet* test")

   (check-equal? (eval-exp (mlet* (list (cons "x" (int 10)) (cons "y" (add (var "x") (int 10))) (cons "z" (add (var "x") (var "y")))) (var "z"))) (int 30) "mlet* test")

   
   ;; ifeq test
   (check-equal? (eval-exp (ifeq (int 1) (int 2) (int 3) (int 4))) (int 4) "ifeq test")

   (check-equal? (eval-exp (ifeq (add (int 1) (int 1)) (int 2) (int 3) (int 4))) (int 3) "ifeq test")
   

   ;; mupl-map test
   (check-equal? (eval-exp (call (call mupl-map (fun #f "x" (add (var "x") (int 7)))) (apair (int 1) (aunit)))) 
                 (apair (int 8) (aunit)) "mupl-map test")
   
   (check-equal? (eval-exp (call (call mupl-map (fun #f "x" (add (var "x") (int 7)))) (aunit))) 
                 (aunit) "mupl-map test")
      
      
   ;; problems 1, 2, and 4 combined test
   (check-equal? (mupllist->racketlist
                  (eval-exp (call (call mupl-mapAddN (int 7))
                                  (racketlist->mupllist 
                                   (list (int 3) (int 4) (int 9)))))) (list (int 10) (int 11) (int 16)) "combined test")
      
   ))

(require rackunit/text-ui)
;; runs the test
(run-tests tests)
