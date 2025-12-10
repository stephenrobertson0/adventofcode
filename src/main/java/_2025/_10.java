package _2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class _10 {

    private record Button(List<Integer> toggles) {

    }

    private record Config(String desired, List<Button> buttons) {

    }

    private record State(String current, int pressCount, int differenceCount) {

    }

    private static int getDifferenceCount(String current, String desired) {
        int count = 0;

        for (int j = 0; j < current.length(); j++) {
            if (current.charAt(j) != desired.charAt(j)) {
                count++;
            }
        }

        return count;
    }

    private static String pressButton(String current, List<Integer> toggles) {
        char[] result = current.toCharArray();

        for (Integer toggle : toggles) {
            if (result[toggle] == '.') {
                result[toggle] = '#';
            } else {
                result[toggle] = '.';
            }
        }

        return new String(result);
    }

    private static int getMinPresses(Config config) {

        String desired = config.desired;

        String start = IntStream.range(0, desired.length()).mapToObj(v-> ".").collect(Collectors.joining());

        State startState = new State(start, 0, getDifferenceCount(desired, start));

        List<State> states = new ArrayList<>();
        states.add(startState);

        while (!states.isEmpty()) {

            State considerState = states.remove(0);

            if (considerState.current.equals(desired)) {
                return considerState.pressCount;
            }

            for (int j = 0; j < config.buttons.size(); j++) {

                Button button = config.buttons.get(j);

                String newStateStr = pressButton(considerState.current, button.toggles);

                State newState = new State(newStateStr, considerState.pressCount + 1, getDifferenceCount(newStateStr, desired));

                states.add(newState);
            }

            states.sort(Comparator.comparingInt(x -> x.differenceCount + x.pressCount*2));

            //System.out.println(states);

        }

        //System.out.println(start);

        return 0;
    }

    public static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input10.txt"));

        List<Config> configs = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String desired = line.substring(line.indexOf('[') + 1, line.indexOf(']'));

            String buttons = line.substring(line.indexOf(']') + 1, line.indexOf('{'));

            //System.out.println(desired);
            String[] buttonsArr = buttons.trim().split(" ");

            List<Button> buttonList = new ArrayList<>();

            for (String button : buttonsArr) {
                String[] toggles = button.substring(1, button.length() - 1).split(",");
                List<Integer> toggleList = Arrays.stream(toggles).map(Integer::parseInt).toList();
                buttonList.add(new Button(toggleList));
            }

            configs.add(new Config(desired, buttonList));
        }

        System.out.println(configs.size());

        long total = 0;

        for (Config config : configs) {
            int minPresses = getMinPresses(config);

            System.out.println(minPresses);
            total += minPresses;
        }

        // 485 - Wrong
        System.out.println(total);

    }

    private record Config2(List<Integer> desired, List<Button> buttons) {

    }

    private static final Map<List<Integer>, Integer> stateMap = new HashMap<>();

    private static int getMinCount(Config2 config) {

        if (stateMap.containsKey(config.desired)) {
            return stateMap.get(config.desired);
        }

        if (config.desired.stream().allMatch(v->v==0)) {
            return 0;
        }

        int min = 100000;

        List<Integer> pickedJoltage = null;

        for (int j = 0; j < config.buttons.size(); j++) {
            Button button = config.buttons.get(j);

            List<Integer> joltage = new ArrayList<>(config.desired);

            for (int k = 0; k < button.toggles.size(); k++) {
                joltage.set(button.toggles.get(k), joltage.get(button.toggles.get(k))-1);
            }

            //System.out.println("New joltage: " + joltage);

            if (joltage.stream().anyMatch(v -> v < 0)) {
                continue;
            }

            int newCount = getMinCount(new Config2(joltage, config.buttons));

            if (newCount == 100001) {
                continue;
            }

            if (newCount < min) {
                min = newCount;
                pickedJoltage = joltage;
            }
        }

        if (pickedJoltage != null) {
            //System.out.println(pickedJoltage + " " + min);
            stateMap.put(pickedJoltage, min);
        }

        return 1 + min;
    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input10.txt"));

        List<Config2> configs = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String buttons = line.substring(line.indexOf(']') + 1, line.indexOf('{'));

            //System.out.println(desired);
            String[] buttonsArr = buttons.trim().split(" ");

            List<Button> buttonList = new ArrayList<>();

            for (String button : buttonsArr) {
                String[] toggles = button.substring(1, button.length() - 1).split(",");
                List<Integer> toggleList = Arrays.stream(toggles).map(Integer::parseInt).toList();
                buttonList.add(new Button(toggleList));
            }

            String joltage = line.substring(line.indexOf('{') + 1, line.indexOf('}'));

            String[] joltageArr = joltage.trim().split(",");

            List<Integer> joltageList = Arrays.stream(joltageArr).map(Integer::parseInt).toList();

            configs.add(new Config2(joltageList, buttonList));
        }

        long total = 0;

        for (Config2 config : configs) {
            System.out.println("HERE");
            int minPresses = getMinCount(config);
            total += minPresses;
        }

        System.out.println(total);

    }

    public static void main(String[] args) throws Exception {
        //a();
        b();
    }
}
