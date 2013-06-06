(ns practical-clojure.10-java-interoperability)

;; There are three ways to interact with java classes
;; - new, return a new instance of the class
(new String) ;; => ""
(new String "Hey man, look at me") ;; => "Hey man, look at me"
(new java.util.Date) ;; => #inst "2013-06-05T22:02:33.634-00:00"
(new java.util.Date 55 10 12) ;; #inst "1955-11-11T23:00:00.000-00:00"

;; - . (dot), a special form to call method and field
(. Integer valueOf "42") ;; => 42
(. Integer MAX_VALUE) ;; => 2147483647
(. "I'm on the radio" substring 4 10) ;; => "on the"

;; - set!, a special form to set the value of a public field
;; (set! (. target name) value)

;; Method calls can be made in a more clojure friendly syntax
(.toUpperCase "banana banana banana terracotta") ;; => "BANANA BANANA BANANA TERRACOTTA"

;; Another way to create an object, by placing a dot after class name
(java.util.Date. 110 3 12) ;; => #inst "2010-04-11T22:00:00.000-00:00"
(StringBuilder. "Hello") ;; => #<StringBuilder Hello>

;; Static method can be call using class name/method name
(Integer/parseInt "101") ;; => 101
(Integer/MIN_VALUE) ;; => -2147483648

;; Java arrays can be created with make-array function
(make-array Double/TYPE 40) ;; create a double[40] array
(make-array Double/TYPE 40 10) ;; create a double[40][10] 2d array

;; There are functions to easily create primitive type arrays, such as int-array,
;; long-array, double-array and float-array.
;;
;; to-array function converts a clojure collection type to an Object[] array
;; to-array-2d does the same with a 2d collection
(def matrix [[1 0 0][0 1 0][0 0 1]])
(to-array-2d matrix) ;; => #<Object[][] [[Ljava.lang.Object;@7e0892c5>

;; into-array function helps to get an array of the same type as the first item
(into-array '(1 2 3 4)) ;; => #<Long[] [Ljava.lang.Long;@70eb4f20>
(into-array Integer/TYPE '(1 2 3 4)) ;; => #<int[] [I@2388c8b6>

;; aget function returns a value from an array
(aget (into-array '(1 2 3 4)) 3) ;; => 4

;; aset function sets an element of an Object[] array
(def a (int-array 4))
(aset a 2 10) ;; => 10
(map identity a) ;; => (0 0 10 0)

;; To iterate over java arrays there exists a macro for each map and reduce functions
(def an-array (int-array 10))
(map identity
     (amap an-array index ret (+ (int 1) (aget an-array index)))) ;; => (1 1 1 1 1 ...)