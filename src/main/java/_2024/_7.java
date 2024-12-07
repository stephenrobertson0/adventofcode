package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class _7 {

    private record Input (long value, List<Integer> numbers){}

    private static List<Input> parseInputs() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input7.txt"));

        List<Input> inputs = new ArrayList<>();

        while (true) {
            String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String[] input = line.split(": ");
            long value = Long.parseLong(input[0]);
            String[] nums = input[1].split(" ");
            List<Integer> numbers = Arrays.stream(nums).map(v->Integer.parseInt(v)).toList();

            inputs.add(new Input(value, numbers));
        }
        return inputs;
    }

    private static boolean testInput(Input input) {

        int gaps = input.numbers.size() - 1;
        int permutations = (int)Math.pow(2, gaps);

        for (int j = 0; j < permutations; j++) {

            List<Character> operations = new ArrayList<>();

            int bin = j;

            for (int k = 0; k < gaps; k++) {
                if (bin % 2 == 0) {
                    operations.add('+');
                } else {
                    operations.add('*');
                }

                bin /= 2;
            }

            long num = input.numbers.get(0);
            int index = 1;

            for (char operation : operations) {

                if (operation == '+') {
                    num += input.numbers.get(index);
                } else {
                    num *= input.numbers.get(index);
                }

                index++;

            }

            if (num == input.value) {
                return true;
            }

        }

        return false;

    }

    private static boolean testInputV2(Input input) {

        int gaps = input.numbers.size() - 1;
        int permutations = (int)Math.pow(3, gaps);

        for (int j = 0; j < permutations; j++) {

            List<Character> operations = new ArrayList<>();

            int bin = j;

            for (int k = 0; k < gaps; k++) {
                if (bin % 3 == 0) {
                    operations.add('+');
                } else if (bin % 3 == 1) {
                    operations.add('*');
                } else {
                    operations.add('|');
                }

                bin /= 3;
            }

            long num = input.numbers.get(0);
            int index = 1;

            for (char operation : operations) {

                if (operation == '+') {
                    num += input.numbers.get(index);
                } else if (operation == '*') {
                    num *= input.numbers.get(index);
                } else {
                    num = Long.parseLong("" + num + input.numbers.get(index));
                }

                index++;

            }

            if (num == input.value) {
                return true;
            }

        }

        return false;

    }

    public static void a() throws Exception {

        List<Input> inputs = parseInputs();

        long total = 0;

        for (Input input : inputs) {

            if (testInput(input)) {
                total += input.value;
            }

        }

        System.out.println(total);

    }

    public static void b() throws Exception {

        List<Input> inputs = parseInputs();

        long total = 0;

        for (Input input : inputs) {

            if (testInputV2(input)) {
                total += input.value;
            }

        }

        System.out.println(total);

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
