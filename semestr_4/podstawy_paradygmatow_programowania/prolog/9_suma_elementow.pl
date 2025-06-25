% z Góry do Dołu
sumaGD([], 0).
sumaGD([H|T], W) :- sumaGD(T, W1), W is H + W1.

% z Dołu do Góry
sumaDG([H|T], I, W) :- I1 is H + I, sumaDG(T, I1, W).
sumaDG([], I, I).
