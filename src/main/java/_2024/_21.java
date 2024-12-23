package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;


public class _21 {

    private record XY(int x, int y) {}
    private static int[][] directions = new int[][] {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

    private record State(char current, int distance, List<Character> keyMoves, int index) {}

    private static int KEY_X_SIZE = 3;
    private static int KEY_Y_SIZE = 4;

    private static int DIR_X_SIZE = 3;
    private static int DIR_Y_SIZE = 2;

    private static void printGrid(Character[][] grid) {
        for (int j = 0; j < KEY_Y_SIZE; j++) {
            for (int k = 0; k < KEY_X_SIZE; k++) {
                System.out.print(grid[k][j]);
            }
            System.out.println();
        }
    }

    private static List<String> parseInput() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input21.txt"));

        List<String> codes = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            codes.add(line);

        }

        return codes;

    }

    private static XY getKeyXY(Character[][] keyGrid, char key) {
        for (int j = 0; j < KEY_Y_SIZE; j++) {
            for (int k = 0; k < KEY_X_SIZE; k++) {
                if (keyGrid[k][j] == key) {
                    return new XY(k, j);
                }
            }
        }

        return null;
    }

    private static XY getDirXY(Character[][] dirGrid, char key) {
        for (int j = 0; j < DIR_Y_SIZE; j++) {
            for (int k = 0; k < DIR_X_SIZE; k++) {
                if (dirGrid[k][j] == key) {
                    return new XY(k, j);
                }
            }
        }

        return null;
    }

    private static List<State> getBestMoves(Character[][] keyGrid, char start, String moves) {

        List<State> bestStates = new ArrayList<>();

        Queue<State> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));

        queue.add(new State(start, 0, new ArrayList<>(), 0));

        Map<String, Integer> alreadyVisited = new HashMap<>();

        while (!queue.isEmpty()) {
            State state = queue.poll();

            int existingDistance = alreadyVisited.getOrDefault(state.current + " " + state.index, Integer.MAX_VALUE);
            if (state.distance <= existingDistance) {
                alreadyVisited.put(state.current + " " + state.index, state.distance);
            } else {
                continue;
            }

            if (state.current == moves.charAt(state.index)) {

                List<Character> newKeyMoves = new ArrayList<>(state.keyMoves);
                newKeyMoves.add('A');

                if (state.index == moves.length() - 1) {
                    State minState = new State(state.current, state.distance+1, newKeyMoves, state.index+1);

                    bestStates.add(minState);
                } else {
                    queue.add(new State(state.current, state.distance+1, newKeyMoves, state.index+1));
                }

                continue;
            }

            for (int i = 0; i < directions.length; i++) {

                XY currentXY = getKeyXY(keyGrid, state.current);

                int newX = currentXY.x + directions[i][0];
                int newY = currentXY.y + directions[i][1];

                if (newX >= 0 && newX < KEY_X_SIZE && newY >= 0 && newY < KEY_Y_SIZE && keyGrid[newX][newY] != '#') {

                    char move = ' ';

                    if (i == 0) {
                        move = '^';
                    }
                    if (i == 1) {
                        move = '>';
                    }
                    if (i == 2) {
                        move = 'v';
                    }
                    if (i == 3) {
                        move = '<';
                    }

                    List<Character> newKeyMoves = new ArrayList<>(state.keyMoves);
                    newKeyMoves.add(move);

                    queue.add(new State(keyGrid[newX][newY], state.distance + 1, newKeyMoves, state.index));
                }
            }
        }

        return bestStates;
    }

    private static List<State> getBestDirMoves(Character[][] dirGrid, char start, String moves) {

        List<State> bestStates = new ArrayList<>();

        Queue<State> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));

        queue.add(new State(start, 0, new ArrayList<>(), 0));

        Map<String, Integer> alreadyVisited = new HashMap<>();

        long min = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            State state = queue.poll();

            int existingDistance = alreadyVisited.getOrDefault(state.current + " " + state.index, Integer.MAX_VALUE);
            if (state.distance <= existingDistance) {
                alreadyVisited.put(state.current + " " + state.index, state.distance);
            } else {
                continue;
            }

            if (state.current == moves.charAt(state.index)) {

                List<Character> newKeyMoves = new ArrayList<>(state.keyMoves);
                newKeyMoves.add('A');

                if (state.index == moves.length() - 1) {
                    State minState = new State(state.current, state.distance+1, newKeyMoves, state.index+1);

                    bestStates.add(minState);
                } else {
                    min = state.keyMoves.size();
                    queue.add(new State(state.current, state.distance+1, newKeyMoves, state.index+1));
                }

                continue;
            }

            for (int i = 0; i < directions.length; i++) {

                XY currentXY = getDirXY(dirGrid, state.current);

                int newX = currentXY.x + directions[i][0];
                int newY = currentXY.y + directions[i][1];

                if (newX >= 0 && newX < DIR_X_SIZE && newY >= 0 && newY < DIR_Y_SIZE && dirGrid[newX][newY] != '#') {

                    char move = ' ';

                    if (i == 0) {
                        move = '^';
                    }
                    if (i == 1) {
                        move = '>';
                    }
                    if (i == 2) {
                        move = 'v';
                    }
                    if (i == 3) {
                        move = '<';
                    }

                    List<Character> newKeyMoves = new ArrayList<>(state.keyMoves);
                    newKeyMoves.add(move);

                    queue.add(new State(dirGrid[newX][newY], state.distance + 1, newKeyMoves, state.index));
                }
            }
        }

        return bestStates;
    }

    public static void a() throws Exception {

        List<String> codes = parseInput();

        Character[][] keyGrid = new Character[KEY_X_SIZE][KEY_Y_SIZE];
        keyGrid[0][0] = '7';
        keyGrid[1][0] = '8';
        keyGrid[2][0] = '9';
        keyGrid[0][1] = '4';
        keyGrid[1][1] = '5';
        keyGrid[2][1] = '6';
        keyGrid[0][2] = '1';
        keyGrid[1][2] = '2';
        keyGrid[2][2] = '3';
        keyGrid[0][3] = '#';
        keyGrid[1][3] = '0';
        keyGrid[2][3] = 'A';

        //printGrid(keyGrid);

        Character[][] dirGrid = new Character[DIR_X_SIZE][DIR_Y_SIZE];
        dirGrid[0][0] = '#';
        dirGrid[1][0] = '^';
        dirGrid[2][0] = 'A';
        dirGrid[0][1] = '<';
        dirGrid[1][1] = 'v';
        dirGrid[2][1] = '>';

        long overallComplexity = 0;

        for (String code : codes) {

            // Start on A key
            char start = 'A';

            List<State> moves = getBestMoves(keyGrid, start, code);

            int minLength = Integer.MAX_VALUE;

            for (State keyState : moves) {
                String keyMoves = keyState.keyMoves.stream().map(v->""+v).collect(Collectors.joining());

                List<State> dirMoves = getBestDirMoves(dirGrid, start, keyMoves);

                for (State dirState : dirMoves) {
                    String dirMove = dirState.keyMoves.stream().map(v->""+v).collect(Collectors.joining());

                    List<State> dirMoves2 = getBestDirMoves(dirGrid, start, dirMove);

                    for (State dirState2 : dirMoves2) {

                        String finalMove = dirState2.keyMoves.stream().map(v->""+v).collect(Collectors.joining());

                        if (finalMove.length() < minLength) {
                            minLength = finalMove.length();
                        }
                    }
                }

            }

            long complexity = (long)minLength * Integer.parseInt(code.substring(0,3));
            overallComplexity += complexity;
        }

        System.out.println(overallComplexity);
    }

    static Map<String, Set<String>> transitions = new HashMap<>();

    static {
        transitions.put("A<", Set.of("v<<A", "<v<A"));
        transitions.put("A^", Set.of("<A"));
        transitions.put("A>", Set.of("vA"));
        transitions.put("Av", Set.of("v<A", "<vA"));
        transitions.put("AA", Set.of("A"));

        transitions.put("<A", Set.of(">>^A", ">^>A"));
        transitions.put("<>", Set.of(">>A"));
        transitions.put("<v", Set.of(">A"));
        transitions.put("<^", Set.of(">^A"));
        transitions.put("<<", Set.of("A"));

        transitions.put("^A", Set.of(">A"));
        transitions.put("^<", Set.of("v<A"));
        transitions.put("^>", Set.of("v>A", ">vA"));
        transitions.put("^v", Set.of("vA"));
        transitions.put("^^", Set.of("A"));

        transitions.put(">A", Set.of("^A"));
        transitions.put("><", Set.of("<<A"));
        transitions.put(">^", Set.of("^<A", "<^A"));
        transitions.put(">v", Set.of("<A"));
        transitions.put(">>", Set.of("A"));

        transitions.put("vA", Set.of(">^A", "^>A"));
        transitions.put("v>", Set.of(">A"));
        transitions.put("v<", Set.of("<A"));
        transitions.put("v^", Set.of("^A"));
        transitions.put("vv", Set.of("A"));
    }

    private static long getShortest(String instr, int depth) {

        if (depth == 0) {
            return instr.length();
        }

        long total = 0;

        for (int j = 0; j < instr.length(); j++) {

            char first;
            char second;

            if (j == 0) {
                first = 'A';
            } else {
                first = instr.charAt(j-1);
            }

            second = instr.charAt(j);

            Set<String> possible = transitions.get(""+first+second);

            long minPossible = Long.MAX_VALUE;

            for (String p : possible) {
                long shortest = getShortest(p, depth-1);

                if (shortest < minPossible) {
                    minPossible = shortest;
                }
            }

            total += minPossible;
        }

        return total;

    }

    public static void b() throws Exception {

        List<String> codes = parseInput();

        Character[][] keyGrid = new Character[KEY_X_SIZE][KEY_Y_SIZE];
        keyGrid[0][0] = '7';
        keyGrid[1][0] = '8';
        keyGrid[2][0] = '9';
        keyGrid[0][1] = '4';
        keyGrid[1][1] = '5';
        keyGrid[2][1] = '6';
        keyGrid[0][2] = '1';
        keyGrid[1][2] = '2';
        keyGrid[2][2] = '3';
        keyGrid[0][3] = '#';
        keyGrid[1][3] = '0';
        keyGrid[2][3] = 'A';

        //printGrid(keyGrid);

        Character[][] dirGrid = new Character[DIR_X_SIZE][DIR_Y_SIZE];
        dirGrid[0][0] = '#';
        dirGrid[1][0] = '^';
        dirGrid[2][0] = 'A';
        dirGrid[0][1] = '<';
        dirGrid[1][1] = 'v';
        dirGrid[2][1] = '>';

        for (String code : codes) {
            List<State> bestMoves = getBestMoves(keyGrid, 'A', code);

            String start = bestMoves.get(0).keyMoves.stream().map(v->v.toString()).collect(Collectors.joining());

            System.out.println(start);

            System.out.println(getShortest(start, 2));
        }

        List<State> bestMoves = getBestMoves(keyGrid, 'A', "379A");

        String start = "A" + bestMoves.get(0).keyMoves.stream().map(v->v.toString()).collect(Collectors.joining());

        System.out.println(start);

        String newString = "";

        for (int j = 0; j < start.length() - 1; j++) {
            newString += transitions.get(""+start.charAt(j) + start.charAt(j+1)).iterator().next();
        }

        System.out.println(newString);

        newString = "A" + newString;

        String newString2 = "";

        for (int j = 0; j < newString.length() - 1; j++) {
            newString2 += transitions.get(""+newString.charAt(j) + newString.charAt(j+1)).iterator().next();
        }

        System.out.println(newString2);
    }

    public static void test() throws Exception {
        String s = "v<<A>>^AvA^Av<<A>>^Av<A<A>>^AvA^<A>Av<A<A>>^AvAA^<A>Av<A>^AA<A>Av<A<A>>^AAAvA^<A>A";

        Character[][] keyGrid = new Character[KEY_X_SIZE][KEY_Y_SIZE];
        keyGrid[0][0] = '7';
        keyGrid[1][0] = '8';
        keyGrid[2][0] = '9';
        keyGrid[0][1] = '4';
        keyGrid[1][1] = '5';
        keyGrid[2][1] = '6';
        keyGrid[0][2] = '1';
        keyGrid[1][2] = '2';
        keyGrid[2][2] = '3';
        keyGrid[0][3] = '#';
        keyGrid[1][3] = '0';
        keyGrid[2][3] = 'A';

        printGrid(keyGrid);

        Character[][] dirGrid = new Character[DIR_X_SIZE][DIR_Y_SIZE];
        dirGrid[0][0] = '#';
        dirGrid[1][0] = '^';
        dirGrid[2][0] = 'A';
        dirGrid[0][1] = '<';
        dirGrid[1][1] = 'v';
        dirGrid[2][1] = '>';

        XY xyDir2 = getDirXY(dirGrid, 'A');

        String str = "";

        for (char c : s.toCharArray()) {

            //printGridWithDir(dirGrid, xyDir2);

            //System.out.println("Moving 1: " + c);

            int[] dir = null;

            if (c == '>') {
                dir = new int[] { 1, 0 };
            }

            if (c == '^') {
                dir = new int[] { 0, -1 };
            }

            if (c == '<') {
                dir = new int[] { -1, 0 };
            }

            if (c == 'v') {
                dir = new int[] { 0, 1 };
            }

            if (c == 'A') {

                //System.out.println("Output: " + dirGrid[xyDir2.x][xyDir2.y]);

                str += dirGrid[xyDir2.x][xyDir2.y];
                continue;
            }

            xyDir2 = new XY(xyDir2.x + dir[0], xyDir2.y + dir[1]);
        }

        XY xyDir1 = getDirXY(dirGrid, 'A');

        System.out.println(str);

        String str2 = "";

        for (char c : str.toCharArray()) {

            //printGridWithDir(dirGrid, xyDir1);

            //System.out.println("Moving 2: " + c);

            int[] dir = null;

            if (c == '>') {
                dir = new int[] { 1, 0 };
            }

            if (c == '^') {
                dir = new int[] { 0, -1 };
            }

            if (c == '<') {
                dir = new int[] { -1, 0 };
            }

            if (c == 'v') {
                dir = new int[] { 0, 1 };
            }

            if (c == 'A') {
                str2 += dirGrid[xyDir1.x][xyDir1.y];
                continue;
            }

            xyDir1 = new XY(xyDir1.x + dir[0], xyDir1.y + dir[1]);
        }

        System.out.println(str2);

        XY xyKey = getKeyXY(keyGrid, 'A');

        String str3 = "";

        for (char c : str2.toCharArray()) {

            int[] dir = null;

            if (c == '>') {
                dir = new int[] { 1, 0 };
            }

            if (c == '^') {
                dir = new int[] { 0, -1 };
            }

            if (c == '<') {
                dir = new int[] { -1, 0 };
            }

            if (c == 'v') {
                dir = new int[] { 0, 1 };
            }

            if (c == 'A') {
                str3 += keyGrid[xyKey.x][xyKey.y];
                continue;
            }

            xyKey = new XY(xyKey.x + dir[0], xyKey.y + dir[1]);
        }

        System.out.println(str3);

    }

    public static void main(String[] args) throws Exception {
        //a();
        b();

        //test();
    }
}
