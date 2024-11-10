def wyznacz_pochodne_w_punkcie(stopien: int, wspolczynniki: list[float], punkt: float) -> list[float]:
    for i in range(stopien):
        for k in range(1, stopien - i + 1):
            wspolczynniki[k] = punkt * wspolczynniki[k - 1] + wspolczynniki[k]

    wspolczynniki = wspolczynniki[::-1]

    for i in range(2, stopien + 1):
        for k in range(2, i + 1):
            wspolczynniki[i] *= k

    return wspolczynniki


def main() -> None:
    stopien = int(input("Podaj stopień wielomianu (n >= 1): "))

    print("\nPodaj współczynniki wielomianu (od współczynnika przy najwyższej potędze):")
    wspolczynniki = [float(input(f"a[{i}] = ")) for i in range(stopien + 1)]

    punkt = float(input("\nPodaj, w jakim punkcie obliczyć wartość wielomianu: "))

    wyniki = wyznacz_pochodne_w_punkcie(stopien, wspolczynniki, punkt)

    print(f"\nWartość podanego wielomianu w punkcie p = {punkt}:\n"
          f"{wyniki[0]}\n\n"
          f"Wartości pochodnych podanego wielomianu w punkcie p = {punkt}:")

    for i in range(1, stopien + 1):
        print(f"{i} pochodna = {wyniki[i]}")


if __name__ == "__main__":
    main()
