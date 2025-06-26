delta a b c = b * b - 4 * a * c

pierwDeltaWhere a b c =
  if d > 0
    then sqrt d
    else
      if d == 0
        then 0
        else error "delta < 0"
  where
    d = delta a b c

pierwDeltaLetIn a b c =
  let d = delta a b c
   in if d > 0
        then sqrt d
        else
          if d == 0
            then 0
            else error "delta < 0"
