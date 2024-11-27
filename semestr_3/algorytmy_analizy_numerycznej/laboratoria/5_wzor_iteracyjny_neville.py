def wyznacz_wartosc_wielomianu(wezly: int, x: list[float], y: list[float], punkt: float) -> float:
    for k in range(1, wezly):
        for j in range(wezly - k):
            y[j] = ((punkt - x[j]) * y[j + 1] - (punkt - x[j + k]) * y[j]) / (x[j + k] - x[j])

    return y[0]


def sprawdz_wezly(wezly: int, x: list[float], y: list[float]) -> tuple[list[float], list[float]] | None:
    x, y = map(list, zip(*sorted(dict(zip(x, y)).items())))

    if len(x) != wezly:
        print("Założenia dotyczące węzłów nie są spełnione.")
        return None

    print("Założenia dotyczące węzłów są spełnione.")
    return x, y


def sprawdz_punkt(wezly: int, x: list[float], punkt: float) -> bool:
    if punkt < x[0] or punkt > x[wezly - 1]:
        print(f"Podany punkt nie należy do przedziału [{x[0]}, {x[wezly - 1]}].")
        return False

    print(f"Podany punkt należy do przedziału [{x[0]}, {x[wezly - 1]}].")
    return True


def main() -> None:
    wezly = int(input("Podaj liczbę węzłów (w >= 1): "))

    print()
    x = [float(input(f"x[{i}] = ")) for i in range(wezly)]
    print()
    y = [float(input(f"y[{i}] = ")) for i in range(wezly)]
    print()

    punkt = float(input("Podaj, w jakim punkcie obliczyć wartość wielomianu: "))
    print()

    punkty = sprawdz_wezly(wezly, x, y)
    if not punkty or not sprawdz_punkt(wezly, punkty[0], punkt):
        print("Nie można wyznaczyć przybliżonej wartości wielomianu według algorytmu Neville'a.")
        return

    print(f"Wartość wielomianu obliczona według algorytmu Neville'a w podanym punkcie: "
          f"{wyznacz_wartosc_wielomianu(wezly, punkty[0], punkty[1], punkt):.2f}")


if __name__ == "__main__":
    main()
