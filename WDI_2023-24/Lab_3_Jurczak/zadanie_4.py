liczba = int(input("Podaj liczbę parzystą, aby kontynuować lub nieparzystą, aby zakończyć: "))
minimum = maximum = liczba
licznik = 0

while liczba % 2 == 0:
    if liczba < minimum:
        minimum = liczba
    if liczba > maximum:
        maximum = liczba

    liczba = int(input("Podaj liczbę parzystą, aby kontynuować lub nieparzystą, aby zakończyć: "))
    licznik += 1

if licznik == 0:
    print("\nNie podano żadnych liczb parzystych.")
else:
    wynik = 1
    licznik = 0

    while licznik < abs(maximum):
        wynik *= minimum
        licznik += 1

    if maximum < 0:
        wynik = 1 / wynik

    print(f"\nWynik potęgowania (minimum^maximum = {minimum}^{maximum}) to: {wynik}")
