;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname ta-solver-starter) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #f)))
;; ta-solver-starter.rkt



;  PROBLEM 1:
;
;  Consider a social network similar to Twitter called Chirper. Each user has a name, a note about
;  whether or not they are a verified user, and follows some number of people.
;
;  Design a data definition for Chirper, including a template that is tail recursive and avoids
;  cycles.
;
;  Then design a function called most-followers which determines which user in a Chirper Network is
;  followed by the most people.
;

(define-struct user (name verified? followers))
;; User is (make-user String Boolean (listof User))
;; interp. a user on Chirper. Each user has a name, verified status and a list of other users that he/she is following

;; user A follows user B but not the other way around
(define U1 (make-user "A" false (list (make-user "B" false empty))))

;; user A follows user B and B follows a
(define U2 (shared ((-A- (make-user "A" false (list -B-)))
                    (-B- (make-user "B" false (list -A-))))
             -A-))

;; user A follows user B and B follows C, C follows A
(define U3 (shared ((-A- (make-user "A" false (list -B-)))
                    (-B- (make-user "B" false (list -C-)))
                    (-C- (make-user "C" false (list -A-))))
             -A-))


(define U4 (shared ((-A- (make-user "A" false (list -B- -D- -E-)))
                    (-B- (make-user "B" false (list -D- -F- -A-)))
                    (-C- (make-user "C" true (list -E- -B- -A-)))
                    (-D- (make-user "D" true (list -F-)))
                    (-E- (make-user "E" false (list -F- -C-)))
                    (-F- (make-user "F" true (list -A- -B- -D- -C-))))
             -A-))


(define U5 (shared ((-A- (make-user "A" false (list -B- -D- -E-)))
                    (-B- (make-user "B" false (list -D- -F- -A-)))
                    (-C- (make-user "C" true (list -E- -B- -A- -F-)))
                    (-D- (make-user "D" true (list -F-)))
                    (-E- (make-user "E" false (list -F- -C-)))
                    (-F- (make-user "F" true (list -A- -B- -D-))))
             -A-))

;; arb arity tree, tail rec w/ worklist acc, context-preserving acc
#;
(define (fn-for-user u0)
  (local [(define (fn-for-user u todo visited)
            (if (member u visited)
                (fn-for-lou todo visited)
                (fn-for-lou (append (user-followers u) todo) (cons u visited))))

          (define (fn-for-lou todo visited)
            (cond [(empty? todo) (...)]
                  [else
                   (fn-for-user (first todo) (rest todo) visited)]))]
    ;(fn-for-lou (rest todo) visited))]))]
    (fn-for-user u0 empty empty)))


;; FUNCTIONS
;; User -> User
;; takes in an user and returns the user with the most followers

(check-expect (most-followers U1) (make-user "B" false empty))
(check-expect (most-followers U2) (shared ((-A- (make-user "A" false (list -B-)))
                                           (-B- (make-user "B" false (list -A-))))
                                    -B-))
(check-expect (most-followers U3) (shared ((-A- (make-user "A" false (list -B-)))
                                           (-B- (make-user "B" false (list -C-)))
                                           (-C- (make-user "C" false (list -A-))))
                                    -B-))


(check-expect (most-followers U4) (shared ((-A- (make-user "A" false (list -B- -D- -E-)))
                                           (-B- (make-user "B" false (list -D- -F- -A-)))
                                           (-C- (make-user "C" true (list -E- -B- -A-)))
                                           (-D- (make-user "D" true (list -F-)))
                                           (-E- (make-user "E" false (list -F- -C-)))
                                           (-F- (make-user "F" true (list -A- -B- -D- -C-))))
                                    -D-))

(check-expect (most-followers U5) (shared ((-A- (make-user "A" false (list -B- -D- -E-)))
                                           (-B- (make-user "B" false (list -D- -F- -A-)))
                                           (-C- (make-user "C" true (list -E- -B- -A- -F-)))
                                           (-D- (make-user "D" true (list -F-)))
                                           (-E- (make-user "E" false (list -F- -C-)))
                                           (-F- (make-user "F" true (list -A- -B- -D-))))
                                    -F-))


(define (most-followers u0)
  ;; todo is (listof Room); a worklist accumulator
  ;; visited is (listof String); context preserving accumulator, names of rooms already visited
  (local [
          (define-struct entries (user num))
          (define (max-el lst max)
            (cond [(empty? lst) (entries-user max)]
                  [else
                   (if (> (entries-num (first lst)) (entries-num max))
                       (max-el (rest lst) (first lst))
                       (max-el (rest lst) max))]))

          (define (update-list lst k)
            (cond [(empty? lst) empty]
                  [else
                   (if (string=? (user-name (entries-user k)) (user-name (entries-user (first lst))))
                       (cons (make-entries (entries-user (first lst)) (add1 (entries-num (first lst)))) (update-list (rest lst) k))
                       (cons (first lst) (update-list (rest lst) k)))]))
                       
            
          (define (count-el lst rst)             ;; (listof Entries) -> Room
            (cond [(empty? lst) (max-el (rest rst) (first rst))]
                  [else
                   (if (member (first lst) rst)
                       (count-el (rest lst) (update-list rst (first lst)))
                       (count-el (rest lst) (cons (first lst) rst)))]))
                             
                   
          (define (fn-for-room r todo visited rst) 
            (if (member (user-name r) visited)
                (fn-for-lor todo visited rst)
                (fn-for-lor (append (user-followers r) todo)
                            (cons (user-name r) visited)
                            (append (map (lambda (el)
                                           (make-entries el 1)) (user-followers r)) rst)))) ; (... (room-name r))
          
          (define (fn-for-lor todo visited rst)
            (cond [(empty? todo) (count-el rst empty)]
                  [else
                   (fn-for-room (first todo) 
                                (rest todo)
                                visited
                                rst)]))]
    (fn-for-room u0 empty empty empty))) 

