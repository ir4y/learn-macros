(ns learn-macros.defargs)

(comment
  (defargs sum [a 1 b 2]
      (+ a b))

  (sum 0 0) => 0
  (sum 0) => 2
  (sum) => 3

  (defargs sum-and-print [a 1 b 2]
      (println "a =" a "b =" b)
      (+ a b))

  (sum-and-print 2) => 4)

(defn getfull-args [[key value] & args]
  (if (nil? args) 
    [key]
    (concat [key] (apply getfull-args args))))

(defn getfull-values [[key value] & args]
  (if (nil? args) 
    [value]
    (concat [value] (apply getfull-args args))))

(defn get-compositions [args default-args default-values body]
  (if (nil? default-args)
     `(~(vec args) ~body)
     `(~(vec args) (let ~(vec (interleave default-args default-values)) ~body))))

(defmacro defargs [name args body]
  (let [full-args (apply getfull-args (partition 2 args))
        full-values (apply getfull-values (partition 2 args))
        a (symbol "a") 
        b (symbol "b")]
    `(defn ~name 
        ~(get-compositions [a b] nil nil body)
        ~(get-compositions [a] [b] [1] body)
        ~(get-compositions [] [a b] [2 1] body))))

(macroexpand-1 '(defargs sum [a 1 b 2](+ a b)))

(defargs sum [a 1 b 2] (+ a b))
(sum 1 2)
(sum 1)
(sum)

