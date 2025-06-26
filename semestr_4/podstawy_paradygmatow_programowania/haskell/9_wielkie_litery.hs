czyWielka x = x `elem` ['A' .. 'Z']

usunWielkie1 xs =
  [x | x <- xs, x `elem` ['a' .. 'z']]

usunWielkie2 xs =
  [x | x <- xs, not (x `elem` [' '] ++ ['A' .. 'Z'])]

usunWielkie3 xs =
  filter (`elem` ['a' .. 'z']) xs
