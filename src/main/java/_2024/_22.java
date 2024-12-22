package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _22 {

    private record NumAndDiff (long num, int diff){}

    private static long getNextNumber(long number) {

        long toMix = number * 64;

        long newNumber = number ^ toMix;

        newNumber = newNumber % 16777216;



        toMix = newNumber / 32;

        newNumber = newNumber ^ toMix;

        newNumber = newNumber % 16777216;



        toMix = newNumber * 2048;

        newNumber = newNumber ^ toMix;

        newNumber = newNumber % 16777216;

        return newNumber;
    }

    public static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input22.txt"));

        List<Long> secretNumbers = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            secretNumbers.add(Long.parseLong(line));

        }

        long count = 0;

        for (long num : secretNumbers) {

            long newNum = 0;

            for (int j = 0; j < 2000; j ++) {
                newNum = getNextNumber(num);
                num = newNum;
            }

            //System.out.println(newNum);

            count += newNum;

        }

        System.out.println(count);

    }

    public static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input22.txt"));

        List<Long> secretNumbers = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            secretNumbers.add(Long.parseLong(line));

        }

        List<List<NumAndDiff>> allDiffs = new ArrayList<>();

        for (long num : secretNumbers) {

            long newNum = 0;

            List<NumAndDiff> numAndDiffs = new ArrayList<>();

            for (int k = 0; k < 2000; k ++) {
                newNum = getNextNumber(num);

                NumAndDiff numAndDiff = new NumAndDiff(newNum % 10, (int)(newNum % 10 - num % 10));

                numAndDiffs.add(numAndDiff);

                num = newNum;
            }

            allDiffs.add(numAndDiffs);

            //System.out.println(numAndDiffs);

        }

        int possibleForOneNum = 19;

        int maxSellCount = 0;
        String selected = null;

        for (int j = 0; j < Math.pow(possibleForOneNum,4); j++) {

            int current = j;

            int num1 = current%19 - 9;
            current = current/19;
            int num2 = current%19 - 9;
            current = current/19;
            int num3 = current%19 - 9;
            current = current/19;
            int num4 = current%19 - 9;

            if (num1 + num2 + num3 + num4 < 0
                    || num1 + num2 + num3 < -10 || num1 + num2 + num4 < -10 || num1 + num3 + num4 < -10 || num2 + num3 + num4 < -10
                    || num1 + num2 + num3 > 15 || num1 + num2 + num4 > 15 || num1 + num3 + num4 > 15 || num2 + num3 + num4 > 15
                    || num1 + num2 + num3 + num4 > 10
            ) {
                continue;
            }

            int sellCount = 0;

            for (int i = 0; i < allDiffs.size(); i++) {

                List<NumAndDiff> diffs = allDiffs.get(i);

                for (int k = 0; k < diffs.size(); k++) {

                    if (k >= 3) {
                        if (diffs.get(k - 3).diff == num1 && diffs.get(k - 2).diff == num2 && diffs.get(k - 1).diff == num3 && diffs.get(k).diff == num4) {
                            sellCount += diffs.get(k).num;
                            break;
                        }
                    }
                }

                //System.out.println(diffs);

            }

            if (sellCount > maxSellCount) {
                maxSellCount = sellCount;
                selected = num1 + " " + num2 + " " + num3 + " " + num4;
            }
        }

        System.out.println(selected);
        System.out.println(maxSellCount);

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
