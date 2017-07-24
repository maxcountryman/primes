(ns primes.sieve-test
  (:require [clojure.test :refer [deftest is]]
            [primes.sieve :as sieve]))

(def ^:const first-ten-primes (list 2 3 5 7 11 13 17 19 23 29))

(defn divisible? [a b]
  (zero? (mod a b)))

(defn prime? [n]
  (and (> n 1)
       (not-any? (partial divisible? n) (range 2 n))))

(deftest test-update-comps->facs
  (is (= {6 [2]} (sieve/update-comps->facs 4 2 {})))
  (is (= {10 (list 2) 12 (list 2 3) 25 (list 5) 49 (list 7)}
         (sieve/update-comps->facs 10 2
           {10 (list 2) 12 (list 3) 25 (list 5) 49 (list 7)}))))

(deftest test-gen-primes
  (is (= (list 2) (take 1 (sieve/gen-primes))))
  (is (= first-ten-primes (take 10 (sieve/gen-primes)))))

(deftest test-primality
  (is (every? prime? (take 100 sieve/lazy-primes))))
