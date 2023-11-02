symbol_1 = symbol_2 = 0

while (symbol_1 == symbol_2 == 0) or (symbol_1 == symbol_2 == 1) or (symbol_1 == symbol_2 == 2):
    symbol_1 = int(input("[gracz_1] Podaj jeden z symboli (0 - papier, 1- kamień, 2 - nożyce): "))
    symbol_2 = int(input("[gracz_2] Podaj jeden z symboli (0 - papier, 1- kamień, 2 - nożyce): "))

    if (symbol_1 != 0 and symbol_1 != 1 and symbol_1 != 2) or (symbol_2 != 0 and symbol_2 != 1 and symbol_2 != 2):
        print("Podano niepoprawne dane.")
    else:
        if symbol_1 == symbol_2:
            print("Remis!")
        elif (symbol_1 == 0 and symbol_2 == 1) or (symbol_1 == 1 and symbol_2 == 2) or (
                symbol_1 == 2 and symbol_2 == 0):
            print("Wygrywa gracz 1!")
        else:
            print("Wygrywa gracz 2!")