;  PROBLEM 2:
;
;  In UBC's version of How to Code, there are often more than 800 students taking
;  the course in any given semester, meaning there are often over 40 Teaching Assistants.
;
;  Designing a schedule for them by hand is hard work - luckily we've learned enough now to write
;  a program to do it for us!
;
;  Below are some data definitions for a simplified version of a TA schedule. There are some
;  number of slots that must be filled, each represented by a natural number. Each TA is
;  available for some of these slots, and has a maximum number of shifts they can work.
;
;  Design a search program that consumes a list of TAs and a list of Slots, and produces one
;  valid schedule where each Slot is assigned to a TA, and no TA is working more than their
;  maximum shifts. If no such schedules exist, produce false.
;
;  You should supplement the given check-expects and remember to follow the recipe!



;; Slot is Natural
;; interp. each TA slot has a number, is the same length, and none overlap

(define-struct ta (name max avail))
;; TA is (make-ta String Natural (listof Slot))
;; interp. the TA's name, number of slots they can work, and slots they're available for

(define SOBA (make-ta "Soba" 2 (list 1 3)))
(define UDON (make-ta "Udon" 1 (list 3 4)))
(define RAMEN (make-ta "Ramen" 1 (list 2)))

(define NOODLE-TAs (list SOBA UDON RAMEN))



(define-struct assignment (ta slot))
;; Assignment is (make-assignment TA Slot)
;; interp. the TA is assigned to work the slot

;; Schedule is (listof Assignment)


;; ============================= FUNCTIONS


;; (listof TA) (listof Slot) -> Schedule or false
;; produce valid schedule given TAs and Slots; false if impossible

(check-expect (schedule-tas empty empty) empty)
(check-expect (schedule-tas empty (list 1 2)) false)
(check-expect (schedule-tas (list SOBA) empty) empty)

(check-expect (schedule-tas (list SOBA) (list 1)) (list (make-assignment SOBA 1)))
(check-expect (schedule-tas (list SOBA) (list 2)) false)
(check-expect (schedule-tas (list SOBA) (list 1 3)) (list (make-assignment SOBA 3)
                                                          (make-assignment SOBA 1)))

(check-expect (schedule-tas NOODLE-TAs (list 1 2 3 4))
              (list
               (make-assignment UDON 4)
               (make-assignment SOBA 3)
               (make-assignment RAMEN 2)
               (make-assignment SOBA 1)))

(check-expect (schedule-tas NOODLE-TAs (list 1 2))
              (list
               (make-assignment RAMEN 2)
               (make-assignment SOBA 1)))

(check-expect (schedule-tas NOODLE-TAs (list 1 2 3 4 5)) false)



;;(define (schedule-tas tas slots) empty) ;stub

(define (schedule-tas lotas los)
  (local [
          ;; Schedule -> Boolean
          ;; check if the given schedule exceeds the max shifts for a TA, true if not exceeding, false otherwise

          (define (not-ta-max-exceeded? sch)
            (local [(define (entries-in-schedule ta)
                      (length (filter (lambda (el)
                                        (string=? (ta-name ta)
                                                  (ta-name (assignment-ta el)))) sch)))]
              (andmap (lambda (ta)
                        (>= (ta-max ta) (entries-in-schedule ta))) lotas)))
                 
          ;; (listof Schedule) -> (listof Schedule)
          ;; filter out the invalid schedules
          
          (define (keep-only-valid losch)
            (filter not-ta-max-exceeded? losch))


          ;; (listof TA) Slot -> Schedule
          ;; get possible schedule for the given spot

          (define (get-assignments-for-slot lotas s)
            (map (lambda (el)
                   (make-assignment el s)) (filter (lambda (el)
                                                     (member s (ta-avail el)))
                                                   lotas)))

          ;; Schedule -> (listof Schedule)
          ;; given the existing schedule, generate the next possible combinations
          ;; assume the given schedule is correct up until that point
          
          (define (generate-new-schedules sch slot)
            (map (lambda (el)
                   (cons el sch))        
                 (get-assignments-for-slot lotas slot)))
          
          ;; Schedule -> (listof Schedule)
          ;; generates the next possible schedules based on the current schedule
          ;; assume the given schedule is already valid
          
          (define (next-schedules sch slots)
            (keep-only-valid (generate-new-schedules sch (first slots))))


          ;; Schedule (listof Slot) -> (listof Slot)
          ;; check which slots are occupied and return the rest available slots
          
          (define (available-slots sch los)
            (filter (lambda (el)
                      (not (member el (map assignment-slot sch)))) los))
          

          ;; Schedule -> Boolean
          ;; takes in a schedule and checks if it is valid
          ;; assume that the correct TA's will be assigned to the correct timeframes and no TA will exceed their maximum possible windows

          (define (solved? sch)
            (= (length sch) (length los)))

          ;; Schedule -> Schedule | False
          ;; search for a schedule that fits the given TA's and slots and return it, or False if it is not possible

          (define (fn-for-sch sch)       
            (cond [(solved? sch) sch]
                  [else
                   (fn-for-losch (next-schedules sch (available-slots sch los)))]))
          
          (define (fn-for-losch losch)
            (cond [(empty? losch) false]
                  [else
                   (local [(define try (fn-for-sch (first losch)))]         ;try first child
                     (if (not (false? try))                             ;successful?
                         try                                            ;if so produce that
                         (fn-for-losch (rest losch))))]))]                  ;or try rest of children

    (fn-for-sch empty)))







