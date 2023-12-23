package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;


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

    private static class NodeAndDistance {
        private Node node;
        private int distance;

        public NodeAndDistance(Node node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        public Node getNode() {
            return node;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public String toString() {
            return "NodeAndDistance{" +
                    "node=" + node +
                    ", distance=" + distance +
                    '}';
        }
    }

    private static class Node {
        private int x;
        private int y;
        List<NodeAndDistance> neighbours;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public List<NodeAndDistance> getNeighbours() {
            return neighbours;
        }

        public void setNeighbours(List<NodeAndDistance> neighbours) {
            this.neighbours = neighbours;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    ", neighbours=" + neighbours +
                    '}';
        }
    }

    private static int countEmptyAdj(int x, int y, char[][] forest, Set<String> visited) {
        int count = 0;

        for (int i = 0; i < 4; i++) {
            int newX = x + intervals[i][0];
            int newY = y + intervals[i][1];

            if (newY == -1) {
                continue;
            }

            if (forest[newX][newY] != '#' && !visited.contains(newX + "," + newY)) {
                count++;
            }
        }

        return count;
    }

    private static List<NodeAndDistance> getNeighbours(Node node, char[][] forest, Set<String> visited) {

        visited.add(node.getX() + "," + node.getY());
        State start = new State(node.getX(), node.getY(), 0, visited);

        Queue<State> queue = new ArrayDeque<>();
        queue.add(start);

        List<NodeAndDistance> neighbours = new ArrayList<>();

        while (!queue.isEmpty()) {

            State state = queue.remove();

            if ((state.getY() == size - 1 || countEmptyAdj(state.getX(), state.getY(), forest, visited) >= 2) && !(state.getX() == node.getX() && state.getY() == node.getY())) {

                neighbours.add(new NodeAndDistance(new Node(state.getX(), state.getY()), state.getSteps()));

                continue;
            }

            for (int i = 0; i < 4; i++) {
                int newX = state.getX() + intervals[i][0];
                int newY = state.getY() + intervals[i][1];

                if (newY == -1) {
                    continue;
                }

                if (forest[newX][newY] != '#' && !visited.contains(newX + "," + newY)) {
                    queue.add(new State(newX, newY, state.getSteps()+1, visited));
                    visited.add(newX + "," + newY);
                }
            }

        }

        return neighbours;

    }

    private static class StateB {
        private Node node;
        private Set<String> visited;

        public StateB(Node node, Set<String> visited) {
            this.node = node;
            this.visited = visited;
        }

        public Node getNode() {
            return node;
        }

        public Set<String> getVisited() {
            return visited;
        }
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

        Set<String> visited = new HashSet<>();

        Node node = new Node(1, 0);

        Queue<StateB> queue = new ArrayDeque<>();
        queue.add(new StateB(node, visited));

        while (!queue.isEmpty()) {

            StateB state = queue.remove();

            List<NodeAndDistance> neighbours = getNeighbours(state.getNode(), forest, state.getVisited());

            state.getNode().setNeighbours(neighbours);

            //System.out.println("node: " + state.getNode());

            for (NodeAndDistance neighbour : neighbours) {

                if (neighbour.getNode().getY() != size - 1) {
                    queue.add(new StateB(neighbour.getNode(), new HashSet<>(state.getVisited())));
                }
            }

        }


    }
    
    public static void main(String[] args) throws Exception {
        //a();
        b();
    }
}
