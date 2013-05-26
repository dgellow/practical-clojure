(ns practical-clojure.7-namespaces-libraries)


;; REPL
(in-ns 'my-new-namespace)
;; refer maps all public symbols from a namespace into the current one.
;; Options are :exclude, :only, :rename
(refer 'clojure.core :exclude '(map set))
(refer 'clojure.string :rename {'lower-case 'lower})

;; alias is an alternative to copying the mappings from one namespace.
(:alias 'set 'clojure.set)
(set/union #{1 3 5} #{2 3 4}) ;; => #{1 2 3 4 5}

;; load-file read and evaluate every clojure form in a file
(load-file "path/to/file.clj") ;; => unix-like
(load-file "C:\\Documents\\file.clj") ;; => windows

;; load a resource on the classpath
(load "/my-project/a-library-file")


;; SOURCE FILE
;; clojure provides require and use function to load ressources from namespaces
;;
;; require can be called in different ways
(require 'clojure.string)
(require '[clojure.string :as string])
(require '(clojure.tools cli macro))
(require '(clojure zip [set :as s]))
;; :verbose flag can be used to show debugging information about lower level
;; function calls.
(require '(clojure pprint [template :as t]) :verbose)
;; => (clojure.core/in-ns 'user)
;;    (clojure.core/alias 't 'clojure.template)
;;     nil

;; :reload flag causes require to load all namespaces even if they have already
;; been loaded.
;;
;; :reload-all flag works the same way but will also load all dependent namespaces
;; required by loaded ones.

;; use function provides a way to require a namespace and refer some of its symbols.
;; It supports :reload, :reload-all, :verbose flags from require and :exclude, :only,
;; :rename flags of refer.
(use '[clojure.string :only (escape reverse replace)] :reload-all :verbose)


;; java classes can be imported
(import java.util.Date)
(new Date)


;; A complete exemple
(ns practical-clojure.a-new-namespace
  (:require [clojure.contrib.sql :as sql])
  (:use [clojure.string :only (reverse lower-case)])
  (:import (java.util Date Calendar)
           (java.io File FileInputStream)))


;; Using namespaces metadata
(ns #^{:doc "This is an email management library"
       :author "Mr. Knuux <knu@ux.io>"}
  mechoui.mail)


;; Using private definition
;; By default symbols are publicly accessible
(defn- *my-private-println* [& more] (comment "..."))
(def #^{:private true} *my-private-value* 123)


;; Operations on namespaces
;;
;; *ns* is bound to the current namespace.
;; all-ns function returns a sequence of namespaces currently defined.
;; find-ns function takes a symbol and returns the namespace with that name and return
;; nil if it doesn't exist.
(find-ns 'clojure.core) ;; => #<Namespace clojure.core>

;; the-ns function takes a namespace object or a symbol and throws an exception if it
;; doesn't exist.
;; ns-name function returns the name of a namespace as a symbol.
;; ns-aliases function returns a map representing all aliases defined in a namespace.
(ns-aliases 'clojure.core) ;; => {jio #<Namespace clojure.java.io>}

;; ns-public function returns a map of all public vars.
;; ns-interns function returns a map of all vars.
;; ns-refers returns a map of all symbols referred from other namespaces.
;; ns-imports returns a map of all imported java classes.
;; ns-resolve function takes a namespace and a symbol and returns the var or class to
;; which the symbol is mapped.
(ns-resolve 'clojure.core 'BigDecimal) ;; => java.math.BigDecimal



