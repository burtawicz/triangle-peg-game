(ns triangle-peg-game.solver
  (:require [clojure.test :refer [deftest is testing]]
            [triangle-peg-game.moves :as moves]
            [triangle-peg-game.renderer :as renderer]))


(def five-row-strategy [["d" "a"]
                        ["f" "d"]
                        ["a" "f"]
                        ["g" "b"]
                        ["m" "d"]
                        ["b" "g"]
                        ["j" "h"]
                        ["g" "i"]
                        ["o" "m"]
                        ["l" "n"]
                        ["f" "m"]
                        ["n" "l"]
                        ["k" "m"]])

(deftest ^:integration solve-5-rows
  (testing "if we can solve 5 rows using the classic strategy"
    (let [initial-board (moves/remove-peg (moves/new-board 5) (renderer/letter->position "a"))
          final-board (reduce
                        (fn [board coords]
                          ;(renderer/render-board board)
                          (moves/make-move board
                                           (renderer/letter->position (first coords))
                                           (renderer/letter->position (second coords))))
                        initial-board
                        five-row-strategy)]
      ;(renderer/render-board final-board)
      (is (= {7 {:connections {2 4 9 8} :is-pegged? false} 
              1 {:is-pegged? false :connections {4 2 6 3}}
              4 {:connections {1 2 6 5 11 7 13 8} :is-pegged? false}
              15 {:connections {6 10 13 14} :is-pegged? false} 
              13 {:connections {4 8 6 9 11 12 15 14} :is-pegged? true} 
              :rows 5 
              6 {:connections {1 3 4 5 13 9 15 10} :is-pegged? false} 
              3 {:is-pegged? false :connections {8 5 10 6}} 
              12 {:connections {5 8 14 13} :is-pegged? false} 
              2 {:is-pegged? false :connections {7 4 9 5}} 
              11 {:connections {4 7 13 12} :is-pegged? false} 
              9 {:connections {2 5 7 8} :is-pegged? false} 
              5 {:is-pegged? false :connections {12 8 14 9}} 
              14 {:connections {5 9 12 13} :is-pegged? false}
              10 {:connections {3 6 8 9} :is-pegged? false} 
              8 {:connections {3 5 10 9} :is-pegged? false}}
             final-board)))))