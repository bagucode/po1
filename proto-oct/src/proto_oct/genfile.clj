(ns proto-oct.genfile
  (:use proto-oct.core
        proto-oct.define
        proto-oct.include
        proto-oct.ifdef
        proto-oct.typedef
        proto-oct.oct-type))

(defmacro octarine [& exprs]
  `(add-expressions {} ~@exprs))

(defn -main []
  (octarine
   (include "stdio.h" true)))
