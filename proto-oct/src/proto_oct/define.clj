(ns proto-oct.define
  (:use [proto-oct.core]))

(defn define
  [name value & dependencies]
  {:type :define
   :name name
   :value value
   :dependencies dependencies})

(defmethod emit-definition :define [m]
  (if (:value m)
    (str "#define " (:name m) " " (:value m))
    (str "#define " (:name m))))

