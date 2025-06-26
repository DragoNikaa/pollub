sign1 n =
  if n < 0
    then -1
    else
      if n == 0
        then 0
        else 1

sign2 n
  | n < 0 = -1
  | n == 0 = 0
  | otherwise = 1

sign3 0 = 0
sign3 n =
  if n < 0
    then -1
    else 1

sign4 n = case (compare n 0) of
  LT -> -1
  EQ -> 0
  GT -> 1

sign5 0 = 0
sign5 n = case n < 0 of
  True -> -1
  False -> 1

sign6 n = case n < 0 of
  True -> -1
  _ -> case n == 0 of
    True -> 0
    _ -> 1

sign7 n = case n of
  0 -> 0
  _ -> n / abs n
