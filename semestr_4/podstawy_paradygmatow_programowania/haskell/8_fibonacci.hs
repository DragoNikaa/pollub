fib1 n = case n of
  1 -> 1
  2 -> 1
  _ -> fib1 (n - 1) + fib1 (n - 2)

fib2 n = fibPom n 1 1
fibPom n x1 x2 =
  if n <= 2
    then x2
    else fibPom (n - 1) x2 (x1 + x2)
