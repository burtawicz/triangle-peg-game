(ns triangle-peg-game.moves-test
  (:require [clojure.test :refer [deftest is testing]]
            [triangle-peg-game.test-data :refer [test-board]]
            [triangle-peg-game.moves :refer :all]))

(deftest ^:unit is-triangular?-test
  (testing "if 0 is a triangular number"
    (is (not (is-triangular? 0))))
  (testing "if 1 is a triangular number"
    (is (is-triangular? 1)))
  (testing "if 2 is a triangular number"
    (is (not (is-triangular? 2))))
  (testing "if 3 is a triangular number"
    (is (is-triangular? 3)))
  (testing "if 15 is a triangular number"
    (is (is-triangular? 15))))

(deftest ^:unit row-tri-test
  (testing "if last number in row 1 is 1"
    (is (= 1 (row-tri 1))))
  (testing "if last number in row 2 is 3"
    (is (= 3 (row-tri 2))))
  (testing "if last number in row 3 is 6"
    (is (= 6 (row-tri 3))))
  (testing "if last number in row 4 is 10"
    (is (= 10 (row-tri 4)))))

(deftest ^:unit row-num-test
  (testing "if row num 1 is 1"
    (is (= 1 (row-num 1))))
  (testing "if row num 5 is 3"
    (is (= 3 (row-num 5))))
  (testing "if row num 10 is 4"
    (is (= 4 (row-num 10)))))

(deftest ^:unit connect-positions-test
  (testing "if connect-positions returns as expected"
    (is (= {1 {:connections {4 2}}
            4 {:connections {1 2}}}
           (connect-positions {} 15 1 2 4)))
    (is (= {3 {:connections {2 4}}
            2 {:connections {3 4}}}
           (connect-positions {} 25 3 4 2)))))

(deftest ^:unit connect-right-test
  (testing "connect-right with a basic 3 row board"
    (is (= {:rows 3
            1     {:is-pegged? true, :connections {4 2 6 3}}
            4     {:connections {1 2 6 5} :is-pegged? true}
            6     {:connections {1 3 4 5} :is-pegged? true}
            2     {:is-pegged? true} 3 {:is-pegged? true}
            5     {:is-pegged? true}}
           (connect-right (new-board 3) 9 3))))
  (testing "connect-right with a basic 5 row board"
    (is (= {7     {:connections {2 4 9 8} :is-pegged? true}
            1     {:is-pegged? true :connections {4 2 6 3}}
            4     {:connections {1 2 6 5 11 7 13 8} :is-pegged? true}
            15    {:connections {6 10 13 14} :is-pegged? true}
            13    {:connections {4 8 6 9 11 12 15 14} :is-pegged? true}
            :rows 5
            6     {:connections {1 3 4 5 13 9 15 10} :is-pegged? true}
            3     {:is-pegged? true :connections {8 5 10 6}}
            12    {:connections {5 8 14 13} :is-pegged? true}
            2     {:is-pegged? true :connections {7 4 9 5}}
            11    {:connections {4 7 13 12} :is-pegged? true}
            9     {:connections {2 5 7 8} :is-pegged? true}
            5     {:is-pegged? true :connections {12 8 14 9}}
            14    {:connections {5 9 12 13} :is-pegged? true}
            10    {:connections {3 6 8 9} :is-pegged? true}
            8     {:connections {3 5 10 9} :is-pegged? true}}
           (connect-right (new-board 5) 15 4)))))

(deftest ^:unit connect-down-left-test
  (testing "connect-down-left with an empty board"
    (is (= {1 {:connections {4 2}}
            4 {:connections {1 2}}}
           (connect-down-left {} 15 1))))
  (testing "connect-down-left with a basic 3 row board"
    (is (= {:rows 3
            1     {:is-pegged? true :connections {4 2 6 3}}
            4     {:connections {1 2 6 5} :is-pegged? true}
            6     {:connections {1 3 4 5} :is-pegged? true}
            2     {:is-pegged? true} 3 {:is-pegged? true}
            5     {:is-pegged? true}}
           (connect-down-left (new-board 3) 15 1)))))

