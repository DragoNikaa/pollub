import random
import time

gracz_1_chce_grac = gracz_2_chce_grac = True
wynik_gracz_1 = wynik_gracz_2 = 0
licznik_asow_gracz_1 = licznik_asow_gracz_2 = 0
licznik_rund = 0

while gracz_1_chce_grac and gracz_2_chce_grac:
    licznik_rund += 1

    print("*dobieranie kart*\n")
    time.sleep(1)

    dobrana_karta_gracz_1 = random.randint(2, 14)

    if 2 <= dobrana_karta_gracz_1 <= 10:
        wynik_gracz_1 += dobrana_karta_gracz_1
    elif 11 <= dobrana_karta_gracz_1 <= 13:
        wynik_gracz_1 += dobrana_karta_gracz_1 - 9
    else:
        wynik_gracz_1 += 11
        licznik_asow_gracz_1 += 1

    print(f"Gracz_1 dobiera kartę: {dobrana_karta_gracz_1}\nWynik gracza_1: {wynik_gracz_1}\n")
    time.sleep(1)

    dobrana_karta_gracz_2 = random.randint(2, 14)

    if 2 <= dobrana_karta_gracz_2 <= 10:
        wynik_gracz_2 += dobrana_karta_gracz_2
    elif 11 <= dobrana_karta_gracz_2 <= 13:
        wynik_gracz_2 += dobrana_karta_gracz_2 - 9
    else:
        wynik_gracz_2 += 11
        licznik_asow_gracz_2 += 1

    print(f"Gracz_2 dobiera kartę: {dobrana_karta_gracz_2}\nWynik gracza_2: {wynik_gracz_2}\n")
    time.sleep(1)

    if wynik_gracz_1 >= 21 or wynik_gracz_2 >= 21:
        break

    gracz_1_chce_grac = int(input("[gracz_1] Czy chcesz grać dalej? (0 - nie, 1 - tak): "))
    gracz_2_chce_grac = int(input("[gracz_2] Czy chcesz grać dalej? (0 - nie, 1 - tak): "))
    print()

if wynik_gracz_1 == wynik_gracz_2:
    print("Remis!")
elif ((licznik_asow_gracz_1 == 2 and licznik_rund == 2) or
      wynik_gracz_2 < wynik_gracz_1 <= 21 or wynik_gracz_1 <= 21 < wynik_gracz_2 or 21 <= wynik_gracz_1 < wynik_gracz_2):
    print("Gracz_1 wygrywa grę! 🏆")
elif ((licznik_asow_gracz_2 == 2 and licznik_rund == 2) or
      wynik_gracz_1 < wynik_gracz_2 <= 21 or wynik_gracz_2 <= 21 < wynik_gracz_1 or 21 <= wynik_gracz_2 < wynik_gracz_1):
    print("Gracz_2 wygrywa grę! 🏆")
