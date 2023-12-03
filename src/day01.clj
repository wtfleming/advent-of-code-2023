(ns day01
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(def test-data
  ["1abc2"
   "pqr3stu8vwx"
   "a1b2c3d4e5f"
   "treb7uchet"])

(def test-data-2
  ["two1nine"
   "eightwothree"
   "abcone2threexyz"
   "xtwone3four"
   "4nineeightseven2"
   "zoneight234"
   "7pqrstsixteen"])

(defn- read-data [file-name]
  (->> file-name
       (io/resource)
       (slurp)
       (s/split-lines)))

(defn nums->number [nums]
  (+ (* 10 (first nums)) (last nums)))

(defn part1-line->number
  "Converts a line string like a1b2c3d4e5f to the number 15"
  [line]
  (->> (re-seq #"\d" line)
       (map #(Integer/parseInt %))
       nums->number))

(defn solve-part-one []
  (->> (read-data "day1-input.txt")
       (map part1-line->number)
       (reduce +)))

(comment
  (solve-part-one) ;; 54968
  )

;; ------ Part 2

(def str->number
  {"one" 1, "two" 2, "three" 3, "four" 4, "five" 5,
   "six" 6, "seven" 7, "eight" 8, "nine" 9
   "eno" 1, "owt" 2, "eerht" 3, "ruof" 4, "evif" 5,
   "xis" 6, "neves" 7, "thgie" 8, "enin" 9,
   "1" 1, "2" 2, "3" 3, "4" 4, "5" 5,
   "6" 6, "7" 7, "8" 8, "9" 9})

(defn find-first-number [line]
  (->> line
       (re-find #"one|two|three|four|five|six|seven|eight|nine|\d")
       str->number))

(defn find-last-number [line]
  (->> line
       s/reverse
       (re-find #"eno|owt|eerht|ruof|evif|xis|neves|thgie|enin|\d")
       str->number))

(defn part2-line->number [line]
  (let [first-num (find-first-number line)
        last-num (find-last-number line)]
    (+ (* 10 first-num) last-num)))

(defn solve-part-two []
  (->> (read-data "day1-input.txt")
       (map part2-line->number)
       (reduce +)))

(comment
  (solve-part-one) ;; 54968
  (solve-part-two) ;; 54094
  )
