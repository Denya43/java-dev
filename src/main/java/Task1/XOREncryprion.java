package Task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class XOREncryprion {

    private static final String KEY = "папич";

    public static void main(String[] args) {
        String cipherText = encryptAndDecrypt(readFromTXT());
        System.out.println("ШИФРТЕКСТ:");
        System.out.println("--------------------------");
        System.out.println(cipherText);
        System.out.println("--------------------------");

        String openText = encryptAndDecrypt(cipherText);
        System.out.println("ДЕШИФРОВАННЫЙ ТЕКСТ:");
        System.out.println("--------------------------");
        System.out.println(openText);
    }

    private static String encryptAndDecrypt(String text) {
        char[] keyCharArray = generateKey(text.length());

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            str.append((char) (text.charAt(i) ^ keyCharArray[i]));
        }
        return str.toString();
    }

    private static String readFromTXT() {
        String str = "";
        try {
            str = new String(
                    Files.readAllBytes(Paths.get(
                            "/Users/aleksandrmatveev/java-dev/src/main/java/Task1/opentext.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    private static char[] generateKey(Integer countOfSymbols) {
        char[] keyCharArray = new char[countOfSymbols];
        char[] key = KEY.toCharArray();
        for (int i = 0; i < keyCharArray.length - 1; i++) {
            if (i != 0 && i % key.length == 0) {
                char buf = key[0];
                if (key.length - 1 >= 0) System.arraycopy(key, 1, key, 0, key.length - 1);
                key[key.length - 1] = buf;
            }
            keyCharArray[i] = key[i % key.length];
        }
        System.out.println("The key is: " + Arrays.toString(keyCharArray));
        return keyCharArray;
    }
}
