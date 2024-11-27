def wyznacz_ilorazy_roznicowe(wezly: int, x: list[float], f: list[float]) -> list[float]:
    ilorazy = []
    indeks_wartosci_funkcji = 0

    for i in range(1, wezly):
        if x[i] == x[i - 1]:
            ilorazy.append(f[indeks_wartosci_funkcji + 1])
        else:
            ilorazy.append((f[i] - f[indeks_wartosci_funkcji]) / (x[i] - x[i - 1]))
            indeks_wartosci_funkcji = i

    return ilorazy


def main() -> None:
    wezly = int(input("Podaj liczbę węzłów (w >= 1): "))

    print("\nPodaj wartości węzłów:")
    x = [float(input()) for _ in range(wezly)]
    print("\nPodaj wartości funkcji i odpowiednich pochodnych w tych węzłach:")
    f = [float(input()) for _ in range(wezly)]
    print()

    print(f"Ilorazy różnicowe pierwszego rzędu zdefiniowane w interpolacji wielomianowej Hermite'a:")
    for iloraz in wyznacz_ilorazy_roznicowe(wezly, x, f):
        print(iloraz)


if __name__ == "__main__":
    main()
