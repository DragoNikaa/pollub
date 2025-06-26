czyPalindrom tekst =
  if n <= 1
    then True
    else
      if (tekst !! 0) == (tekst !! (n - 1))
        then czyPalindrom (take (n - 2) (drop 1 tekst))
        else False
  where
    n = length tekst
