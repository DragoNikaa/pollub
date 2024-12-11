import math
from typing import Callable


def wyznacz_wartosc_calki_wzor_trapezow(h: float, y: list[float]) -> float:
    t = (y[0] + y[-1]) / 2
    t += sum(y[1:-1])
    return h * t


def wyznacz_wartosc_calki_wzor_simpsona(h: float, y: list[float]) -> float:
    s = y[0] + y[-1]
    s += 4 * sum(y[1:-1:2]) + 2 * sum(y[2:-2:2])
    return h / 3 * s


def wyznacz_wartosc_calki_wzor_simpsona_3_8(h: float, y: list[float]) -> float:
    s = y[0] + y[-1]
    s += 3 * sum(y[1:-2:3] + y[2:-1:3]) + 2 * sum(y[3:-3:3])
    return 3 / 8 * h * s


def main() -> None:
    nr_calki = int(input("Wybierz całkę:\n"
                         "1 - ∫ sqrt(1 + x) dx\t\tod 0 do 1\n"
                         "2 - ∫ (sin(x) ^ 2 + 2) dx\tod 0 do 2π\n"
                         "3 - ∫ e ^ x * 2 * x ^ 3 dx\tod 0 do 2\n"
                         "4 - ∫ x * e ^ (2 * x) dx\tod 0 do 4\n"))

    nr_wzoru = int(input("\nWybierz wzór:\n"
                         "1 - wzór złożony trapezów\n"
                         "2 - wzór złożony Simpsona\n"
                         "3 - wzór złożony Simpsona 3/8\n"))

    podprzedzialy = int(input("\nPodaj liczbę podprzedziałów (m > 1): "))
    print()

    match nr_calki:
        case 1:
            a, b = 0., 1.
            f: Callable[[float], float] = lambda x: math.sqrt(1 + x)
        case 2:
            a, b = 0, 2 * math.pi
            f = lambda x: pow(math.sin(x), 2) + 2
        case 3:
            a, b = 0, 2
            f = lambda x: math.exp(x) * 2 * pow(x, 3)
        case _:
            a, b = 0, 4
            f = lambda x: x * math.exp(2 * x)

    h = (b - a) / podprzedzialy
    x = [a + i * h for i in range(podprzedzialy + 1)]
    y = [f(xi) for xi in x]

    match nr_wzoru:
        case 1:
            print(f"Przybliżona wartość całki obliczona za pomocą wzoru złożonego trapezów: "
                  f"{wyznacz_wartosc_calki_wzor_trapezow(h, y):.4f}")
        case 2:
            if podprzedzialy % 2:
                print("Założenia wzoru nie są spełnione. Liczba podprzedziałów musi być parzysta.")
                return

            print(f"Przybliżona wartość całki obliczona za pomocą wzoru złożonego Simpsona: "
                  f"{wyznacz_wartosc_calki_wzor_simpsona(h, y):.4f}")
        case _:
            if podprzedzialy % 3:
                print("Założenia wzoru nie są spełnione. Liczba podprzedziałów musi być podzielna przez 3.")
                return

            print(f"Przybliżona wartość całki obliczona za pomocą wzoru złożonego Simpsona 3/8: "
                  f"{wyznacz_wartosc_calki_wzor_simpsona_3_8(h, y):.4f}")


if __name__ == "__main__":
    main()
