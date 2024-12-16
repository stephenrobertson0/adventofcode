package _2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Stream;


public class _17 {

    private record State(List<Integer> current, List<Integer> remaining) {}
    private static int TARGET = 150;

    private static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2015/input/input17.txt"));

        List<Integer> sizes = new ArrayList<>();

        while (true) {

            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            sizes.add(Integer.parseInt(line));

        }

        Stack<State> stack = new Stack<>();

        stack.push(new State(List.of(), sizes));

        long count = 0;

        while (!stack.isEmpty()) {

            final State state = stack.pop();

            for (int j = 0; j < state.remaining.size(); j++) {

                int size = state.remaining.get(j);

                List<Integer> current = Stream.concat(state.current.stream(), Stream.of(size)).toList();
                List<Integer> remaining = state.remaining.subList(j+1, state.remaining.size());

                Integer sum = current.stream().reduce(0, Integer::sum);
                if (sum == TARGET) {
                    count++;
                } else if (sum < TARGET && !remaining.isEmpty()) {
                    stack.push(new State(current, remaining));
                }
            }

        }

        System.out.println(count);

    }

    private static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2015/input/input17.txt"));

        List<Integer> sizes = new ArrayList<>();

        while (true) {

            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            sizes.add(Integer.parseInt(line));

        }

        Stack<State> stack = new Stack<>();

        stack.push(new State(List.of(), sizes));

        List<List<Integer>> results = new ArrayList<>();

        int minimum = Integer.MAX_VALUE;

        while (!stack.isEmpty()) {

            final State state = stack.pop();

            for (int j = 0; j < state.remaining.size(); j++) {

                int size = state.remaining.get(j);

                List<Integer> current = Stream.concat(state.current.stream(), Stream.of(size)).toList();
                List<Integer> remaining = state.remaining.subList(j+1, state.remaining.size());

                Integer sum = current.stream().reduce(0, Integer::sum);
                if (sum == TARGET) {
                    results.add(current);
                    if (current.size() < minimum) {
                        minimum = current.size();
                    }
                } else if (sum < TARGET && !remaining.isEmpty()) {
                    stack.push(new State(current, remaining));
                }
            }

        }

        int min = minimum;;

        System.out.println(results.stream().filter(v->v.size() == min).count());

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }

}