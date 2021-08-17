(ns triangle-peg-game.prompts
  (:require [triangle-peg-game.renderer :as renderer]
            [triangle-peg-game.moves :as moves]))

(declare prompt-rows prompt-move)

(defn get-input
  "Waits for user to enter text and hit enter, then cleans the input"
  ([] (get-input nil))
  ([default]
   (let [input (clojure.string/trim (read-line))]
     (if (empty? input)
       default
       (clojure.string/lower-case input)))))

(defn prompt-game-over
  "Announce the game is over and prompt to play again"
  [board]
  (let [remaining-pegs (count (filter :is-pegged? (vals board)))]
    (println "Game over! You had" remaining-pegs "pegs left:")
    (renderer/render-board board)
    (println "Would you like to play again? y/n [y]")
    (let [input (get-input "y")]
      (if (= "y" input)
        (prompt-rows)
        (do (println "Bye")
            (System/exit 0))))))

(defn user-entered-invalid-move
  "Handles the next step after a user has entered an invalid move"
  [board]
  (println "\n!!! That was an invalid move :(\n")
  (prompt-move board))

(defn user-entered-valid-move
  "Handles the next step after a user has entered a valid move"
  [board]
  (if (moves/can-move? board)
    (prompt-move board)
    (prompt-game-over board)))

(defn prompt-move
  "Prompts the user to make a move"
  [board]
  (println "\nHere's your board:")
  (renderer/render-board board)
  (println "Move from where to where? Enter two letters:")
  (let [input (map renderer/letter->position (renderer/characters-as-strings (get-input)))]
    (if-let [new-board (moves/make-move board (first input) (second input))]
      (user-entered-valid-move new-board)
      (user-entered-invalid-move board))))

(defn prompt-empty-peg
  [board]
  (println "Here's your board:")
  (renderer/render-board board)
  (println "Remove which peg? [e]")
  (prompt-move (moves/remove-peg board (renderer/letter->position (get-input "e")))))

(defn prompt-rows
  []
  (println "How many rows? [5]")
  (let [rows (Integer. (get-input 5))
        board (moves/new-board rows)]
    (prompt-empty-peg board)))
