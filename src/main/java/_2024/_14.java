package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _14 {

    static int X_SIZE = 101;
    static int Y_SIZE = 103;

    private static class XY {
        int x;
        int y;

        public XY(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void move(XY velocity) {
            this.x += velocity.getX();
            this.y += velocity.getY();

            while (x < 0) {
                x += X_SIZE;
            }

            while (x >= X_SIZE) {
                x -= X_SIZE;
            }

            while (y < 0) {
                y += Y_SIZE;
            }

            while (y >= Y_SIZE) {
                y -= Y_SIZE;
            }
        }

        @Override
        public String toString() {
            return "XY{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    private record Robot(XY position, XY velocity) {}

    private static List<Robot> parseInput() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input14.txt"));

        List<Robot> robots = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String pos = line.split(" ")[0];
            String vel = line.split(" ")[1];

            XY position = new XY(Integer.parseInt(pos.substring(pos.indexOf("p=")+2, pos.indexOf(","))), Integer.parseInt(pos.substring(pos.indexOf(",")+1)));
            XY velocity = new XY(Integer.parseInt(vel.substring(vel.indexOf("v=")+2, vel.indexOf(","))), Integer.parseInt(vel.substring(vel.indexOf(",")+1)));

            Robot robot = new Robot(position, velocity);

            robots.add(robot);
        }

        return robots;
    }

    public static void a() throws Exception {

        List<Robot> robots = parseInput();

        for (int j = 0; j < 100; j++) {
            for (Robot robot : robots) {
                robot.position.move(robot.velocity);
            }
        }

        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;

        for (Robot robot : robots) {
            if (robot.position.x < X_SIZE / 2) {
                if (robot.position.y < Y_SIZE / 2) {
                    count1++;
                } else if (robot.position.y == Y_SIZE / 2) {
                    // do nothing
                } else {
                    count2++;
                }

            } else if (robot.position.x == X_SIZE / 2) {
                // do nothing
            } else {
                if (robot.position.y < Y_SIZE / 2) {
                    count3++;
                } else if (robot.position.y == Y_SIZE / 2) {
                    // do nothing
                } else {
                    count4++;
                }
            }
        }

        System.out.println(count1*count2*count3*count4);

    }

    private static int maxCluster(char[][] grid) {

        int maxCluster = 0;

        for (int k = 0; k < X_SIZE; k++) {

            boolean previousHadRobot = false;
            int clusterCount = 0;

            for (int m = 0; m < X_SIZE; m++) {
                if (grid[m][k] == '#') {
                    if (previousHadRobot) {
                        clusterCount++;

                        if (clusterCount > maxCluster) {
                            maxCluster = clusterCount;
                        }
                    }
                    previousHadRobot = true;
                } else {
                    previousHadRobot = false;
                    clusterCount = 0;
                }
            }
        }

        return maxCluster;
    }
    
    public static void b() throws Exception {
        List<Robot> robots = parseInput();

        char[][] grid = new char[X_SIZE][Y_SIZE];

        for (int j = 0; j < 10000; j++) {
            for (Robot robot : robots) {
                robot.position.move(robot.velocity);
            }

            for (int k = 0; k < X_SIZE; k++) {
                for (int m = 0; m < X_SIZE; m++) {
                    grid[k][m] = ' ';
                }
            }

            for (Robot robot : robots) {
                grid[robot.position.x][robot.position.y] = '#';
            }

            if (maxCluster(grid) > 10) {
                for (int k = 0; k < X_SIZE; k++) {
                    for (int m = 0; m < X_SIZE; m++) {
                        System.out.print(grid[m][k]);
                    }
                    System.out.println();
                }

                System.out.println(j+1);
            }

        }
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
