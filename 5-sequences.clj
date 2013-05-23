(ns practical-clojure.5-sequences)

;; sequences are a common programming interface that generalizes behavior common to all collections

;; exemple with first and rest function applied on different collection type
(first '(1 2 3))

(first [1 2 3])

(first #{1 2 3})

(first {:a 1 :b 2 :c 3})

(rest '(1 2 3))

(rest [1 2 3])


;; exemple of common behavior with different type
(defn side-effect-to-all
  [f coll]
  (loop [c coll]
   (if (not (empty? c))
    (do
      (f (first c))
      (recur (rest c))))))

(defn println-to-all
  [string coll]
  (println "——" string "——")
  (side-effect-to-all #(println  %) coll))

(comment
(println-to-all "List" '(1 2 3))
(println-to-all "Vector" [1 2 3])
(println-to-all "Set" #{1 2 3})
(println-to-all "Map" {:a 1 :b 2 :c 3})
(println-to-all "String" "123"))


;; cons can be use to __construct__ a sequence giving it a first/rest pair
(cons 4 '(1 2 3))
;; => (4 1 2 3)
;;     | |___|
;;     |   |
;;  first rest
(cons 1 [2 3 4])  ;; => (1 2 3 4)


;; conj is similar to cons but reuses the underlying implementation of the sequence
;; instead of always creating a cons cell (first/rest pair)
(conj '(1 2 3) 4) ;; => (4 1 2 3)

(conj [1 2 3] 4)  ;; => [1 2 3 4]


;; lazy-seq is a way to explicitly construct a lazy sequence
(defn lazy-counter
  [base increment]
  (lazy-seq
   (cons base (lazy-counter (+ base increment) increment))))

(take 10 (lazy-counter 0 2)) ;; => (0 2 4 6 8 10 12 14 16 18)
(nth (lazy-counter 0 2) 10000000) ;; => 20000000


;; iterate function as a sequence generator
(def integers (iterate inc 0))

(take 5 integers) ;; => (0 1 2 3 4)

(defn lazy-counter-iterate
  [base increment]
  (iterate (fn [n] (+ n increment)) base))

(nth (lazy-counter-iterate 2 3) 1000000) ;; => 3000002
