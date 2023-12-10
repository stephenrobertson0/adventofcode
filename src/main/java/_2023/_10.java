package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


public class _10 {

    // 0 up
    // 1 right
    // 2 down
    // 3 left

    private static int size = 140;

    static Map<Character, int[]> openings = new HashMap<>();

    static {
        openings.put('|', new int[]{0, 2});
        openings.put('-', new int[]{1, 3});
        openings.put('L', new int[]{0, 1});
        openings.put('J', new int[]{0, 3});
        openings.put('7', new int[]{2, 3});
        openings.put('F', new int[]{1, 2});
    }

    private static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Position position = (Position)o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    static int[][] neighbours;

    static {
        // 3rd is new block entry direction
        neighbours = new int[][]{{0,-1, 2}, {1, 0, 3}, {0, 1, 0}, {-1, 0, 1}};
    }

    private static class PositionAndDirection {
        Position position;
        int direction;

        public PositionAndDirection(Position position, int direction) {
            this.position = position;
            this.direction = direction;
        }

        public Position getPosition() {
            return position;
        }

        public int getDirection() {
            return direction;
        }

        @Override
        public String toString() {
            return "PositionAndDirection{" +
                    "position=" + position +
                    ", direction=" + direction +
                    '}';
        }
    }

    private static int getInverseDirection(int direction) {
        if (direction == 0) {
            return 2;
        }

        if (direction == 2) {
            return 0;
        }

        if (direction == 1) {
            return 3;
        }

        if (direction == 3) {
            return 1;
        }

        return -1;
    }

    private static List<PositionAndDirection> findNextPositionsFromStart(Position start, char[][] board) {

        List<PositionAndDirection> positions = new ArrayList<>();

        for (int j = 0; j < 4; j ++) {
            int x = start.getX() + neighbours[j][0];
            int y = start.getY() + neighbours[j][1];

            if (x < 0 || x >= size || y < 0 || y >= size) {
                continue;
            }

            char neighbour = board[x][y];

            if (neighbour == '.') {
                continue;
            }

            int[] neighbourOpenings = openings.get(neighbour);

            if (Arrays.stream(neighbourOpenings).boxed().collect(Collectors.toSet()).contains(neighbours[j][2])) {
                positions.add(new PositionAndDirection(new Position(x, y), neighbours[j][2]));
            }
        }

        return positions;

    }

    private static PositionAndDirection getNextPosition(Position position, int entryDirection, char[][] board) {

        if (position.getX() < 0 || position.getX() >= size || position.getY() < 0 || position.getY() >= size) {
            return null;
        }

        char boardValue = board[position.getX()][position.getY()];

        if (boardValue == '.' || boardValue == 'S') {
            return null;
        }

        int[] positionOpenings = openings.get(board[position.getX()][position.getY()]);

        int exitDirection = positionOpenings[0] == entryDirection ? positionOpenings[1] : positionOpenings[0];

        int x = position.getX() + neighbours[exitDirection][0];
        int y = position.getY() + neighbours[exitDirection][1];

        return new PositionAndDirection(new Position(x, y), getInverseDirection(exitDirection));
    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input10.txt"));

        char[][] board = new char[size][size];

        Position start = null;

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < line.length(); j++) {
                board[j][yIndex] = line.charAt(j);

                if (board[j][yIndex] == 'S') {
                    start = new Position(j, yIndex);
                }
            }

            yIndex++;
        }

        List<PositionAndDirection> neighbourPositions = findNextPositionsFromStart(start, board);

        Map<Position, Integer> distances = new HashMap<>();

        PositionAndDirection startPosition = neighbourPositions.get(0);

        int distanceTravelled = 1;
        distances.put(startPosition.getPosition(), distanceTravelled);

        while (true) {

            PositionAndDirection nextPosition = getNextPosition(startPosition.getPosition(), startPosition.getDirection(), board);

            if (nextPosition == null) {
                break;
            }

            startPosition = nextPosition;

            distances.put(startPosition.getPosition(), ++distanceTravelled);

        }

        startPosition = neighbourPositions.get(1);

        distanceTravelled = 1;
        distances.put(startPosition.getPosition(), distanceTravelled);

        while (true) {

            PositionAndDirection nextPosition = getNextPosition(startPosition.getPosition(), startPosition.getDirection(), board);

            if (nextPosition == null) {
                break;
            }

            startPosition = nextPosition;

            Integer existingDistance = distances.get(startPosition.getPosition());

            if (existingDistance == null) {
                distances.put(startPosition.getPosition(), ++distanceTravelled);
            } else {
                distanceTravelled++;

                if (distanceTravelled < existingDistance) {
                    distances.put(startPosition.getPosition(), distanceTravelled);
                }
            }
        }

        distances.remove(new Position(start.getX(), start.getY()));

        int maxDistance = 0;

        for (int distance : distances.values()) {
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }

        int[][] distancesBoard = new int[size][size];

        for (Map.Entry<Position, Integer> entry : distances.entrySet()) {
            distancesBoard[entry.getKey().getX()][entry.getKey().getY()] = entry.getValue();
        }

        /*for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {
                System.out.print(distancesBoard[k][j] + " ");
            }
            System.out.println();
        }*/

        System.out.println(maxDistance);

    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input10.txt"));

        char[][] board = new char[size][size];

        Position start = null;

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int j = 0; j < line.length(); j++) {
                board[j][yIndex] = line.charAt(j);

                if (board[j][yIndex] == 'S') {
                    start = new Position(j, yIndex);
                }
            }

            yIndex++;
        }

        List<PositionAndDirection> neighbourPositions = findNextPositionsFromStart(start, board);

        PositionAndDirection startPosition = neighbourPositions.get(0);

        Set<Position> allPositions = new HashSet<>();
        allPositions.add(startPosition.getPosition());

        while (true) {

            PositionAndDirection nextPosition = getNextPosition(startPosition.getPosition(), startPosition.getDirection(), board);

            if (nextPosition == null) {
                break;
            }

            startPosition = nextPosition;

            allPositions.add(startPosition.getPosition());

        }

        // Replacing the start position with its actual pipe value by manual inspection
        board[start.getX()][start.getY()] = '-';

        Set<Position> allInsides = new HashSet<>();

        int count = 0;

        for (int k = 0; k < size; k++) {

            boolean inside = false;
            boolean upFound = false;
            boolean downFound = false;

            for (int j = 0; j < size; j++) {

                if (inside && !allPositions.contains(new Position(j, k))) {
                    allInsides.add(new Position(j, k));
                    count++;
                }

                char current = board[j][k];

                if (allPositions.contains(new Position(j, k))) {

                    if (current == '|') {
                        inside = !inside;
                    }

                    if (allPositions.contains(new Position(j, k)) && (current == 'F' || current == '7')) {
                        downFound = !downFound;
                    }

                    if (allPositions.contains(new Position(j, k)) && (current == 'J' || current == 'L')) {
                        upFound = !upFound;
                    }

                    if (upFound && downFound) {
                        upFound = false;
                        downFound = false;
                        inside = !inside;
                    }
                }
            }
        }

        /*for (int j = 0; j < size; j++) {
            for (int k = 0; k < size; k++) {

                if (allInsides.contains(new Position(k, j))) {
                    System.out.print("I ");
                } else {
                    System.out.print(board[k][j] + " ");
                }
            }
            System.out.println();
        }*/

        System.out.println(count);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
