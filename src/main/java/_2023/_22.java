package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class _22 {

    private static class Coord {
        int x;
        int y;
        int z;

        public Coord(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public void setZ(int z) {
            this.z = z;
        }

        @Override
        public String toString() {
            return "Coord{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }

    private static class Brick {
        Coord coord1;
        Coord coord2;

        public Brick(Coord coord1, Coord coord2) {
            this.coord1 = coord1;
            this.coord2 = coord2;
        }

        public Coord getCoord1() {
            return coord1;
        }

        public Coord getCoord2() {
            return coord2;
        }

        public int getMinX() {
            return Math.min(coord1.getX(), coord2.getX());
        }

        public int getMaxX() {
            return Math.max(coord1.getX(), coord2.getX());
        }

        public int getMinY() {
            return Math.min(coord1.getY(), coord2.getY());
        }

        public int getMaxY() {
            return Math.max(coord1.getY(), coord2.getY());
        }

        public int getMinZ() {
            return Math.min(coord1.getZ(), coord2.getZ());
        }

        @Override
        public String toString() {
            return "Brick{" +
                    "coord1=" + coord1 +
                    ", coord2=" + coord2 +
                    '}';
        }
    }

    private static int zSize = 400;
    private static int xySize = 10;

    private static int[][][] getSpace(List<Brick> bricks) {
        int[][][] space = new int[xySize][xySize][zSize];

        for (Brick brick : bricks) {

            int xMin = Math.min(brick.getCoord1().getX(), brick.getCoord2().getX());
            int xMax = Math.max(brick.getCoord1().getX(), brick.getCoord2().getX());

            int yMin = Math.min(brick.getCoord1().getY(), brick.getCoord2().getY());
            int yMax = Math.max(brick.getCoord1().getY(), brick.getCoord2().getY());

            int zMin = Math.min(brick.getCoord1().getZ(), brick.getCoord2().getZ());
            int zMax = Math.max(brick.getCoord1().getZ(), brick.getCoord2().getZ());

            for (int x = xMin; x <= xMax; x++) {
                for (int y = yMin; y <= yMax; y++) {
                    for (int z = zMin; z <= zMax; z++) {
                        space[x][y][z] = 1;
                    }
                }
            }

        }

        return space;
    }

    public static void a() throws Exception {

        List<Brick> bricks = parseBricks();
        moveBricksToBottom(bricks);

        int count = 0;

        for (int j = 0; j < bricks.size(); j++) {

            Brick brick = bricks.get(j);

            List<Brick> bricksClone = new ArrayList<>(bricks);
            bricksClone.remove(brick);

            int[][][] space = getSpace(bricksClone);

            boolean anyBrickCanMove = false;

            for (int k = 0; k < bricksClone.size(); k++) {

                Brick brick1 = bricksClone.get(k);

                boolean brickCanMove = true;

                int minZ = brick1.getMinZ();
                if (minZ == 1) {
                    brickCanMove = false;
                } else {
                    for (int x = brick1.getMinX(); x <= brick1.getMaxX(); x++) {
                        for (int y = brick1.getMinY(); y <= brick1.getMaxY(); y++) {
                            if (space[x][y][minZ-1] == 1) {
                                brickCanMove = false;
                            }
                        }
                    }
                }

                if (brickCanMove) {
                    anyBrickCanMove = true;
                    break;
                }

            }

            if (anyBrickCanMove) {
                count ++;
            }

        }

        System.out.println(bricks.size() - count);

    }

    private static List<Brick> parseBricks() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input22.txt"));

        List<Brick> bricks = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            List<Integer> corner1 = Arrays.stream(line.split("~")[0].split(",")).map(v->Integer.parseInt(v)).collect(Collectors.toList());
            List<Integer> corner2 = Arrays.stream(line.split("~")[1].split(",")).map(v->Integer.parseInt(v)).collect(Collectors.toList());

            bricks.add(new Brick(new Coord(corner1.get(0), corner1.get(1), corner1.get(2)), new Coord(corner2.get(0), corner2.get(1), corner2.get(2))));
        }

        return bricks;
    }

    private static void moveBricksToBottom(List<Brick> bricks) {
        bricks.sort((a,b)-> new Integer(a.getMinZ()).compareTo(b.getMinZ()));

        for (int j = 0; j < bricks.size(); j++) {

            int[][][] space = getSpace(bricks);
            Brick brick = bricks.get(j);

            while (true) {
                int minZ = brick.getMinZ();
                if (minZ == 1) {
                    break;
                }

                boolean canMove = true;

                for (int x = brick.getMinX(); x <= brick.getMaxX(); x++) {
                    for (int y = brick.getMinY(); y <= brick.getMaxY(); y++) {
                        if (space[x][y][minZ-1] == 1) {
                            canMove = false;
                            break;
                        }
                    }
                }

                if (!canMove) {
                    break;
                }

                brick.getCoord1().setZ(brick.getCoord1().getZ() - 1);
                brick.getCoord2().setZ(brick.getCoord2().getZ() - 1);

            }

        }
    }

    private static int getBrickFallCount(List<Brick> bricks, Brick disintegrate) {

        List<Brick> bricksClone = new ArrayList<>(bricks);
        bricksClone.remove(disintegrate);

        int brickMoveCount = 0;

        for (int k = 0; k < bricksClone.size(); k++) {

            int[][][] space = getSpace(bricksClone);

            Brick brick = bricksClone.get(k);

            boolean didMove = false;

            while (true) {
                int minZ = brick.getMinZ();
                if (minZ == 1) {
                    break;
                }

                boolean canMove = true;

                for (int x = brick.getMinX(); x <= brick.getMaxX(); x++) {
                    for (int y = brick.getMinY(); y <= brick.getMaxY(); y++) {
                        if (space[x][y][minZ-1] == 1) {
                            canMove = false;
                            break;
                        }
                    }
                }

                if (!canMove) {
                    break;
                }

                didMove = true;
                brick.getCoord1().setZ(brick.getCoord1().getZ() - 1);
                brick.getCoord2().setZ(brick.getCoord2().getZ() - 1);

            }

            if (didMove) {
                brickMoveCount++;
            }

        }

        return brickMoveCount;

    }

    public static void b() throws Exception {

        int count = 0;

        List<Brick> bricks = parseBricks();
        moveBricksToBottom(bricks);

        for (int j = 0; j < bricks.size(); j++) {

            bricks = parseBricks();
            moveBricksToBottom(bricks);

            count += getBrickFallCount(bricks, bricks.get(j));

        }

        System.out.println(count);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
