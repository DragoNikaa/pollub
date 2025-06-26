mulADodat 1 b = b
mulADodat _ 0 = 0
mulADodat a b = b + mulADodat (a - 1) b

mulBDodat a 1 = a
mulBDodat 0 _ = 0
mulBDodat a b = a + mulBDodat a (b - 1)

mul1 a b
  | a < 0 && b < 0 = mulADodat (-a) (-b)
  | a > 0 = mulADodat a b
  | otherwise = mulBDodat a b

mul2 a 1 = a
mul2 1 b = b
mul2 _ 0 = 0
mul2 0 _ = 0
mul2 a b
  | a < 0 && b < 0 = -b + mul2 (-a - 1) (-b)
  | a > 0 = b + mul2 (a - 1) b
  | otherwise = a + mul2 a (b - 1)
