import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // zadanie 1
//        wystapieniaWLancuchu('a', "Ala ma kota");
//        wystapieniaWLancuchu('w', "Tu nie ma podanego znaku");

        // zadanie 2
//        sumaASCII("Aa Bb");
//        sumaASCII("abc 123 !@#$");

        // zadanie 3
//        liczbaNaZnak();

        // zadanie 4
        String zaszyfrowany = SzyfrCezara.zaszyfruj(SzyfrCezara.wczytaj());
        System.out.println("Zaszyfrowany tekst: \"" + zaszyfrowany + "\"");
        System.out.println("Deszyfrowany tekst: \"" + SzyfrCezara.deszyfruj(zaszyfrowany) + "\"");
    }

    public static void wystapieniaWLancuchu(char znak, String lancuch) {
        int wystapienia = 0;

        for (int i = 0; i < lancuch.length(); i++)
            if (lancuch.charAt(i) == znak) wystapienia++;

        if (wystapienia == 0) System.out.println("Znak '" + znak + "' nie występuje w łańcuchu \"" + lancuch + "\".");
        else System.out.println("Liczba wystąpień znaku '" + znak + "' w łańcuchu \"" + lancuch + "\": " + wystapienia);
    }

    public static void sumaASCII(String lancuch) {
        int suma = 0;
        char[] znaki = lancuch.toCharArray();

        for (char znak : znaki)
            if ((znak >= 'a' && znak <= 'z') || (znak >= '0' && znak <= '9')) suma += znak;

        System.out.println("Suma wartości kodów ASCII małych liter i cyfr w łańcuchu \"" + lancuch + "\": " + suma);
    }

    public static void liczbaNaZnak() {
        Scanner skaner = new Scanner(System.in);
        System.out.print("Podaj liczbę całkowitą z zakresu [33, 126]: ");

        if (skaner.hasNextInt()) {
            int liczba = skaner.nextInt();

            if (liczba >= 33 && liczba <= 126)
                System.out.println("Znak przypisany do liczby " + liczba + ": '" + (char) liczba + "'");
            else System.out.println("Wprowadzono liczbę spoza zakresu.");
        } else {
            System.out.println("Wprowadzono nieprawidłową wartość.");
        }
    }
}
