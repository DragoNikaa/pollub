aktualna_wartosc = 0.
print(f"aktualna wartość = {aktualna_wartosc}\n")
operacja = int(input("Podaj operację (0 - wyjście, 1 [+], 2 [-], 3 [*], 4 [/], 5[^]): "))

while operacja != 0:
    if operacja == 1:
        liczba = float(input("Podaj liczbę do dodania: "))
        aktualna_wartosc += liczba

    elif operacja == 2:
        liczba = float(input("Podaj liczbę do odjęcia: "))
        aktualna_wartosc -= liczba

    elif operacja == 3:
        liczba = float(input("Podaj liczbę, przez którą przemnożyć wynik: "))
        aktualna_wartosc *= liczba

    elif operacja == 4:
        liczba = float(input("Podaj liczbę, przez którą podzielić wynik: "))

        if liczba == 0:
            print("Nie można dzielić przez 0!")
        else:
            aktualna_wartosc /= liczba

    elif operacja == 5:
        liczba = float(input("Podaj wykładnik potęgi: "))
        wynik = 1

        if liczba < 0:
            liczba = -liczba
            ujemna = True
        else:
            ujemna = False

        while liczba > 0:
            wynik *= aktualna_wartosc
            liczba -= 1

        if ujemna:
            wynik = 1 / wynik

        aktualna_wartosc = wynik

    else:
        print("Wprowadzono nieprawidłową wartość. Spróbuj ponownie.")

    print(f"aktualna wartość = {aktualna_wartosc}\n")
    operacja = int(input("Podaj operację (0 - wyjście, 1 [+], 2 [-], 3 [*], 4 [/], 5[^]): "))

print("Dziękujemy za skorzystanie z naszego kalkulatora!")
