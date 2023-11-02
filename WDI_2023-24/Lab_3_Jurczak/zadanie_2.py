n = int(input("Podaj liczbę n: "))
print()
licznik = 0
suma_kwadratow = 0

while licznik < n:
    liczba = int(input("Podaj liczbę z przedziału od 10 do 50: "))
    if not 10 <= liczba <= 50:
        print("Podano niepoprawną liczbę (nie będzie brana pod uwagę).")
    else:
        suma_kwadratow += liczba ** 2
        licznik += 1

if licznik <= 0:
    print("Nie podano żadnych liczb.")
else:
    print(f"\nSuma kwadratów podanych liczb to: {suma_kwadratow}")
