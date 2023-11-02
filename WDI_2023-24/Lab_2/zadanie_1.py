def run_game() -> None:
    symbol_1 = input("[gracz 1] Podaj jeden z symboli (0 – papier, 1 – nożyce, 2 – kamień): ")
    symbol_2 = input("[gracz 2] Podaj jeden z symboli (0 – papier, 1 – nożyce, 2 – kamień): ")

    if (symbol_1 != "0" and symbol_1 != "1" and symbol_1 != "2") or (
            symbol_2 != "0" and symbol_2 != "1" and symbol_2 != "2"):
        print("Wprowadzono niepoprawne dane.")

    elif symbol_1 == symbol_2:
        print("Remis!")
    elif (symbol_1 == "0" and symbol_2 == "2") or (symbol_1 == "1" and symbol_2 == "0") or (
            symbol_1 == "2" and symbol_2 == "1"):
        print("Wygrywa gracz 1!")
    else:
        print("Wygrywa gracz 2!")


if __name__ == '__main__':
    run_game()
