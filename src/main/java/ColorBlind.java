import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;


public class ColorBlind {

    private static boolean DEBUG = false;

    private static final int X_SIZE = 20;
    private static final int Y_SIZE = 16;

    private record XY(int x, int y) {}

    private record Line(XY point1, XY point2, int length, boolean horizontal) {}

    private record Box(Set<XY> points, boolean isFull, boolean isProtected, int protectionCount, int size) {}

    private record EnemyAndBoxCount(char enemy, long boxCount) {}

    private Character[][] grid = new Character[X_SIZE][Y_SIZE];
    private Character enemyFinalized = null;
    private char myColor;

    public ColorBlind(char myColor) {
        this.myColor = myColor;
        initGrid(grid);
    }

    public void doPlacement(String placement) {
        doPlacement(grid, new Move(placement));
    }

    private String getNextMove(String myBlockStr) {
        Set<Character> identifiedEnemyColors;

        if (enemyFinalized == null) {
            Set<EnemyAndBoxCount> identifiedEnemyColorsAndCounts = getIdentifiedEnemyColors(grid, myColor);
            identifiedEnemyColors = identifiedEnemyColorsAndCounts.stream().map(v->v.enemy).collect(Collectors.toSet());

            if (identifiedEnemyColorsAndCounts.stream().findFirst().get().boxCount >= 1) {
                enemyFinalized = identifiedEnemyColorsAndCounts.stream().findFirst().get().enemy;
            }

        } else {
            identifiedEnemyColors = Set.of(enemyFinalized);
        }

        debugPrintln("Identified enemy colors: " + identifiedEnemyColors);

        Block myBlock = new Block(myBlockStr, true);

        Move move = getBestMove(grid, myBlock, myColor, identifiedEnemyColors).move;

        doPlacement(move.getAsPlacement());

        return move.getAsMove();
    }

    private static class Score {
        private Set<Line> lines;
        private Set<Box> boxes;

        public Score(Set<Line> lines, Set<Box> boxes) {
            this.lines = lines;
            this.boxes = boxes;
        }

        public double getNumericScore() {
            int lineScore = lines.stream().filter(v -> v.length < 16).map(v -> v.length).reduce(Integer::sum).orElse(0);
            double partialBoxScore = boxes.stream().filter(v -> !v.isFull).map(v -> v.size * Math.pow((double)v.protectionCount / 3, 1.5)).reduce(Double::sum).orElse(0d);
            int fullBoxScore = boxes.stream().filter(v -> v.isFull && v.isProtected).map(v -> v.size).reduce(Integer::sum).orElse(0);

            return lineScore + 35 * partialBoxScore + 200 * fullBoxScore;
        }

        public Set<Line> getLines() {
            return lines;
        }

        public Set<Box> getBoxes() {
            return boxes;
        }
    }

    private static class EnemyScore {
        private Map<Character, Set<Box>> boxesByEnemy;
        private Set<Box> boxes;

        public EnemyScore(Map<Character, Set<Box>> boxesByEnemy) {
            this.boxesByEnemy = boxesByEnemy;
            this.boxes = boxesByEnemy.entrySet().stream().flatMap(v->v.getValue().stream()).collect(Collectors.toSet());
        }

        public double getNumericScore() {
            double partialBoxScore = boxes.stream().filter(v -> !v.isFull).map(v -> v.size * Math.pow((double)v.protectionCount / 3, 1.5)).reduce(Double::sum).orElse(0d);
            double fullBoxScore = boxes.stream().filter(v -> v.isFull).map(v -> v.size * Math.pow((double)v.protectionCount / 4, 1.5)).reduce(Double::sum).orElse(0d);

            return 35 * partialBoxScore + 200 * fullBoxScore;
        }

        public Set<Box> getBoxes() {
            return boxes;
        }
    }

    private static class MoveAndScore {
        private Move move;
        private Score scoreBefore;
        private Score scoreAfter;
        private EnemyScore enemyScoreBefore;
        private EnemyScore enemyScoreAfter;

        public MoveAndScore(Move move, Score scoreBefore, Score scoreAfter, EnemyScore enemyScoreBefore, EnemyScore enemyScoreAfter) {
            this.move = move;
            this.scoreBefore = scoreBefore;
            this.scoreAfter = scoreAfter;
            this.enemyScoreBefore = enemyScoreBefore;
            this.enemyScoreAfter = enemyScoreAfter;
        }

        public double getNumericScore() {

            if (scoreAfter == null) {
                return 0;
            }

            return scoreAfter.getNumericScore() - enemyScoreAfter.getNumericScore();
        }

