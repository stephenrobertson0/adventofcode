package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import processing.core.PApplet;


public class _14_Visualization extends PApplet {

    static int X_SIZE = 101;
    static int Y_SIZE = 103;

    private static class XY {
        int initialX;
        int initialY;
        double x;
        double y;
        int color;

        public XY(int x, int y) {
            this.x = x;
            this.y = y;
            this.initialX = x;
            this.initialY = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public void move(XY velocity, double percentage) {
            this.x += velocity.getX() * percentage;
            this.y += velocity.getY() * percentage;;

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

        public void setColor(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }

        public void resetInitial() {
            this.x = initialX;
            this.y = initialY;
        }
    }

    private record Robot(XY position, XY velocity) {}

    private static List<Robot> parseInput() throws RuntimeException {

        try {

            BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input14.txt"));

            List<Robot> robots = new ArrayList<>();

            while (true) {
                final String line = fileReader.readLine();

                if (line == null) {
                    break;
                }

                String pos = line.split(" ")[0];
                String vel = line.split(" ")[1];

                XY position = new XY(Integer.parseInt(pos.substring(pos.indexOf("p=") + 2, pos.indexOf(","))), Integer.parseInt(pos.substring(pos.indexOf(",") + 1)));
                XY velocity = new XY(Integer.parseInt(vel.substring(vel.indexOf("v=") + 2, vel.indexOf(","))), Integer.parseInt(vel.substring(vel.indexOf(",") + 1)));

                Robot robot = new Robot(position, velocity);

                robots.add(robot);
            }

            return robots;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int maxCluster(List<Robot> robots) {

        char[][] grid = new char[X_SIZE][Y_SIZE];

        for (int k = 0; k < X_SIZE; k++) {
            for (int m = 0; m < X_SIZE; m++) {
                grid[k][m] = ' ';
            }
        }

        for (Robot robot : robots) {
            grid[(int)robot.position.x][(int)robot.position.y] = '#';
        }

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

    private static final int ROBOT_SIZE = 10;
    private static final int X_WINDOW_SIZE = X_SIZE * ROBOT_SIZE;
    private static final int Y_WINDOW_SIZE = Y_SIZE * ROBOT_SIZE;

    public void settings() {
        size(X_WINDOW_SIZE, Y_WINDOW_SIZE);
        smooth();
    }

    private void drawRobots(List<Robot> robots) {

        fill(0, 0, 0, (float)(255 - (230 * (1 - percentage))));
        stroke(0, 0, 0, (float)(255 - (230 * (1 - percentage))));
        rect(0, 0, X_WINDOW_SIZE, Y_WINDOW_SIZE);

        for (Robot robot : robots) {
            if (robot.position.getColor() != 0) {
                fill(robot.position.getColor());
                stroke(robot.position.getColor());
            } else {
                fill(0xFFFFFFFF);
                stroke(0xFFFFFFFF);
            }

            float x = (float)robot.position.x;
            float y = (float)robot.position.y;

            rect(x*ROBOT_SIZE, y*ROBOT_SIZE, ROBOT_SIZE, ROBOT_SIZE);
        }

        fill(0);
        stroke(0);
        rect(885, 20, 110, 40);
        DecimalFormat df = new DecimalFormat("#.0", new DecimalFormatSymbols(Locale.ENGLISH));
        textSize(30);
        fill(0xFFFFFFFF);
        text(df.format(count), 900, 50);

        if (count == clusterPoints.get(clusterPoints.size()-1) && !hasPaused) {
            pauseCount = 500;
            hasPaused = true;
        }

        if (pauseCount == 0) {

            for (int clusterPoint : clusterPoints) {

                List<Integer> slowPoints = clusterPoint == clusterPoints.get(clusterPoints.size() - 1) ? List.of(40, 20, 10, 6, 5, 4, 3, 2, 1) : List.of(40, 20, 10, 5, 4, 3, 2, 1);

                for (int slowPoint : slowPoints) {
                    if (count == clusterPoint - slowPoint) {
                        percentage *= 0.5;
                    }

                    if (count == clusterPoint + slowPoint) {
                        percentage *= 2;
                    }
                }
            }

            count += percentage;

            for (Robot robot : robots) {
                robot.position.move(robot.velocity, percentage);
            }
        } else {
            pauseCount--;
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void draw() {

        if (sleepTime != 0) {
            sleep(sleepTime);
            sleepTime = 0;
        }

        drawRobots(robots);

        //saveFrame("frames/######.png");
    }

    private static List<Robot> robots = parseInput();
    private static int sleepTime = 0;
    private static double count = 0;
    private static double percentage = 1;
    private static List<Integer> clusterPoints = new ArrayList<>();
    private static int pauseCount = 0;
    private static boolean hasPaused = false;

    public static void main(String[] args) {

        for (int j = 0; j < 10000; j++) {
            for (Robot robot : robots) {
                robot.position.move(robot.velocity, 1);
            }

            int maxCluster = maxCluster(robots);

            if (maxCluster > 4) {
                clusterPoints.add(j+1);
            }

            if (maxCluster > 10) {

                for (Robot robot : robots) {
                    double x = robot.position.x;
                    double y = robot.position.y;

                    if (x > 62 && x < 66 && y > 51 && y < 55) {
                        robot.position().setColor(0xFF6B492B);
                    } else if (x > 50 && x < 75 && y > 28 && y < 52) {
                        robot.position().setColor(0xFF00873E);
                    } else if (x > 48 && x < 82 && y > 26 && y < 60) {
                        robot.position().setColor(0xFFD6001C);
                    }
                }

                break;
            }
        }

        System.out.println(clusterPoints);

        for (Robot robot : robots) {
            robot.position.resetInitial();
        }

        PApplet.main("_2024._14_Visualization");
    }
}
