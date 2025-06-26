-- naturalne nieparzyste z dokładnie jedną cyfrą 0

ileLiczb1 min max = ileLiczbPom min max 0

ileLiczbPom min max licznik =
  if min < 0 || max < 0
    then error "liczby ujemne"
    else
      if min > max
        then licznik
        else
          if even min
            then ileLiczbPom (min + 1) max licznik
            else
              if ileZer min 0 == 1
                then ileLiczbPom (min + 2) max (licznik + 1)
                else ileLiczbPom (min + 2) max licznik

ileLiczb2 min max =
  if min < 0 || max < 0
    then error "liczby ujemne"
    else length [x | x <- [min .. max], odd x, ileZer x 0 == 1]

ileZer 0 licznik = licznik
ileZer liczba licznik =
  ileZer
    (liczba `div` 10)
    ( if liczba `mod` 10 == 0
        then licznik + 1
        else licznik
    )
