(ns practical-clojure.3-controlling-program-flow)

;; listing 3-1. calculating square roots
(defn abs
  "Calculates the absolute value of a number"
  [n]
  (if (< n 0)
    (* -1 n)
    n))

(defn avg
  "Returns the average of two arguments"
  [a b]
  (/ (+ a b) 2))

(defn good-enough?
  "Tests if a guess is close enought to the real square root"
  [number guess]
  (let [diff (- (* guess guess) number)]
    (if (< (abs diff) 0.001)
      true
      false)))

(defn sqrt
  "Returns the square root of the supplied number using the famous Newton's algorithm"
  ([number] (sqrt number 1.0))
  ([number guess]
   (if (good-enough? number guess)
     guess
     (recur number (avg guess (/ number guess))))))


;; listing 3-2. calculating exponent
(defn power
  "Calculate a number power of a provided exponent"
  [number exponent]
  (if (zero? exponent)
    1
    (* number (power number (- exponent 1)))))


;; listing 3-4. adding up numbers correctly with tail-recursion
(defn add-up
  "Adds all the numbers below a given limit"
  ([limit] (add-up limit 0 0))
  ([limit current acc]
   (if (< limit current)
     acc
     (recur (+ current 1) (+ acc current)))))


;; exemple of recursive function using loop-recur pattern
(defn loop-sqrt
  "Returns the square root of the supplied number, using loop-recur"
  [number]
  (loop [guess 1.0]
    (if (good-enough? number guess)
      guess
      (recur number (avg guess (/number guess))))))


;; exemple of side effects in function definitions
(defn square
  "Square a number, with side effects"
  [x]
  (println "squaring" x)
  (println "the return will be" (* x x))
  (* x x))

