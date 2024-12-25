package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _25 {

    public static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input25.txt"));

        List<List<String>> lockStrs = new ArrayList<>();
        List<List<String>> keyStrs = new ArrayList<>();

        List<String> current = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null || line.isEmpty()) {
                if (current.get(0).equals("#####")) {
                    lockStrs.add(current);
                } else {
                    keyStrs.add(current);
                }

                current = new ArrayList<>();

                if (line == null) {
                    break;
                }

                continue;
            }

            current.add(line);
        }

        List<int[]> locks = new ArrayList<>();

        for (List<String> lock : lockStrs) {

            int[] heights = new int[5];

            for (int j = 0; j < 7; j ++) {

                String lockLine = lock.get(j);

                for (int k = 0; k < lockLine.length(); k ++) {

                    if (lockLine.charAt(k) == '.' && heights[k] == 0) {
                        heights[k] = j;
                    }

                }

            }

            locks.add(heights);

        }

        List<int[]> keys = new ArrayList<>();

        for (List<String> key : keyStrs) {

            int[] heights = new int[5];

            for (int j = 0; j < 7; j ++) {

                String keyLine = key.get(6 - j);

                for (int k = 0; k < keyLine.length(); k ++) {

                    if (keyLine.charAt(k) == '.' && heights[k] == 0) {
                        heights[k] = j;
                    }

                }

            }

            keys.add(heights);

        }

        int count = 0;

        for (int[] lock : locks) {
            for (int[] key : keys) {

                boolean allMatch = true;

                for (int j = 0; j < 5; j++) {
                    if (key[j] + lock[j] > 7) {
                        allMatch = false;
                    }
                }

                if (allMatch) {
                    count++;
                }
            }
        }

        System.out.println(count);

    }

    public static void main(String[] args) throws Exception {
        a();
    }
}
