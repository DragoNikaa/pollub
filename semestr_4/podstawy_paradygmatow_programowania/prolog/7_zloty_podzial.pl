fib(N, W) :- fib(1, 1, N, W).
fib(X1, _, 1, X1) :- !.
fib(X1, X2, N, W) :- N1 is N - 1, X3 is X1 + X2, fib(X2, X3, N1, W).

gold(N, Fi) :- N >= 2, N1 is N - 1, fib(N, W1), fib(N1, W2), Fi is W1 / W2.
