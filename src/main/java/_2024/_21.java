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
import java.util.stream.Collectors;


public class _21 {

    private record XY(int x, int y) {}
    private record State(char current, char dirCurrent, char dir2Current, int distance, List<Character> keyMoves, List<Character> dirMoves, List<Character> dir2Moves) {}
    private record StateDir(char dirCurrent, char dir2Current, int distance, List<Character> moves, List<Character> dir2Moves) {}
    private record StateDir2(char dir2Current, int distance, List<Character> moves) {}
    private static int[][] directions = new int[][] {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

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

    private static StateDir2 getDir2Moves(Character[][] dirGrid, char start, char end) {
        Queue<StateDir2> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));

        queue.add(new StateDir2(start, 0, new ArrayList<>()));

        Map<Character, Integer> alreadyVisited = new HashMap<>();

        long min = Integer.MAX_VALUE;
        StateDir2 minState = null;

        while (!queue.isEmpty()) {
            StateDir2 state = queue.poll();

            int existingDistance = alreadyVisited.getOrDefault(state.dir2Current, Integer.MAX_VALUE);
            if (state.moves.size() < existingDistance) {
                alreadyVisited.put(state.dir2Current, state.moves.size());
            } else {
                continue;
            }

            if (state.dir2Current == end) {
                if (state.moves.size() < min) {
                    min = state.moves.size();
                    minState = state;
                }

                continue;
            }

            for (int i = 0; i < directions.length; i++) {

                XY currentXY = getDirXY(dirGrid, state.dir2Current);

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

                    List<Character> newMoves = new ArrayList<>(state.moves);

                    newMoves.add(move);

                    queue.add(new StateDir2(dirGrid[newX][newY], state.moves.size() + 1, newMoves));
                }
            }
        }

        List<Character> finalMoves = new ArrayList<>(minState.moves);
        finalMoves.add('A');

        return new StateDir2(minState.dir2Current, finalMoves.size(), finalMoves);
    }

    private static StateDir getDirMoves(Character[][] dirGrid, char start, char startDir2, char end) {
        Queue<StateDir> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));

        queue.add(new StateDir(start, startDir2, 0, new ArrayList<>(), new ArrayList<>()));

        Map<Character, Integer> alreadyVisited = new HashMap<>();

        long min = Integer.MAX_VALUE;
        StateDir minState = null;

        while (!queue.isEmpty()) {
            StateDir state = queue.poll();

            int existingDistance = alreadyVisited.getOrDefault(state.dirCurrent, Integer.MAX_VALUE);
            if (state.dir2Moves.size() < existingDistance) {
                alreadyVisited.put(state.dirCurrent, state.dir2Moves.size());
            } else {
                continue;
            }

            if (state.dirCurrent == end) {

                StateDir2 dir2Moves = getDir2Moves(dirGrid, state.dir2Current, 'A');
                int totalDistance = state.dir2Moves.size() + dir2Moves.moves.size();

                List<Character> newDir2Moves = new ArrayList<>(state.dir2Moves);
                newDir2Moves.addAll(dir2Moves.moves);

                List<Character> newDirMoves = new ArrayList<>(state.moves);
                //newDirMoves.add('A');

                if (totalDistance < min) {
                    min = state.dir2Moves.size();
                    minState = new StateDir(state.dirCurrent, state.dir2Current, totalDistance, newDirMoves, newDir2Moves);
                }

                continue;
            }

            for (int i = 0; i < directions.length; i++) {

                XY currentXY = getDirXY(dirGrid, state.dirCurrent);

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

                    List<Character> newMoves = new ArrayList<>(state.moves);

                    newMoves.add(move);

                    StateDir2 dir2Moves = getDir2Moves(dirGrid, state.dir2Current, move);

                    List<Character> newDir2Moves = new ArrayList<>(state.dir2Moves);
                    newDir2Moves.addAll(dir2Moves.moves);

                    queue.add(new StateDir(dirGrid[newX][newY], move, state.dir2Moves.size() + dir2Moves.distance, newMoves, newDir2Moves));
                }
            }
        }

        List<Character> finalMoves = new ArrayList<>(minState.moves);
        finalMoves.add('A');

        return new StateDir(minState.dirCurrent, minState.dir2Current, finalMoves.size(), finalMoves, minState.dir2Moves);
    }

    private static State getMoves(Character[][] keyGrid, Character[][] dirGrid, char start, char dirStart, char dir2Start, char end) {
        Queue<State> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));

        queue.add(new State(start, dirStart, dir2Start, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

        Map<Character, Integer> alreadyVisited = new HashMap<>();

        long min = Integer.MAX_VALUE;
        State minState = null;

        while (!queue.isEmpty()) {
            State state = queue.poll();

            int existingDistance = alreadyVisited.getOrDefault(state.current, Integer.MAX_VALUE);
            if (state.dir2Moves.size() < existingDistance) {
                alreadyVisited.put(state.current, state.dir2Moves.size());
            } else {
                continue;
            }

            if (state.current == end) {

                StateDir dirMoves = getDirMoves(dirGrid, state.dirCurrent, state.dir2Current, 'A');
                int totalDistance = dirMoves.dir2Moves.size() + state.dir2Moves.size();

                List<Character> newDir2Moves = new ArrayList<>(state.dir2Moves);
                newDir2Moves.addAll(dirMoves.dir2Moves);

                List<Character> newDirMoves = new ArrayList<>(state.dirMoves);
                newDirMoves.addAll(dirMoves.moves);

                List<Character> newKeyMoves = new ArrayList<>(state.keyMoves);
                newKeyMoves.add('A');

                if (totalDistance < min) {
                    min = state.dir2Moves.size();
                    minState = new State(state.current, state.dirCurrent, 'A', totalDistance, newKeyMoves, newDirMoves, newDir2Moves);
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

                    StateDir dirMoves = getDirMoves(dirGrid, state.dirCurrent, state.dir2Current, move);
                    int distance = dirMoves.dir2Moves.size();

                    List<Character> newDir2Moves = new ArrayList<>(state.dir2Moves);
                    newDir2Moves.addAll(dirMoves.dir2Moves);

                    List<Character> newDirMoves = new ArrayList<>(state.dirMoves);
                    newDirMoves.addAll(dirMoves.moves);

                    List<Character> newKeyMoves = new ArrayList<>(state.keyMoves);
                    newKeyMoves.add(move);

                    queue.add(new State(keyGrid[newX][newY], move, state.dir2Current, state.distance + distance, newKeyMoves, newDirMoves, newDir2Moves));
                }
            }
        }

        return minState;
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

        printGrid(keyGrid);

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

            // Start on A key
            char dirStart = 'A';

            char dir2Start = 'A';

            List<Character> allMoves = new ArrayList<>();

            for (char c : code.toCharArray()) {
                State moves = getMoves(keyGrid, dirGrid, start, dirStart, dir2Start, c);
                System.out.println(moves);

                allMoves.addAll(moves.dir2Moves);

                dirStart = 'A';
                dir2Start = 'A';

                start = c;
            }

            System.out.println(allMoves.stream().map(v -> v.toString()).collect(Collectors.joining("")));

            long complexity = (long)allMoves.size() * Integer.parseInt(code.substring(0,3));

            System.out.println("Size: " + allMoves.size());
            System.out.println("Numeric portion: " + Integer.parseInt(code.substring(0,3)));
            System.out.println("Adding complexity: " + complexity);

            overallComplexity += complexity;
        }

        System.out.println(overallComplexity);

        //System.out.println(getMoves(keyGrid, dirGrid, '9', dirStart, 'A'));

        //System.out.println(getDirMoves(dirGrid, 'v','A'));
        //System.out.println(getDirMoves(dirGrid, 'A','>'));
        //System.out.println(getDirMoves(dirGrid, 'A','v'));
        //System.out.println(getDirMoves(dirGrid, 'A','<'));

        //System.out.println();

        //System.out.println(getDirMoves(dirGrid, 'A', '<'));
    }

    private static void printGridWithKey(Character[][] grid, XY key) {
        for (int j = 0; j < KEY_Y_SIZE; j++) {
            for (int k = 0; k < KEY_X_SIZE; k++) {
                if (k == key.x && j == key.y) {
                    System.out.print('O');
                } else {
                    System.out.print(grid[k][j]);
                }
            }
            System.out.println();
        }
    }

    private static void printGridWithDir(Character[][] grid, XY dir) {
        for (int j = 0; j < DIR_Y_SIZE; j++) {
            for (int k = 0; k < DIR_X_SIZE; k++) {
                if (k == dir.x && j == dir.y) {
                    System.out.print('O');
                } else {
                    System.out.print(grid[k][j]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void b() throws Exception {
        String s = "v<<A>^>AAv<A<A>^>AAvAA^<A>Av<<A>^>AvA<A>^A<A>Av<A<A>^>AAAvA^<A>Av<A>^A<A>A";

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

            printGridWithDir(dirGrid, xyDir2);

            System.out.println("Moving 1: " + c);

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

                System.out.println("Output: " + dirGrid[xyDir2.x][xyDir2.y]);

                str += dirGrid[xyDir2.x][xyDir2.y];
                continue;
            }

            xyDir2 = new XY(xyDir2.x + dir[0], xyDir2.y + dir[1]);
        }

        XY xyDir1 = getDirXY(dirGrid, 'A');

        System.out.println(str);

        String str2 = "";

        for (char c : str.toCharArray()) {

            printGridWithDir(dirGrid, xyDir1);

            System.out.println("Moving 2: " + c);

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
        // 214170 too high
        a();
        b();
    }
}
