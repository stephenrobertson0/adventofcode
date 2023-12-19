package _2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class _16 {

    private static Map<String, Integer> requirements = new HashMap<>();

    static {
        requirements.put("children", 3);
        requirements.put("cats", 7);
        requirements.put("samoyeds", 2);
        requirements.put("pomeranians", 3);
        requirements.put("akitas", 0);
        requirements.put("vizslas", 0);
        requirements.put("goldfish", 5);
        requirements.put("trees", 3);
        requirements.put("cars", 2);
        requirements.put("perfumes", 1);
    }

    private static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2015/input/input16.txt"));

        int index = 1;

        while (true) {

            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String after = line.substring(line.indexOf(":") + 1);

            Map<String, Integer> counts =
                    Arrays.stream(after.split(",")).collect(Collectors.toMap(v -> v.substring(1, v.indexOf(":")), v -> Integer.parseInt(v.substring(v.indexOf(":") + 2))));

            if (counts.entrySet().stream().map(v->requirements.get(v.getKey()) == v.getValue()).reduce(true, (x,y)->x&&y)) {
                System.out.println(index);
                break;
            }

            index++;

        }

    }

    private static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2015/input/input16.txt"));

        int index = 1;

        while (true) {

            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String after = line.substring(line.indexOf(":") + 1);

            Map<String, Integer> counts =
                    Arrays.stream(after.split(",")).collect(Collectors.toMap(v -> v.substring(1, v.indexOf(":")), v -> Integer.parseInt(v.substring(v.indexOf(":") + 2))));

            boolean match = true;

            for (Map.Entry<String, Integer> entry :  counts.entrySet())  {
                String key = entry.getKey();

                if ("cats".equals(key) || "trees".equals(key)) {
                    if (entry.getValue() < requirements.get(key)) {
                        match = false;
                        break;
                    }
                } else if ("pomeranians".equals(key) || "goldfish".equals(key)) {
                    if (entry.getValue() > requirements.get(key)) {
                        match = false;
                        break;
                    }
                } else {
                    if (entry.getValue() != requirements.get(key)) {
                        match = false;
                        break;
                    }
                }
            }

            if (match) {
                System.out.println(index);
                break;
            }

            index++;

        }

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }

}