import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;


public class Referee {

    private static final int X_SIZE = 20;
    private static final int Y_SIZE = 16;

    private record XY(int x, int y) {}

    private record Line(XY point1, XY point2, int length, boolean horizontal) {}

    private record Box(Set<XY> points, int size) {}

    private static class Block {
        private final char[][] block;
        private final int width;
        private final int height;
        private final boolean horizontal;
        private final String blockStr;

        public Block(String blockStr, boolean horizontal) {
            this.blockStr = blockStr;
            char[] blockChars = blockStr.toCharArray();

            if (horizontal) {
                this.width = 6;
                this.height = 2;

                block = new char[width][height];

                for (int i = 0; i < blockChars.length; i++) {
                    block[i][0] = blockChars[i];
                    block[i][1] = blockChars[5 - i];
                }
            } else {
                this.width = 2;
                this.height = 6;

                block = new char[width][height];

                for (int i = 0; i < blockChars.length; i++) {
                    block[0][i] = blockChars[5 - i];
                    block[1][i] = blockChars[i];
                }
            }

            this.horizontal = horizontal;

        }

        public Block makeVertical() {

            return new Block(blockStr, false);
        }

        @Override
        public String toString() {

            String s = "";

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    s += block[j][i];
                }
                s+= '\n';
            }

