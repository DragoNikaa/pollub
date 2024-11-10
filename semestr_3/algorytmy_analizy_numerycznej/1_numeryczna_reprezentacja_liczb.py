import numpy as np


def zamien_system_liczbowy(liczba: float, podstawa: int, dokladnosc: int) -> str:
    cyfra_na_litere = {i + 10: chr(ord('A') + i) for i in range(10)}

    liczba = int(liczba * pow(podstawa, dokladnosc))
    wynik = ""

    while liczba > 0:
        cyfra = str(liczba % podstawa)
        if int(cyfra) >= 10:
            cyfra = cyfra_na_litere[int(cyfra)]

        liczba //= podstawa
        wynik += cyfra

    if dokladnosc > 0:
        wynik = wynik[:dokladnosc] + '.' + wynik[dokladnosc:]

    return wynik[::-1]


type Rzeczywista = np.float32 | np.double


def wyznacz_pierwiastki_klasycznie(a: Rzeczywista, b: Rzeczywista, c: Rzeczywista) -> tuple[Rzeczywista, Rzeczywista]:
    delta = b * b - 4 * a * c
    return (-b - np.sqrt(delta)) / (2 * a), (-b + np.sqrt(delta)) / (2 * a)


def wyznacz_pierwiastki_niestandardowo(a: Rzeczywista, b: Rzeczywista, c: Rzeczywista) -> tuple[
    Rzeczywista, Rzeczywista]:
    wyrazenie = -b / (2 * a)
    if wyrazenie > 0:
        znak = 1
    elif wyrazenie == 0:
        znak = 0
    else:
        znak = -1

    if b == 0:
        x1 = np.sqrt(-c / a)
        x2 = -np.sqrt(-c / a)
    else:
        x1 = wyrazenie + znak * np.sqrt(pow(wyrazenie, 2) - c / a)
        x2 = c / (a * x1)

    return x1, x2


def sprawdz_rownanie(a: Rzeczywista, b: Rzeczywista, c: Rzeczywista) -> bool:
    if a == 0:
        print("Równanie nie jest kwadratowe.")
        return False

    elif b * b - 4 * a * c < 0:
        print("Równanie nie ma pierwiastków rzeczywistych.")
        return False

    return True


def main() -> None:
    nr_programu = int(input("Wybierz program:\n"
                            "0 - przelicz liczbę w zapisie dziesiętnym na zapis w systemie o podanej podstawie\n"
                            "1 - oblicz pierwiastki równania kwadratowego klasycznymi wzorami\n"
                            "2 - oblicz pierwiastki równania kwadratowego niestandardowymi wzorami\n"))
    print()

    if nr_programu == 0:
        liczba = float(input("Podaj liczbę w zapisie dziesiętnym: "))
        podstawa = int(input("Podaj podstawę systemu (2-20): "))
        dokladnosc = int(input("Podaj liczbę cyfr po przecinku: "))

        print(f"\nWynik: {zamien_system_liczbowy(liczba, podstawa, dokladnosc)}")

    else:
        print("a * x^2 + b * x + c = 0")
        a_str = input("a = ")
        b_str = input("b = ")
        c_str = input("c = ")
        print()

        # a, b, c = (np.float32(liczba) for liczba in [a_str, b_str, c_str])
        a, b, c = (np.double(liczba) for liczba in [a_str, b_str, c_str])

        if not sprawdz_rownanie(a, b, c):
            return

        if nr_programu == 1:
            x1, x2 = wyznacz_pierwiastki_klasycznie(a, b, c)
            print(f"Pierwiastki równania {a} * x^2 + {b} * x + {c} = 0 obliczone klasycznymi wzorami:\n"
                  f"x1 = {x1}\nx2 = {x2}")

        else:
            x1, x2 = wyznacz_pierwiastki_niestandardowo(a, b, c)
            print(f"Pierwiastki równania {a} * x^2 + {b} * x + {c} = 0 obliczone niestandardowymi wzorami:\n"
                  f"x1 = {x1}\nx2 = {x2}")


if __name__ == "__main__":
    main()
