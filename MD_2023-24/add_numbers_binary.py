def convert_dec_to_bin(dec_number: int) -> str:
    bin_number_reversed = ""
    while dec_number != 0:
        bin_number_reversed += str(dec_number % 2)
        dec_number //= 2
    return reverse_bin_number(bin_number_reversed)


def convert_bin_to_dec(bin_number: str) -> int:
    dec_number = 0
    for power in range(16):
        reversed_index = 15 - power
        dec_number += int(int(bin_number[reversed_index]) * pow(2, power))
    return dec_number


def reverse_bin_number(bin_number_reversed: str) -> str:
    bin_number = ""
    for index in range(1, len(bin_number_reversed) + 1):
        bin_number += bin_number_reversed[len(bin_number_reversed) - index]
    return bin_number


def fill_bin_number_with_0(bin_number: str) -> str:
    for _ in range(16):
        places_to_fill = 16 - len(bin_number)
        bin_number = places_to_fill * "0" + bin_number
    return bin_number


def add_bin_numbers(bin_number_1: str, bin_number_2: str) -> str:
    bin_sum = ""
    next_digit = "0"
    for index in range(16):
        n1_d = bin_number_1[15 - index]
        n2_d = bin_number_2[15 - index]
        if n1_d == n2_d == next_digit == "0":
            bin_sum += "0"
            next_digit = "0"
        elif (n1_d == "1" and n2_d == "0" and next_digit == "0") or (
                n1_d == "0" and n2_d == "1" and next_digit == "0") or (
                n1_d == "0" and n2_d == "0" and next_digit == "1"):
            bin_sum += "1"
            next_digit = "0"
        elif (n1_d == "1" and n2_d == "1" and next_digit == "0") or (
                n1_d == "1" and n2_d == "0" and next_digit == "1") or (
                n1_d == "0" and n2_d == "1" and next_digit == "1"):
            bin_sum += "0"
            next_digit = "1"
        else:
            bin_sum += "1"
            next_digit = "1"
    return bin_sum


def main() -> None:
    dec_number_1 = int(input("Podaj pierwszą liczbę całkowitą: "))
    dec_number_2 = int(input("Podaj drugą liczbę całkowitą: "))

    bin_number_1 = convert_dec_to_bin(dec_number_1)
    bin_number_2 = convert_dec_to_bin(dec_number_2)

    bin_number_1 = fill_bin_number_with_0(bin_number_1)
    bin_number_2 = fill_bin_number_with_0(bin_number_2)
    print(f"\nPodane liczby w systemie binarnym:\n\t{bin_number_1}\n\t{bin_number_2}")

    bin_sum = reverse_bin_number(add_bin_numbers(bin_number_1, bin_number_2))
    dec_sum = convert_bin_to_dec(bin_sum)
    print(f"\nSuma podanych liczb:\n\t{bin_sum}(2) = {dec_sum}(10)")


if __name__ == '__main__':
    main()
