package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;


public class _1 {
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input1.txt"));

        int total = 0;

        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }

            //System.out.println("line: " + line);

            int firstNum = -1;

            for (char c : line.toCharArray()) {
                String s = ""+c;

                //System.out.println(s);

                try {
                    firstNum = Integer.parseInt(s);
                } catch (Exception e) {
                    continue;
                }
                break;
            }

            int lastNum = -1;

            for (int j = line.length() - 1; j >= 0; j--) {
                char c = line.charAt(j);

                String s = ""+c;

                //System.out.println(s);

                try {
                    lastNum = Integer.parseInt(s);
                } catch (Exception e) {
                    continue;
                }
                break;
            }

            total += Integer.parseInt(""+ firstNum +lastNum);

        }

        System.out.println(total);
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input1.txt"));

        Map<String, Integer> map = new HashMap<>();

        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);
        map.put("four", 4);
        map.put("five", 5);
        map.put("six", 6);
        map.put("seven", 7);
        map.put("eight", 8);
        map.put("nine", 9);
        map.put("1", 1);
        map.put("2", 2);
        map.put("3", 3);
        map.put("4", 4);
        map.put("5", 5);
        map.put("6", 6);
        map.put("7", 7);
        map.put("8", 8);
        map.put("9", 9);

        int total = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            int minIndex = Integer.MAX_VALUE;
            String selectedKey = null;

            for (String s : map.keySet()) {

                //System.out.println(s);
                //System.out.println(minIndex);

                int index = line.indexOf(s);
                if (index != -1 && index < minIndex) {
                    minIndex = index;
                    selectedKey = s;
                }
            }

            String firstNum = ""+map.get(selectedKey);

            int maxIndex = -1;
            String selectedKey2 = null;

            for (String s : map.keySet()) {
                int index = line.lastIndexOf(s);

                //System.out.println("s: " + s);
                //System.out.println("index: " + index);

                if (index > maxIndex) {
                    maxIndex = index;
                    selectedKey2 = s;
                }
            }

            String lastNum = ""+map.get(selectedKey2);

            int total1 = Integer.parseInt(firstNum + lastNum);

            //System.out.println(total1);

            total += total1;

        }

        System.out.println(total);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
