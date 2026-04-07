from typing import overload

import yaml

from deserialize_json import DeserializeJson


class ConvertJsonToYaml:
    @staticmethod
    @overload
    def run(deserializer: DeserializeJson, yaml_file_path: str) -> None:
        ...

    @staticmethod
    @overload
    def run(json_file_path: str, yaml_file_path: str) -> None:
        ...

    @staticmethod
    def run(arg: DeserializeJson | str, yaml_file_path: str) -> None:
        if isinstance(arg, DeserializeJson):
            deserializer = arg
        else:
            deserializer = DeserializeJson(arg)

        with open(yaml_file_path, "w", encoding="utf8") as file:
            yaml.dump(deserializer.data, file, allow_unicode=True)
