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


;; Hierarchies

;; derive function creates a relation child -> parent
;; isa? function test if a type is a child of another type
;;
;; factions
(defn ->good [species]
  (derive species ::good))

(defn good? [species]
  (isa? species ::good))

(defn ->evil [species]
  (derive species ::evil))

(defn evil? [species]
  (isa? species ::evil))

;; creature type
(defn ->magical [species]
  (derive species ::magical))

(defn magical? [species]
  (isa? species ::magical))

(defn ->hero [species]
  (derive species ::hero))

(defn hero? [species]
  (isa? species ::hero))


(map ->good [::human ::elf])
(map ->evil [::orc])
(map ->magical [::elf ::orc])
(map ->hero [::human])

;; Hierarchy querying
(def species [::human ::elf ::orc])
(map good? species) ;; => (true true false)
(map evil? species) ;; => (false false true)
(map magical? species) ;; => (false true true)
(map hero? species) ;; => (true false false)
;; parents function returns a set of immediate parents
(parents ::orc) ;; => #{:user/magical :user/evil}
(parents ::hero) ;; => #{:user/human}
;; ancestors function returns a set of any parental level
(ancestors ::hero) ;; => #{:user/good :user/human}
;; descendants function returns a set of any child level
(descendants ::good) ;; => #{:user/elf :user/hero :user/human}


;; Dispatching works well with derived types ...
(defmulti cast-spell :species)
(defmethod cast-spell ::magical [creature]
  (str (:name creature) " casts a spell."))
(defmethod cast-spell :default [creature]
  (str (:name creature) " tries to cast a spell but loosly fails."))

;; ... and java classes.
(defmulti invert class)
(defmethod invert Number [x]
  (- x))
(defmethod invert String [x]
  (apply str (reverse x)))

(invert 3.141) ;; => -3.141
(invert "hello") ;; => "olleh"

(parents java.util.Date) ;; => #{java.lang.Object java.lang.Cloneable
                         ;;      java.io.Serializable java.lang.Comparable}


;; Dispatching conflicts
(defmulti slay :species)
(defmethod slay ::good [creature]
  (str "Oh no ! A good creature was slain !"))
(defmethod slay ::magical [creature]
  (str "A magical creature was slain !"))

;; b is an ::elf, so is also a ::good creature
(slay b) ;; => java.lang.IllegalArgumentException:
         ;;    Multiple methods in multimethod 'slay' match
         ;;    dispatch value: :user/elf -> :user/magical
         ;;    and :user/good, and neither is preferred

;; prefer-method function values one implementation over other
(prefer-method slay ::good ::magical) ;; ::good is preferred over ::magical

;; remove-method function deletes an implementation
(remove-method slay ::magical)



