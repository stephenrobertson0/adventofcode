package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class _1 {
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input1.txt"));

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }

            String[] nums = line.split("   ");
            int num1 = Integer.parseInt(nums[0]);
            int num2 = Integer.parseInt(nums[1]);

            list1.add(num1);
            list2.add(num2);
        }

        Collections.sort(list1);
        Collections.sort(list2);

        long total = 0;

        for (int j = 0; j < list1.size(); j++) {
            total += Math.abs(list1.get(j) - list2.get(j));
        }

        System.out.println(total);

    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input1.txt"));

        List<Integer> list = new ArrayList<>();
        Map<Integer, Integer> counts = new HashMap<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String[] nums = line.split("   ");
            int num1 = Integer.parseInt(nums[0]);
            int num2 = Integer.parseInt(nums[1]);

            list.add(num1);
            Integer count = counts.get(num2);
            if (count == null) {
                count = 1;
            } else {
                count += 1;
            }

            counts.put(num2, count);

        }

        long total = 0;

        for (int j = 0; j < list.size(); j++) {
            total += list.get(j) * Optional.ofNullable(counts.get(list.get(j))).orElse(0);
        }

        System.out.println(total);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
