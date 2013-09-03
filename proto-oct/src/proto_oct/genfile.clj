(ns proto-oct.genfile
  (:require [proto-oct.core :as poc]))

;; c9 dev helper
(defn- r []
  (load-file "proto_oct/core.clj")
  (load-file "proto_oct/genfile.clj"))

;; c9 dev helper
(defn- q []
  (System/exit 0))



(defn genfile []
  (apply str (map emit-code
                  [(include "stdio.h" true)
                   ])))
