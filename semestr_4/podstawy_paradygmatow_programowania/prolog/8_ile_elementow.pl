% z Góry do Dołu
ileGD([], 0).
ileGD([_|T], W) :- ileGD(T, W1), W is W1 + 1.

% z Dołu do Góry
ileDG([_|T], I, W) :- I1 is I + 1, ileDG(T, I1, W).
ileDG([], I, I).
