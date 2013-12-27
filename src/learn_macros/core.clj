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

(infix 1 + 2 + 3)

(defmacro defrpc [[fun & args]]
  (let [edn (gensym "edn")]
    `(fn [~edn]
       (~fun ~@(for [arg args] `(~edn ~(keyword arg)))))))

((defrpc (+ a b)) {:a 1 :b 2})

(defn -main [& args]
  (println "Hello, World!"))
