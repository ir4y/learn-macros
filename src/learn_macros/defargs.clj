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
    (concat [value] (apply getfull-values args))))

(defn get-compositions [args default-args default-values body]
;  (println args default-args default-values body) 
  (if (= default-args [])
     `(~(vec args) ~body)
     `(~(vec args) (let ~(vec (interleave default-args default-values)) ~body))))

(defn get-function-body [args use-args default-args use-values default-values body]
;  (println args use-args default-args use-values default-values body)
  (if (= args '())
    [(get-compositions args use-args use-values body)]
    (concat
      [(get-compositions args use-args use-values body)]
      (get-function-body 
        (rest args)
        (concat use-args [(last default-args)])
        (rest default-args)
        (concat use-values [(last default-values)])
        (rest default-values)
        body))))

(defmacro defargs [name args body]
  (let [full-args (apply getfull-args (partition 2 args))
        full-values (apply getfull-values (partition 2 args))]
    `(defn ~name 
        ~(get-function-body full-args [] full-args [] full-values body))))

(macroexpand-1 '(defargs sum [a 1 b 2](+ a b)))

(defargs sum [a 1 b 2] (+ a b))
(sum 1 2)
(sum 1)
(sum)

