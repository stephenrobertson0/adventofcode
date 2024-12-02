package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class _2 {
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input2.txt"));

        int safeCount = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String[] lines = line.split(" ");

            List<Integer> nums = Arrays.stream(lines).map(v->Integer.parseInt(v)).toList();

            if (isSafe(nums)) {
                safeCount++;
            }
        }

        System.out.println(safeCount);

    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input2.txt"));

        int safeCount = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String[] lines = line.split(" ");

            List<Integer> nums = Arrays.stream(lines).map(v->Integer.parseInt(v)).toList();

            if (isSafe(nums)) {
                safeCount++;
            } else {
                for (int j = 0; j < nums.size(); j++) {

                    List<Integer> clone = new ArrayList<>(nums);
                    clone.remove(j);

                    if (isSafe(clone)) {
                        safeCount++;
                        break;
                    }
                }
            }
        }

        System.out.println(safeCount);
    }

    private static boolean isSafe(List<Integer> nums) {
        boolean safe = true;
        boolean increasing = false;
        int lastNum = 0;

        for (int j = 0; j < nums.size(); j++) {

            int num = nums.get(j);

            if (j != 0) {

                if (Math.abs(num - lastNum) > 3 || num == lastNum) {
                    safe = false;
                    break;
                }

                if (j != 1) {
                    if (num > lastNum && !increasing || num < lastNum && increasing) {
                        safe = false;
                        break;
                    }
                }

                if (num > lastNum) {
                    increasing = true;
                } else {
                    increasing = false;
                }
            }

            lastNum = num;

        }

        return safe;
    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
