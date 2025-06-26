ileNiepKwadr1 =
  length [x * x | x <- [1, 3 .. 10000]]

ileNiepKwadr2 =
  length [x | x <- [1 .. 10000], odd x]

ileNiepKwadr3 =
  length (filter odd [x * x | x <- [1 .. 10000]])

ileNiepKwadr4 =
  length (map (^ 2) [x | x <- [1, 3 .. 10000]])
