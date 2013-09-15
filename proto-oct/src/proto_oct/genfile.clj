(ns proto-oct.genfile
  (:require [proto-oct.core :as c]))

;; c9 dev helper
(defn- r []
  (load-file "proto_oct/core.clj")
  (load-file "proto_oct/genfile.clj"))

;; c9 dev helper
(defn- q []
  (System/exit 0))

(defmacro octarine [& exprs]
  `(println
    (c/emit-program
     (c/add-expressions [] ~@exprs))))

(octarine
 (c/include "stdio.h" true)
 (c/ifdef
  "_WIN32"
  [(c/define "WINDOWS")
   (c/include "Windows.h" true)
   (c/typedef "__int8" "I8")
   (c/typedef "__int8" "I8")
   (c/typedef "__int16" "I16")
   (c/typedef "unsigned __int16" "U16")
   (c/typedef "__int32" "I32")
   (c/typedef "unsigned __int32" "U32")
   (c/typedef "__int64" "I64")
   (c/typedef "unsigned __int64" "U64")
   (c/typedef "float" "F32")
   (c/typedef "double" "F64")
   (c/ifdef
    "_WIN64"
    [(c/define "OCT64")]
    [(c/define "OCT32")])
   (c/ifdef
    "_DEBUG"
    [(c/define "DEBUG")])])
 (c/ifdef
  "__APPLE__"
  [(c/define "MACOSX")
   (c/include "inttypes.h" true)
   (c/typedef "int8_t" "I8")
   (c/typedef "uint8_t" "U8")
   (c/typedef "int16_t" "I16")
   (c/typedef "uint16_t" "U16")
   (c/typedef "int32_t" "I32")
   (c/typedef "uint32_t" "U32")
   (c/typedef "int64_t" "I64")
   (c/typedef "uint64_t" "U64")
   (c/typedef "float" "F32")
   (c/typedef "double" "F64")
   (c/ifdef
    "__LP64__"
    [(c/define "OCT64")]
    [(c/define "OCT32")])
   (c/ifndef
    "NDEBUG"
    [(c/define "DEBUG")])])
 (c/typedef "U8" "Bool")
 (c/define "True" 1)
 (c/define "False" 0)
 (c/typedef "I32" "Char")
 (c/ifdef
  "OCT64"
  [(c/typedef "I64" "Word")
   (c/typedef "U64" "Uword")]
  [(c/typedef "I32" "Word")
   (c/typedef "U32" "Uword")])

 (c/oct-type
  "U8Array" true
  "Uword" "size"
  "U8" "data[]")


 )
