suma1(X, Y, W) :- W is X + Y.
suma2(W) :- read(X), W is X + 6.
suma3(X, Y) :- W is X + Y, write(W).
suma4 :- read(X), read(Y), W is sqrt(X + Y), write(W).

czyRowne(X, Y) :- X = Y, write('rowne').
czyRowne(X, Y) :- X =\= Y, write('nierowne').

poleProstokata(X, Y, _):- (X =< 0; Y =< 0), write('bok < 0').
poleProstokata(X, Y, W):- X > 0, Y > 0, W is X * Y.
