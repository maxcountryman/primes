(ns primes.sieve)

;; A sieve, after the fashion of Eratosthenes, for generating a lazy, infinite
;; sequence of prime numbers. However, instead of keeping track of which
;; natural numbers are prime, we can build up a map of composites to a list
;; of their known factors. Using this map we can efficiently (in terms of
;; memory used) generate primes.
;;
;; As an example, assume we have a composite map, like so:
;;
;;  => (def composites {})
;;
;; We start by asking if 2 is in a key in this map. Because it's not, we know
;; 2 is not a composite number and therefore prime. But we now must update our
;; composites map to look like this:
;;
;;  => (def composites {4 [2]})
;;
;; Above we have inserted the square of 2 as a key to the list of its known
;; factors, i.e. in this case 2, the prime we just discovered.
;;
;; Let's do the same for 3 such that our map is now:
;;
;;  => (def composites {4 [2] 9 [3]})
;;
;; Now a more interesting case occurs with 4: because we find 4 in our map we
;; immediately know 4 is a composite, and not prime. Then we can take the list of
;; factors keyed to 4 and re-insert them into the composite map by adding 4
;; to each factor. Finally we can remove 4 from the map. So we should end up
;; with:
;;
;;  => (def composites {6 [2] 9 [3]})
;;
;; This technique is encapsulated in a var `lazy-primes` which is a lazy,
;; infinite sequence of prime numbers. We can extract primes from this sequence
;; using `take` as such:
;;
;;   => (take 10 lazy-primes)
;;   (2 3 5 7 11 13 17 19 23 29)
;;

(defn update-comps->facs
  "Returns an updated map of composites to a list of their known factors."
  [n factor comps->facs]
  (update-in comps->facs [(+ n factor)] conj factor))

(defn gen-primes
  "A generator which produces a lazy, infinite sequence of prime numbers via
  a sieve method."
  ([]
   (gen-primes 2 {}))
  ([n comps->facs]
    (if-let [factors (get comps->facs n)]
      (recur (inc n)
             (reduce
               #(update-comps->facs n %2 %1) (dissoc comps->facs n) factors))
      (lazy-seq
        (cons n  ;; N is prime because it's not in the composites.
          (gen-primes (inc n) (assoc comps->facs (* n n) (list n))))))))

(def lazy-primes (gen-primes))
