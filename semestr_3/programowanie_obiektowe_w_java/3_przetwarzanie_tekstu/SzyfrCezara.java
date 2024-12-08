import java.util.Scanner;

public class SzyfrCezara {
    static int przesuniecie = 3;
    static int liczbaLiter = 'z' - 'a' + 1;

    public static String wczytaj() {
        System.out.print("Podaj tekst do zaszyfrowania przy użyciu szyfru Cezara: ");
        Scanner skaner = new Scanner(System.in);
        return skaner.nextLine();
    }

    public static String zaszyfruj(String tekst) {
        char[] zaszyfrowane = tekst.toCharArray();

        for (int i = 0; i < zaszyfrowane.length; i++) {
            char znak = zaszyfrowane[i];

            if ((znak >= 'A' && znak <= 'Z') || (znak >= 'a' && znak <= 'z')) {
                znak = (char) ((int) znak + przesuniecie);
                if ((znak > 'Z' && znak < 'a') || znak > 'z') znak -= (char) liczbaLiter;
                zaszyfrowane[i] = znak;
            }
        }
        return String.valueOf(zaszyfrowane);
    }

    public static String deszyfruj(String tekst) {
        char[] deszyfrowane = tekst.toCharArray();

        for (int i = 0; i < deszyfrowane.length; i++) {
            char znak = deszyfrowane[i];

            if ((znak >= 'A' && znak <= 'Z') || (znak >= 'a' && znak <= 'z')) {
                znak = (char) ((int) znak - przesuniecie);
                if (znak < 'A' || (znak > 'Z' && znak < 'a')) znak += (char) liczbaLiter;
                deszyfrowane[i] = znak;
            }
        }
        return String.valueOf(deszyfrowane);
    }
}
