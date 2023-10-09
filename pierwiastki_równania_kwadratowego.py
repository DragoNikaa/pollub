import math


def find_roots_of_quadratic_equation() -> None:
    print("Wprowadź wartości zgodnie ze wzorem: a * x^2 + b * x + c = 0")
    a = int(input("a = "))
    b = int(input("b = "))
    c = int(input("c = "))
    delta = b * b - 4 * a * c
    if delta < 0:
        print("Brak pierwiastków.")
    elif delta == 0:
        print(f"x0 = {-b / (2 * a)}")
    else:
        print(f"x1 = {(-b - math.sqrt(delta)) / 2 * a}\nx2 = {(-b + math.sqrt(delta)) / 2 * a}")


if __name__ == '__main__':
    try:
        find_roots_of_quadratic_equation()
    except ValueError:
        print("Wprowadzono niepoprawne dane.")
