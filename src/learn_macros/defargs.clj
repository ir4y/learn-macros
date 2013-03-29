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

(defmacro defargs [name args body]
  (let [full-args (apply getfull-args (partition 2 args))]
    `(defn ~name 
       (~(vec full-args) ~body)

       )))

(macroexpand-1 '(defargs sum [a 1 b 2](+ a b)))

(defargs sum [a 1 b 2] (+ a b))
(sum 1 2)

