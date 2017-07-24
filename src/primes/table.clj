(ns primes.table
  (:require [clojure.pprint :as pprint]
            [primes.sieve :as sieve]))

(defn gen-prime-multi-table
  "Returns a list of lists of maps, where each map is a cell in a table
  containing the result of multiplying the row by the column. Note rows and
  columns are primes up to `n`, where `n` defaults to `10`."
  ([] (gen-prime-multi-table 10))
  ([n]
    (let [primes (take n sieve/lazy-primes)]
      (partition (inc n)
        (for [row primes col (cons 1 primes)]
          {col (* row col)})))))

(defn print-multi-table
  "Prints a formatted representation of a multiplication table to stdout. The
  `table` parameter must conform to `pprint/print-table`."
  [table]
  (pprint/print-table
    (map #(into (sorted-map) %) table)))
