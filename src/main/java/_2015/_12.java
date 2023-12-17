package _2015;

import java.io.BufferedReader;
import java.io.FileReader;


public class _12 {

    private static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2015/input/input12.txt"));

        final String line = fileReader.readLine();

        int total = 0;

        String currentNumber = "";

        for (char c : line.toCharArray()) {
            if (c == '-' || (c >= '0' && c <= '9')) {
                currentNumber += c;
            } else {

                if (!currentNumber.isEmpty()) {
                    total += Integer.parseInt(currentNumber);
                    currentNumber = "";
                }

            }
        }

        System.out.println(total);

    }

    private static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2015/input/input12.txt"));

        String line = fileReader.readLine();

        while (line.contains(":\"red\"")) {

            int index = line.indexOf(":\"red\"");

            int startIndex = 0;

            for (int j = index; j >= 0; j--) {
                if (line.charAt(j) == '{') {
                    startIndex = j;
                    break;
                }
            }

            int openCount = 0;
            int endIndex = 0;

            for (int j = startIndex+1; j < line.length(); j++) {

                if (line.charAt(j) == '}') {
                    if (openCount == 0) {
                        endIndex = j;
                        break;
                    }
                    openCount--;
                }

                if (line.charAt(j) == '{') {
                    openCount++;
                }

            }

            line = line.substring(0, startIndex) + line.substring(endIndex+1);

        }

        int total = 0;

        String currentNumber = "";

        for (char c : line.toCharArray()) {
            if (c == '-' || (c >= '0' && c <= '9')) {
                currentNumber += c;
            } else {

                if (!currentNumber.isEmpty()) {
                    total += Integer.parseInt(currentNumber);
                    currentNumber = "";
                }

            }
        }

        System.out.println(total);

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }

}