maxPodzKlasycznie =
  last [x | x <- [1 .. 1000000], (x `mod` 3829) == 0]

maxPodzLeniwie =
  head [x | x <- [1000000, 999999 ..], (x `mod` 3829) == 0]
