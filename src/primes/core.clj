(ns primes.core
  (:require [primes.table :as table])
  (:gen-class))

(defn -main [& _]
  (-> (table/gen-prime-multi-table)
      table/print-multi-table))
