silnia1 n
  | n < 0 = error "liczba ujemna"
  | n == 0 = 1
  | otherwise = n * silnia1 (n - 1)

silnia2 n = silniaPom n 1
silniaPom 0 w = w
silniaPom n w = silniaPom (n - 1) (n * w)

listaParzSilni1 n = map silnia1 [0, 2 .. n]

listaParzSilni2 n =
  [product [1 .. x] | x <- [0 .. n], mod x 2 == 0]
