(ns learn-macros.core-test
  (:use clojure.test
        learn-macros.core
        learn-macros.defargs))

(deftest check-defargs-macros
  (testing "Checking simple defargs macros"
    (let [_ (defargs sum [a 1 b 2 c 3] (+ a (+ b c)))]
      (is (= (sum 9 9 9) 27)
      (is (= (sum 9 9) 21)
      (is (= (sum 9) 14)
      (is (= (sum) 6)))))))
  (testing "Checking defargs macros for multiltne function"
    (let [_ (defargs sum_and_print [a 1 b 2 c 3] 
              (println a b c)
              (+ a (+ b c)))]
      (is (= (sum_and_print 9 9 9) 27)
      (is (= (sum_and_print 9 9) 21)
      (is (= (sum_and_print 9) 14)
      (is (= (sum_and_print) 6))))))))
