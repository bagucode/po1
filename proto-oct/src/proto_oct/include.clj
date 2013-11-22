(ns proto-oct.include
  (:use [proto-oct.core]))

(defn include
  [file system? & dependencies]
  {:type :include
   :file file
   :system system?
   :dependencies dependencies})

(defmethod emit-definition :include [m]
  (if (:system m)
    (str "#include <" (:file m) ">")
    (str "#include \"" (:file m) "\"")))



