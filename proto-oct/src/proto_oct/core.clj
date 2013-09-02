(ns proto-oct.core)

(defmulti emit-code :type)

;; Pass-through version of emit-code for literals
(defmethod emit-code nil [literal]
  literal)

(defn define
  ([name value]
     {:type :define
      :name name
      :value value})
  ([name]
     (define name nil)))

(defmethod emit-code :define [m]
  (if (:value m)
    (str "#define " (:name m) " " (:value m))
    (str "#define " (:name m))))

(defn include
  ([file system?]
     {:type :include
      :file file
      :system system?})
  ([file]
     (include file nil)))

(defmethod emit-code :include [m]
  (if (:system m)
    (str "#include <" (:file m) ">")
    (str "#include \"" (:file m) "\"")))

(defn ifdef
  ([cond then else]
     {:type :ifdef
      :cond cond
      :then then
      :else else})
  ([cond then]
     (ifdef cond then nil)))

(defmethod emit-code :ifdef [m]
  (if (:else m)
    (str "#ifdef " (:cond m) \newline
         (emit-code (:then m)) \newline
         "#else" \newline
         (emit-code (:else m)) \newline
         "#endif")
    (str "#ifdef " (:cond m) \newline
         (emit-code (:then m)) \newline
         "#endif")))

;; copy of ifdef, generalize?
(defn ifndef
  ([cond then else]
     {:type :ifndef
      :cond cond
      :then then
      :else else})
  ([cond then]
     (ifndef cond then nil)))

(defmethod emit-code :ifndef [m]
  (if (:else m)
    (str "#ifndef " (:cond m) \newline
         (emit-code (:then m)) \newline
         "#else" \newline
         (emit-code (:else m)) \newline
         "#endif")
    (str "#ifndef " (:cond m) \newline
         (emit-code (:then m)) \newline
         "#endif")))
