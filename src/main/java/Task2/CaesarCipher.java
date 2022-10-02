package Task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class CaesarCipher {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String cipherText = encrypt(readFromTXT());
        System.out.println("ШИФРТЕКСТ:");
        System.out.println("--------------------------");
        System.out.println(cipherText);
        System.out.println("--------------------------");

        String openText = decrypt(cipherText);
        System.out.println("ДЕШИФРОВАННЫЙ ТЕКСТ:");
        System.out.println("--------------------------");
        System.out.println(openText);
    }

    private static String encrypt(String text) {
        System.out.println("Введите K - количество сдвигов:");
        int countOfShifts = scanner.nextInt();

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            str.append((char) (text.charAt(i) + countOfShifts));
        }

        return str.toString();
    }

    private static String decrypt(String text) {
        System.out.println("Введите K - количество сдвигов:");
        int countOfShifts = scanner.nextInt();

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            str.append((char) (text.charAt(i) - countOfShifts));
        }

        return str.toString();
    }

    private static String readFromTXT() {
        String str = "";
        try {
            str = new String(
                    Files.readAllBytes(Paths.get(
                            "/Users/aleksandrmatveev/java-dev/src/main/java/Task2/opentext.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}
