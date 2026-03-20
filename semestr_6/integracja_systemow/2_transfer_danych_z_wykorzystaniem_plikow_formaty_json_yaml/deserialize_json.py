import json
from collections import defaultdict


class DeserializeJson:
    def __init__(self, file_path: str):
        with open(file_path, encoding="utf8") as file:
            self.data = json.load(file)

    def display_statistics(self) -> None:
        province_to_department_type_count: dict[str, dict[str, int]] = defaultdict(lambda: defaultdict(int))

        for department in self.data:
            province = department["Województwo"]
            department_type = department["typ_JST"]

            province_to_department_type_count[province][department_type] += 1

        print("Liczba urzędów w każdym województwie z podziałem na typy JST:")

        for province, department_type_count in province_to_department_type_count.items():
            print(f"- {province}:")

            for department_type, count in department_type_count.items():
                print(f"\t- {department_type}: {count}")
