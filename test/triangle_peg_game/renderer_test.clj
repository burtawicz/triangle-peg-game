(ns triangle-peg-game.renderer-test
  (:require [clojure.test :refer [deftest is testing]]
            [triangle-peg-game.test-data :refer [test-board]]
            [triangle-peg-game.renderer :refer :all]))

(deftest ^:unit characters-as-strings-test
  (testing "if characters-as-strings works with whitespace"
    (is (= ["a" "b" "c"] (characters-as-strings "a  bc"))))
  (testing "if characters-as-string works with new lines"
    (is (= ["a" "b" "c"] (characters-as-strings "a\nbc"))))
  (testing "if characters-as-strings works with numbers"
    (is (= ["a" "b" "c"] (characters-as-strings "a1b2c3")))))

(deftest ^:unit letter->position-test
  (testing "if letter->position a = 1"
    (is (= 1 (letter->position "a"))))
  (testing "if letter->position z = 26"
    (is (= 26 (letter->position "z"))))
  (testing "if letter->position b = 2"
    (is (= 2 (letter->position "b")))))

(deftest ^:unit row-positions-test
  (testing "row-positions returns sequences as expected"
    (is (= [4 5 6] (row-positions 3)))
    (is (= [7 8 9 10] (row-positions 4)))
    (is (= [11 12 13 14 15] (row-positions 5)))
    (is (= [191 192 193 194 195 196 197 198 199 200 201 202 203 204 205 206 207 208 209 210]
           (row-positions 20)))))

(deftest ^:unit row-padding-test
  (testing "if row-padding pads rows properly"
    (is (= "   " (row-padding 3 5)))))

