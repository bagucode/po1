(ns proto-oct.genfile
  (:require [proto-oct.core :as poc]))

;; c9 dev helper
(defn- r []
  (load-file "proto_oct/core.clj")
  (load-file "proto_oct/genfile.clj"))

;; c9 dev helper
(defn- q []
  (System/exit 0))

(defmacro octarine [& exprs]
  `(println
     (poc/emit-program
       (poc/add-expressions [] ~@exprs))))

(octarine
  (poc/include "stdio.h" true)
  (poc/ifdef "_WIN32"
    [(poc/define "WINDOWS")
     (poc/include "Windows.h" true)
     (poc/typedef "__int8" "I8")
     (poc/typedef "__int8" "I8")
     (poc/typedef "__int16" "I16")
     (poc/typedef "unsigned __int16" "U16")
     (poc/typedef "__int32" "I32")
     (poc/typedef "unsigned __int32" "U32")
     (poc/typedef "__int64" "I64")
     (poc/typedef "unsigned __int64" "U64")
     (poc/typedef "float" "F32")
     (poc/typedef "double" "F64")]))