        @Override
        public String toString() {

            if (scoreAfter == null) {
                return "Random Move";
            }

            Set<Line> linesAdded = new HashSet<>(scoreAfter.getLines());
            linesAdded.removeAll(scoreBefore.getLines());

            Set<Box> boxesAdded = new HashSet<>(scoreAfter.getBoxes());
            boxesAdded.removeAll(scoreBefore.getBoxes());

            Set<Box> boxesRemoved = new HashSet<>(scoreBefore.getBoxes());
            boxesRemoved.removeAll(scoreAfter.getBoxes());

            Set<Box> enemyBoxesRemoved = new HashSet<>(enemyScoreBefore.getBoxes());
            enemyBoxesRemoved.removeAll(enemyScoreAfter.getBoxes());

            Set<Box> enemyBoxesAdded = new HashSet<>(enemyScoreAfter.getBoxes());
            enemyBoxesAdded.removeAll(enemyScoreBefore.getBoxes());

            return "Lines added: " + linesAdded +
                    "\n Partial boxes added: " + boxesAdded.stream().filter(v->!v.isFull).toList() +
                    "\n Full boxes added: " + boxesAdded.stream().filter(v->v.isFull).toList() +
                    "\n Partial boxes removed: " + boxesRemoved.stream().filter(v->!v.isFull).toList() +
                    "\n Full boxes removed: " + boxesRemoved.stream().filter(v->v.isFull).toList() +
                    "\n Enemy partial boxes removed: " + enemyBoxesRemoved.stream().filter(v->!v.isFull).toList() +
                    "\n Enemy full boxes removed: " + enemyBoxesRemoved.stream().filter(v->v.isFull).toList() +
                    "\n Enemy partial boxes added: " + enemyBoxesAdded.stream().filter(v->!v.isFull).toList() +
                    "\n Enemy full boxes added: " + enemyBoxesAdded.stream().filter(v->v.isFull).toList() +
                    "\n Score: " + getNumericScore();

        }
    }

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

                        int protectionCount = (int)Set.of(boxPoint1, boxPoint2, boxPoint3, boxPoint4).stream().filter(v->isXYProtected(grid, v)).count();

