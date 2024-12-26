import math
from typing import Callable


def wyznacz_wartosc_calki_gauss(n: int, x: list[float], H: list[float], f: Callable[[float], float]) -> float:
    Q = 0.
    for j in range(n + 1):
        Q += H[j] * f(x[j])
    return Q


def main() -> None:
    nr_calki = int(input("Wybierz całkę:\n"
                         "1 - ∫ x ^ 2 / sqrt(1 - x ^ 2) dx\tod -1 do 1,\tGauss-Chebyshev\tdla n = 3\n"
                         "2 - ∫ 1 / sqrt(1 + x ^ 2) dx\t\tod -1 do 1,\tGauss-Legendre\tdla n = 4\n"
                         "3 - ∫ e ^ (-2 * x) * sin(x) / x dx\tod 0 do ∞,\tGauss-Laguerre\tdla n = 3\n"
                         "4 - ∫ e ^ (-x ^ 2) * cos(x) dx\t\tod -∞ do ∞,\tGauss-Hermite\tdla n = 4\n"))
    print()

    match nr_calki:
        case 1:
            n = 3
            x = [math.cos((2 * j + 1) * math.pi / (2 * (n + 1))) for j in range(n + 1)]
            H = [math.pi / (n + 1)] * (n + 1)
            f: Callable[[float], float] = lambda xi: pow(xi, 2)

            print(f"Przybliżona wartość całki obliczona za pomocą kwadratury Gauss-Chebyshev: "
                  f"{wyznacz_wartosc_calki_gauss(n, x, H, f):.4f}")
        case 2:
            n = 4
            x = [-0.906180, -0.538469, 0, 0.538469, 0.906180]
            H = [0.236927, 0.478629, 0.568889, 0.478629, 0.236927]
            f = lambda xi: 1 / math.sqrt(1 + pow(xi, 2))

            print(f"Przybliżona wartość całki obliczona za pomocą kwadratury Gauss-Legendre: "
                  f"{wyznacz_wartosc_calki_gauss(n, x, H, f):.4f}")
        case 3:
            n = 3
            x = [0.322548, 1.745761, 4.536620, 9.395071]
            H = [0.603154, 0.357419, 0.038888, 0.000539]
            f = lambda xi: math.exp(-xi) * math.sin(xi) / xi

            print(f"Przybliżona wartość całki obliczona za pomocą kwadratury Gauss-Laguerre: "
                  f"{wyznacz_wartosc_calki_gauss(n, x, H, f):.4f}")
        case _:
            n = 4
            x = [-2.020183, -0.958572, 0, 0.958572, 2.020183]
            H = [0.019953, 0.393619, 0.945309, 0.393619, 0.019953]
            f = lambda xi: math.cos(xi)

            print(f"Przybliżona wartość całki obliczona za pomocą kwadratury Gauss-Hermite: "
                  f"{wyznacz_wartosc_calki_gauss(n, x, H, f):.4f}")


if __name__ == "__main__":
    main()
