package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class _24 {

    private record Func (List<String> vars, String op, String wire){}

    private static int getResult(String wire, Map<String, Integer> values, Map<String, Func> funcs, int depth) {

        if (depth > 4) {
            return 0;
        }

        if (values.get(wire) != null) {
            return values.get(wire);
        }

        Func func = funcs.get(wire);

        String var1 = func.vars.get(0);
        String var2 = func.vars.get(1);

        int value1 = getResult(var1, values, funcs, depth+1);
        int value2 = getResult(var2, values, funcs, depth+1);

        String op = func.op;

        if ("OR".equals(op)) {
            return value1 | value2;
        }

        if ("AND".equals(op)) {
            return value1 & value2;
        }

        if ("XOR".equals(op)) {
            return value1 ^ value2;
        }

        throw new RuntimeException();
    }

    public static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input24.txt"));

        Map<String, Integer> values = new HashMap<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line.isEmpty()) {
                break;
            }

            String name = line.split(": ")[0];
            String value = line.split(": ")[1];

            values.put(name, Integer.parseInt(value));

        }

        Map<String, Func> funcs = new HashMap<>();
        List<String> allWires = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String var1 = line.split(" ")[0];
            String op = line.split(" ")[1];
            String var2 = line.split(" ")[2];
            String result = line.split(" -> ")[1];

            Func func = new Func(List.of(var1, var2), op, result);

            allWires.add(result);

            funcs.put(result, func);
        }


        Collections.sort(allWires, Comparator.reverseOrder());

        System.out.println(allWires);

        Map<String, Integer> wiresAndResults = new HashMap<>();

        for (String wire : allWires) {
            wiresAndResults.put(wire, getResult(wire, values, funcs, 0));
        }

        String finalStr = "";

        for (String wire : allWires) {

            if (wire.startsWith("z")) {
                finalStr += wiresAndResults.get(wire);
            }
        }

        System.out.println(finalStr);

        System.out.println(Long.valueOf(finalStr, 2));
    }

    private static List<Integer> getWrongCount(String input1, String input2, String added) {

        String finalXString = "0" + new StringBuilder(input1).reverse();
        String finalYString = "0" + new StringBuilder(input2).reverse();

        //System.out.println(Long.valueOf(finalStrZ, 2));

        List<Integer> wrongZs = new ArrayList<>();

        int carry = 0;

        for (int j = finalXString.length() - 1; j >=0; j --) {
            int num1 = Integer.parseInt(""+finalXString.charAt(j));
            int num2 = Integer.parseInt(""+finalYString.charAt(j));

            //System.out.println("Num1: " + num1);
            //System.out.println("Num2: " + num2);

            int result = num1 + num2 + carry;

            if (result == 2 || result == 3) {
                carry = 1;
            } else {
                carry = 0;
            }

            int expected = result == 0 || result == 2 ? 0 : 1;

            int actual = Integer.parseInt(""+added.charAt(j));

            //System.out.println("Actual: " + actual);
            //System.out.println("Expected: " + expected);

            if (expected != actual) {
                wrongZs.add(j);
            }

            //System.out.println();

        }

        //System.out.println(wrongZs);

        return wrongZs;
    }

    private static List<Integer> getWrongCount(Map<String, Func> funcs, List<String> allWires, String input1, String input2) {
        //String xString = new StringBuilder("101101010111101101000111000000010110000000011").reverse().toString();
        //String yString = new StringBuilder("100011100111110100010111100110001110000011011").reverse().toString();

        Map<String, Integer> values = new HashMap<>();

        for (int j = 0; j < input1.length(); j++) {
            values.put("x" + ((j < 10) ? "0" : "") + j, Integer.parseInt(""+input1.charAt(j)));
        }

        for (int j = 0; j < input2.length(); j++) {
            values.put("y" + ((j < 10) ? "0" : "") + j, Integer.parseInt(""+input2.charAt(j)));
        }

        Collections.sort(allWires, Comparator.reverseOrder());

        //System.out.println(allWires);

        Map<String, Integer> wiresAndResults = new HashMap<>();

        for (String wire : allWires) {
            wiresAndResults.put(wire, getResult(wire, values, funcs, 0));
        }

        String finalStrZ = "";

        for (String wire : allWires) {

            if (wire.startsWith("z")) {
                finalStrZ += wiresAndResults.get(wire);
            }
        }

        //System.out.println(finalStrZ);

        return getWrongCount(input1, input2, finalStrZ);
    }

    private static List<String> getAllOneBits() {
        List<String> list = new ArrayList<>();

        for (int j = 0; j < 45; j++) {

            String s = "";

            for (int k = 0; k < j; k++) {
                s += "0";
            }

            s += "1";

            for (int k = j; k < 44; k++) {
                s += "0";
            }

            list.add(s);
        }

        return list;
    }

    private record Swap (String str1, String str2){}

    private static List<Swap> pickGoodSwaps(Map<String, Func> funcs, List<String> allWires, List<Swap> allSwaps) {

        int wrongCountNoSwaps = 0;

        List<String> allOneBits = getAllOneBits();

        for (String oneBits : allOneBits) {
            wrongCountNoSwaps += getWrongCount(funcs, allWires, oneBits, oneBits).size();
        }

        List<Swap> goodSwaps = new ArrayList<>();

        for (Swap swap : allSwaps) {

            String swap1 = swap.str1;
            String swap2 = swap.str2;

            Map<String, Func> newFuncs = new HashMap<>(funcs);
            newFuncs.put(swap1, funcs.get(swap2));
            newFuncs.put(swap2, funcs.get(swap1));

            int wrongCount = 0;

            for (String oneBits : allOneBits) {
                wrongCount += getWrongCount(newFuncs, allWires, oneBits, oneBits).size();
            }

            /*List<Integer> wrongCountList1 = getWrongCount(newFuncs, allWires, "101010101010101010101010101010101010101010101", "010101010101010101010101010101010101010101010");
            List<Integer> wrongCountList2 = getWrongCount(newFuncs, allWires, "101101010111101101000111000000010110000000011", "100011100111110100010111100110001110000011011");
            List<Integer> wrongCountList3 = getWrongCount(newFuncs, allWires, "111111000011100001000111001110010110000000011", "100000000111110100010111100010001010000001011");
            int wrongCount = wrongCountList1.size();
            wrongCount += wrongCountList2.size();
            wrongCount += wrongCountList3.size();*/
            //System.out.println(wrongCount);

            if (wrongCount == 0) {
                System.out.println("FOUND!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }

            if (wrongCount < 8) {
                goodSwaps.add(swap);
            }
        }

        return goodSwaps;
    }

    public static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input24.txt"));

        while (true) {
            final String line = fileReader.readLine();

            if (line.isEmpty()) {
                break;
            }
        }

        Map<String, Func> funcs = new HashMap<>();
        List<String> allWires = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String var1 = line.split(" ")[0];
            String op = line.split(" ")[1];
            String var2 = line.split(" ")[2];
            String result = line.split(" -> ")[1];

            Func func = new Func(List.of(var1, var2), op, result);

            allWires.add(result);

            funcs.put(result, func);
        }

        List<Swap> allSwaps = new ArrayList<>();

        for (int j = 0; j < allWires.size(); j++) {
            for (int k = j+1; k < allWires.size(); k++) {

                String swap1 = allWires.get(j);
                String swap2 = allWires.get(k);

                allSwaps.add(new Swap(swap1, swap2));
            }
        }

        List<Swap> goodSwaps = pickGoodSwaps(funcs, allWires, allSwaps);

        //System.out.println(goodSwaps.stream().min((x,y)->new Integer(x.numWrong).compareTo(y.numWrong)));

        for (Swap swap : goodSwaps) {

            Map<String, Func> newFuncs = new HashMap<>(funcs);
            newFuncs.put(swap.str2, funcs.get(swap.str1));
            newFuncs.put(swap.str1, funcs.get(swap.str2));

            List<Swap> goodSwaps2 = pickGoodSwaps(newFuncs, allWires, goodSwaps);

            for (Swap swap2 : goodSwaps2) {

                Map<String, Func> newFuncs2 = new HashMap<>(newFuncs);
                newFuncs2.put(swap2.str2, funcs.get(swap2.str1));
                newFuncs2.put(swap2.str1, funcs.get(swap2.str2));

                List<Swap> goodSwaps3 = pickGoodSwaps(newFuncs2, allWires, goodSwaps2);

                for (Swap swap3 : goodSwaps3) {

                    Map<String, Func> newFuncs3 = new HashMap<>(newFuncs);
                    newFuncs3.put(swap3.str2, funcs.get(swap3.str1));
                    newFuncs3.put(swap3.str1, funcs.get(swap3.str2));

                    List<Swap> goodSwaps4 = pickGoodSwaps(newFuncs3, allWires, goodSwaps3);
                    System.out.println(goodSwaps4.size());
                }
            }

            System.out.println(goodSwaps2.size());
        }

        /*

        // 1
        Swap swap1 = pickSwap(funcs, allWires);

        Func func1 = funcs.get(swap1.str1);
        Func func2 = funcs.get(swap1.str2);

        funcs.put(swap1.str2, func1);
        funcs.put(swap1.str1, func2);

        // 2
        Swap swap2 = pickSwap(funcs, allWires);

        func1 = funcs.get(swap2.str1);
        func2 = funcs.get(swap2.str2);

        funcs.put(swap2.str2, func1);
        funcs.put(swap2.str1, func2);

        // 3

        Swap swap3 = pickSwap(funcs, allWires);

        func1 = funcs.get(swap3.str1);
        func2 = funcs.get(swap3.str2);

        funcs.put(swap3.str2, func1);
        funcs.put(swap3.str1, func2);

        // 4

        Swap swap4 = pickSwap(funcs, allWires);

        func1 = funcs.get(swap4.str1);
        func2 = funcs.get(swap4.str2);

        funcs.put(swap4.str2, func1);
        funcs.put(swap4.str1, func2);

        System.out.println(swap1);
        System.out.println(swap2);
        System.out.println(swap3);
        System.out.println(swap4);*/

        String input1 = "011111000011100001000111001110010110000000011";
        String input2 = "100000000111110100010111100010001010000001011";

        long num1 = Long.parseLong(input1, 2);
        long num2 = Long.parseLong(input2, 2);

        long added = num1 + num2;

        String addedString = Long.toBinaryString(added);

        System.out.println(getWrongCount(input1, input2, addedString));

    }

    private static List<Func> findFaulty(final List<Func> funcs) {
        final List<Func> faultyGates = new ArrayList<>();
        for (final Func func : funcs) {
            String wire = func.wire;
            if (wire.startsWith("z") && !wire.equals("z45")) {
                if (!func.op.equals("XOR")) {
                    faultyGates.add(func);
                }
            } else {
                String var1 = func.vars.get(0);
                String var2 = func.vars.get(1);
                if (!wire.startsWith("z") && !(var1.startsWith("x") || var1.startsWith("y")) && !(var2.startsWith("x") || var2.startsWith("y"))) {
                    if (func.op.equals("XOR")) {
                        faultyGates.add(func);
                    }
                } else if (func.op.equals("XOR") && (var1.startsWith("x") || var1.startsWith("y")) && (var2.startsWith("x") || var2.startsWith("y"))) {
                    if (!(var1.endsWith("00") && var2.endsWith("00"))) {
                        boolean anotherFound = false;
                        for (final Func func2 : funcs) {
                            if (!func2.equals(func)) {
                                if ((func2.vars.get(0).equals(wire) || func2.vars.get(1).equals(wire))
                                        && func2.op.equals("XOR")) {
                                    anotherFound = true;
                                    break;
                                }
                            }
                        }
                        if (!anotherFound) {
                            faultyGates.add(func);
                        }
                    }
                } else if (func.op.equals("AND") && (var1.startsWith("x") || var1.startsWith("y")) && (var2.startsWith("x") || var2.startsWith("y"))) {
                    if (!(var1.endsWith("00") && var2.endsWith("00"))) {
                        boolean anotherFound = false;
                        for (final Func func2 : funcs) {
                            if (!func2.equals(func)) {
                                if ((func2.vars.get(0).equals(wire) || func2.vars.get(1).equals(wire))
                                        && func2.op.equals("OR")) {
                                    anotherFound = true;
                                    break;
                                }
                            }
                        }
                        if (!anotherFound) {
                            faultyGates.add(func);
                        }
                    }
                }
            }
        }
        return faultyGates;
    }

    public static void b_v2() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input24.txt"));

        while (true) {
            final String line = fileReader.readLine();

            if (line.isEmpty()) {
                break;
            }
        }

        List<Func> funcs = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String var1 = line.split(" ")[0];
            String op = line.split(" ")[1];
            String var2 = line.split(" ")[2];
            String result = line.split(" -> ")[1];

            Func func = new Func(List.of(var1, var2), op, result);

            funcs.add(func);
        }

        List<Func> faulty = findFaulty(funcs);
        System.out.println(getOutput(faulty));
    }

    private static String getOutput(final List<Func> faulty) {
        Collections.sort(faulty, Comparator.comparing(x -> x.wire));
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < faulty.size(); i++) {
            sb.append(faulty.get(i).wire);
            if (i < faulty.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        a();
        // Wrong: fgb,kck,mnd,psp,vgw,z17,z35,z36
        //b();
        b_v2();
    }
}
