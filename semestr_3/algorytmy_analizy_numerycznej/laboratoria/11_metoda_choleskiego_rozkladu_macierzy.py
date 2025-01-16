import math
from typing import Literal


def wyznacz_macierz_L(n: int, A: list[list[float]]) -> list[list[float]]:
    L = [n * [0.] for _ in range(n)]

    for i in range(n):
        for j in range(i, n):
            if i == j:
                L[i][i] = math.sqrt(A[i][i] - sum(pow(L[i][k], 2) for k in range(i)))
            else:
                L[j][i] = L[i][j] = (A[j][i] - sum(L[i][k] * L[j][k] for k in range(i))) / L[i][i]

    return L


def rozwiaz_uklad_macierz_trojkatna(rodzaj: Literal["górna", "dolna"], n: int, L: list[list[float]],
                                    wektor_we: list[float]) -> list[float]:
    if rodzaj == "górna":
        start, stop, krok = 0, n, 1
    else:
        start, stop, krok = n - 1, -1, -1

    wektor_wy = n * [0.]

    for i in range(start, stop, krok):
        wektor_wy[i] = (wektor_we[i] - sum(L[i][j] * wektor_wy[j] for j in range(start, i, krok))) / L[i][i]

    return wektor_wy


def wydrukuj_macierz(n: int, A: list[list[float]], b: list[float]) -> None:
    for i in range(n):
        if i == n // 2:
            print("[A | b] = ", end="")
        else:
            print(10 * " ", end="")

        if i == 0:
            print("⎡", end="")
        elif i == n - 1:
            print("⎣", end="")
        else:
            print("⎢", end="")

        for a in A[i]:
            print(f"{a:8.2f}", end="")
        print(f"   ⎢{b[i]:8.2f}   ", end="")

        if i == 0:
            print("⎤")
        elif i == n - 1:
            print("⎦")
        else:
            print("⎢")


def main() -> None:
    nr_danych = int(input("Wybierz dane wejściowe:\n"
                          "0 - podane przez użytkownika\n"
                          "1 - zawarte w programie\n"))

    if nr_danych == 0:
        n = int(input("\nPodaj liczbę równań: "))
        print("\nPodaj A:")
        A = [[float(input(f"a[{i}][{j}] = ")) for j in range(1, n + 1)] for i in range(1, n + 1)]
        print("\nPodaj b:")
        b = [float(input(f"b[{i}] = ")) for i in range(1, n + 1)]
    else:
        n = 4
        A = [[4, 6, -4, -6], [6, 25, 6, -17], [-4, 6, 14, 1], [-6, -17, 1, 23]]
        b = [2, -13, -14, 5]

        # n = 3
        # A = [[4, 6, -4], [6, 25, 6], [-4, 6, 14]]
        # b = [2, -13, -14]

    print()
    wydrukuj_macierz(n, A, b)
    print()

    L = wyznacz_macierz_L(n, A)
    y = rozwiaz_uklad_macierz_trojkatna("górna", n, L, b)
    x = rozwiaz_uklad_macierz_trojkatna("dolna", n, L, y)

    print("Rozwiązanie układu równań:")
    for i, xi in enumerate(x):
        print(f"x[{i + 1}] = {xi:.2f}")


if __name__ == "__main__":
    main()
