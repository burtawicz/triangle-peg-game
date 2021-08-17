(ns triangle-peg-game.core
  (:require [triangle-peg-game.prompts :as prompts])
  (:gen-class))

(defn -main
  [& args]
  (println "Get ready to play...")
  (prompts/prompt-rows))




