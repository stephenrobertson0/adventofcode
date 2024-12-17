package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class _17 {

    private static List<Long> getOutput(long registerA, long registerB, long registerC, List<Integer> nums) throws Exception {

        List<Long> outputs = new ArrayList<>();

        int pointer = 0;

        while (pointer < nums.size()) {

            int instruction = nums.get(pointer);
            int operand = nums.get(pointer+1);

            //System.out.println("Instruction: " + instruction);

            if (instruction == 0) {
                long numerator = registerA;
                long denominator = (long)Math.pow(2, getComboValue(operand, registerA, registerB, registerC));

                //System.out.println("Denominator:" + denominator);

                registerA = numerator / denominator;
            } else if (instruction == 1) {

                registerB = operand ^ registerB;

            } else if (instruction == 2) {
                registerB = getComboValue(operand, registerA, registerB, registerC) % 8;
            } else if (instruction == 3) {
                if (registerA != 0) {
                    pointer = operand - 2;
                }
            } else if (instruction == 4) {
                registerB = registerB ^ registerC;
            } else if (instruction == 5) {

                //System.out.println("Outputting: " + getComboValue(operand, registerA, registerB, registerC) % 8);

                outputs.add(getComboValue(operand, registerA, registerB, registerC) % 8);
            } else if (instruction == 6) {
                long numerator = registerA;
                long denominator = (long)Math.pow(2, getComboValue(operand, registerA, registerB, registerC));

                registerB = numerator / denominator;
            } else if (instruction == 7) {
                long numerator = registerA;
                long denominator = (long)Math.pow(2, getComboValue(operand, registerA, registerB, registerC));

                registerC = numerator / denominator;
            } else {
                throw new Exception();
            }

            pointer+=2;

            //System.out.println("Register A: " + registerA + ", B: " + registerB + ", C: " + registerC);
        }

        return outputs;
    }

    private static long getComboValue(int operand, long registerA, long registerB, long registerC) {

        if (operand == 4) {
            return registerA;
        }

        if (operand == 5) {
            return registerB;
        }

        if (operand == 6) {
            return registerC;
        }

        return operand;

    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input17.txt"));

        final String line1 = fileReader.readLine();
        final String line2 = fileReader.readLine();
        final String line3 = fileReader.readLine();
        fileReader.readLine();
        final String line4 = fileReader.readLine();

        long registerA = Long.parseLong(line1.substring(line1.indexOf("Register A: ") + 12));
        long registerB = Long.parseLong(line2.substring(line2.indexOf("Register B: ") + 12));
        long registerC = Long.parseLong(line3.substring(line3.indexOf("Register C: ") + 12));

        String numStr = line4.substring(line4.indexOf("Program: ") + 9);
        List<Integer> nums = Arrays.stream(numStr.split(",")).map(v->Integer.parseInt(v)).toList();

        List<Long> outputs = getOutput(registerA, registerB, registerC, nums);

        System.out.println(outputs.stream().map(v->v.toString()).collect(Collectors.joining(",")));
    }

    public static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input17.txt"));

        final String line1 = fileReader.readLine();
        final String line2 = fileReader.readLine();
        final String line3 = fileReader.readLine();
        fileReader.readLine();
        final String line4 = fileReader.readLine();

        long registerA = Long.parseLong(line1.substring(line1.indexOf("Register A: ") + 12));
        long registerB = Long.parseLong(line2.substring(line2.indexOf("Register B: ") + 12));
        long registerC = Long.parseLong(line3.substring(line3.indexOf("Register C: ") + 12));

        String numStr = line4.substring(line4.indexOf("Program: ") + 9);
        List<Integer> nums = Arrays.stream(numStr.split(",")).map(v->Integer.parseInt(v)).toList();

        List<Long> options = List.of(0L);

        for (int j = 15; j >= 0; j --) {

            List<Long> newOptions = new ArrayList<>();

            for (int k = 0; k < 8; k ++) {
                for (long option : options) {

                    List<Long> output = getOutput(option + k, registerB, registerC, nums);

                    List<Long> expected = nums.subList(nums.size() - output.size(), nums.size()).stream().map(v -> (long)v).toList();

                    if (output.equals(expected)) {

                        newOptions.add((option + k) * (j == 0 ? 1 : 8));
                    }
                }
            }

            options = newOptions;
        }

        //System.out.println(getOutput(236581108670061L, registerB, registerC, nums));

        System.out.println(options.stream().min(Comparator.comparingLong(v->v)).get());

    }

    private static void experimenting() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input17.txt"));

        final String line1 = fileReader.readLine();
        final String line2 = fileReader.readLine();
        final String line3 = fileReader.readLine();
        fileReader.readLine();
        final String line4 = fileReader.readLine();

        long registerA = Long.parseLong(line1.substring(line1.indexOf("Register A: ") + 12));
        long registerB = Long.parseLong(line2.substring(line2.indexOf("Register B: ") + 12));
        long registerC = Long.parseLong(line3.substring(line3.indexOf("Register C: ") + 12));

        String numStr = line4.substring(line4.indexOf("Program: ") + 9);
        List<Integer> nums = Arrays.stream(numStr.split(",")).map(v->Integer.parseInt(v)).toList();

        //System.out.println(getOutput(registerA, registerB, registerC, nums));

        /*for (long j = 100500462592703L; j < 200500450484927L; j+=1) {
            String output = getOutput(j, registerB, registerC, nums);

            if (output.startsWith("2,4,1,3,7,5")) {
                System.out.println(j);
                System.out.println(output);
            }
        }*/

        //getOutput(100500462592703L, registerB, registerC, nums);

        int[] increments = new int[]{8, 7, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8, 8};

        long test = 8L * 7 * 8 * 8 * 8 * 8 * 8 * 8 * 8 * 8 * 8 * 8 * 8 * 8 * 8;
        System.out.println(test);

        int index = 15;

        Map<Integer, Integer> counts = new HashMap<>();

        long increment = 8L * 7 * 8 * 8 * 8 * 8 * 8 * 8 * 8 * 8 * 8 * 8 * 8 * 8;

        for (long k = test; k < test * 8; k+=increment) {
            long a = k;
            long b = 0;
            long c = 0;

            List<Integer> output = new ArrayList<>();

            for (int j = 0; j < 20; j++) {

                //System.out.println("a=" + a + ", b=" + b + ", c=" + c);

                // 2
                b = a % 8;

                //System.out.println("a=" + a + ", b=" + b + ", c=" + c);
                // 1
                b = 3 ^ b;

                //System.out.println("a=" + a + ", b=" + b + ", c=" + c);
                // 7
                c = a / (long)Math.pow(2, b);

                //System.out.println("a=" + a + ", b=" + b + ", c=" + c);

                // 0
                a /= 8;

                //System.out.println("a=" + a + ", b=" + b + ", c=" + c);

                // 4
                b = b ^ c;

                //System.out.println("a=" + a + ", b=" + b + ", c=" + c);

                // 1
                b = 5 ^ b;

                //System.out.println("a=" + a + ", b=" + b + ", c=" + c);

                output.add((int)(b % 8));

                if (a == 0) {
                    break;
                }
            }
            //System.out.println(output);

            //Integer orDefault = counts.getOrDefault(output.size(), 0);
            //counts.put(output.size(), orDefault + 1);

            if (output.size() == 16 && output.get(index) == nums.get(index)) {
                index--;

                System.out.println(output);
                System.out.println(k);

                if (increment > 8L * 7 * 8 * 8) {
                    increment /= increments[index];
                }
            }
        }

        System.out.println(counts);

        System.out.println();

        // Backwards

        List<Integer> backwardsNums = new ArrayList<>(nums);

        Collections.reverse(backwardsNums);

        long a = 0L;
        long b = 0;
        long c = 0;

        for (int j = 0; j < 2; j++) {
            b = backwardsNums.get(j);

            b = 5 ^ b;

            //0,6,6,4,5,5,0,5

            c ^= b;

            a *= 8;

            a /= c;

            for (int k = 0; k < b; k++) {
                a *= b;
            }

            b = 3 ^ b;

            a = a*8 + b;
        }

        System.out.println("a=" + a + ", b=" + b + ", c=" + c);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
        //experimenting();
    }
}
