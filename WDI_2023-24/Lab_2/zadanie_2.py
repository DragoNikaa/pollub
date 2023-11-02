def input_positive_number() -> int:
    number = int(input("Podaj liczbę dodatnią: "))
    if number <= 0:
        raise ValueError
    return number


def find_max() -> None:
    numbers = [input_positive_number() for _ in range(3)]
    max_number = 0
    for number in numbers:
        if number > max_number:
            max_number = number
    print(f"Największa z tych liczb to: {max_number}")


if __name__ == '__main__':
    try:
        find_max()
    except ValueError:
        print("Wprowadzono niepoprawne dane.")
