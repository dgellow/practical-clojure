(ns practical-clojure.9-multimethods)

;; multimethods provide a way to define a function with multiple implementations.
;; The choice of the implementation is based on the arguments to the function.

;; defmulti function declares a multimethod and are implemented with the defmethod
;; function.
(def a {:name "Arthur", :species ::human, :strength 8})
(def b {:name "Balfor", :species ::elf, :strength 7})
(def c {:name "Calis", :species ::elf, :strength 5})
(def d {:name "Drung", :species ::orc, :strength 6})

;; defmulti takes a function name and dispatch function
(defmulti move :species)

;; Choice of implementation is based on the value returned by the dispatch function.
;; Don't forget keywords are function.
(defmethod move ::elf [creature]
  (str (:name creature) " runs swiftly."))

(defmethod move ::human [creature]
  (str (:name creature) " walks steadily."))

(defmethod move ::orc [creature]
  (str (:name creature) " stomps heavily."))

(move a) ;; => "Arthur walks steadily."
(move c) ;; => "Calis runs swiftly."


(defmulti attack (fn [creature]
                   (if (> (:strength creature) 5)
                     :strong
                     :weak)))

(defmethod attack :strong [creature]
  (str (:name creature) " attacks mightily."))

(defmethod attack :weak [creature]
  (str (:name creature) " attacks feebly."))


;; multimethods support dispatch on multiple arguments
(defmulti encounter (fn [x y]
                      [(:species x) (:species y)]))
;; elf and orc are ennemies
(defmethod encounter [::elf ::orc] [elf orc]
  (str "Brave elf " (:name elf)
       " attackes evil orc " (:name orc)))
(defmethod encounter [::orc ::elf] [orc elf]
  (str "Evil orc " (:name orc)
       " attacks innocent elf " (:name elf)))
;; elves are happy to encounter another ones
(defmethod encounter [::elf ::elf] [e1 e2]
  (str "Two elves, " (:name e1)
       " and " (:name e2)
       ", greet each other."))

;; default implementation
(defmethod encounter :default [x y]
  (str (:name x) " and " (:name y)
       " ignore each other."))
;; We can instead specify an alternate default dispatch value by adding the :default
;; option to defmulti.
(defmulti talk :species :default :other)
(defmethod talk ::orc [creature]
  (str (:name creature) " grunts."))
(defmethod talk :other [creature]
  (str (:name creature) " speaks."))













