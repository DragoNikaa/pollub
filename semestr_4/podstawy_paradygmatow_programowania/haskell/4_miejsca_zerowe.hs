delta a b c = b * b - 4 * a * c

miejscaZeroweGlobal a b c =
  if a == 0
    then error "a = 0"
    else
      if delta a b c < 0
        then error "delta < 0"
        else
          if delta a b c == 0
            then [-b / (2 * a)]
            else
              [ (-b - sqrt (delta a b c)) / (2 * a),
                (-b + sqrt (delta a b c)) / (2 * a)
              ]

miejscaZeroweLokal a b c =
  if a == 0
    then error "a = 0"
    else
      if delta < 0
        then error "delta < 0"
        else
          if delta == 0
            then [x1]
            else [x1, x2]
  where
    delta = b * b - 4 * a * c
    x1 = (-b - sqrt delta) / (2 * a)
    x2 = (-b + sqrt delta) / (2 * a)
