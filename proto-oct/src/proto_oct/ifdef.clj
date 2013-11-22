(ns proto-oct.ifdef
  (:use [proto-oct.core]))

(defn ifdef
  [cond then else & dependencies]
  {:type :ifdef
   :cond cond
   :then then
   :else else
   :dependencies dependencies})

;; (defmethod emit-definition :ifdef [m]
;;   (if (:else m)
;;     (str "#ifdef " (:cond m) \newline
;;          (emit-seq (:then m)) \newline
;;          "#else" \newline
;;          (emit-seq (:else m)) \newline
;;          "#endif")
;;     (str "#ifdef " (:cond m) \newline
;;          (emit-seq (:then m)) \newline
;;          "#endif")))

;; copy of ifdef, generalize?
(defn ifndef
  [cond then else & dependencies]
  {:type :ifndef
   :cond cond
   :then then
   :else else
   :dependencies dependencies})

;; (defmethod emit-definition :ifndef [m]
;;   (if (:else m)
;;     (str "#ifndef " (:cond m) \newline
;;          (emit-seq (:then m)) \newline
;;          "#else" \newline
;;          (emit-seq (:else m)) \newline
;;          "#endif")
;;     (str "#ifndef " (:cond m) \newline
;;          (emit-seq (:then m)) \newline
;;          "#endif")))