                        boxes.add(new Box(
                                Set.of(boxPoint1, boxPoint2, boxPoint3, boxPoint4),
                                true,
                                protectionCount == 4,
                                protectionCount,
                                maxX - minX));
                    } else {

                        Set<XY> missingPointSet = new HashSet<>(Set.of(boxPoint1, boxPoint2, boxPoint3, boxPoint4));
                        missingPointSet.removeAll(points);
                        XY missingPoint = missingPointSet.stream().findFirst().get();

                        // If the missing point from the partial box can't be changed, then don't count the partial box
                        if (!isXYProtected(grid, missingPoint)) {

                            Set<XY> pointsInPartialBox = new HashSet<>(Set.of(boxPoint1, boxPoint2, boxPoint3, boxPoint4));
                            pointsInPartialBox.removeAll(missingPointSet);
                            int protectionCount = (int)pointsInPartialBox.stream().filter(v->isXYProtected(grid, v)).count();

                            boxes.add(new Box(pointsInLines, false, protectionCount == 3, protectionCount, maxX - minX));
                        }
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

    private static Score getScoreAfterMove(Character[][] grid, Move move, char myColor) {
        Character[][] gridClone = cloneGrid(grid);
        doPlacement(gridClone, move);

        return getScore(gridClone, myColor);
    }

    private static Score getScore(Character[][] grid, char myColor) {
        Set<XY> xyMyColor = getAllPointsWithColor(grid, myColor);
        Set<Line> lines = getLines(xyMyColor);
        Set<Box> boxes = getBoxes(grid, lines, xyMyColor);

        return new Score(lines, boxes);
    }

    private static EnemyScore getEnemyScoreAfterMove(Character[][] grid, Move move, Set<Character> identifiedEnemyColors) {
        Character[][] gridClone = cloneGrid(grid);
        doPlacement(gridClone, move);

        return getEnemyScore(gridClone, identifiedEnemyColors);
    }

    private static EnemyScore getEnemyScore(Character[][] grid, Set<Character> identifiedEnemyColors) {
        Map<Character, Set<Box>> boxesByEnemy = new HashMap<>();

        for (char potentialEnemy : identifiedEnemyColors) {
            Set<XY> xyEnemyColor = getAllPointsWithColor(grid, potentialEnemy);
            Set<Line> enemyLines = getLines(xyEnemyColor);
            Set<Box> enemyBoxes = getBoxes(grid, enemyLines, xyEnemyColor);
            boxesByEnemy.put(potentialEnemy, enemyBoxes);
        }

        return new EnemyScore(boxesByEnemy);
    }

    public static MoveAndScore getBestMove(Character[][] grid, Block block, char myColor, Set<Character> identifiedEnemyColors) {

        List<MoveAndScore> movesAndScores = new ArrayList<>();

        Score scoreBefore = getScore(grid, myColor);
        EnemyScore enemyScoreBefore = getEnemyScore(grid, identifiedEnemyColors);

        List<Move> validMoves = getAllValidMoves(grid, block);

        for (Move move : validMoves) {

            Score scoreAfter = getScoreAfterMove(grid, move, myColor);
            EnemyScore enemyScoreAfter = getEnemyScoreAfterMove(grid, move, identifiedEnemyColors);

            movesAndScores.add(new MoveAndScore(move, scoreBefore, scoreAfter, enemyScoreBefore, enemyScoreAfter));
        }

        if (movesAndScores.isEmpty()) {
            return new MoveAndScore(getRandomValidMove(grid, block), null, null, null, null);
        } else {
            List<MoveAndScore> sorted = movesAndScores.stream().sorted((x, y) -> Double.compare(y.getNumericScore(), x.getNumericScore())).toList();
            MoveAndScore moveAndScore = sorted.stream().findFirst().get();

            //debugPrintln("Moves and scores: " + sorted);

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

    // South and East
    private static List<XY> directions = List.of(new XY(0, 1), new XY(1, 0));

    private record StartAndDirection(List<XY> starts, XY direction) {}

    private static List<XY> horizontalStarts = List.of(new XY(0, 1), new XY(0, -1));
    private static List<XY> verticalStarts = List.of(new XY(1, 0), new XY(-1, 0));

    private static boolean isXYAvailable(Character[][] grid, XY xy) {
        return grid[xy.x][xy.y] == '.';
    }

    private static boolean isXYOnGrid(XY xy) {
        return xy.x>=0 && xy.x<X_SIZE && xy.y>=0 && xy.y<Y_SIZE;
    }

    private static boolean isXYProtected(Character[][] grid, XY xy) {

        List<StartAndDirection> startAndDirections = new ArrayList<>();

        for (XY direction : directions) {

            if (direction.y == 0) {
                for (XY horizontalStart : horizontalStarts) {
                    XY start1 = xy;
                    // Looking horizontally
                    XY start2 = new XY(xy.x + horizontalStart.x, xy.y + horizontalStart.y);

                    startAndDirections.add(new StartAndDirection(List.of(start1, start2), direction));
                }
            } else {
                for (XY verticalStart : verticalStarts) {
                    XY start1 = xy;
                    // Looking vertically
                    XY start2 = new XY(xy.x + verticalStart.x, xy.y + verticalStart.y);

                    startAndDirections.add(new StartAndDirection(List.of(start1, start2), direction));
                }
            }
        }

        for (StartAndDirection startAndDirection : startAndDirections) {

            for (int j = -5; j <= 0; j++) {

                int count = 0;
                boolean onGrid = true;

                for (XY start : startAndDirection.starts) {

                    for (int k = 0; k < 6; k++) {
                        XY check = new XY(
                                start.x + startAndDirection.direction.x * j + startAndDirection.direction.x * k,
                                start.y + startAndDirection.direction.y * j + startAndDirection.direction.y * k);

                        if (!isXYOnGrid(check)) {
                            onGrid = false;
                            break;
                        }

                        if (!isXYAvailable(grid, check)) {
                            count++;
                        }
                    }
                }

                if (onGrid && count <= 4) {
                    return false;
                }

            }

        }

        return true;
    }

    private static Set<EnemyAndBoxCount> getIdentifiedEnemyColors(Character[][] grid, char myColor) {
        Set<EnemyAndBoxCount> enemyColorBoxCounts = new HashSet<>();
        Set<Character> potentialEnemyColors = new HashSet<>(Set.of('1', '2', '3', '4', '5', '6'));
        potentialEnemyColors.remove(myColor);

        for (char potentialEnemy : potentialEnemyColors) {
            Set<XY> xyEnemyColor = getAllPointsWithColor(grid, potentialEnemy);
            Set<Line> enemyLines = getLines(xyEnemyColor);
            Set<Box> enemyBoxes = getBoxes(grid, enemyLines, xyEnemyColor);

            long boxCount = enemyBoxes.stream().filter(v -> v.isFull).count();
            enemyColorBoxCounts.add(new EnemyAndBoxCount(potentialEnemy, boxCount));
        }

        Set<EnemyAndBoxCount> identifiedEnemyColors;

        long maxCount = enemyColorBoxCounts.stream().map(v->v.boxCount).max(Comparator.comparingLong(v->v)).get();
        identifiedEnemyColors = enemyColorBoxCounts.stream().filter(v->v.boxCount == maxCount).collect(Collectors.toSet());

        return identifiedEnemyColors;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        char myColor = reader.readLine().charAt(0);

        ColorBlind colorBlind = new ColorBlind(myColor);

        String firstBlockPlacement = reader.readLine();

        debugPrintln("First block placement: " + firstBlockPlacement);

        colorBlind.doPlacement(firstBlockPlacement);

        while (true) {

            String opponentMove = reader.readLine();

            debugPrintln("Opponent Move: " + opponentMove);

            if (opponentMove.equals("Quit")) {
                break;
            }

            if (!opponentMove.equals("Start")) {
                colorBlind.doPlacement(opponentMove);
            }

            String myBlockStr = reader.readLine();

            debugPrintln("My block: " + myBlockStr);

            if (myBlockStr.equals("Quit")) {
                break;
            }

            String move = colorBlind.getNextMove(myBlockStr);
            debugPrintln("Making move: " + move);
            System.out.println(move);

        }
    }
}
