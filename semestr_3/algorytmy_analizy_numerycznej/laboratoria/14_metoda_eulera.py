from typing import Callable


def wyznacz_rozwiazanie_rownania(n: int, a: float, y: float, b: float,
                                 krok: Callable[[float, float, float], float]) -> float:
    h = (b - a) / n
    for _ in range(n):
        y += krok(h, a, y)
        a += h
    return y


def main() -> None:
    nr_rownania = int(input("Wybierz równanie różniczkowe i metodę rozwiązania:\n"
                            "1 - y' = y ^ 2 / (x + 1),\ty(0) = 3\t->\tmetoda Eulera\n"
                            "2 - y' = y / x ^ 2,\t\t\ty(1) = 2\t->\tmetoda Heuna\n"
                            "3 - y' = 2 * x * y,\t\t\ty(0) = 1\t->\tmetoda zmodyfikowana Eulera\n"))
    n = int(input("\nPodaj liczbę podprzedziałów (n > 1): "))
    b = float(input("Podaj punkt, w którym obliczana będzie wartość funkcji: "))

    match nr_rownania:
        case 1:
            a, y = 0., 3.
            f: Callable[[float, float], float] = lambda xi, yi: pow(yi, 2) / (xi + 1)
            krok = lambda h, xi, yi: h * f(xi, yi)

            print(f"\nRozwiązanie równania różniczkowego metodą Eulera:\n"
                  f"y({b}) = {wyznacz_rozwiazanie_rownania(n, a, y, b, krok):.4f}")
        case 2:
            a, y = 1., 2.
            f = lambda xi, yi: yi / pow(xi, 2)
            krok = lambda h, xi, yi: h / 2 * (f(xi, yi) + f(xi + h, yi + h * f(xi, yi)))

            print(f"\nRozwiązanie równania różniczkowego metodą Heuna:\n"
                  f"y({b}) = {wyznacz_rozwiazanie_rownania(n, a, y, b, krok):.4f}")
        case _:
            a, y = 0., 1.
            f = lambda xi, yi: 2 * xi * yi
            krok = lambda h, xi, yi: h * f(xi + h / 2, yi + h / 2 * f(xi, yi))

            print(f"\nRozwiązanie równania różniczkowego metodą zmodyfikowaną Eulera:\n"
                  f"y({b}) = {wyznacz_rozwiazanie_rownania(n, a, y, b, krok):.4f}")


if __name__ == "__main__":
    main()
