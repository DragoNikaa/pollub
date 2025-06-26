pierw1 n =
  if n >= 0
    then sqrt n
    else error "liczba ujemna"

pierw2 n =
  sqrt
    ( if n >= 0
        then n
        else error "liczba ujemna"
    )
