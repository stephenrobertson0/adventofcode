package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


public class _19 {

    private static class Condition {
        private char rating;
        private char condition;
        private int value;
        private String destination;

        public Condition(char rating, char condition, int value, String destination) {
            this.rating = rating;
            this.condition = condition;
            this.value = value;
            this.destination = destination;
        }

        public char getRating() {
            return rating;
        }

        public char getCondition() {
            return condition;
        }

        public int getValue() {
            return value;
        }

        public String getDestination() {
            return destination;
        }
    }

    private static boolean getResult(Map<Character, Integer> ratingMap, Map<String, List<Condition>> conditions) {

        String location = "in";

        while (true) {

            List<Condition> condition = conditions.get(location);

            String nextState = "";

            for (Condition cond : condition) {

                if (cond.getRating() == 0) {
                    nextState = cond.getDestination();
                    break;
                }

                int value = ratingMap.get(cond.getRating());

                boolean match;

                if (cond.getCondition() == '<') {
                    match = value < cond.getValue();
                } else {
                    match = value > cond.getValue();
                }

                if (match) {
                    nextState = cond.getDestination();
                    break;
                }
            }

            if ("R".equals(nextState)) {
                return false;
            }
            if ("A".equals(nextState)) {
                return true;
            }

            location = nextState;
        }

    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input19.txt"));

        Map<String, List<Condition>> map = new HashMap<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line.isEmpty()) {
                break;
            }

            List<Condition> conditionList = new ArrayList<>();

            String name = line.substring(0, line.indexOf("{"));

            String conditions = line.substring(line.indexOf("{") + 1, line.length() - 1);

            String[] cond = conditions.split(",");

            for (int i = 0; i < cond.length; i++) {
                String c = cond[i];

                if (c.contains(":")) {
                    conditionList.add(new Condition(c.charAt(0), c.charAt(1), Integer.parseInt(c.substring(2, c.indexOf(":"))), c.substring(c.indexOf(":") + 1)));
                } else {
                    conditionList.add(new Condition((char)0, (char)0, 0, c));
                }
            }

            map.put(name, conditionList);
        }

        long total = 0;

        while (true) {
            String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            Map<Character, Integer> ratingMap = new HashMap<>();

            line = line.substring(1, line.length() - 1);

            String[] items = line.split(",");

            for (String item : items) {
                ratingMap.put(item.charAt(0), Integer.parseInt(item.substring(2)));
            }

            if (getResult(ratingMap, map)) {
                total += ratingMap.get('x') + ratingMap.get('m') + ratingMap.get('a') + ratingMap.get('s');
            }

        }

        System.out.println(total);


    }

    private static class MinAndMax {
        private int min;
        private int max;

        public MinAndMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        @Override
        public String toString() {
            return "MinAndMax{" +
                    "min=" + min +
                    ", max=" + max +
                    '}';
        }
    }

    private static class State {
        private String location;
        private Map<Character, MinAndMax> minAndMaxMap;

        public State(String location, Map<Character, MinAndMax> minAndMaxMap) {
            this.location = location;
            this.minAndMaxMap = minAndMaxMap;
        }

        public String getLocation() {
            return location;
        }

        public Map<Character, MinAndMax> getMinAndMaxMap() {
            return minAndMaxMap;
        }

        @Override
        public String toString() {
            return "State{" +
                    "location='" + location + '\'' +
                    ", minAndMaxMap=" + minAndMaxMap +
                    '}';
        }
    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input19.txt"));

        Map<String, List<Condition>> map = new HashMap<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line.isEmpty()) {
                break;
            }

            List<Condition> conditionList = new ArrayList<>();

            String name = line.substring(0, line.indexOf("{"));

            String conditions = line.substring(line.indexOf("{") + 1, line.length() - 1);

            String[] cond = conditions.split(",");

            for (int i = 0; i < cond.length; i++) {
                String c = cond[i];

                if (c.contains(":")) {
                    conditionList.add(new Condition(c.charAt(0), c.charAt(1), Integer.parseInt(c.substring(2, c.indexOf(":"))), c.substring(c.indexOf(":") + 1)));
                } else {
                    conditionList.add(new Condition((char)0, (char)0, 0, c));
                }
            }

            map.put(name, conditionList);
        }


        Stack<State> states = new Stack<>();

        Map<Character, MinAndMax> minAndMaxMap = new HashMap<>();
        minAndMaxMap.put('x', new MinAndMax(1, 4000));
        minAndMaxMap.put('m', new MinAndMax(1, 4000));
        minAndMaxMap.put('a', new MinAndMax(1, 4000));
        minAndMaxMap.put('s', new MinAndMax(1, 4000));

        states.push(new State("in", minAndMaxMap));

        long total = 0;

        while (!states.isEmpty()) {

            State state = states.pop();

            if ("R".equals(state.getLocation())) {
                System.out.println("R hit");
                continue;
            }

            if ("A".equals(state.getLocation())) {
                System.out.println("A hit: " + state);
                total += (long)(state.getMinAndMaxMap().get('x').getMax() - state.getMinAndMaxMap().get('x').getMin() + 1) * (
                        state.getMinAndMaxMap().get('m').getMax() - state.getMinAndMaxMap().get('m').getMin() + 1) * (
                        state.getMinAndMaxMap().get('a').getMax() - state.getMinAndMaxMap().get('a').getMin() + 1) * (
                        state.getMinAndMaxMap().get('s').getMax() - state.getMinAndMaxMap().get('s').getMin() + 1);
                continue;
            }

            List<Condition> condition = map.get(state.getLocation());

            String nextState = "";

            Map<Character, MinAndMax> passingThrough = new HashMap<>(state.getMinAndMaxMap());

            for (Condition cond : condition) {

                if (cond.getRating() == 0) {
                    nextState = cond.getDestination();
                    states.push(new State(nextState, passingThrough));
                    break;
                }

                MinAndMax minAndMax = state.getMinAndMaxMap().get(cond.getRating());
                if (cond.getCondition() == '<') {
                    if (minAndMax.getMin() < cond.getValue() && minAndMax.getMax() < cond.getValue()) {

                        Map<Character, MinAndMax> clone = new HashMap<>(passingThrough);
                        states.push(new State(cond.getDestination(), clone));
                        break;

                    } else if (minAndMax.getMin() <= cond.getValue() && minAndMax.getMax() >= cond.getValue()) {
                        Map<Character, MinAndMax> clone = new HashMap<>(passingThrough);

                        clone.put(cond.getRating(), new MinAndMax(minAndMax.getMin(), cond.getValue()-1));
                        states.push(new State(cond.getDestination(), clone));

                        passingThrough.put(cond.getRating(), new MinAndMax(cond.getValue(), minAndMax.getMax()));
                    }
                } else {
                    if (minAndMax.getMin() > cond.getValue() && minAndMax.getMax() > cond.getValue()) {

                        Map<Character, MinAndMax> clone = new HashMap<>(passingThrough);
                        states.push(new State(cond.getDestination(), clone));
                        break;

                    } else if (minAndMax.getMin() <= cond.getValue() && minAndMax.getMax() >= cond.getValue()) {
                        Map<Character, MinAndMax> clone = new HashMap<>(passingThrough);

                        clone.put(cond.getRating(), new MinAndMax(cond.getValue()+1, minAndMax.getMax()));
                        states.push(new State(cond.getDestination(), clone));

                        passingThrough.put(cond.getRating(), new MinAndMax(minAndMax.getMin(), cond.getValue()));
                    }
                }
            }
        }

        System.out.println(total);
        System.out.println(4000L*4000*4000*4000);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
