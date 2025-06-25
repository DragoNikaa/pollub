% z Góry do Dołu, licznik Malejący
mulGDM(1, Y, Y) :- !.
mulGDM(X, Y, W) :- X1 is X - 1, mulGDM(X1, Y, W1), W is Y + W1.

% z Góry do Dołu, licznik Rosnący
mulGDR(X, Y, W) :- mulGDR(X, Y, 1, W).
mulGDR(X, Y, X, Y) :- !.
mulGDR(X, Y, I, W) :- I1 is I + 1, mulGDR(X, Y, I1, W1), W is Y + W1.

% z Dołu do Góry, licznik Malejący
mulDGM(X, Y, W) :- mulDGM(X, Y, Y, W).
mulDGM(1, _, W, W) :- !.
mulDGM(X, Y, W1, W) :- X1 is X - 1, W2 is Y + W1, mulDGM(X1, Y, W2, W).

% z Dołu do Góry, licznik Rosnący
mulDGR(X, Y, W) :- mulDGR(X, Y, 1, Y, W).
mulDGR(X, _, X, W, W) :- !.
mulDGR(X, Y, I, W1, W) :- I1 is I + 1, W2 is Y + W1, mulDGR(X, Y, I1, W2, W).
