(ns triangle-peg-game.generators-test
  (:require [clojure.test :refer [deftest is testing]])
  (:require [triangle-peg-game.generators :refer :all]))

(deftest ^:unit generate-triangular-number-series-test
  (testing "if generate-triangular-number-series returns sequences as expected"
    (is (= [1 3 6 10 15] (take 5 (generate-triangular-number-series))))
    (is (= [1 3 6 10 15 21 28 36 45 55] (take 10 (generate-triangular-number-series))))))
