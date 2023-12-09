package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class _9 {

    private static long getNextNumberOfSequence(List<Long> sequence) {
        List<List<Long>> sequenceSteps = new ArrayList<>();

        List<Long> lastSequence = sequence;
        List<Long> nextSequence = new ArrayList<>();

        sequenceSteps.add(sequence);

        while (true) {

            for (int j = 0; j < lastSequence.size()-1; j++) {
                nextSequence.add(lastSequence.get(j+1) - lastSequence.get(j));
            }

            sequenceSteps.add(nextSequence);
            lastSequence = nextSequence;
            nextSequence = new ArrayList<>();

            boolean allZero = true;

            for (Long value : lastSequence) {
                if (value != 0) {
                    allZero = false;
                }
            }

            if (allZero) {
                break;
            }

        }

        for (int j = sequenceSteps.size()-1; j > 0; j--) {
            List<Long> lastStep = sequenceSteps.get(j);
            List<Long> secondToLastStep = sequenceSteps.get(j-1);
            secondToLastStep.add(secondToLastStep.get(secondToLastStep.size()-1) + lastStep.get(lastStep.size()-1));
        }

        return sequenceSteps.get(0).get(sequenceSteps.get(0).size()-1);
    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input9.txt"));

        List<List<Long>> sequences = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            sequences.add(Arrays.stream(line.split(" ")).map(v->Long.parseLong(v)).collect(Collectors.toList()));
        }

        long total = 0;

        for (List<Long> sequence : sequences) {
            total += getNextNumberOfSequence(sequence);
        }

        System.out.println(total);

    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input9.txt"));

        List<List<Long>> sequences = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            List<Long> numberList = Arrays.stream(line.split(" ")).map(v -> Long.parseLong(v)).collect(Collectors.toList());
            Collections.reverse(numberList);
            sequences.add(numberList);
        }

        long total = 0;

        for (List<Long> sequence : sequences) {
            total += getNextNumberOfSequence(sequence);
        }

        System.out.println(total);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
