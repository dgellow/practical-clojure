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

