(ns triangle-peg-game.test-data)

(def test-board {7 {:connections {2 4 9 8} :is-pegged? true} 
                 1 {:is-pegged? true :connections {4 2 6 3}} 
                 4 {:connections {1 2 6 5 11 7 13 8} :is-pegged? false} 
                 15 {:connections {6 10 13 14} :is-pegged? true} 
                 13 {:connections {4 8 6 9 11 12 15 14} :is-pegged? true} 
                 :rows 5 
                 6 {:connections {1 3 4 5 13 9 15 10} :is-pegged? true} 
                 3 {:is-pegged? true :connections {8 5 10 6}} 
                 12 {:connections {5 8 14 13} :is-pegged? true} 
                 2 {:is-pegged? true :connections {7 4 9 5}} 
                 11 {:connections {4 7 13 12} :is-pegged? true} 
                 9 {:connections {2 5 7 8} :is-pegged? true} 
                 5 {:is-pegged? true :connections {12 8 14 9}} 
                 14 {:connections {5 9 12 13} :is-pegged? true} 
                 10 {:connections {3 6 8 9} :is-pegged? true} 
                 8 {:connections {3 5 10 9} :is-pegged? true}})
