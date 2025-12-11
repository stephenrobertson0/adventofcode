package _2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;
import com.microsoft.z3.IntNum;
import com.microsoft.z3.Model;
import com.microsoft.z3.Optimize;
import com.microsoft.z3.Status;


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

        //System.out.println(configs.size());

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

    private static int getMinCount(Config2 config) {

        List<List<Integer>> positionToButtons = new ArrayList<>();

        for (int i = 0; i < config.desired().size(); i++) {

            List<Integer> buttons = new ArrayList<>();

            for (int j = 0; j < config.buttons().size(); j++) {
                if (config.buttons.get(j).toggles().contains(i)) {
                    buttons.add(j);
                }
            }

            positionToButtons.add(buttons);
        }

        Context ctx = new Context();
        Optimize opt = ctx.mkOptimize();
        IntExpr presses = ctx.mkIntConst("presses");

        IntExpr[] buttonVars = IntStream.range(0, config.buttons.size())
                .mapToObj(i -> ctx.mkIntConst("button" + i))
                .toArray(IntExpr[]::new);

        for (int j = 0; j < config.desired().size(); j++) {
            List<IntExpr> counterButtons = positionToButtons.get(j).stream().map(v -> buttonVars[v]).toList();

            IntExpr targetValue = ctx.mkInt(config.desired.get(j));

            IntExpr[] buttonPressesArray = counterButtons.toArray(new IntExpr[0]);

            IntExpr sumOfButtonPresses = (IntExpr) ctx.mkAdd(buttonPressesArray);

            BoolExpr equation = ctx.mkEq(targetValue, sumOfButtonPresses);
            opt.Add(equation);
        }

        IntExpr zero = ctx.mkInt(0);
        for (IntExpr buttonVar : buttonVars) {
            BoolExpr nonNegative = ctx.mkGe(buttonVar, zero);
            opt.Add(nonNegative);
        }

        IntExpr sumOfAllButtonVars = (IntExpr) ctx.mkAdd(buttonVars);
        BoolExpr totalPressesEq = ctx.mkEq(presses, sumOfAllButtonVars);
        opt.Add(totalPressesEq);

        opt.MkMinimize(presses);

        Status status = opt.Check();

        if (status == Status.SATISFIABLE) {
            Model model = opt.getModel();
            IntNum outputValue = (IntNum) model.evaluate(presses, false);
            return outputValue.getInt();
        } else if (status == Status.UNSATISFIABLE) {
            System.out.println("No solution exists");
        } else {
            System.out.println("Optimization could not be determined (" + status + ").");
        }
        return Integer.MIN_VALUE;
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
            int minPresses = getMinCount(config);
            total += minPresses;
        }

        System.out.println(total);

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
