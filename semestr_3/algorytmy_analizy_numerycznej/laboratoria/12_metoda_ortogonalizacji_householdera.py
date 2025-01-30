import math


def przesztalcenie_householdera(a: list[float]) -> list[float]:
    norma = math.sqrt(sum(pow(ai, 2) for ai in a))

    if a[0] < 0:
        znak = -1
    elif a[0] == 0:
        znak = 0
    else:
        znak = 1

    a = len(a) * [0.]
    a[0] = -1 * znak * norma

    return a


def main() -> None:
    n = int(input("Podaj wymiar wektora: "))
    print("\nPodaj współrzędne wektora:")
    a = [float(input(f"a[{i}] = ")) for i in range(1, n + 1)]

    print(f"\nWektor a po przekształceniu na kierunek wektora e1, stosując przekształcenie Householdera:"
          f"\na = {przesztalcenie_householdera(a)}T")


if __name__ == "__main__":
    main()
