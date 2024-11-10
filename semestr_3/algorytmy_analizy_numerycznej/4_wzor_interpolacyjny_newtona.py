import numpy as np


def wyznacz_wartosc_wielomianu(wezly: int, x: list[float], y: list[float], punkt: float) -> float:
    y = wyznacz_ilorazy_roznicowe(wezly, x, y)
    wynik = 0.0

    for i in range(wezly):
        for k in range(i):
            y[i] *= (punkt - x[k])

        wynik += y[i]

    return wynik


def wyznacz_wielomian(wezly: int, x: list[float], y: list[float]) -> np.poly1d:
    y = wyznacz_ilorazy_roznicowe(wezly, x, y)
    wielomian = np.poly1d(0)

    for i in range(wezly):
        skladnik = np.poly1d(y[i])

        for k in range(i):
            skladnik *= [1, -x[k]]

        wielomian += skladnik

    return wielomian


def wyznacz_ilorazy_roznicowe(wezly: int, x: list[float], y: list[float]) -> list[float]:
    for i in range(1, wezly):
        for k in range(wezly - 1, i - 1, -1):
            y[k] = (y[k] - y[k - 1]) / (x[k] - x[k - i])

    return y


def sprawdz_wezly(wezly: int, x: list[float]) -> bool:
    for i in range(wezly - 1):
        if x[i] >= x[i + 1]:
            print("Założenia dotyczące węzłów nie są spełnione.")
            return False

    print("Założenia dotyczące węzłów są spełnione.")
    return True


def sprawdz_punkt(wezly: int, x: list[float], punkt: float) -> bool:
    if punkt < x[0] or punkt > x[wezly - 1]:
        print(f"Podany punkt nie należy do przedziału [{x[0]}, {x[wezly - 1]}].")
        return False

    print(f"Podany punkt należy do przedziału [{x[0]}, {x[wezly - 1]}].")
    return True


def main() -> None:
    nr_programu = int(input("Wybierz program:\n"
                            "0 - wyznacz wartość wielomianu interpolacyjnego Newtona w podanym punkcie\n"
                            "1 - wyznacz wielomian interpolacyjny Newtona\n"))

    wezly = int(input("\nPodaj liczbę węzłów (w >= 1): "))

    print()
    x = [float(input(f"x[{i}] = ")) for i in range(wezly)]
    print()
    y = [float(input(f"y[{i}] = ")) for i in range(wezly)]
    print()

    if nr_programu == 0:
        punkt = float(input("Podaj, w jakim punkcie obliczyć wartość wielomianu: "))
        print()
    else:
        punkt = 0

    if not sprawdz_wezly(wezly, x) or (nr_programu == 0 and not sprawdz_punkt(wezly, x, punkt)):
        print("Nie można wyznaczyć wielomianu interpolacyjnego Newtona.")
        return

    if nr_programu == 0:
        print(f"Wartość wielomianu Newtona w podanym punkcie: {wyznacz_wartosc_wielomianu(wezly, x, y, punkt):.2f}")
    else:
        wielomian = wyznacz_wielomian(wezly, x, y)
        wielomian = np.poly1d(np.where(abs(wielomian.coeffs) < 1e-10, 0, wielomian.coeffs))
        print(f"Wielomian interpolacyjny Newtona:\n{wielomian}")


if __name__ == "__main__":
    main()
