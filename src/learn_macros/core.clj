(ns learn-macros.core)

(defn inline-transorm
  ([[a op b & args]]
   (inline-transorm args (list op a b)))
  ([args acc]
    (cond
      (>= (count args) 2) 
      (recur (drop 2 args)
             (concat (list (first args))
             (list acc) (list (second args))))
      :else acc)))

(defmacro infix [& args]
  (let [res (inline-transorm args)]
    res))

(defmacro as-rpc [[fun & args]]
  (let [edn (gensym "edn")]
    `{~(keyword fun) (fn [~edn]
       (~fun ~@(for [arg args] `(~edn ~(keyword arg)))))}))

(defn -main [& args]
  (println "Hello, World!"))