(deftest ^:unit connect-down-right-test
  (testing "connect-down-right with a basic 3 row board"
    (is (= {3  {:connections {10 6}}
            10 {:connections {3 6}}}
           (connect-down-right {} 15 3))))
  (testing "connect-down-right with a basic 3 row board"
    (is (= {7     {:connections {2 4 9 8} :is-pegged? true}
            1     {:is-pegged? true :connections {4 2 6 3}}
            4     {:connections {1 2 6 5} :is-pegged? true}
            :rows 4
            6     {:connections {1 3 4 5} :is-pegged? true}
            3     {:is-pegged? true :connections {8 5 10 6}}
            2     {:is-pegged? true :connections {7 4 9 5}}
            9     {:connections {2 5 7 8} :is-pegged? true}
            5     {:is-pegged? true}
            10    {:connections {3 6 8 9} :is-pegged? true}
            8     {:connections {3 5 10 9} :is-pegged? true}}
           (connect-down-right (new-board 4) 6 3)))))

(deftest ^:unit add-position-test
  (testing "if add position returns as expected with empty board"
    (is (= {1 {:connections {6 3 4 2} :is-pegged? true}
            4 {:connections {1 2}}
            6 {:connections {1 3}}}
           (add-position {} 15 1)))))

(deftest ^:unit new-board-test
  (testing "if new board is generated as expected"
    (is (= {7     {:connections {2 4 9 8} :is-pegged? true}
            1     {:is-pegged? true :connections {4 2 6 3}}
            4     {:connections {1 2 6 5 11 7 13 8} :is-pegged? true}
            15    {:connections {6 10 13 14} :is-pegged? true}
            13    {:connections {4 8 6 9 11 12, 15 14} :is-pegged? true}
            :rows 5
            6     {:connections {1 3 4 5 13 9 15 10} :is-pegged? true}
            3     {:is-pegged? true :connections {8 5 10 6}}
            12    {:connections {5 8 14 13} :is-pegged? true}
            2     {:is-pegged? true :connections {7 4 9 5}}
            11    {:connections {4 7 13 12} :is-pegged? true}
            9     {:connections {2 5 7 8} :is-pegged? true}
            5     {:is-pegged? true :connections {12 8 14 9}}
            14    {:connections {5 9 12 13} :is-pegged? true}
            10    {:connections {3 6 8 9} :is-pegged? true}
            8     {:connections {3 5 10 9} :is-pegged? true}}
           (new-board 5)))))

(deftest ^:unit is-pegged?-test
  (testing "if is-pegged? can verify a pegged position"
    (is (is-pegged? test-board 5)))
  (testing "if is-pegged? can verify a non-pegged position"
    (is (not (is-pegged? test-board 4)))))

(deftest ^:unit remove-peg-test
  (testing "if remove-peg works as expected"
    (is (= {7     {:connections {2 4 9 8} :is-pegged? true}
            1     {:is-pegged? true :connections {4 2 6 3}}
            4     {:connections {1 2 6 5 11 7 13 8} :is-pegged? false}
            15    {:connections {6 10 13 14} :is-pegged? true}
            13    {:connections {4 8 6 9 11 12 15 14} :is-pegged? true}
            :rows 5
            6     {:connections {1 3 4 5 13 9 15 10} :is-pegged? true}
            3     {:is-pegged? true :connections {8 5 10 6}}
            12    {:connections {5 8 14 13} :is-pegged? true}
            2     {:is-pegged? true :connections {7 4 9 5}}
            11    {:connections {4 7 13 12} :is-pegged? true}
            9     {:connections {2 5 7 8} :is-pegged? true}
            5     {:is-pegged? false :connections {12 8 14 9}}
            14    {:connections {5 9 12 13} :is-pegged? true}
            10    {:connections {3 6 8 9} :is-pegged? true}
            8     {:connections {3 5 10 9} :is-pegged? true}}
           (remove-peg test-board 5)))))

