def eliminacja_gaussa_podstawowa(A: list[list[float]], b: list[float]) -> list[float] | None:
    n = len(b)

    for i in range(n):
        if abs(A[i][i]) < 1e-7:
            print("Układu nie da się rozwiązać podstawową metodą eliminacji Gaussa.")
            return None

        oblicz_elementy_macierzy(A, b, i, n)

    return rozwiaz_uklad(A, b)


def eliminacja_gaussa_pelny_wybor(A: list[list[float]], b: list[float]) -> list[float] | None:
    n = len(b)
    zamiany: list[tuple[int, int]] = []

    for i in range(n):
        max_a, max_x, max_y = znajdz_max(A, i, n)

        if max_a < 1e-7:
            for bi in b:
                if abs(bi) > 1e-7:
                    print("Układ jest sprzeczny.")
                    return None

            print("Układ ma nieskończenie wiele rozwiązań.")
            return None

        zamien_wiersze_i_kolumny(A, b, zamiany, i, n, max_x, max_y)
        oblicz_elementy_macierzy(A, b, i, n)

    rozwiazanie = rozwiaz_uklad(A, b)

    for i, j in zamiany[::-1]:
        rozwiazanie[i], rozwiazanie[j] = rozwiazanie[j], rozwiazanie[i]

    return rozwiazanie


def oblicz_elementy_macierzy(A: list[list[float]], b: list[float], i: int, n: int) -> None:
    for j in range(i + 1, n):
        czynnik = A[j][i] / A[i][i]
        A[j][i] = 0
        b[j] -= czynnik * b[i]

        for k in range(i + 1, n):
            A[j][k] -= czynnik * A[i][k]


def znajdz_max(A: list[list[float]], i: int, n: int) -> tuple[float, int, int]:
    max_a = abs(A[i][i])
    max_x, max_y = i, i

    for x in range(i, n):
        for y in range(i, n):
            if abs(A[x][y]) > max_a:
                max_a = abs(A[x][y])
                max_x, max_y = x, y

    return max_a, max_x, max_y


def zamien_wiersze_i_kolumny(A: list[list[float]], b: list[float], zamiany: list[tuple[int, int]], i: int, n: int,
                             max_x: int, max_y: int) -> None:
    if max_x != i:
        A[i], A[max_x] = A[max_x], A[i]
        b[i], b[max_x] = b[max_x], b[i]

    if max_y != i:
        zamiany.append((i, max_y))

        for j in range(n):
            A[j][i], A[j][max_y] = A[j][max_y], A[j][i]


def rozwiaz_uklad(A: list[list[float]], b: list[float]) -> list[float]:
    n = len(b)

    for i in range(n - 1, -1, -1):
        for j in range(n - 1, i, -1):
            b[i] -= b[j] * A[i][j]

        b[i] /= A[i][i]

    return b


def wydrukuj_macierz(A: list[list[float]], b: list[float]) -> None:
    n = len(b)

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
            print(f"{a:7.2f}", end="")
        print(f"   ⎢{b[i]:7.2f}   ", end="")

        if i == 0:
            print("⎤")
        elif i == n - 1:
            print("⎦")
        else:
            print("⎢")


def main() -> None:
    nr_metody = int(input("Wybierz metodę eliminacji Gaussa:\n"
                          "0 - podstawowa\n"
                          "1 - z pełnym wyborem elementu maksymalnego\n"))

    nr_danych = int(input("\nWybierz dane wejściowe:\n"
                          "0 - podane przez użytkownika\n"
                          "1 - zawarte w programie\n"))

    if nr_danych == 0:
        n = int(input("\nPodaj liczbę równań: "))
        print("\nPodaj A:")
        A = [[float(input(f"a[{i}][{j}] = ")) for j in range(1, n + 1)] for i in range(1, n + 1)]
        print("\nPodaj b:")
        b = [float(input(f"b[{i}] = ")) for i in range(1, n + 1)]
    else:
        # A = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]
        # b = [0, 0, 0]

        A = [[1, -2, 0, 3, 1], [2, -3, 1, 8, 2], [1, -2, 1, 3, -1], [0, 1, 0, 3, 5], [1, -2, 0, 5, 8]]
        b = [1, 3, 1, 0, -1]

    print()
    wydrukuj_macierz(A, b)
    print()

    if nr_metody == 0:
        rozwiazanie = eliminacja_gaussa_podstawowa(A, b)
    else:
        rozwiazanie = eliminacja_gaussa_pelny_wybor(A, b)

    if rozwiazanie:
        print("Rozwiązanie układu równań:")
        for i, x in enumerate(rozwiazanie):
            print(f"x[{i + 1}] = {x:.2f}")


if __name__ == "__main__":
    main()
