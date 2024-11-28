import math


def wyznacz_wspolczynniki_funkcji(wezly: int, x: list[float], y: list[float], funkcja: int) -> tuple[float, float]:
    if funkcja == 1:
        x = [1 / xi for xi in x]
    elif funkcja >= 2:
        y = [math.log(yi) for yi in y]

    suma_x = sum(x)
    suma_y = sum(y)
    suma_x_2 = sum(xi ** 2 for xi in x)
    suma_x_y = sum(xi * yi for xi, yi in zip(x, y))

    w = wezly * suma_x_2 - suma_x * suma_x
    w0 = suma_y * suma_x_2 - suma_x_y * suma_x
    w1 = wezly * suma_x_y - suma_x * suma_y

    a0, a1 = w0 / w, w1 / w

    if funkcja in (0, 1):
        return a0, a1
    return math.exp(a0), math.exp(a1)


def main() -> None:
    wezly = int(input("Podaj liczbę węzłów (w >= 1): "))

    print()
    x = [float(input(f"x[{i}] = ")) for i in range(wezly)]
    print()
    y = [float(input(f"y[{i}] = ")) for i in range(wezly)]
    print()

    funkcja = int(input("Wybierz funkcję aproksymującą:\n"
                        "0 -> g(x) = a * x + b\n"
                        "1 -> g(x) = a / x + b\n"
                        "2 -> g(x) = b * a ^ x\n"))

    b, a = wyznacz_wspolczynniki_funkcji(wezly, x, y, funkcja)
    print(f"\nWspółczynniki funkcji, która najlepiej aproksymuje podane dane w sensie najmniejszych kwadratów:\n"
          f"a = {a:.2f}, b = {b:.2f}\n\n"
          f"Wzór funkcji aproksymującej:")

    match funkcja:
        case 0:
            print(f"g(x) = {a:.2f} * x + {b:.2f}")
        case 1:
            print(f"g(x) = {a:.2f} / x + {b:.2f}")
        case _:
            print(f"g(x) = {b:.2f} * {a:.2f} ^ x")


if __name__ == "__main__":
    main()
