(ns learn-macros.core-test
  (:use clojure.test
        learn-macros.core
        learn-macros.defargs))

(deftest check-defargs-macros
  (testing "Checking defargs macros"
    (let [_ (defargs sum [a 1 b 2 c 3] (+ a (+ b c)))]
      (is (= (sum 9 9 9) 27)
      (is (= (sum 9 9) 21)
      (is (= (sum 9) 14)
      (is (= (sum) 6))))))))
