package _2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class _11 {

    private static Map<String, Long> counts = new HashMap<>();

    private static long getCountToOut(String name, Map<String, List<String>> nodes) {
        if ("out".equals(name)) {
            return 1;
        }

        if (counts.containsKey(name)) {
            return counts.get(name);
        }

        List<String> neighbours = nodes.get(name);

        long total = 0;

        for (int j = 0; j < neighbours.size(); j++) {

            long count = getCountToOut(neighbours.get(j), nodes);

            total += count;
        }

        counts.put(name, total);

        return total;
    }

    private static Map<String, Long> counts2 = new HashMap<>();

    private static long getCountToOut2(String name, Map<String, List<String>> nodes, boolean hasHitDac, boolean hasHitFft) {
        if ("out".equals(name)) {
            if (hasHitDac && hasHitFft) {
                return 1;
            } else {
                return 0;
            }
        }

        if (counts2.containsKey(name + hasHitDac + hasHitFft)) {
            return counts2.get(name + hasHitDac + hasHitFft);
        }

        List<String> neighbours = nodes.get(name);

        long total = 0;

        boolean newHasHitDac = hasHitDac || "dac".equals(name);
        boolean newHasHitFft = hasHitFft || "fft".equals(name);

        for (int j = 0; j < neighbours.size(); j++) {

            long count = getCountToOut2(neighbours.get(j), nodes, newHasHitDac, newHasHitFft);

            total += count;
        }

        counts2.put(name + newHasHitDac + newHasHitFft, total);

        return total;
    }

    public static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input11.txt"));

        Map<String, List<String>> nodes = new HashMap<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String nodeName = line.substring(0, 3);

            String neighboursString = line.substring(5);

            List<String> neighbours = Arrays.stream(neighboursString.split(" ")).toList();

            nodes.put(nodeName, neighbours);
        }

        System.out.println(getCountToOut("you", nodes));

    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input11.txt"));

        Map<String, List<String>> nodes = new HashMap<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String nodeName = line.substring(0, 3);

            String neighboursString = line.substring(5);

            List<String> neighbours = Arrays.stream(neighboursString.split(" ")).toList();

            nodes.put(nodeName, neighbours);
        }

        System.out.println(getCountToOut2("svr", nodes, false, false));

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
