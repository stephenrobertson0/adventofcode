package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class _8 {

    private static class Node {

        public Node(String name, String left, String right) {
            this.name = name;
            this.left = left;
            this.right = right;
        }

        String name;
        String left;
        String right;

        public String getName() {
            return name;
        }

        public String getLeft() {
            return left;
        }

        public String getRight() {
            return right;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "name='" + name + '\'' +
                    ", left='" + left + '\'' +
                    ", right='" + right + '\'' +
                    '}';
        }
    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input8.txt"));

        String instructions = fileReader.readLine();
        fileReader.readLine();

        Map<String, Node> nodeMap = new HashMap<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String name = line.substring(0, 3);
            String left = line.substring(7, 10);
            String right = line.substring(12, 15);

            nodeMap.put(name, new Node(name, left, right));
        }

        int count = 0;

        String current = "AAA";
        boolean done = false;

        while (true) {

            for (Character c : instructions.toCharArray()) {

                Node node = nodeMap.get(current);

                if (c == 'L') {
                    current = nodeMap.get(node.getLeft()).getName();
                } else {
                    current = nodeMap.get(node.getRight()).getName();
                }

                count++;

                if ("ZZZ".equals(current)) {
                    done = true;
                    break;
                }
            }

            if (done) {
                break;
            }

        }

        System.out.println(count);

    }

    private static class InitialAndStep {
        private int initial;
        private int step;

        public InitialAndStep(int initial, int step) {
            this.initial = initial;
            this.step = step;
        }

        public int getInitial() {
            return initial;
        }

        public int getStep() {
            return step;
        }

        @Override
        public String toString() {
            return "InitialAndStep{" +
                    "initial=" + initial +
                    ", step=" + step +
                    '}';
        }
    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input8.txt"));

        String instructions = fileReader.readLine();
        fileReader.readLine();

        Map<String, Node> nodeMap = new HashMap<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String name = line.substring(0, 3);
            String left = line.substring(7, 10);
            String right = line.substring(12, 15);

            nodeMap.put(name, new Node(name, left, right));
        }

        List<Node> currentNodes = new ArrayList<>();

        for (Node node : nodeMap.values()) {
            if (node.getName().endsWith("A")) {
                currentNodes.add(node);
            }
        }

        List<InitialAndStep> initialAndSteps = new ArrayList<>();

        for (Node current : currentNodes) {

            int count = 0;

            boolean foundInitial = false;
            int initial = 0;
            int step = 0;

            boolean done = false;

            while (!done) {
                for (Character c : instructions.toCharArray()) {

                    Node node = nodeMap.get(current.getName());

                    if (c == 'L') {
                        current = nodeMap.get(node.getLeft());
                    } else {
                        current = nodeMap.get(node.getRight());
                    }

                    count++;

                    if (current.getName().charAt(2) == 'Z') {
                        if (!foundInitial) {
                            foundInitial = true;
                            initial = count;
                        } else {
                            step = count - initial;

                            initialAndSteps.add(new InitialAndStep(initial, step));

                            done = true;

                            break;
                        }
                    }
                }
            }
        }

        //System.out.println(initialAndSteps);

        Set<Integer> allLowestCommonFactors = new HashSet<>();

        for (InitialAndStep initialAndStep : initialAndSteps) {
            allLowestCommonFactors.addAll(getLowestCommonFactors(initialAndStep.getStep()));
        }

        long count = 1;

        for (Integer factor : allLowestCommonFactors) {
            count *= factor;
        }

        System.out.println(count);

    }

    private static Set<Integer> getLowestCommonFactors(int num) {

        Set<Integer> set = new HashSet<>();

        for (int j = 2; j < num; j++) {
            if (num % j == 0) {
                set.add(j);
            }
        }

        // Will happen if the number is prime
        if (set.size() == 0) {
            set.add(num);
        }

        return set;

    }

    public static void bruteForce() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input8.txt"));

        String instructions = fileReader.readLine();
        fileReader.readLine();

        Map<String, Node> nodeMap = new HashMap<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String name = line.substring(0, 3);
            String left = line.substring(7, 10);
            String right = line.substring(12, 15);

            nodeMap.put(name, new Node(name, left, right));
        }

        Node[] currentNodes = new Node[6];

        int index = 0;

        for (Node node : nodeMap.values()) {
            if (node.getName().endsWith("A")) {
                currentNodes[index++] = node;
            }
        }

        long count = 0;

        boolean done = false;

        while (true) {

            for (Character c : instructions.toCharArray()) {

                for (int j = 0; j < 6; j++) {

                    Node node = currentNodes[j];

                    if (c == 'L') {
                        currentNodes[j] = nodeMap.get(node.getLeft());
                    } else {
                        currentNodes[j] = nodeMap.get(node.getRight());
                    }
                }

                count++;

                boolean finished = true;

                for (Node node : currentNodes) {
                    if (node.getName().charAt(2) != 'Z') {
                        finished = false;
                        break;
                    }
                }

                if (finished) {
                    done = true;
                    break;
                }
            }

            //System.out.println(currentNodes);

            if (done) {
                break;
            }

        }

        System.out.println(count);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
