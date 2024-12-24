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

    private record Func (List<String> vars, String op){}

    private static int getResult(String wire, Map<String, Integer> values, Map<String, Func> funcs) {

        if (values.get(wire) != null) {
            return values.get(wire);
        }

        Func func = funcs.get(wire);

        String var1 = func.vars.get(0);
        String var2 = func.vars.get(1);

        int value1 = getResult(var1, values, funcs);
        int value2 = getResult(var2, values, funcs);

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

            Func func = new Func(List.of(var1, var2), op);

            allWires.add(result);

            funcs.put(result, func);
        }


        Collections.sort(allWires, Comparator.reverseOrder());

        System.out.println(allWires);

        Map<String, Integer> wiresAndResults = new HashMap<>();

        for (String wire : allWires) {
            wiresAndResults.put(wire, getResult(wire, values, funcs));
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

    public static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input24.txt"));

        Map<String, Integer> values = new HashMap<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line.isEmpty()) {
                break;
            }
        }

        String xString = new StringBuilder("101101010111101101000111000000010110000000011").reverse().toString();
        String yString = new StringBuilder("100011100111110100010111100110001110000011011").reverse().toString();

        for (int j = 0; j < xString.length(); j++) {
            values.put("x" + ((j < 10) ? "0" : "") + j, Integer.parseInt(""+xString.charAt(j)));
        }

        for (int j = 0; j < yString.length(); j++) {
            values.put("y" + ((j < 10) ? "0" : "") + j, Integer.parseInt(""+yString.charAt(j)));
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

            Func func = new Func(List.of(var1, var2), op);

            allWires.add(result);

            funcs.put(result, func);
        }


        Collections.sort(allWires, Comparator.reverseOrder());

        System.out.println(allWires);

        Map<String, Integer> wiresAndResults = new HashMap<>();

        for (String wire : allWires) {
            wiresAndResults.put(wire, getResult(wire, values, funcs));
        }

        String finalStrZ = "";

        for (String wire : allWires) {

            if (wire.startsWith("z")) {
                finalStrZ += wiresAndResults.get(wire);
            }
        }

        System.out.println(finalStrZ);

        /*String finalXString = "0" + new StringBuilder(xString).reverse();
        String finalYString =

        System.out.println();
        System.out.println("0" + new StringBuilder(yString).reverse());*/

        System.out.println(Long.valueOf(finalStrZ, 2));

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
