(ns practical-clojure.6-state-management)

;; States are immutable values.
;; An identity is a link to each state which describes the thing.
;; Thus a _thing_ is a _complex_ formed by an identity and every state linked with it.

;; States are any Clojure's immutable data types.
;; Identities can be modeled using a reference type:
;; - refs manage synchronous, coordinated state
;; - agents manage asychronous, independent state
;; - atoms manage synchronous, independent state
;; - (vars ? Dunno, need some search)

;; Coordinated updates have to manage the states of several interdependent identities
;; to ensure that they are all updated at the same time and that none are left out.
;;
;; Independent identities stand on their own and an have their state updated without concern for other identities.

;; Sychronous updates to the values identities occur immediately, in the same thread from which they are invoked.
;; The execution of the code does not continue until the update has taken place.
;;
;; Asynchronous updates do not occur immediately, but at some unspecified point in the future, usually in another
;; thread. The code execution continues immediately from the point at which the update was invoked, without waiting
;; for it to complete.


;; Using refs
(def my-ref (ref 5))

(deref my-ref) ;; => 5

(+ 1 @my-ref) ;; => 6

;; refs can only be updated during a transaction.
(dosync (ref-set my-ref 2)) ;; => 2

;; The function passed to alter and commute MUST BE pure function.
;; This is because the function may be executed multiple times as the STM restries the transaction.
(dosync (alter my-ref + 3 10 100 1000)) ;; => 1115

;; The function passed to commute MUST BE a commutative function.
(dosync (commute my-ref * -1 10 100)) ;; => -1115000


;; listing 6-1. Bank accounts in STM
(def account1 (ref 1000))
(def account2 (ref 1500))

(defn transfer
  "transfer amount of money from a to b"
  [a b amount]
  (dosync
   (alter a - amount)
   (alter b + amount)))

(transfer account1 account2 300)
(transfer account2 account1 50)


;; listing 6-2. An adresse book in STM
(def my-contacts (ref []))

(defn add-contact
  "adds a contact to the provided contact list"
  [contacts contact]
  (dosync
   (alter contacts conj (ref contact))))

(defn print-contacts
  "prints a list of contacts"
  [contacts]
  (doseq [c @contacts]
    (println (str "Name:" (@c :lname) ", " (@c :fname)))))

(add-contact my-contacts {:fname "Luke" :lname "VanderHart"})
(add-contact my-contacts {:fname "Sam" :lname "Gamji"})
(add-contact my-contacts {:fname "John" :lname "Doe"})

(print-contacts my-contacts)


;; listing 6-3. Adding initials to the address book
 (defn add-initials
   "adds initials to a single contact and retuns it"
   [contact]
   (assoc contact :initials
     (str (first (contact :fname)) (first (contact :lname)))))

 (defn add-all-initials
   "adds initials to each of the contacts in a list of contacts"
   [contacts]
   (dosync
    (doseq [contact (ensure contacts)]
      (alter contact add-initials))))

 (defn print-contacts-and-initials
   "prints a list of contacts with their initials"
   [contacts]
   (doseq [c @contacts]
     (println "Name: " (@c :fname) ", " (@c :lname) " (" (@c :initials) ")")))

(add-all-initials my-contacts)
(print-contacts-and-initials my-contacts)


;; Using atoms
(def my-atom (atom 5))

(deref my-atom) ;; => 5

;; atom doesn't need a transaction to be updated
(swap! my-atom + 3) ;; => 8

(reset! my-atom 1) ;; => 1

