package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;


public class _23 {

    private static class State {
        private int x;
        private int y;
        private int steps;
        private Set<String> visited;

        public State(int x, int y, int steps, Set<String> visited) {
            this.x = x;
            this.y = y;
            this.steps = steps;
            this.visited = visited;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getSteps() {
            return steps;
        }

        public Set<String> getVisited() {
            return visited;
        }
    }

    private static int[][] intervals = {{0,1},{0,-1},{1,0},{-1,0}};

    private static int size = 141;

    public static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input23.txt"));

        char[][] forest = new char[size][size];

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int i = 0; i < size; i++) {
                forest[i][yIndex] = line.charAt(i);
            }

            yIndex++;

        }

        Set<String> startVisited = new HashSet<>();
        startVisited.add("1,0");
        State start = new State(1, 0, 0, startVisited);

        Queue<State> queue = new ArrayDeque<>();
        queue.add(start);

        int max = 0;

        while (!queue.isEmpty()) {

            State state = queue.remove();

            Set<String> visited = state.getVisited();

            if (state.getY() == size - 1) {

                if (state.getSteps() > max) {
                    max = state.getSteps();
                }

                continue;
            }

            char c = forest[state.getX()][state.getY()];

            int newX = state.getX();
            int newY = state.getY();

            if (c == '>') {
                newX ++;

                if (!visited.contains(newX + "," + newY)) {
                    queue.add(new State(newX, newY, state.getSteps() + 1, visited));
                    visited.add(newX + "," + newY);
                }
            } else if (c == '<') {
                newX --;

                if (!visited.contains(newX + "," + newY)) {
                    queue.add(new State(newX, newY, state.getSteps() + 1, visited));
                    visited.add(newX + "," + newY);
                }
            } else if (c == 'v') {
                newY ++;

                if (!visited.contains(newX + "," + newY)) {
                    queue.add(new State(newX, newY, state.getSteps() + 1, visited));
                    visited.add(newX + "," + newY);
                }
            } else if (c == '^') {
                newY --;

                if (!visited.contains(newX + "," + newY)) {
                    queue.add(new State(newX, newY, state.getSteps() + 1, visited));
                    visited.add(newX + "," + newY);
                }
            } else {

                for (int i = 0; i < 4; i++) {
                    newX = state.getX() + intervals[i][0];
                    newY = state.getY() + intervals[i][1];

                    if (newY == -1) {
                        continue;
                    }

                    if (forest[newX][newY] != '#' && !visited.contains(newX + "," + newY)) {
                        Set<String> newVisited = new HashSet<>(visited);
                        queue.add(new State(newX, newY, state.getSteps()+1, newVisited));
                        newVisited.add(newX + "," + newY);
                    }
                }
            }

        }

        System.out.println(max);

    }

    private static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
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
            return "Point{" +
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
            Point point = (Point)o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static class PointAndDistance {

        private Point point;
        private int distance;

        public PointAndDistance(Point point, int distance) {
            this.point = point;
            this.distance = distance;
        }

        public Point getPoint() {
            return point;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public String toString() {
            return "PointAndDistance{" +
                    "point=" + point +
                    ", distance=" + distance +
                    '}';
        }
    }

    private static int countEmptyAdj(int x, int y, char[][] forest) {
        int count = 0;

        for (int i = 0; i < 4; i++) {
            int newX = x + intervals[i][0];
            int newY = y + intervals[i][1];

            if (newX == -1 || newY == -1 || newX == size || newY == size) {
                continue;
            }

            if (forest[newX][newY] != '#') {
                count++;
            }
        }

        return count;
    }

    public static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input23.txt"));

        char[][] forest = new char[size][size];

        int yIndex = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            for (int i = 0; i < size; i++) {
                forest[i][yIndex] = line.charAt(i);
            }

            yIndex++;

        }

        List<Point> nodes = new ArrayList<>();
        nodes.add(new Point(1, 0));
        nodes.add(new Point(size - 2, size - 1));

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                char c = forest[i][j];

                if (c != '#') {
                    if (countEmptyAdj(i, j, forest) >= 3) {
                        nodes.add(new Point(i, j));
                    }
                }

            }
        }

        Map<Point, List<PointAndDistance>> pointsAndNeighbours = new HashMap<>();

        for (Point currentNode : nodes) {

            Queue<State> queue = new ArrayDeque<>();
            queue.add(new State(currentNode.getX(), currentNode.getY(), 0, null));

            Set<String> visited = new HashSet<>();

            while (!queue.isEmpty()) {

                State state = queue.remove();

                for (int i = 0; i < 4; i++) {
                    int newX = state.getX() + intervals[i][0];
                    int newY = state.getY() + intervals[i][1];

                    if (newX == -1 || newY == -1 || newX == size || newY == size) {
                        continue;
                    }

                    if (nodes.contains(new Point(newX, newY)) && !currentNode.equals(new Point(newX, newY))) {
                        List<PointAndDistance> pointAndDistances = pointsAndNeighbours.get(currentNode);

                        if (pointAndDistances == null) {
                            pointAndDistances = new ArrayList<>();
                            pointsAndNeighbours.put(currentNode, pointAndDistances);
                        }

                        pointAndDistances.add(new PointAndDistance(new Point(newX, newY), state.getSteps()+1));

                        continue;

                    }

                    if (forest[newX][newY] != '#' && !visited.contains(newX + "," + newY)) {
                        queue.add(new State(newX, newY, state.getSteps() + 1, null));
                        visited.add(newX + "," + newY);
                    }
                }
            }
        }


        Set<String> startVisited = new HashSet<>();
        startVisited.add("1,0");
        State start = new State(1, 0, 0, startVisited);

        Stack<State> queue = new Stack<>();
        queue.add(start);

        int max = 0;

        while (!queue.isEmpty()) {

            State state = queue.pop();

            Set<String> visited = state.getVisited();

            if (state.getY() == size - 1) {

                if (state.getSteps() > max) {
                    max = state.getSteps();
                }

                continue;
            }

            List<PointAndDistance> neighbours = pointsAndNeighbours.get(new Point(state.getX(), state.getY()));

            for (PointAndDistance pointAndDistance : neighbours) {
                int newX = pointAndDistance.getPoint().getX();
                int newY = pointAndDistance.getPoint().getY();

                if (!visited.contains(newX + "," + newY)) {
                    Set<String> newVisited = new HashSet<>(visited);
                    queue.add(new State(newX, newY, state.getSteps() + pointAndDistance.getDistance(), newVisited));
                    newVisited.add(newX + "," + newY);
                }
            }

        }

        System.out.println(max);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
