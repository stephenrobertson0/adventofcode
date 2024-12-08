package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class _8 {

    private static final int SIZE = 50;

    private static Map<Character, List<int[]>> parseInputs() throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input8.txt"));

        Map<Character, List<int[]>> inputs = new HashMap<>();

        int yIndex = 0;

        while (true) {
            String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < SIZE; j++) {
                char c = line.charAt(j);

                if (c != '.') {
                    List<int[]> points = inputs.getOrDefault(c, new ArrayList<>());
                    points.add(new int[] { j, yIndex });
                    inputs.put(c, points);
                }
            }

            yIndex++;
        }
        return inputs;
    }

    public static void a() throws Exception {

        Set<String> allNodes = new HashSet<>();

        Map<Character, List<int[]>> inputs = parseInputs();

        for (char c : inputs.keySet()) {
            List<int[]> points = inputs.get(c);

            for (int j = 0; j < points.size(); j++) {
                for (int k = j + 1; k < points.size(); k++) {
                    int[] point1 = points.get(j);
                    int[] point2 = points.get(k);

                    int xDist = Math.abs(point1[0] - point2[0]);
                    int yDist = Math.abs(point1[1] - point2[1]);

                    int xNode1;
                    int yNode1;

                    if (point1[0] < point2[0]) {
                        xNode1 = point1[0] - xDist;
                    } else {
                        xNode1 = point1[0] + xDist;
                    }

                    if (point1[1] < point2[1]) {
                        yNode1 = point1[1] - yDist;
                    } else {
                        yNode1 = point1[1] + yDist;
                    }

                    int xNode2;
                    int yNode2;

                    if (point2[0] < point1[0]) {
                        xNode2 = point2[0] - xDist;
                    } else {
                        xNode2 = point2[0] + xDist;
                    }

                    if (point2[1] < point1[1]) {
                        yNode2 = point2[1] - yDist;
                    } else {
                        yNode2 = point2[1] + yDist;
                    }

                    if (xNode1 >= 0 && yNode1 >= 0 && xNode1 < SIZE && yNode1 < SIZE) {
                        allNodes.add(xNode1 + " " + yNode1);
                    }

                    if (xNode2 >= 0 && yNode2 >= 0 && xNode2 < SIZE && yNode2 < SIZE) {
                        allNodes.add(xNode2 + " " + yNode2);
                    }

                }
            }

        }

        System.out.println(allNodes.size());

    }

    public static void b() throws Exception {

        Set<String> allNodes = new HashSet<>();

        Map<Character, List<int[]>> inputs = parseInputs();

        for (char c : inputs.keySet()) {
            List<int[]> points = inputs.get(c);

            for (int j = 0; j < points.size(); j++) {
                for (int k = j + 1; k < points.size(); k++) {
                    int[] point1 = points.get(j);
                    int[] point2 = points.get(k);

                    int xDist = Math.abs(point1[0] - point2[0]);
                    int yDist = Math.abs(point1[1] - point2[1]);

                    int xNode1 = point1[0];
                    int yNode1 = point1[1];

                    while (xNode1 >= 0 && yNode1 >= 0 && xNode1 < SIZE && yNode1 < SIZE) {

                        allNodes.add(xNode1 + " " + yNode1);

                        if (point1[0] < point2[0]) {
                            xNode1 -= xDist;
                        } else {
                            xNode1 += xDist;
                        }

                        if (point1[1] < point2[1]) {
                            yNode1 -= yDist;
                        } else {
                            yNode1 += yDist;
                        }
                    }

                    int xNode2 = point2[0];
                    int yNode2 = point2[1];

                    while (xNode2 >= 0 && yNode2 >= 0 && xNode2 < SIZE && yNode2 < SIZE) {

                        allNodes.add(xNode2 + " " + yNode2);

                        if (point2[0] < point1[0]) {
                            xNode2 -= xDist;
                        } else {
                            xNode2 += xDist;
                        }

                        if (point2[1] < point1[1]) {
                            yNode2 -= yDist;
                        } else {
                            yNode2 += yDist;
                        }
                    }

                }
            }

        }

        System.out.println(allNodes.size());

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
