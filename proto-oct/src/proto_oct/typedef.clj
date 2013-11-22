(ns proto-oct.typedef
  (:use [proto-oct.core]))

(defn typedef [name alias]
  {:type :typedef
   :name name
   :alias alias})

(defmethod emit-definition :typedef [m]
  (str "typedef " (:name m) " " (:alias m) ";"))

