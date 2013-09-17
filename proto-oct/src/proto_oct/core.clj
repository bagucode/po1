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

(defn- emit-seq [s]
  (reduce str (map #(emit-code %) s)))

(defn define
  ([name value dependencies]
     {:type :define
      :name name
      :value value
      :dependencies dependencies})
  ([name value]
     (define name value nil))
  ([name]
     (define name nil nil)))

(defemit :define [m]
  (if (:value m)
    (str "#define " (:name m) " " (:value m))
    (str "#define " (:name m))))

(defn include
  ([file system? dependencies]
     {:type :include
      :file file
      :system system?
      :dependencies dependencies})
  ([file system?]
     (include file system? nil))
  ([file]
     (include file false nil)))

(defemit :include [m]
  (if (:system m)
    (str "#include <" (:file m) ">")
    (str "#include \"" (:file m) "\"")))

(defn ifdef
  ([cond then else dependencies]
     {:type :ifdef
      :cond cond
      :then then
      :else else
      :dependencies dependencies})
  ([cond then]
     (ifdef cond then nil)))

(defemit :ifdef [m]
  (if (:else m)
    (str "#ifdef " (:cond m) \newline
         (emit-seq (:then m)) \newline
         "#else" \newline
         (emit-seq (:else m)) \newline
         "#endif")
    (str "#ifdef " (:cond m) \newline
         (emit-seq (:then m)) \newline
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
         (emit-seq (:then m)) \newline
         "#else" \newline
         (emit-seq (:else m)) \newline
         "#endif")
    (str "#ifndef " (:cond m) \newline
         (emit-seq (:then m)) \newline
         "#endif")))

(defn typedef [name alias]
  {:type :typedef
   :name name
   :alias alias})

(defemit :typedef [m]
  (str "typedef " (:name m) " " (:alias m) ";"))

(defn oct-type [name reftype? & members]
  {:type :oct-type
   :name name
   :reftype? reftype?
   :members members})

(defemit :oct-type [m]
  (str "typedef struct _" (:name m) " {" \newline
       (reduce str
               (map #(str (first %) " " (second %) ";" \newline)
                    (partition 2 (:members m))))
       (if (:reftype? m)
         (str "}* " (:name m) "Ref;" \newline)
         (str "} " (:name m) ";" \newline))))

(defn add-expressions* [s & exprs]
  (reduce conj s exprs))

(defmacro add-expressions [s & exprs]
  `(add-expressions* ~s ~@exprs))

(defn emit-program [s]
  (reduce str (map emit-code s)))
