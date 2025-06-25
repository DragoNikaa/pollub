suma1a(X, Y, W) :- X =< 6, W is X + Y.

suma1b(X, Y, W) :- X = 6, W is X + Y, !.
suma1b(X, Y, W) :- X < 6, W is X + Y.

suma1c(X, _, _) :- X > 6, !.
suma1c(X, Y, W) :- W is X + Y.

suma2a(X, Y, W) :- X < 6, W is X + Y, !.
suma2a(_, Y, Y).

suma2b(X, Y, W) :- X < 6, W is X + Y, !; W is Y.
