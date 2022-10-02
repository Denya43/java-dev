package Task3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class FrequencyAnalysis {

    private static final Map<Character, Double> MAP = new HashMap<>();

    public static void main(String[] args) {

        MAP.put('а', 0.07998);
        MAP.put('б', 0.01592);
        MAP.put('в', 0.04533);
        MAP.put('г', 0.01687);
        MAP.put('д', 0.02977);
        MAP.put('е', 0.08483);
        MAP.put('ё', 0.00013);
        MAP.put('ж', 0.0094);
        MAP.put('з', 0.01641);
        MAP.put('и', 0.07367);
        MAP.put('й', 0.01208);
        MAP.put('к', 0.03486);
        MAP.put('л', 0.04343);
        MAP.put('м', 0.03203);
        MAP.put('н', 0.067);
        MAP.put('о', 0.10983);
        MAP.put('п', 0.02804);
        MAP.put('р', 0.04746);
        MAP.put('с', 0.05473);
        MAP.put('т', 0.06318);
        MAP.put('у', 0.02615);
        MAP.put('ф', 0.00267);
        MAP.put('х', 0.00966);
        MAP.put('ц', 0.00486);
        MAP.put('ч', 0.0145);
        MAP.put('ш', 0.00718);
        MAP.put('щ', 0.00361);
        MAP.put('ъ', 0.00037);
        MAP.put('ы', 0.01898);
        MAP.put('ь', 0.01735);
        MAP.put('э', 0.00331);
        MAP.put('ю', 0.00639);
        MAP.put('я', 0.02001);

        double[] listOfErrors = new double[32];

        String text = readFromTXT();

        Runtime runtime = Runtime.getRuntime();
        Thread[] threads = new Thread[runtime.availableProcessors()];
        for (int threadId = 0; threadId < threads.length; threadId++) {
            int finalThreadId = threadId;
            threads[threadId] = new Thread(() -> {
                for (int i = finalThreadId; i < listOfErrors.length; i += threads.length) {
                    listOfErrors[i] = predict(text, i);
                }
            });
            threads[threadId].start();

        }

        for (Thread th: threads) {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (double listOfError : listOfErrors) {
            System.out.println(listOfError);
        }
        System.out.println("--------------------------");

        double minError = Arrays.stream(listOfErrors).min().getAsDouble();
        System.out.println("Минимальное количество ошибок - " + minError);
        System.out.println("--------------------------");

        int predictedCountOfShifts = 0;
        for (int i = 0; i < listOfErrors.length; i++) {
            if (listOfErrors[i] == minError) {
                System.out.println("Предполагаемое количество сдвигов - " + i);
                predictedCountOfShifts = i;
            }
        }
        System.out.println("Дешифрованный текст:");
        System.out.println(decrypt(text, predictedCountOfShifts));
    }

    private static Double predict(String cipherText, int countOfShifts) {

        cipherText = cipherText.toLowerCase();
        HashMap<Character, Double> hashMap = new HashMap<>();

        cipherText.chars().mapToObj(i -> (char) i).
                forEach(e -> hashMap.compute(charReverseShift(e, countOfShifts), (k, v) -> v == null ? v = 1.0 : ++v));

        HashMap<Character, Double> sortedHashMap = hashMap.entrySet().stream()
                .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .filter(entry -> MAP.containsKey(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));

        Double sum = sortedHashMap.values().stream().reduce(0.0, Double::sum);

        sortedHashMap.replaceAll((k, v) -> v = v / sum);

        return calculateError(sortedHashMap);
    }

    private static String decrypt(String text, int predictedCountOfShifts) {

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            str.append(charReverseShift(text.charAt(i), predictedCountOfShifts));
        }
        return str.toString();
    }

    private static Character charReverseShift(Character symbol, int numberOfShifts) {

        return (char) (symbol - numberOfShifts);
    }

    private static String readFromTXT() {

        String str = "";
        try {
            str = new String(
                    Files.readAllBytes(Paths.get(
                            "/Users/aleksandrmatveev/java-dev/src/main/java/Task3/opentext.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toLowerCase().replaceAll("\\s+","");
    }

    private static Double calculateError(Map<Character, Double> map) {

        return map.entrySet().stream().reduce(
                0.0, (error, entry) ->
                        error + Math.pow(MAP.get(entry.getKey()) - entry.getValue(), 2), Double::sum);
    }
}
