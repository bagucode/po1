(ns proto-oct.oct-type
  (:use [proto-oct.core]))

(defn oct-type [name heaptype? & members]
  {:type :oct-type
   :name name
   :heaptype? heaptype?
   :members members})

(defmethod emit-definition :oct-type [m]
  (str "typedef struct _" (:name m) " {" \newline
       (reduce str
               (map #(str (first %) " " (second %) ";" \newline)
                    (partition 2 (:members m))))
       (if (:reftype? m)
         (str "}* " (:name m) "Ref;" \newline)
         (str "} " (:name m) ";" \newline))))
