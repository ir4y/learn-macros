(ns learn-macros.defargs)

(defn getfull-args [[key value] & args]
  (if (nil? args) 
    [key]
    (concat [key] (apply getfull-args args))))

(defn get-compositions [args default-args body]
  (if (= default-args [])
     `(~(vec args) ~@body)
     `(~(vec args) (let ~(vec (flatten default-args)) ~@body))))

(defn get-function-body [args default-args use-args body]
  (if (= args '())
    [(get-compositions args use-args body)]
    (concat
      [(get-compositions args use-args body)]
      (get-function-body 
        (drop-last args)
        (drop-last 2 default-args)
        (concat use-args [(take-last 2 default-args)])
        body))))

(defmacro defargs [name args & body]
  (let [full-args (apply getfull-args (partition 2 args))]
        `(defn ~name ~@(get-function-body full-args args [] body))))
