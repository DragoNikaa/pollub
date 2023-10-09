def calculate_sum_of_digits() -> None:
    number = int(input("Podaj liczbę w zakresie 0-999: "))
    if number < 0 or number > 999:
        raise ValueError
    unities = number % 10
    number = number // 10
    tens = number % 10
    number = number // 10
    hundreds = number % 10
    sum_of_digits = unities + tens + hundreds
    print(f"Suma cyfr: {sum_of_digits}, setki: {hundreds}, dziesiątki: {tens}, jedności: {unities}")


if __name__ == '__main__':
    try:
        calculate_sum_of_digits()
    except ValueError:
        print("Wprowadzono niepoprawne dane.")
