(defproject triangle-peg-game "0.1.0-SNAPSHOT"
  :description "Implementation of the peg game from Daniel Higginbotham's Clojure for the Brave and True"
  :dependencies [[org.clojure/clojure "1.10.1"]]
  :main ^:skip-aot triangle-peg-game.core
  :repl-options {:init-ns triangle-peg-game.core}
  :source-paths ["src"]
  :test-paths ["test"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
