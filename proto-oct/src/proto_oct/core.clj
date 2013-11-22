(ns proto-oct.core)

(defmulti emit-declaration :type)

(defmulti emit-definition :type)

;; Pass-through versions for literals
(defmethod emit-declaration nil [literal]
  literal)

(defmethod emit-definition nil [literal]
  literal)

(defn add-expressions* [s & exprs]
  (letfn [(add-or-update [s expr]
            (let [t (:type expr)]
              (if (s t)
                (update-in s [t] conj expr)
                (assoc s t #{expr}))))]
    (reduce add-or-update s exprs)))

(defmacro add-expressions [s & exprs]
  `(add-expressions* ~s ~@exprs))

(defn find-dependencies [s expr]
  (let [deps (:dependencies expr)]
    ;;(println deps)
    (cond
     deps (do
            ;;(println (concat deps (map find-dependencies s deps)))
            (concat deps (map find-dependencies s deps))))))

(defn emit-program [s]
  (reduce str (map emit-definition s)))
