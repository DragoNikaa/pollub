import math


def find_roots_of_quadratic_equation() -> None:
    print("Wprowadź wartości zgodnie ze wzorem: a * x^2 + b * x + c = 0")
    a = int(input("a = "))
    b = int(input("b = "))
    c = int(input("c = "))

    if a == 0:
        print("To nie jest równanie kwadratowe!")
    else:
        delta = b * b - 4 * a * c
        if delta < 0:
            print("Brak pierwiastków rzeczywistych.")
        elif delta == 0:
            x0 = -b / (2 * a)
            print(f"x0 = {x0}")
        else:
            x1 = (-b - math.sqrt(delta)) / (2 * a)
            x2 = (-b + math.sqrt(delta)) / (2 * a)
            print(f"x1 = {x1}\nx2 = {x2}")


if __name__ == '__main__':
    try:
        find_roots_of_quadratic_equation()
    except ValueError:
        print("Wprowadzono niepoprawne dane.")
