package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;


public class _21 {

    private record XY(int x, int y) {}
    private record State(char current, char dirCurrent, int distance, List<Character> keyMoves, List<Character> dirMoves, List<Character> dir2Moves) {}
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

        Set<Character> alreadyVisited = new HashSet<>();

        long min = Integer.MAX_VALUE;
        StateDir2 minState = null;

        while (!queue.isEmpty()) {
            StateDir2 state = queue.poll();

            if (alreadyVisited.contains(state.dir2Current)) {
                continue;
            }

            if (state.dir2Current == end) {
                if (state.distance < min) {
                    min = state.distance;
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

                    queue.add(new StateDir2(dirGrid[newX][newY], state.distance + 1, newMoves));
                }
            }

            alreadyVisited.add(state.dir2Current);
        }

        List<Character> finalMoves = new ArrayList<>(minState.moves);
        finalMoves.add('A');

        return new StateDir2(minState.dir2Current, finalMoves.size(), finalMoves);
    }

    private static StateDir getDirMoves(Character[][] dirGrid, char start, char end) {
        Queue<StateDir> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));

        queue.add(new StateDir(start, end, 0, new ArrayList<>(), new ArrayList<>()));

        Map<Character, Integer> alreadyVisited = new HashMap<>();

        long min = Integer.MAX_VALUE;
        StateDir minState = null;

        while (!queue.isEmpty()) {
            StateDir state = queue.poll();

            int existingDistance = alreadyVisited.getOrDefault(state.dirCurrent, Integer.MAX_VALUE);
            if (state.distance <= existingDistance) {
                alreadyVisited.put(state.dirCurrent, state.distance);
            } else {
                continue;
            }

            if (state.dirCurrent == end) {

                StateDir2 dir2Moves = getDir2Moves(dirGrid, state.dirCurrent, 'A');
                int totalDistance = state.distance + dir2Moves.moves.size();

                List<Character> newDir2Moves = new ArrayList<>(state.dir2Moves);
                newDir2Moves.addAll(dir2Moves.moves);

                List<Character> newDirMoves = new ArrayList<>(state.moves);
                newDirMoves.add('A');

                if (totalDistance < min) {
                    min = state.distance;
                    minState = state;
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

                    StateDir2 dir2Moves = getDir2Moves(dirGrid, state.dirCurrent, move);

                    List<Character> newDir2Moves = new ArrayList<>(state.dir2Moves);
                    newDir2Moves.addAll(dir2Moves.moves);

                    queue.add(new StateDir(dirGrid[newX][newY], move, state.distance + dir2Moves.distance, newMoves, newDir2Moves));
                }
            }
        }

        List<Character> finalMoves = new ArrayList<>(minState.moves);
        finalMoves.add('A');

        return new StateDir(minState.dirCurrent, minState.dir2Current, finalMoves.size(), finalMoves, minState.dir2Moves);
    }

    private static State getMoves(Character[][] keyGrid, Character[][] dirGrid, char start, char dirStart, char end) {
        Queue<State> queue = new PriorityQueue<>(Comparator.comparingInt(o -> o.distance));

        queue.add(new State(start, dirStart, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

        Map<Character, Integer> alreadyVisited = new HashMap<>();

        long min = Integer.MAX_VALUE;
        State minState = null;

        while (!queue.isEmpty()) {
            State state = queue.poll();

            int existingDistance = alreadyVisited.getOrDefault(state.current, Integer.MAX_VALUE);
            if (state.distance <= existingDistance) {
                alreadyVisited.put(state.current, state.distance);
            } else {
                continue;
            }

            if (state.current == end) {

                StateDir dirMoves = getDirMoves(dirGrid, state.dirCurrent, 'A');
                int totalDistance = state.distance + dirMoves.moves.size();

                List<Character> newDir2Moves = new ArrayList<>(state.dir2Moves);
                newDir2Moves.addAll(dirMoves.dir2Moves);

                List<Character> newDirMoves = new ArrayList<>(state.dirMoves);
                newDirMoves.addAll(dirMoves.moves);

                List<Character> newKeyMoves = new ArrayList<>(state.keyMoves);
                newKeyMoves.add('A');

                if (totalDistance < min) {
                    min = state.distance;
                    minState = new State(state.current, state.dirCurrent, totalDistance, newKeyMoves, newDirMoves, newDir2Moves);
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

                    StateDir dirMoves = getDirMoves(dirGrid, state.dirCurrent, move);
                    int distance = dirMoves.dir2Moves.size();

                    List<Character> newDir2Moves = new ArrayList<>(state.dir2Moves);
                    newDir2Moves.addAll(dirMoves.dir2Moves);

                    List<Character> newDirMoves = new ArrayList<>(state.dirMoves);
                    newDirMoves.addAll(dirMoves.moves);

                    List<Character> newKeyMoves = new ArrayList<>(state.keyMoves);
                    newKeyMoves.add(move);

                    queue.add(new State(keyGrid[newX][newY], move, state.distance + distance, newKeyMoves, newDirMoves, newDir2Moves));
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

        Character[][] dirGrid = new Character[KEY_X_SIZE][KEY_Y_SIZE];
        dirGrid[0][0] = '#';
        dirGrid[1][0] = '^';
        dirGrid[2][0] = 'A';
        dirGrid[0][1] = '<';
        dirGrid[1][1] = 'v';
        dirGrid[2][1] = '>';

        String code = "029A";

        // Start on A key
        char start = 'A';

        // Start on A key
        char dirStart = 'A';

        List<Character> allMoves = new ArrayList<>();

        for (char c : code.toCharArray()) {
            State moves = getMoves(keyGrid, dirGrid, start, dirStart, c);
            System.out.println(moves);

            allMoves.addAll(moves.dir2Moves);

            start = c;
        }

        System.out.println(allMoves.stream().map(v->v.toString()).collect(Collectors.joining("")));

        //System.out.println(getMoves(keyGrid, dirGrid, '9', dirStart, 'A'));

        System.out.println(getDirMoves(dirGrid, 'v','A'));
        System.out.println(getDirMoves(dirGrid, 'A','>'));
        //System.out.println(getDirMoves(dirGrid, 'A','v'));
        //System.out.println(getDirMoves(dirGrid, 'A','<'));

        //System.out.println();

        //System.out.println(getDirMoves(dirGrid, 'A', '<'));
    }

    public static void b() throws Exception {

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
