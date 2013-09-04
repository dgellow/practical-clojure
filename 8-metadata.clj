(ns practical-clojure.8-metadata)

;; Metadatas can be attached to any symbol or Clojure's built-in data structures

;; By default metadatas are note printed at the REPL.
(set! *print-meta* true)

;; with-meta function attaches a metadata to an object
(with-meta [1 2] {:about "a vector"}) ;; #^{:about "A vector"} [1 2]

;; vary-meta function apply a function to the metadata map of an object
(def x (with-meta [3 4] {:help "small vector"}))
(vary-meta x assoc :help "tiny vector") ;; => #^{:help "Tiny vector"} [3 4]

;; Two objects with the same value and different metadata are equal (same hash) ...
(def v [1 2 3 4])
(= v (with-meta v {:help "a vector"})) ;; => true

;; ... but not identical.
(identical? v (with-meta v {:help "a vector"})) ;; => false

;; meta function can be used to read metadatas attached to an object
(meta (var list)) ;; => {:ns #<Namespace clojure.core>,
                  ;;     :name list,
                  ;;     :arglists ^{:line 17,
                  ;;                 :column 15} ([& items]),
                  ;;     :column 1,
                  ;;     :added "1.0",
                  ;;     :doc "Creates a new list containing the items.",
                  ;;     :line 16,
                  ;;     :file "clojure/core.clj"}

(:added (meta (var list))) ;; => "1.0"
