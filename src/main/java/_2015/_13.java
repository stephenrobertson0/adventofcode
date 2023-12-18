package _2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class _13 {

    private static List<List<String>> getArrangements(Set<String> names) {

        if (names.isEmpty()) {
            return new ArrayList<>();
        }

        if (names.size() == 1) {
            List<List<String>> result = new ArrayList<>();

            String next = names.iterator().next();

            List<String> r = new ArrayList<>();
            r.add(next);
            result.add(r);

            return result;
        }

        List<List<String>> result = new ArrayList<>();

        for (String str : names) {
            Set<String> newNames = new HashSet<>(names);
            newNames.remove(str);

            List<List<String>> arrangements = getArrangements(newNames);

            for (List<String> a : arrangements) {
                a.add(str);
            }

            result.addAll(arrangements);
        }

        return result;
    }

    private static int getCountForList(List<String> list, Map<String, Integer> pairCounts) {

        int count = 0;

        for (int j = 0; j < list.size(); j++) {

            int other1 = j - 1;
            if (other1 < 0) {
                other1 = list.size() - 1;
            }

            int other2 = j + 1;
            if (other2 >= list.size()) {
                other2 = 0;
            }

            count += pairCounts.get(list.get(j) + ";" + list.get(other1)) + pairCounts.get(list.get(j) + ";" + list.get(other2));
        }

        return count;

    }

    private static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2015/input/input13.txt"));

        Map<String, Integer> pairCounts = new HashMap<>();

        Set<String> allNames = new HashSet<>();

        while (true) {

            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String name = line.split(" would")[0];
            String remainder = line.split(" would")[1].trim();
            String action = remainder.split(" ")[0];
            int count = Integer.parseInt(remainder.split(" ")[1]);
            String otherName = remainder.split(" ")[remainder.split(" ").length-1];
            otherName = otherName.substring(0, otherName.length()-1);

            pairCounts.put(name + ";" + otherName, "gain".equals(action) ? count : -count);

            allNames.add(name);

        }

        int max = 0;

        List<List<String>> arrangements = getArrangements(allNames);

        for (List<String> arrangement : arrangements) {

            int count = getCountForList(arrangement, pairCounts);

            if (count > max) {
                max = count;
            }
        }

        System.out.println(max);

    }

    private static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2015/input/input13.txt"));

        Map<String, Integer> pairCounts = new HashMap<>();

        Set<String> allNames = new HashSet<>();

        while (true) {

            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String name = line.split(" would")[0];
            String remainder = line.split(" would")[1].trim();
            String action = remainder.split(" ")[0];
            int count = Integer.parseInt(remainder.split(" ")[1]);
            String otherName = remainder.split(" ")[remainder.split(" ").length-1];
            otherName = otherName.substring(0, otherName.length()-1);

            pairCounts.put(name + ";" + otherName, "gain".equals(action) ? count : -count);

            allNames.add(name);

        }

        for (String name : allNames) {
            pairCounts.put("me;" + name, 0);
            pairCounts.put(name + ";me", 0);
        }

        allNames.add("me");

        int max = 0;

        List<List<String>> arrangements = getArrangements(allNames);

        for (List<String> arrangement : arrangements) {

            int count = getCountForList(arrangement, pairCounts);

            if (count > max) {
                max = count;
            }
        }

        System.out.println(max);

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }

}