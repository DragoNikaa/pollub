from xml.etree import ElementTree


class DeserializeXml:
    def __init__(self, file_path: str):
        root = ElementTree.parse(file_path).getroot()
        self.data = [{child.tag: child.text for child in item} for item in root]

    def display_departments(self, province: str, max_results: int = 10) -> None:
        results = 0

        print(f'Urzędy w województwie "{province}":')

        for department in self.data:
            if department["Województwo"] == province:
                print(f"- {department['nazwa_urzędu_JST']}")

                results += 1
                if results >= max_results:
                    return
