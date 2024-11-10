import numpy as np


def wyznacz_wartosc_wielomianu(wezly: int, x: list[float], y: list[float], punkt: float) -> float:
    wynik = 0.0

    for i in range(wezly):
        l = y[i]

        for k in range(wezly):
            if i != k:
                l *= (punkt - x[k]) / (x[i] - x[k])

        wynik += l

    return wynik


def wyznacz_wielomian(wezly: int, x: list[float], y: list[float]) -> np.poly1d:
    l = [np.poly1d(y[i]) for i in range(wezly)]
    wielomian = np.poly1d(0)

    for i in range(wezly):
        for k in range(wezly):
            if i != k:
                l[i] *= [1, -x[k]]
                l[i] /= x[i] - x[k]

        wielomian += l[i]

    return wielomian


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
                            "0 - wyznacz wartość wielomianu interpolacyjnego Lagrange'a w podanym punkcie\n"
                            "1 - wyznacz wielomian interpolacyjny Lagrange'a\n"))

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
        print("Nie można wyznaczyć wielomianu interpolacyjnego Lagrange'a.")
        return

    if nr_programu == 0:
        print(f"Wartość wielomianu Lagrange'a w podanym punkcie: {wyznacz_wartosc_wielomianu(wezly, x, y, punkt):.2f}")
    else:
        wielomian = wyznacz_wielomian(wezly, x, y)
        wielomian = np.poly1d(np.where(abs(wielomian.coeffs) < 1e-10, 0, wielomian.coeffs))
        print(f"Wielomian interpolacyjny Lagrange'a:\n{wielomian}")


if __name__ == "__main__":
    main()
