package _2024;

import java.io.BufferedReader;
import java.io.FileReader;


public class _3 {
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input3.txt"));

        long total = 0;

        while (true) {
            String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String newLine;

            while (line.indexOf("mul(") != -1) {
                int index1 = line.indexOf("mul(");
                newLine = line.substring(index1);

                int index2 = newLine.indexOf(")");
                String nums = newLine.substring(0, index2);

                newLine = newLine.substring(index2);

                String[] numsS = nums.split(",");

                boolean success = true;

                if (numsS.length == 2) {
                    try {
                        int num1 = Integer.parseInt(numsS[0].substring(4));
                        int num2 = Integer.parseInt(numsS[1]);
                        total += (long)num1 * num2;
                    } catch (Exception e) {
                        success = false;
                    }

                } else {
                    success = false;
                }

                if (success) {
                    line = newLine;
                } else {
                    line = line.substring(4);
                }
            }
        }

        System.out.println(total);

    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input3.txt"));

        boolean enabled = true;

        long total = 0;

        while (true) {
            String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String newLine;

            while (line.indexOf("mul(") != -1) {

                int dontIndex = line.indexOf("don't()");
                int doIndex = line.indexOf("do()");

                int index1 = line.indexOf("mul(");

                if (dontIndex != -1 && dontIndex < index1) {
                    enabled = false;
                    line = line.substring(dontIndex + 7);
                    continue;
                }

                if (doIndex != -1 && doIndex < index1) {
                    enabled = true;
                    line = line.substring(doIndex + 4);
                    continue;
                }

                newLine = line.substring(index1);

                int index2 = newLine.indexOf(")");
                String nums = newLine.substring(0, index2);

                newLine = newLine.substring(index2);

                String[] numsS = nums.split(",");

                boolean success = true;

                if (numsS.length == 2) {

                    try {
                        int num1 = Integer.parseInt(numsS[0].substring(4));
                        int num2 = Integer.parseInt(numsS[1]);
                        if (enabled) {
                            total += (long)num1 * num2;
                        }
                    } catch (Exception e) {
                        success = false;
                    }

                } else {
                    success = false;
                }

                if (success) {
                    line = newLine;
                } else {
                    line = line.substring(4);
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