(deftest ^:unit place-peg-test
  (testing "if place-peg works as expected"
    (is (= {7     {:connections {2 4 9 8} :is-pegged? true}
            1     {:is-pegged? true :connections {4 2 6 3}}
            4     {:connections {1 2 6 5 11 7 13 8} :is-pegged? true}
            15    {:connections {6 10 13 14} :is-pegged? true}
            13    {:connections {4 8 6 9 11 12 15 14} :is-pegged? true}
            :rows 5
            6     {:connections {1 3 4 5 13 9 15 10} :is-pegged? true}
            3     {:is-pegged? true :connections {8 5 10 6}}
            12    {:connections {5 8 14 13} :is-pegged? true}
            2     {:is-pegged? true :connections {7 4 9 5}}
            11    {:connections {4 7 13 12} :is-pegged? true}
            9     {:connections {2 5 7 8} :is-pegged? true}
            5     {:is-pegged? true :connections {12 8 14 9}}
            14    {:connections {5 9 12 13} :is-pegged? true}
            10    {:connections {3 6 8 9} :is-pegged? true}
            8     {:connections {3 5 10 9} :is-pegged? true}}
           (place-peg test-board 4)))))

(deftest ^:unit move-peg-test
  (testing ""
    (is (= {7     {:connections {2 4 9 8} :is-pegged? true}
            1     {:is-pegged? true :connections {4 2 6 3}}
            4     {:connections {1 2 6 5 11 7 13 8} :is-pegged? true}
            15    {:connections {6 10 13 14} :is-pegged? true}
            13    {:connections {4 8 6 9 11 12 15 14} :is-pegged? true}
            :rows 5
            6     {:connections {1 3 4 5 13 9 15 10} :is-pegged? false}
            3     {:is-pegged? true :connections {8 5 10 6}}
            12    {:connections {5 8 14 13} :is-pegged? true}
            2     {:is-pegged? true :connections {7 4 9 5}}
            11    {:connections {4 7 13 12} :is-pegged? true}
            9     {:connections {2 5 7 8} :is-pegged? true}
            5     {:is-pegged? true :connections {12 8 14 9}}
            14    {:connections {5 9 12 13} :is-pegged? true}
            10    {:connections {3 6 8 9} :is-pegged? true}
            8     {:connections {3 5 10 9} :is-pegged? true}}
           (move-peg test-board 6 4)))))

(deftest ^:unit get-valid-moves-test
  (testing "if 4 and 2 are valid moves from position 1"
    (is (= {4 2} (get-valid-moves test-board 1))))
  (testing "if 4 and 5 are valid moves from position 6"
    (is (= {4 5} (get-valid-moves test-board 6))))
  (testing "if 4 and 7 are valid moves from position 11"
    (is (= {4 7} (get-valid-moves test-board 11))))
  (testing "if there are no valid moves from position 5"
    (is (= {} (get-valid-moves test-board 5))))
  (testing "if there are no valid moves from position 8"
    (is (= {} (get-valid-moves test-board 8)))))

(deftest ^:unit is-valid-move?-test
  (testing "if 2 is the jumped position from position 1 to position 4"
    (is (= 2 (is-valid-move? test-board 1 4))))
  (testing "if jumping from 8 to 4 is a valid move"
    (is (= nil (is-valid-move? test-board 8 4)))))

(deftest ^:unit make-move-test
  (testing "if make-move works as expected"
    (is (= {7     {:connections {2 4 9 8} :is-pegged? true}
            1     {:is-pegged? true :connections {4 2 6 3}}
            4     {:connections {1 2 6 5 11 7 13 8} :is-pegged? true}
            15    {:connections {6 10 13 14} :is-pegged? true}
            13    {:connections {4 8 6 9 11 12 15 14} :is-pegged? true}
            :rows 5
            6     {:connections {1 3 4 5 13 9 15 10} :is-pegged? false}
            3     {:is-pegged? true :connections {8 5 10 6}}
            12    {:connections {5 8 14 13} :is-pegged? true}
            2     {:is-pegged? true :connections {7 4 9 5}}
            11    {:connections {4 7 13 12} :is-pegged? true}
            9     {:connections {2 5 7 8} :is-pegged? true}
            5     {:is-pegged? false :connections {12 8 14 9}}
            14    {:connections {5 9 12 13} :is-pegged? true}
            10    {:connections {3 6 8 9} :is-pegged? true}
            8     {:connections {3 5 10 9} :is-pegged? true}}
           (make-move test-board 6 4)))))

(deftest ^:unit can-move?-test
  (testing "if can-move? works as expected"
    (is (= {4 2} (can-move? test-board))))
  (testing "if can-move? works with empty board"
    (is (= nil (can-move? {})))))