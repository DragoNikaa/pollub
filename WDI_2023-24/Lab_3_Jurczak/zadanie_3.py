suma = 0
iloczyn = 1
licznik = 0

while suma <= 255 and iloczyn <= 50_000:
    liczba = int(input("Podaj liczbę: "))
    suma += liczba
    iloczyn *= liczba
    licznik += 1

srednia = suma / licznik
print(f"\nŚrednia arytmetyczna podanych liczb to: {srednia:.2f}")
print(f"(suma = {suma}, iloczyn = {iloczyn})")
