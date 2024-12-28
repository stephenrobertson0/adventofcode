import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class ColorBlind {

    private static boolean DEBUG = false;

    private static final int X_SIZE = 20;
    private static final int Y_SIZE = 16;

    private static Character[][] grid = new Character[X_SIZE][Y_SIZE];

    private record XY(int x, int y) {}

    private record Line(XY point1, XY point2, int length, boolean horizontal) {}

    private record Box(Set<XY> points, boolean isFull, int size) {}

    private record MoveAndScore(Move move, int score) {}

    private static void debugPrint(Object string) {
        if (DEBUG) {
            System.err.print(string);
        }
    }

    private static void debugPrintln(Object string) {
        if (DEBUG) {
            System.err.println(string);
        }
    }

    private static void printGrid(Character[][] grid) {
        for (int j = 0; j < Y_SIZE; j++) {
            for (int k = 0; k < X_SIZE; k++) {
                debugPrint(grid[k][j]);
            }
            debugPrintln("");
        }
        debugPrintln("");
    }

    private static void initGrid(Character[][] grid) {
        for (int j = 0; j < Y_SIZE; j++) {
            for (int k = 0; k < X_SIZE; k++) {
                grid[k][j] = '.';
            }
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

    private static Move getRandomValidMove(Character[][] grid, Block block) {
        List<Move> allValidMoves = getAllValidMoves(grid, block);
        return allValidMoves.get(new Random().nextInt(allValidMoves.size()));
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

    public static Set<Box> getBoxes(Set<Line> lines, Set<XY> points) {

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
                        boxes.add(new Box(Set.of(boxPoint1, boxPoint2, boxPoint3, boxPoint4), true, maxX - minX));
                    } else {
                        boxes.add(new Box(pointsInLines, false, maxX - minX));
                    }

                }

            }
        }

        return boxes;
    }

    public static Character[][] cloneGrid(Character[][] grid) {
        Character[][] clone = new Character[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            clone[i] = grid[i].clone();
        }

        return clone;
    }

    private static int getScoreAfterMove(Character[][] grid, Move move, char myColor) {
        Character[][] gridClone = cloneGrid(grid);
        doPlacement(gridClone, move);

        List<Move> allValidEnemyMoves = getAllValidMoves(gridClone, new Block("777777", true));

        int minScoreAfterEnemyMove = Integer.MAX_VALUE;

        for (Move enemyMove : allValidEnemyMoves) {
            Character[][] enemyGridClone = cloneGrid(gridClone);
            doPlacement(enemyGridClone, enemyMove);

            int scoreAfterEnemyMove = getScore(enemyGridClone, myColor);

            if (scoreAfterEnemyMove < minScoreAfterEnemyMove) {
                minScoreAfterEnemyMove = scoreAfterEnemyMove;
            }
        }

        return minScoreAfterEnemyMove;
    }

    private static int getScore(Character[][] grid, char myColor) {
        Set<XY> xyMyColorBefore = getAllPointsWithColor(grid, myColor);
        Set<Line> lines = getLines(xyMyColorBefore);
        Set<Box> boxes = getBoxes(lines, xyMyColorBefore);

        int lineScore = lines.stream().filter(v -> v.length < 16).map(v -> v.length).reduce(Integer::sum).orElse(0);
        int partialBoxScore = boxes.stream().filter(v -> !v.isFull).map(v -> v.size).reduce(Integer::sum).orElse(0);
        int fullBoxScore = boxes.stream().filter(v -> v.isFull).map(v -> v.size).reduce(Integer::sum).orElse(0);

        return lineScore + 10 * partialBoxScore + 100 * fullBoxScore;
    }

    private static int getEnemyScore(Set<Box> boxes) {
        int partialBoxScore = boxes.stream().filter(v -> !v.isFull).map(v -> v.size).reduce(Integer::sum).orElse(0);
        int fullBoxScore = boxes.stream().filter(v -> v.isFull).map(v -> v.size).reduce(Integer::sum).orElse(0);

        return 4 * partialBoxScore + 50 * fullBoxScore;
    }

    private static int getEnemyScoreAfterMove(Character[][] grid, Move move, char myColor) {
        Character[][] gridClone = cloneGrid(grid);
        doPlacement(gridClone, move);

        return getEnemyScore(gridClone, myColor);
    }

    private static int getEnemyScore(Character[][] grid, char myColor) {
        Set<Character> potentialEnemyColors = new HashSet<>(Set.of('1', '2', '3', '4', '5', '6'));
        potentialEnemyColors.remove(myColor);

        int enemyScoreBefore = 0;

        for (char potentialEnemy : potentialEnemyColors) {
            Set<XY> xyEnemyColorBefore = getAllPointsWithColor(grid, potentialEnemy);
            Set<Line> enemyLinesBefore = getLines(xyEnemyColorBefore);
            Set<Box> enemyBoxesBefore = getBoxes(enemyLinesBefore, xyEnemyColorBefore);
            enemyScoreBefore += getEnemyScore(enemyBoxesBefore);
        }

        return enemyScoreBefore;
    }

    public static MoveAndScore getBestMove(Character[][] grid, Block block, char myColor) {

        List<MoveAndScore> movesAndScores = new ArrayList<>();

        int scoreBefore = getScore(grid, myColor) - getEnemyScore(grid, myColor);

        List<Move> validMoves = getAllValidMoves(grid, block);

        for (Move move : validMoves) {

            int scoreAfter = getScoreAfterMove(grid, move, myColor) - getEnemyScoreAfterMove(grid, move, myColor);

            movesAndScores.add(new MoveAndScore(move, scoreAfter - scoreBefore));
        }

        if (movesAndScores.isEmpty()) {
            return new MoveAndScore(getRandomValidMove(grid, block), 0);
        } else {
            List<MoveAndScore> sorted = movesAndScores.stream().sorted((x, y) -> Integer.compare(y.score, x.score)).toList();
            MoveAndScore moveAndScore = sorted.stream().findFirst().get();

            debugPrintln("Moves and scores: " + sorted);

            debugPrintln("Picked move: " + moveAndScore);

            return moveAndScore;
        }
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

    private static void initTestGrid(Character[][] grid) {

        String gridStr = """
                ....................
                ....................
                ..............612345
                .....156432...543216
                .....234651......14.
                ..614352.........62.
                ..253416.52......26.
                .......544613....41.
                .......511344126553.
                .......6431.356214..
                ...14..3264.........
                ...36..2325.........
                ...255341...........
                ...523526...........
                ...63315624.........
                ...41426513.........
                """;

        for (int i = 0; i < Y_SIZE; i++) {
            for (int j = 0; j < X_SIZE; j++) {
                grid[j][i] = gridStr.charAt(i*(X_SIZE+1) + j);
            }
        }

    }

    public static void main(String[] args) throws Exception {

        /*initGrid(grid);

        for (int j = 1; j < Y_SIZE; j++) {
            for (int k = 0; k < X_SIZE; k++) {
                grid[k][j] = '#';

                if (j == 1 && k > 17) {
                    grid[k][j] = '.';
                }
            }
        }

        printGrid(grid);*/

        //doPlacement(grid, new Move("Hh654321h"));

        /*Move move = randomMove(grid,  new Block("163524"));

        System.out.println(move.getAsMove());
        System.out.println(move.getAsMove());

        doPlacement(grid, move);

        printGrid(grid);*/

        /*initGrid(grid);
        initTestGrid(grid);

        printGrid(grid);*/

        /*Set<XY> points = getAllPointsWithColor(grid, '1');
        System.out.println(points);
        Set<Line> lines = getLines(points);
        System.out.println(lines);
        Set<Box> boxes = getBoxes(lines, points);
        System.out.println(boxes);

        Block block = new Block("451326", true);
        System.out.println(block);*/

        /*MoveAndScore bestMove = getBestMove(grid, new Block("623451", true), '1');

        System.err.println(bestMove);

        doPlacement(grid, bestMove.move);

        printGrid(grid);*/

        //System.out.println("Best move: " + bestMove.getAsMove());

        initGrid(grid);

        printGrid(grid);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        char myColor = reader.readLine().charAt(0);
        String firstBlockPlacement = reader.readLine();

        debugPrintln("First block placement: " + firstBlockPlacement);

        doPlacement(grid, new Move(firstBlockPlacement));

        while (true) {

            String opponentMove = reader.readLine();

            debugPrintln("Opponent Move: " + opponentMove);

            if (opponentMove.equals("Quit")) {
                break;
            }

            if (!opponentMove.equals("Start")) {
                doPlacement(grid, new Move(opponentMove));
            }

            printGrid(grid);

            String myBlockStr = reader.readLine();

            debugPrintln("My block: " + myBlockStr);

            if (myBlockStr.equals("Quit")) {
                break;
            }

            Block myBlock = new Block(myBlockStr, true);

            Move move = getBestMove(grid, myBlock, myColor).move;

            List<Move> validMoves = getAllValidMoves(grid, myBlock);

            debugPrintln("Valid moves count: " + validMoves.size());

            doPlacement(grid, new Move(move.getAsPlacement()));

            String asMove = move.getAsMove();
            debugPrintln("Making move: " + asMove);
            System.out.println(asMove);

        }
    }
}
