(ns proto-oct.core)

(def ^:dynamic *emit-newlines* true)

(defmulti emit-code :type)

;; Pass-through version of emit-code for literals
(defmethod emit-code nil [literal]
  literal)

(defmacro defemit [type argvec body]
  (if *emit-newlines*
    `(defmethod emit-code ~type ~argvec (str ~body \newline))
    `(defmethod emit-code ~type ~argvec ~body)))

(defn define
  ([name value]
     {:type :define
      :name name
      :value value})
  ([name]
     (define name nil)))

(defemit :define [m]
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

(defemit :include [m]
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

(defemit :ifdef [m]
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

(defemit :ifndef [m]
  (if (:else m)
    (str "#ifndef " (:cond m) \newline
         (emit-code (:then m)) \newline
         "#else" \newline
         (emit-code (:else m)) \newline
         "#endif")
    (str "#ifndef " (:cond m) \newline
         (emit-code (:then m)) \newline
         "#endif")))

(defn add-expressions* [state & exprs]
  (reduce
    (fn [s e]
      (if ((:type e) s)
        (update-in s [(:type e)] conj e)
        (assoc s (:type e) [e])))
    state
    exprs))

(defmacro add-expressions [state & exprs]
  `(add-expressions* ~state ~@exprs))
