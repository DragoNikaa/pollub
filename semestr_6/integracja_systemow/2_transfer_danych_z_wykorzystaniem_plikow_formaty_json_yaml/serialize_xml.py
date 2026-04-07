from typing import Any
from xml.dom import minidom
from xml.etree import ElementTree


class SerializeXml:
    @staticmethod
    def run(data: list[dict[str, Any]], file_path: str, root_name: str) -> None:
        root = ElementTree.Element(root_name)

        for department in data:
            fields = {
                "Kod_TERYT": department["Kod_TERYT"],
                "Województwo": department["Województwo"],
                **({"Powiat": department["Powiat"]} if "Powiat" in department else {}),
                "typ_JST": department["typ_JST"],
                "nazwa_urzędu_JST": department["nazwa_urzędu_JST"],
                "miejscowość": department["miejscowość"],
                "telefon_z_numerem_kierunkowym": department["telefon_z_numerem_kierunkowym"],
            }

            item = ElementTree.SubElement(root, "department")

            for key, value in fields.items():
                child = ElementTree.SubElement(item, key)
                child.text = str(value)

        xml = ElementTree.tostring(root, encoding="utf-8")
        pretty_xml = minidom.parseString(xml).toprettyxml()

        with open(file_path, "w", encoding="utf-8") as file:
            file.write(pretty_xml)
