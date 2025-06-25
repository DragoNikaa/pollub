ciezszy(pomarancza, jablko).
ciezszy(jablko, mandarynka).
ciezszy(arbuz, pomarancza).
ciezszy(jablko, winogrono).

ciezszy(X, Y) :- ciezszy(X, Z), ciezszy(Z, Y).
lzejszy(X, Y) :- ciezszy(Y, X).
