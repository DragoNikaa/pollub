import time
from itertools import product
from typing import Iterator


def main(max_nr_paczki: int) -> tuple[int, int]:
    liczba = 1

    max_reprezentacja_elfowa = 77
    max_dlugosc_reprezentacji_elfowej = 2

    polowa_dlugosci_reprezentacji_elfowej = 1

    while liczba <= max_nr_paczki:
        for reprezentacja_elfowa in genereruj_reprezentacje_elfowe(polowa_dlugosci_reprezentacji_elfowej):
            if reprezentacja_elfowa % liczba == 0:

                if len(str(reprezentacja_elfowa)) > max_dlugosc_reprezentacji_elfowej:
                    max_liczba = liczba
                    max_reprezentacja_elfowa = reprezentacja_elfowa
                    max_dlugosc_reprezentacji_elfowej = len(str(reprezentacja_elfowa))

                liczba = zwieksz_liczbe(liczba)
                polowa_dlugosci_reprezentacji_elfowej = 0
                break

        polowa_dlugosci_reprezentacji_elfowej += 1

    return max_liczba, max_reprezentacja_elfowa


def zwieksz_liczbe(liczba: int) -> int:
    while True:
        liczba += 1
        if liczba % 5 != 0 and liczba % 16 != 0:
            return liczba


def genereruj_reprezentacje_elfowe(polowa_dlugosci_reprezentacji_elfowej: int) -> Iterator[int]:
    for krotka in product(["77", "88", "99"], repeat=polowa_dlugosci_reprezentacji_elfowej):
        reprezentacja_elfowa = ""

        for czesc_reprezentacji_elfowej in krotka:
            reprezentacja_elfowa += czesc_reprezentacji_elfowej

        yield int(reprezentacja_elfowa)


if __name__ == '__main__':
    max_nr_paczki = 50_000

    start_time = time.process_time()
    max_liczba, max_reprezentacja_elfowa = main(max_nr_paczki)
    end_time = time.process_time()

    print(max_liczba, max_reprezentacja_elfowa)
    print(end_time - start_time)
