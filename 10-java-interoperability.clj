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
