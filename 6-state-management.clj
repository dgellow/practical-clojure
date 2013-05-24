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

