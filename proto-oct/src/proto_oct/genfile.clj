(ns proto-oct.genfile
  (:use [proto-oct.core]))

;; dev helper
(defn- r []
  (load-file "proto_oct/core.clj")
  (load-file "proto_oct/genfile.clj"))

;; dev helper
(defn- q []
  (System/exit 0))

(defn genfile []
  (apply str (map emit-code
                  [(include "stdio.h" true)
                   ])))
