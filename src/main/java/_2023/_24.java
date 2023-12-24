package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class _24 {

    private static class Point {
        long x;
        long y;
        long z;

        public Point(long x, long y, long z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public long getX() {
            return x;
        }

        public long getY() {
            return y;
        }

        public long getZ() {
            return z;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }

    private static class HailStone {
        private Point pos;
        private Point vel;

        public HailStone(Point pos, Point vel) {
            this.pos = pos;
            this.vel = vel;
        }

        public Point getPos() {
            return pos;
        }

        public Point getVel() {
            return vel;
        }

        @Override
        public String toString() {
            return "HailStone{" +
                    "pos=" + pos +
                    ", vel=" + vel +
                    '}';
        }
    }

    public static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input24.txt"));

        List<HailStone> hailStones = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            List<Long> posS = Arrays.stream(line.split("@")[0].trim().split(",")).map(v -> Long.parseLong(v.trim())).collect(Collectors.toList());
            List<Long> velS = Arrays.stream(line.split("@")[1].trim().split(",")).map(v -> Long.parseLong(v.trim())).collect(Collectors.toList());

            Point pos = new Point(posS.get(0), posS.get(1), posS.get(2));
            Point vel = new Point(velS.get(0), velS.get(1), velS.get(2));

            hailStones.add(new HailStone(pos, vel));
        }

        int count = 0;

        long min = 200000000000000L;
        long max = 400000000000000L;

        for (int j = 0; j < hailStones.size() - 1; j++) {
            for (int k = j+1; k < hailStones.size(); k++) {

                HailStone hailStone1 = hailStones.get(j);
                HailStone hailStone2 = hailStones.get(k);

                System.out.println("Comparing: " + hailStone1 + " : " + hailStone2);

                double gradient1 = (double)hailStone1.getVel().getY() / hailStone1.getVel().getX();
                double increment1 = -hailStone1.getPos().getX() * gradient1 + hailStone1.getPos().getY();

                double gradient2 = (double)hailStone2.getVel().getY() / hailStone2.getVel().getX();
                double increment2 = -hailStone2.getPos().getX() * gradient2 + hailStone2.getPos().getY();

                if (gradient1 == gradient2) {
                    continue;
                }

                double intersectionX = - (increment2 - increment1) / (gradient2 - gradient1);
                double intersectionY = - (increment2*gradient1 - increment1*gradient2) / (gradient2 - gradient1);

                System.out.println("Intersection x: " + intersectionX);
                System.out.println("Intersection y: " + intersectionY);

                if (hailStone1.getVel().getX() > 0) {
                    if (intersectionX < hailStone1.getPos().getX()) {
                        continue;
                    }
                }

                if (hailStone1.getVel().getX() < 0) {
                    if (intersectionX > hailStone1.getPos().getX()) {
                        continue;
                    }
                }

                if (hailStone1.getVel().getY() > 0) {
                    if (intersectionY < hailStone1.getPos().getY()) {
                        continue;
                    }
                }

                if (hailStone1.getVel().getY() < 0) {
                    if (intersectionY > hailStone1.getPos().getY()) {
                        continue;
                    }
                }

                if (hailStone2.getVel().getX() > 0) {
                    if (intersectionX < hailStone2.getPos().getX()) {
                        continue;
                    }
                }

                if (hailStone2.getVel().getX() < 0) {
                    if (intersectionX > hailStone2.getPos().getX()) {
                        continue;
                    }
                }

                if (hailStone2.getVel().getY() > 0) {
                    if (intersectionY < hailStone2.getPos().getY()) {
                        continue;
                    }
                }

                if (hailStone2.getVel().getY() < 0) {
                    if (intersectionY > hailStone2.getPos().getY()) {
                        continue;
                    }
                }

                if (intersectionX >= min && intersectionX <= max && intersectionY >= min && intersectionY <= max) {
                    System.out.println("Intersection true");
                    count++;
                }

            }
        }

        System.out.println(count);


    }

    public static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input24.txt"));

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

        }

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
