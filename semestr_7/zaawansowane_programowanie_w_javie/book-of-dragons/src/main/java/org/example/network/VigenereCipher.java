package org.example.network;

public class VigenereCipher {
    private static final String KEY = "DRAGONS";

    private VigenereCipher() {
    }

    public static String encrypt(String text) {
        return transform(text, true);
    }

    public static String decrypt(String text) {
        return transform(text, false);
    }

    private static String transform(String text, boolean encrypt) {
        StringBuilder result = new StringBuilder();

        int keyIndex = 0;
        int direction = encrypt ? 1 : -1;

        for (char character : text.toCharArray()) {
            if (isLatinLetter(character)) {
                int keyShift = KEY.charAt(keyIndex) - 'A';
                result.append(shiftCharacter(character, keyShift, direction));
                keyIndex = (keyIndex + 1) % KEY.length();
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    private static char shiftCharacter(char character, int keyShift, int direction) {
        char alphabetStart = Character.isUpperCase(character) ? 'A' : 'a';
        int shiftedValue = Math.floorMod(character - alphabetStart + direction * keyShift, 26);
        return (char) (alphabetStart + shiftedValue);
    }

    private static boolean isLatinLetter(char character) {
        return (character >= 'A' && character <= 'Z') || (character >= 'a' && character <= 'z');
    }
}