import yaml

from convert_json_to_yaml import ConvertJsonToYaml
from deserialize_json import DeserializeJson
from deserialize_xml import DeserializeXml
from serialize_json import SerializeJson
from serialize_xml import SerializeXml


def main() -> None:
    with open("Assets/basic_config.yaml", encoding="utf8") as file:
        config = yaml.load(file, Loader=yaml.FullLoader)

    paths = config["paths"]
    settings = config["settings"]

    source_folder = paths["source_folder"]
    json_source_file = source_folder + paths["json_source_file"]
    json_modified_file = source_folder + paths["json_modified_file"]
    json_to_yaml_file = source_folder + paths["json_to_yaml_file"]
    json_to_xml_file = source_folder + paths["json_to_xml_file"]
    xml_to_json_file = source_folder + paths["xml_to_json_file"]

    operations = settings["operations_order"]
    enabled = settings["enabled_operations"]

    json_deserializer = xml_deserializer = None

    for operation in operations:
        if not enabled[operation]:
            continue

        if operation == "deserialize_json":
            json_deserializer = DeserializeJson(json_source_file)

        elif operation == "serialize_json":
            if json_deserializer:
                SerializeJson.run(json_deserializer.data, json_modified_file)

        elif operation == "convert_json_to_yaml":
            if settings["convert_source"] == "object":
                ConvertJsonToYaml.run(DeserializeJson(json_modified_file), json_to_yaml_file)
            elif settings["convert_source"] == "file":
                ConvertJsonToYaml.run(json_modified_file, json_to_yaml_file)

        elif operation == "convert_json_to_xml":
            json_data = DeserializeJson(json_modified_file).data["departments"]
            selected_data = [department for department in json_data if department["typ_JST"] == "GW"]
            SerializeXml.run(selected_data, json_to_xml_file, root_name="gminy_wiejskie")

        elif operation == "convert_xml_to_json":
            xml_deserializer = DeserializeXml(json_to_xml_file)
            selected_data = [department for department in xml_deserializer.data if
                             department["Województwo"] == "lubelskie"]
            SerializeJson.run(selected_data, xml_to_json_file)

        elif operation == "display_overview_json":
            if json_deserializer:
                json_deserializer.display_statistics()
                print()

        elif operation == "display_overview_xml":
            if xml_deserializer:
                xml_deserializer.display_departments("lubelskie")
                print()


if __name__ == "__main__":
    main()
