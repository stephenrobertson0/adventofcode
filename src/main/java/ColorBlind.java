import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ColorBlind {

    private static final int X_SIZE = 20;
    private static final int Y_SIZE = 16;

    private static Character[][] grid = new Character[X_SIZE][Y_SIZE];

    private record XY(int x, int y) {}

    private record Line(XY point1, XY point2, int length, boolean horizontal) {}

    private record PartialBox(Line line1, Line line2) {}

    private static void printGrid(Character[][] grid) {
        for (int j = 0; j < Y_SIZE; j++) {
            for (int k = 0; k < X_SIZE; k++) {
                System.err.print(grid[k][j]);
            }
            System.err.println();
        }
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
                    block[0][i] = blockChars[i];
                    block[1][i] = blockChars[5 - i];
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

    private static Move getFirstValidMove(Character[][] grid, Block block) {
        return getAllValidMoves(grid, block).iterator().next();
    }

    private static Move randomMove(Character[][] grid, Block block) {

        //Set<XY> outerPositions = block.getOuterPositions();

        /*for (XY xy : outerPositions) {
            grid[5+xy.x][5+xy.y] = '#';
        }*/

        //Set<XY> innerPositions = block.getInnerPositions();

        /*for (XY xy : innerPositions) {
            grid[5+xy.x][5+xy.y] = 'O';
        }*/

        //printGrid(grid);

        return getFirstValidMove(grid, block);
    }

    public static List<XY> getAllPointsWithColor(Character[][] grid, char color) {
        List<XY> xyWithColor = new ArrayList<>();

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

    public static List<Line> getLines(List<XY> points) {
        List<Line> lines = new ArrayList<>();

        for (int j = 0; j < points.size(); j++) {
            for (int k = j+1; k < points.size(); k++) {

                XY point1 = points.get(j);
                XY point2 = points.get(k);

                if (point1.x == point2.x) {
                    lines.add(new Line(point1, point2, Math.abs(point1.y - point2.y), false));
                }

                if (point1.y == point2.y) {
                    lines.add(new Line(point1, point2, Math.abs(point1.x - point2.x), true));
                }

            }
        }

        return lines;
    }

    public static List<PartialBox> getPartialBoxes(List<Line> lines) {
        List<PartialBox> partialBoxes = new ArrayList<>();

        for (int j = 0; j < lines.size(); j++) {
            for (int k = j+1; k < lines.size(); k++) {

                Line line1 = lines.get(j);
                Line line2 = lines.get(k);

                if (line1.length == line2.length
                        // Lines must be in different directions
                        && line1.horizontal != line2.horizontal
                        && (line1.point1.equals(line2.point1) || line1.point2.equals(line2.point2) || line1.point1.equals(line2.point2) || line1.point2.equals(line2.point1))
                ) {
                    partialBoxes.add(new PartialBox(line1, line2));
                }

            }
        }

        return partialBoxes;
    }

    public static Character[][] cloneGrid(Character[][] grid) {
        Character[][] clone = new Character[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            clone[i] = grid[i].clone();
        }

        return clone;
    }

    public static Move getBestMove(Character[][] grid, Block block, char myColor) {

        List<XY> xyMyColorBefore = getAllPointsWithColor(grid, myColor);
        List<Line> linesBefore = getLines(xyMyColorBefore);
        List<PartialBox> partialBoxesBefore = getPartialBoxes(linesBefore);
        int lineCountBefore = linesBefore.size();
        int partialBoxCountBefore = partialBoxesBefore.size();

        List<Move> validMoves = getAllValidMoves(grid, block);
        List<Move> movesCreatingLines = new ArrayList<>();
        List<Move> movesCreatingPartialBoxes = new ArrayList<>();

        for (Move move : validMoves) {

            Character[][] gridClone = cloneGrid(grid);
            doPlacement(gridClone, move);

            List<XY> xyMyColor = getAllPointsWithColor(gridClone, myColor);
            List<Line> lines = getLines(xyMyColor);
            List<PartialBox> partialBoxes = getPartialBoxes(lines);

            if (lines.size() > lineCountBefore) {
                movesCreatingLines.add(move);
            }

            if (partialBoxes.size() > partialBoxCountBefore) {
                movesCreatingPartialBoxes.add(move);
            }

        }

        if (movesCreatingPartialBoxes.size() > 0) {
            return movesCreatingPartialBoxes.get(0);
        }

        if (movesCreatingLines.size() > 0) {
            return movesCreatingLines.get(0);
        }

        return getFirstValidMove(grid, block);

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



        initGrid(grid);

        printGrid(grid);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        char myColor = reader.readLine().charAt(0);
        String firstBlockPlacement = reader.readLine();

        System.err.println("First block placement: " + firstBlockPlacement);

        doPlacement(grid, new Move(firstBlockPlacement));

        while (true) {

            String opponentMove = reader.readLine();

            System.err.println("Opponent Move: " + opponentMove);

            if (opponentMove.equals("Quit")) {
                break;
            }

            if (!opponentMove.equals("Start")) {
                doPlacement(grid, new Move(opponentMove));
            }

            printGrid(grid);

            String myBlockStr = reader.readLine();

            System.err.println("My block: " + myBlockStr);

            if (myBlockStr.equals("Quit")) {
                break;
            }

            Block myBlock = new Block(myBlockStr, true);

            Move move = getBestMove(grid, myBlock, myColor);

            List<Move> validMoves = getAllValidMoves(grid, myBlock);

            System.err.println("Valid moves count: " + validMoves.size());

            doPlacement(grid, new Move(move.getAsPlacement()));

            String asMove = move.getAsMove();
            System.err.println("Making move: " + asMove);
            System.out.println(asMove);

        }
    }
}
