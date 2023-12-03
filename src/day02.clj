(ns day02
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def test-data
  ["Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
   "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"
   "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"
   "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"
   "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"])

(defn- read-data [file-name]
  (->> file-name
       (io/resource)
       (slurp)
       (str/split-lines)))

(defn draw-color-frequencies [input]
  (let [num-red (if-let [[_ num-str]   (re-find #"(\d+) red" input)]
                  (parse-long num-str)
                  0)
        num-green (if-let [[_ num-str]   (re-find #"(\d+) green" input)]
                    (parse-long num-str)
                    0)
        num-blue (if-let [[_ num-str]   (re-find #"(\d+) blue" input)]
                   (parse-long num-str)
                   0)]

    {:green num-green, :blue num-blue, :red num-red}))

(defn draw-possible? [draw]
  (let [{:keys [red green blue]} (draw-color-frequencies draw)]
    (and
     (<= red 12)
     (<= green 13)
     (<= blue 14))))

(defn game-possible? [draws]
  (every? draw-possible? draws))

(defn game-id [game-str]
  (let [[_ id] (re-find #"Game (\d+): " game-str)]
    (parse-long id)))

(defn game-draws [game-str]
  (let [[_ draws-str] (str/split game-str #"Game \d+: ")]
    (str/split draws-str #"; ")))

(defn game->score [game-str]
  (let [id   (game-id game-str)
        draws (game-draws game-str)]
    (if (game-possible? draws)
      id
      0)))

(defn solve-part-1 []
  (->> (read-data "day2-input.txt")
       (map game->score)
       (reduce +)))

(comment
  (solve-part-1) ;; 2076
  )
;; ------ Part 2

(defn reduce-part-2-fn [{:keys [max-red max-green max-blue] :as acc} {:keys [red green blue] :as _draw}]
  (assoc acc
         :max-red (max max-red red)
         :max-green (max max-green green)
         :max-blue (max max-blue blue)))

(defn game->score-2 [game-str]
  (->> (game-draws game-str)
       (map draw-color-frequencies)
       (reduce reduce-part-2-fn {:max-green 0 :max-blue 0 :max-red 0})
       vals
       (reduce *)))

(defn solve-part-2 []
  (->> (read-data "day2-input.txt")
       (map game->score-2)
       (reduce +)))

(comment
  (solve-part-2) ;; 70950
  )
