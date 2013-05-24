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