            return s;
        }

        public char[][] getBlock() {
            return block;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public Set<XY> getOuterPositions() {
            Set<XY> outerPositions = new HashSet<>();

            for (int i = 0; i < width; i++) {
                outerPositions.add(new XY(i, -1));
                outerPositions.add(new XY(i, height));
            }

            for (int i = 0; i < height; i++) {
                outerPositions.add(new XY(-1, i));
                outerPositions.add(new XY(width, i));
            }

            return outerPositions;
        }

        public Set<XY> getInnerPositions() {
            Set<XY> innerPositions = new HashSet<>();

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    innerPositions.add(new XY(i, j));
                }
            }

            return innerPositions;
        }

        public String getBlockStr() {
            return blockStr;
        }
    }

    private static class Move {
        private final Block block;
        private final XY xy;

        public Move(String placement) {
            char verticalIndex = placement.charAt(0);
            char horizontalIndex = placement.charAt(1);

            boolean isHorizontal = placement.charAt(8) == 'h';

            String blockStr = placement.substring(2, 8);

            Block block = new Block(blockStr, isHorizontal);

            int xIndex = horizontalIndex - 97;
            int yIndex = verticalIndex - 65;

            this.block = block;
            this.xy = new XY(xIndex, yIndex);
        }

        public Move(Block block, XY xy) {
            this.block = block;
            this.xy = xy;
        }

        public String getAsMove() {
            char xChar = (char)(xy.x + 97);
            char yChar = (char)(xy.y + 65);

            return "" + yChar + xChar + (block.horizontal ? 'h' : 'v');
        }

        public String getAsPlacement() {
            char xChar = (char)(xy.x + 97);
            char yChar = (char)(xy.y + 65);

            return "" + yChar + xChar + block.getBlockStr() + (block.horizontal ? 'h' : 'v');
        }

        @Override
        public String toString() {
            return "Move{" +
                    "block=" + block +
                    ", xy=" + xy +
                    '}';
        }
    }

    private static void initGrid(Character[][] grid) {
        for (int j = 0; j < Y_SIZE; j++) {
            for (int k = 0; k < X_SIZE; k++) {
                grid[k][j] = '.';
            }
        }
    }

    public static Set<XY> getAllPointsWithColor(Character[][] grid, char color) {
        Set<XY> xyWithColor = new HashSet<>();

        for (int j = 0; j < Y_SIZE; j++) {
            for (int k = 0; k < X_SIZE; k++) {

                if (grid[k][j] == color) {
                    XY xy = new XY(k, j);

                    xyWithColor.add(xy);
                }
            }
        }

        return xyWithColor;
    }

    public static Set<Line> getLines(Set<XY> points) {

        List<XY> pointsList = new ArrayList<>(points);

        Set<Line> lines = new HashSet<>();

        for (int j = 0; j < points.size(); j++) {
            for (int k = j+1; k < points.size(); k++) {

                XY point1 = pointsList.get(j);
                XY point2 = pointsList.get(k);

                if (point1.x == point2.x) {
                    // Vertical line
                    int maxDistanceFromSides = Math.max(X_SIZE - point1.x - 1, point1.x);
                    int length = Math.abs(point1.y - point2.y);

                    // Lines that are too long cannot be made into boxes, depending on their location,
                    // e.g. especially lines in the centre
                    if (length <= maxDistanceFromSides) {
                        lines.add(new Line(point1, point2, length, false));
                    }
                }

                if (point1.y == point2.y) {
                    // Horizontal line
                    int maxDistanceFromSides = Math.max(Y_SIZE - point1.y - 1, point1.y);

                    // Lines that are too long cannot be made into boxes, depending on their location,
                    // e.g. especially lines in the centre
                    int length = Math.abs(point1.x - point2.x);
                    if (length <= maxDistanceFromSides) {
                        lines.add(new Line(point1, point2, length, true));
                    }
                }

            }
        }

        return lines;
    }

    public static Set<Box> getBoxes(Character[][] grid, Set<Line> lines, Set<XY> points) {

        List<Line> linesList = new ArrayList<>(lines);

        Set<Box> boxes = new HashSet<>();

        for (int j = 0; j < lines.size(); j++) {
            for (int k = j+1; k < lines.size(); k++) {

                Line line1 = linesList.get(j);
                Line line2 = linesList.get(k);

                if (line1.length == line2.length
                        // Lines must be in different directions
                        && line1.horizontal != line2.horizontal
                        && (line1.point1.equals(line2.point1) || line1.point2.equals(line2.point2) || line1.point1.equals(line2.point2) || line1.point2.equals(line2.point1))
                ) {
                    Set<XY> pointsInLines = new HashSet<>();
                    pointsInLines.add(line1.point1);
                    pointsInLines.add(line1.point2);
                    pointsInLines.add(line2.point1);
                    pointsInLines.add(line2.point2);

                    int minX = Integer.MAX_VALUE;
                    int minY = Integer.MAX_VALUE;
                    int maxX = -1;
                    int maxY = -1;

                    for (XY point : pointsInLines) {
                        if (point.x < minX) {
                            minX = point.x;
                        }
                        if (point.x > maxX) {
                            maxX = point.x;
                        }
                        if (point.y < minY) {
                            minY = point.y;
                        }
                        if (point.y > maxY) {
                            maxY = point.y;
                        }
                    }

                    XY boxPoint1 = new XY(minX, minY);
                    XY boxPoint2 = new XY(maxX, minY);
                    XY boxPoint3 = new XY(minX, maxY);
                    XY boxPoint4 = new XY(maxX, maxY);
                    if (points.contains(boxPoint1) && points.contains(boxPoint2) && points.contains(boxPoint3) && points.contains(boxPoint4)) {

                        boxes.add(new Box(Set.of(boxPoint1, boxPoint2, boxPoint3, boxPoint4), maxX - minX));
                    }
                }

            }
        }

        return boxes;
    }

    private static void doPlacement(Character[][] grid, Move move) {

        Block block = move.block;
        XY xy = move.xy;

        for (int i = 0; i < block.getWidth(); i++) {
            for (int j = 0; j < block.getHeight(); j++) {
                grid[i+xy.x][j+xy.y] = block.getBlock()[i][j];
            }
        }
    }

    private static boolean isValidPosition(Character[][] grid, Block block, XY xy) {
        boolean hasNeighbour = false;

        for (XY outerPosition : block.getOuterPositions()) {
            int x = xy.x + outerPosition.x;
            int y = xy.y + outerPosition.y;

            if (x>=0 && x<X_SIZE && y>=0 && y<Y_SIZE) {
                if (grid[x][y] != '.') {
                    hasNeighbour = true;
                }
            }
        }

        boolean allInBounds = true;
        int overlapCount = 0;

        for (XY innerPosition : block.getInnerPositions()) {
            int x = xy.x + innerPosition.x;
            int y = xy.y + innerPosition.y;

            if (x>=0 && x<X_SIZE && y>=0 && y<Y_SIZE) {
                if (grid[x][y] != '.') {
                    overlapCount++;
                }
            } else {
                allInBounds = false;
            }
        }

        return allInBounds && hasNeighbour && overlapCount <= 4;
    }

    private static List<Move> getAllValidMoves(Character[][] grid, Block block) {
        List<Move> validMoves = new ArrayList<>();

        for (int j = 0; j < Y_SIZE - 1; j++) {
            for (int k = 0; k < X_SIZE - 5; k++) {
                XY xy = new XY(k, j);
                if (isValidPosition(grid, block, xy)) {
                    validMoves.add(new Move(block, xy));
                }
            }
        }

        block = block.makeVertical();

        for (int j = 0; j < Y_SIZE - 5; j++) {
            for (int k = 0; k < X_SIZE - 1; k++) {
                XY xy = new XY(k, j);
                if (isValidPosition(grid, block, xy)) {
                    validMoves.add(new Move(block, xy));
                }
            }
        }

        return validMoves;
    }

    private static int getFinalScore(Character[][] grid, char color) {
        Set<XY> xyColor = getAllPointsWithColor(grid, color);
        Set<Line> lines = getLines(xyColor);
        Set<Box> boxes = getBoxes(grid, lines, xyColor);

        return boxes.stream().map(v->v.size).reduce(0, Integer::sum);
    }

    private static String getRandomBlock() {
        List<Character> characters = List.of('1', '2', '3', '4', '5', '6');

         return characters.stream().sorted((x,y)-> new Random().nextInt(2) == 0 ? 1 : -1).map(v->v.toString()).collect(Collectors.joining());
    }

    public static void main(String[] args) {

        int winCount = 0;
        int drawCount = 0;
        int loseCount = 0;

        for (int j = 0; j < 10000; j++) {

            Character[][] grid = new Character[X_SIZE][Y_SIZE];
            initGrid(grid);

            int color1 = new Random().nextInt(6) + 1;
            int color2;
            do {
                color2 = new Random().nextInt(6) + 1;
            } while (color1 == color2);

            char c1 = ("" + color1).charAt(0);
            char c2 = ("" + color2).charAt(0);

            ColorBlind colorBlind1 = new ColorBlind(c1);
            ColorBlindV1 colorBlind2 = new ColorBlindV1(c2);

            String startingMove = "Hh" + getRandomBlock() + "h";

            doPlacement(grid, new Move(startingMove));

            colorBlind1.doPlacement(startingMove);
            colorBlind2.doPlacement(startingMove);

            boolean player1Turn = true;

            while (true) {

                String block = getRandomBlock();

                if (player1Turn) {

                    String moveStr = colorBlind1.getNextMove(block);
                    Move move = new Move(moveStr.substring(0, 2) + block + moveStr.charAt(2));

                    colorBlind2.doPlacement(move.getAsPlacement());
                    doPlacement(grid, new Move(move.getAsPlacement()));

                } else {

                    String moveStr = colorBlind2.getNextMove(block);
                    Move move = new Move(moveStr.substring(0, 2) + block + moveStr.charAt(2));

                    colorBlind1.doPlacement(move.getAsPlacement());
                    doPlacement(grid, new Move(move.getAsPlacement()));

                }

                player1Turn = !player1Turn;

                if (getAllValidMoves(grid, new Block("777777", true)).isEmpty()) {
                    break;
                }
            }

            int score1 = getFinalScore(grid, c1);
            int score2 = getFinalScore(grid, c2);

            if (score1 > score2) {
                winCount++;
            } else if (score1 < score2) {
                loseCount++;
            } else {
                drawCount++;
            }
        }

        System.out.println("Win count: " + winCount);
        System.out.println("Lose count: " + loseCount);
        System.out.println("Draw count: " + drawCount);

    }
}
