(ns triangle-peg-game.moves
  (:require [triangle-peg-game.generators :as generator]))

(defn is-triangular?
  "Determines if a number is triangular."
  [n]
  (= n (last (take-while #(>= n %) generator/get-triangular-numbers))))

(defn row-tri
  "The triangular number at the end of row n"
  [n]
  (last (take n generator/get-triangular-numbers)))

(defn row-num
  "Returns row number the position belongs to: pos 1 in row 1,
  positions 2 and 3 in row 2, etc"
  [position]
  (inc (count (take-while #(> position %) generator/get-triangular-numbers))))

(defn connect-positions
  "Form a connection between two positions"
  [board max-position current-position neighbour destination]
  (if (<= destination max-position)
    (reduce (fn [new-board [position-1 position-2]]
              (assoc-in new-board [position-1 :connections position-2] neighbour))
            board
            [[current-position destination] [destination current-position]])
    board))

(defn connect-right
  [board max-position current-position]
  (let [neighbour (inc current-position)
        destination (inc neighbour)]
    (if-not (or (is-triangular? neighbour) (is-triangular? current-position))
      (connect-positions board max-position current-position neighbour destination)
      board)))

(defn connect-down-left
  [board max-position current-position]
  (let [row (row-num current-position)
        neighbour (+ row current-position)
        destination (+ 1 row neighbour)]
    (connect-positions board max-position current-position neighbour destination)))

(defn connect-down-right
  [board max-position current-position]
  (let [row (row-num current-position)
        neighbour (+ 1 row current-position)
        destination (+ 2 row neighbour)]
    (connect-positions board max-position current-position neighbour destination)))

(defn add-position
  "Pegs the position and performs connections"
  [board max-position current-position]
  (let [pegged-board (assoc-in board [current-position :is-pegged?] true)]
    (reduce (fn [new-board connection-creation-fn]
              (connection-creation-fn new-board max-position current-position))
            pegged-board
            [connect-right connect-down-left connect-down-right])))

(defn new-board
  "Creates a new game board with the given number of rows"
  [num-rows]
  (let [initial-board {:rows num-rows}
        max-position (row-tri num-rows)]
    (reduce (fn [board current-position] (add-position board max-position current-position))
            initial-board
            (range 1 (inc max-position)))))

(defn is-pegged?
  "Determines if the position has a peg in it"
  [board position]
  (get-in board [position :is-pegged?]))

(defn remove-peg
  "Removes a peg from the board at the target position"
  [board target-position]
  (assoc-in board [target-position :is-pegged?] false))

(defn place-peg
  "Adds a peg from the board at the target position"
  [board target-position]
  (assoc-in board [target-position :is-pegged?] true))

(defn move-peg
  "Move a peg from one position to another position"
  [board from-position to-position]
  (place-peg (remove-peg board from-position) to-position))

(defn get-valid-moves
  "Return a map of all valid moves for the given position, where the key is the
  destination and the value is the jumped position"
  [board current-position]
  (into {} (filter (fn [[destination jumped]]
                     (and (not (is-pegged? board destination))
                          (is-pegged? board jumped)))
                   (get-in board [current-position :connections]))))

(defn is-valid-move?
  "Return jumped position if the move from positions is valid, nil otherwise"
  [board from-position to-position]
  (get (get-valid-moves board from-position) to-position))

(defn make-move
  "Executes the movement of a peg"
  [board from-position to-position]
  (if-let [jumped (is-valid-move? board from-position to-position)]
    (move-peg (remove-peg board jumped) from-position to-position)))

(defn can-move?
  "Determine if there are any pegged positions which contain valid moves"
  [board]
  (some (comp not-empty (partial get-valid-moves board))
        (map first (filter #(get (second %) :is-pegged?) board))))