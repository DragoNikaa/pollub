% z Góry do Dołu, licznik Malejący
silniaGDM(1, 1) :- !.
silniaGDM(N, W) :- N1 is N - 1, silniaGDM(N1, W1), W is N * W1.

% z Góry do Dołu, licznik Rosnący
silniaGDR(N, W) :- silniaGDR(1, N, W).
silniaGDR(N, N, N) :- !.
silniaGDR(N1, N, W) :- N2 is N1 + 1, silniaGDR(N2, N, W1), W is N1 * W1.

% z Dołu do Góry, licznik Malejący
silniaDGM(N, W) :- silniaDGM(N, N, W).
silniaDGM(1, W, W) :- !.
silniaDGM(N, W1, W) :- N1 is N - 1, W2 is N1 * W1, silniaDGM(N1, W2, W).

% z Dołu do Góry, licznik Rosnący
silniaDGR(N, W) :- silniaDGR(1, 1, N, W).
silniaDGR(N, W, N, W) :- !.
silniaDGR(N1, W1, N, W) :- N2 is N1 + 1, W2 is N2 * W1, silniaDGR(N2, W2, N, W).
