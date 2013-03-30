(ns learn-macros.defargs)

(defn get-compositions [args default-values body]
  "Create function's pattern for args
  Wrap function body with let expression if default-values is present" 
  (if (= default-values [])
     `(~(vec args) ~@body)  ; if there is no default-value return function body
     `(~(vec args) (let ~(vec (flatten default-values)) ~@body)))) ; else wrap function body with let expr

(defn get-function-body [args default-values using-values body]
  "Walk through args and create pattern for each argument's set" 
  (if (= args '())
    [(get-compositions args using-values body)]  ; return final patter for empty argument list
    (concat
      [(get-compositions args using-values body)]  ; pattern for curent arguments set
      (get-function-body 
        (drop-last args)  ; remove last argument
        (drop-last 2 default-values)  ; remove last value
        (concat using-values [(take-last 2 default-values)])  ; add removed values to using-values
        body))))

(defmacro defargs [name args & body]
  "Create function ~name with default arguments values
  Example:
  (defargs sum [a 1 b 2] (+ a b))

  (sum 0 0) => 0
  (sum 0) => 2
  (sum) => 3

  (defargs sum-and-print [a 1 b 2]
    (println 'a =' a 'b =' b)
    (+ a b))

  (sum-and-print 2) => 4
  ; prints 'a = 2 b = 2'"
  `(defn ~name ~@(get-function-body (take-nth 2 args) args [] body)))
