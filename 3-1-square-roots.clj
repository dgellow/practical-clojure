(ns practical-clojure.3-1-square-roots)

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
  "Returns the square root of the supplied number"
  ([number] (sqrt number 1.0))
  ([number guess]
   (if (good-enough? number guess)
     guess
     (sqrt number (avg guess (/ number guess))))))