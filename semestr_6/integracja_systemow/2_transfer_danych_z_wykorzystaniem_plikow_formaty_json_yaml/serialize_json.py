import json
from typing import Any


class SerializeJson:
    @staticmethod
    def run(data: list[dict[str, Any]], file_path: str) -> None:
        departments = [
            {
                "Kod_TERYT": department["Kod_TERYT"],
                "Województwo": department["Województwo"],
                **({"Powiat": department["Powiat"]} if "Powiat" in department else {}),
                "typ_JST": department["typ_JST"],
                "nazwa_urzędu_JST": department["nazwa_urzędu_JST"],
                "miejscowość": department["miejscowość"],
                "telefon_z_numerem_kierunkowym": SerializeJson._get_phone_number(department),
            }
            for department in data]

        with open(file_path, "w", encoding="utf-8") as file:
            json.dump({"departments": departments}, file, ensure_ascii=False, indent=2)

    @staticmethod
    def _get_phone_number(department: dict[str, Any]) -> Any:
        if "telefon_z_numerem_kierunkowym" in department:
            return department["telefon_z_numerem_kierunkowym"]
        return f"+{department["telefon kierunkowy"]} {department["telefon"]}"
