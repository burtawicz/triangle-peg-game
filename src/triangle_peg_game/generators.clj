(ns triangle-peg-game.generators)

(defn generate-triangular-number-series
  "Generate a lazy sequence of triangular numbers"
  ([] (generate-triangular-number-series 0 1))
  ([sum n]
   (let [new-sum (+ sum n)]
     (cons new-sum (lazy-seq (generate-triangular-number-series new-sum (inc n)))))))

(def get-triangular-numbers (generate-triangular-number-series))