package _2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class _6 {

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input6.txt"));

        List<List<Integer>> list = new ArrayList<>();
        List<String> operationList = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            if (line.startsWith("*")) {
                operationList = Arrays.stream(line.split("\\s+")).toList();
            } else {
                List<Integer> numList = Arrays.stream(line.trim().split("\\s+")).map(Integer::parseInt).toList();

                list.add(numList);
            }
        }

        long total = 0;

        for (int i = 0; i < list.getFirst().size(); i++) {

            String operation = operationList.get(i);

            long result = 0;

            for (int j = 0; j < list.size(); j++) {
                if (j == 0) {
                    result = list.get(j).get(i);
                } else {

                    if ("*".equals(operation)) {
                        result *= list.get(j).get(i);
                    }

                    if ("+".equals(operation)) {
                        result += list.get(j).get(i);
                    }

                }

            }

            total += result;
        }

        System.out.println(total);

    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input6.txt"));

        List<String> operationList = new ArrayList<>();
        List<Integer> startIndexes = new ArrayList<>();
        List<String> numberLines = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            if (line.startsWith("*")) {
                operationList = Arrays.stream(line.split("\\s+")).toList();

                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '*' || line.charAt(i) == '+') {
                        startIndexes.add(i);
                    }
                }

            } else {
                numberLines.add(line);
            }
        }

        int numCount = operationList.size();

        List<List<String>> list = new ArrayList<>();

        for (String numberLine : numberLines) {
            List<String> numList = new ArrayList<>();

            for (int i = 0; i < numCount; i++) {
                String number;

                if (i == numCount - 1) {

                    number = numberLine.substring(startIndexes.get(i));
                } else {

                    number = numberLine.substring(startIndexes.get(i), startIndexes.get(i+1)-1);
                }
                numList.add(number);
            }

            //System.out.println(numList);

            list.add(numList);
        }

        long total = 0;

        for (int i = 0; i < list.getFirst().size(); i++) {

            String operation = operationList.get(i);

            int maxLength = 0;

            for (int j = 0; j < list.size(); j++) {
                String num = list.get(j).get(i);
                if (num.length() > maxLength) {
                    maxLength = num.length();
                }
            }

            //System.out.println(maxLength);

            long result = 0;

            for (int k = maxLength - 1; k >= 0; k--) {

                String finalNum = "";

                for (int j = 0; j < list.size(); j++) {
                    String num = list.get(j).get(i);
                    finalNum += num.charAt(k);
                }

                //System.out.println(finalNum);

                if (k == maxLength-1) {
                    result = Long.parseLong(finalNum.trim());
                } else {

                    if ("*".equals(operation)) {
                        result *= Long.parseLong(finalNum.trim());
                    }

                    if ("+".equals(operation)) {
                        result += Long.parseLong(finalNum.trim());
                    }

                }
            }

            total += result;
        }

        System.out.println(total);
    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
