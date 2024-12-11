package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class _11 {

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input11.txt"));

        final String line = fileReader.readLine();

        List<Long> list = Arrays.stream(line.split(" ")).map(v -> Long.parseLong(v)).toList();

        for (int j = 0; j < 25; j++) {

            List<Long> result = new ArrayList<>();

            for (long num : list) {

                String numStr = "" + num;

                if (num == 0) {
                    result.add(1L);
                } else if (numStr.length() % 2 == 0) {
                    long num1 = Long.parseLong(numStr.substring(0, numStr.length()/2));
                    long num2 = Long.parseLong(numStr.substring(numStr.length()/2));

                    result.add(num1);
                    result.add(num2);
                } else {
                    result.add(num * 2024);
                }
            }

            list = result;

        }

        System.out.println(list.size());

    }

    private record Key (long num, int stepsRemaining){}

    static Map<Key, Long> counts = new HashMap<>();

    private static long count(long num, int stepsRemaining) {

        Key key = new Key(num, stepsRemaining);
        if (counts.containsKey(key)) {
            return counts.get(key);
        }

        if (stepsRemaining == 0) {
            return 1;
        } else {

            String numStr = "" + num;

            long count;

            if (num == 0) {
                count = count(1, stepsRemaining - 1);
            } else if (numStr.length() % 2 == 0) {

                long num1 = Long.parseLong(numStr.substring(0, numStr.length() / 2));
                long num2 = Long.parseLong(numStr.substring(numStr.length() / 2));

                count = count(num1, stepsRemaining - 1) + count(num2, stepsRemaining - 1);
            } else {
                count = count(num * 2024, stepsRemaining - 1);
            }

            counts.put(new Key(num, stepsRemaining), count);

            return count;
        }
    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input11.txt"));

        final String line = fileReader.readLine();

        List<Long> list = Arrays.stream(line.split(" ")).map(v -> Long.parseLong(v)).toList();

        long total = 0;

        for (long num : list) {
            total += count(num, 75);
        }

        System.out.println(total);
    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
