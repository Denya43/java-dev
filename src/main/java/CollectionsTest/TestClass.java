package CollectionsTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestClass {

    public static void main(String[] args) {
        List<String> objects = new ArrayList<>();
        objects.add("Asd");
        objects.add("qwer");
        objects.add("qwerty");
        objects.add("ss");

        List<String> filteredList = objects.stream().filter(e -> e.length() % 2 == 0).collect(Collectors.toList());

        System.out.println(filteredList);

        filteredList.forEach(System.out::println);

        List<Integer> list = new ArrayList<>();
        list.add(6);
        list.add(5);
        list.add(2);
        list.add(10);
        list.add(15);

        int result = list.stream().reduce(Integer::sum).orElseThrow();

        System.out.println(result);

        System.out.println(list.stream().sorted().collect(Collectors.toList()));

        System.out.println(list.stream().filter(e -> e % 5 != 0).sorted().reduce((a, e) -> a * e));
    }
}
