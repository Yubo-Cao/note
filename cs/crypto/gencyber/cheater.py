#!/usr/bin/env python
from abc import abstractmethod
import argparse
from string import ascii_lowercase


class Encryption:
    """The base class for all encryption algorithms."""

    possible_schema: dict[str, "Encryption"] = {}

    def __init_subclass__(cls) -> None:
        Encryption.possible_schema[cls.__name__] = cls

    def __init__(self, key: str):
        self.key = key

    @abstractmethod
    def encrypt(self, text):
        """Encrypts the text."""
        raise NotImplementedError

    @abstractmethod
    def decrypt(self, text):
        """Decrypts the text."""
        raise NotImplementedError


class Substitution(Encryption):
    def __init__(self, key: str):
        self.encrypt_dict = dict(zip(ascii_lowercase, key.lower()))
        self.decrypt_dict = {v: k for k, v in self.encrypt_dict.items()}

    def encrypt(self, text):
        return "".join(
            self.encrypt_dict.get(c.lower(), c)
            if c.islower()
            else self.encrypt_dict.get(c.lower(), c).upper()
            for c in text
        )

    def decrypt(self, text):
        return "".join(
            self.decrypt_dict.get(c.lower(), c)
            if c.islower()
            else self.decrypt_dict.get(c.lower(), c).upper()
            for c in text
        )


class Ceaser(Substitution):
    def __init__(self, key: str):
        key = int(key)
        self.encrypt_dict = dict(
            zip(ascii_lowercase, ascii_lowercase[key:] + ascii_lowercase)
        )
        self.decrypt_dict = {v: k for k, v in self.encrypt_dict.items()}


class RSA(Encryption):
    def __init__(self, key):
        self.private_key, self.public_key, *extra = (int(num) for num in key.split(","))
        if extra:
            raise ValueError("Too many arguments for RSA key.")


def main():
    parser = argparse.ArgumentParser()
    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument(
        "-e",
        "--encrypt",
        help="Encrypt message",
        action="store_true",
        default=False,
    )
    group.add_argument(
        "-d",
        "--decrypt",
        help="Decrypt message",
        action="store_true",
        default=False,
    )

    parser.add_argument("key", help="Key to use")
    parser.add_argument(
        "type",
        help="Type of encryption",
        choices=Encryption.possible_schema.keys(),
    )
    parser.add_argument("message", help="Message to encrypt/decrypt")
    args = parser.parse_args()

    key = args.key
    msg = args.message
    schema: Encryption = Encryption.possible_schema[args.type](key)

    if args.encrypt:
        print(schema.encrypt(msg))
    elif args.decrypt:
        print(schema.decrypt(msg))
    else:
        print("It should never happen!")


if __name__ == "__main__":
    main()
