package _2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.stream.Collectors;


public class _3 {
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input3.txt"));

        int total = 0;

        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }

            int maxNum = 0;
            int maxIndex = 0;

            for (int j = 0; j < line.length()-1; j++) {
                char c = line.charAt(j);
                int num = Integer.parseInt(""+c);
                if (num > maxNum) {
                    maxNum = num;
                    maxIndex = j;
                }
            }

            int maxNum2 = 0;

            for (int j = maxIndex+1; j < line.length(); j++) {
                char c = line.charAt(j);
                int num = Integer.parseInt(""+c);
                if (num > maxNum2) {
                    maxNum2 = num;
                }
            }

            int overallMaxNum = Integer.parseInt(""+maxNum+maxNum2);
            total += overallMaxNum;
        }

        System.out.println(total);
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input3.txt"));

        long total = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            int numberOfDigits = 12;

            int[] maxNums = new int[numberOfDigits];
            int[] maxIndexes = new int[numberOfDigits];

            for (int k = 0; k < numberOfDigits; k++) {
                maxNums[k] = 0;
                maxIndexes[k] = 0;

                int startPos;
                if (k == 0) {
                    startPos = 0;
                } else {
                    startPos = maxIndexes[k-1]+1;
                }

                for (int j = startPos; j < line.length()-(numberOfDigits-1-k); j++) {
                    char c = line.charAt(j);
                    int num = Integer.parseInt(""+c);
                    if (num > maxNums[k]) {
                        maxNums[k] = num;
                        maxIndexes[k] = j;
                    }
                }
            }

            long overallMaxNum = Long.parseLong(Arrays.stream(maxNums).mapToObj(v -> ""+v).collect(Collectors.joining()));
            total += overallMaxNum;
        }

        System.out.println(total);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
