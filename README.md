# primes

A Clojure utility for outputting a multiplication table of primes to `stdout`.

## Installation

Ensure [leiningen is installed](https://leiningen.org/#install) for your platform.

## Usage

In your terminal from the project root:

`$ lein run`

Similarly, to execute the test suite:

`$ lein test`

## Rationale and Design Notes

To generate primes we use a sieve method that is essentially the inverse of
the well-known
[Sieve of Eratosthenes](https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes).
This method is more conducive to a functional environment and turns out to be
relatively memory efficient (unneeded composites and their factors can be
discarded as we produce primes). A better description of this method is
embedded as a contextual comment in the `primes.sieve` namespace. A
comprehensive overview can also be found in
[The Genuine Sieve of Eratosthenes](https://www.cs.hmc.edu/~oneill/papers/Sieve-JFP.pdf)
(see section 3).

By employing a sieve such as the above, we yield a lazy, infinite sequence of
primes with runtime performance that can beat trial division with some tuning.
Given the goals of this project, this should more than suffice. In particular
this approach allows us to ask for N primes, a consideration of our design
document. Our implementation can be found in the `primes.sieve` namespace.

With a sequence of primes the remainder of the program is focused on generating
a multiplication table of said primes. This is done via a list of lists of
maps. Each list contained by the outer list is a row and maps are cells with
keys correlated to columns and values correlated to the cell value. We use
maps as cells because feeding these into `clojure.pprint/print-table` is
convenient and will produce a nicely formatted table we can output via
`stdout`. See the `primes.table` namespace for our implementation.

Finally the goal of our program is print the above table to the `stdout`. We
do so in the `primes.core` namespace via `clojure.pprint/print-table`.
