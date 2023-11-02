import random
import time

print("Pozycja gracza:\n..a......b...e...c.d\n^")
time.sleep(2)

pozycja_gracza = tury_do_odczekania = 0
pulapka = False

while pozycja_gracza != 19:
    if tury_do_odczekania != 0:
        print(f"\nGracz musi odczekać jeszcze {tury_do_odczekania} tur, zanim kontynuuje grę.")
        tury_do_odczekania -= 1
        time.sleep(2)
        continue

    rzut_kostka = random.randint(1, 4)
    print(f"\nOczka wyrzucone przez gracza: {rzut_kostka}")

    if pulapka:
        if rzut_kostka == 4:
            print("Gratulacje, gracz wydostaje sie z pulapki!")
            pulapka = False
        else:
            print("Niestety, gracz nie wydostaje sie z pulapki.")
        time.sleep(2)
        continue

    pozycja_gracza += rzut_kostka

    if pozycja_gracza > 19:
        pozycja_gracza = pozycja_gracza - 20

    print(f"..a......b...e...c.d\n{pozycja_gracza * ' '}^")
    time.sleep(2)

    if pozycja_gracza == 2:
        pozycja_gracza += 5
        print(f"\nGratulacje, gracz idzie 5 pól do przodu!\n..a......b...e...c.d\n{pozycja_gracza * ' '}^")
        time.sleep(2)

    elif pozycja_gracza == 9:
        tury_do_odczekania = random.randint(1, 3)

    elif pozycja_gracza == 13:
        pulapka = True
        print("\nNiestety, gracz wpada w pułapkę i traci turę! "
              "Aby wydostać się z pułapki, musi uzyskać 4 oczka na kostce w kolejnych turach.")
        time.sleep(2)

    elif pozycja_gracza == 17:
        pozycja_gracza = 0
        print(f"\nNiestety, gracz wraca na start!\n..a......b...e...c.d\n{pozycja_gracza * ' '}^")
        time.sleep(2)

print("\nGratulacje, gracz wygrywa grę! 🏆")
