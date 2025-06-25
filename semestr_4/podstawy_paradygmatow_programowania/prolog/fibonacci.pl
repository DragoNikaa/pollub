% z Góry do Dołu, licznik Malejący
fibGDM(1, 1) :- !.
fibGDM(2, 1) :- !.
fibGDM(N, W) :- N1 is N - 1, N2 is N - 2, fibGDM(N1, W1), fibGDM(N2, W2), W is W1 + W2.

% z Dołu do Góry, licznik Malejący
fibDGM(N, W) :- fibDGM(1, 1, N, W).
fibDGM(X1, _, 1, X1) :- !.
fibDGM(X1, X2, N, W) :- N1 is N - 1, X3 is X1 + X2, fibDGM(X2, X3, N1, W).

% z Dołu do Góry, licznik Rosnący
fibDGR(N, W) :- fibDGR(1, 1, 1, N, W).
fibDGR(X1, _, N, N, X1) :- !.
fibDGR(X1, X2, N1, N, W) :- N2 is N1 + 1, X3 is X1 + X2, fibDGR(X2, X3, N2, N, W).
