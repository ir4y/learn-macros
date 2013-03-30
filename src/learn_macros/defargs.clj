(ns learn-macros.defargs)

(defn get-compositions [args default-values body]
  (if (= default-values [])
     `(~(vec args) ~@body)
     `(~(vec args) (let ~(vec (flatten default-values)) ~@body))))

(defn get-function-body [args default-values using-values body]
  (if (= args '())
    [(get-compositions args using-values body)]
    (concat
      [(get-compositions args using-values body)]
      (get-function-body 
        (drop-last args)
        (drop-last 2 default-values)
        (concat using-values [(take-last 2 default-values)])
        body))))

(defmacro defargs [name args & body]
        `(defn ~name ~@(get-function-body (take-nth 2 args) args [] body)))
