import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;


public class ColorBlind {

    private static final int X_SIZE = 20;
    private static final int Y_SIZE = 16;

    private static Character[][] grid = new Character[X_SIZE][Y_SIZE];

    private record XY(int x, int y) {}

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

            Block block = new Block(blockStr);

            int xIndex = horizontalIndex - 97;
            int yIndex = verticalIndex - 65;

            if (!isHorizontal) {
                block.makeVertical();
            }

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
        private char[][] block;
        private int width;
        private int height;
        private boolean horizontal = true;
        private final String blockStr;

        public Block(String blockStr) {
            this.blockStr = blockStr;
            char[] blockChars = blockStr.toCharArray();

            this.width = 6;
            this.height = 2;

            block = new char[width][height];

            for (int i = 0; i < blockChars.length; i++) {
                block[i][0] = blockChars[i];
                block[i][1] = blockChars[5 - i];
            }

        }

        public void makeVertical() {

            horizontal = false;

            this.width = 2;
            this.height = 6;

            char[][] newBlock = new char[width][height];

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    newBlock[i][j] = block[j][1-i];
                }
            }

            block = newBlock;
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

    private static XY getValidPosition(Character[][] grid, Block block) {
        for (int j = 0; j < Y_SIZE; j++) {
            for (int k = 0; k < X_SIZE; k++) {

                boolean hasNeighbour = false;

                for (XY outerPosition : block.getOuterPositions()) {
                    int x = k + outerPosition.x;
                    int y = j + outerPosition.y;

                    if (x>=0 && x<X_SIZE && y>=0 && y<Y_SIZE) {
                        if (grid[x][y] != '.') {
                            hasNeighbour = true;
                        }
                    }
                }

                boolean allInBounds = true;
                int overlapCount = 0;

                for (XY innerPosition : block.getInnerPositions()) {
                    int x = k + innerPosition.x;
                    int y = j + innerPosition.y;

                    if (x>=0 && x<X_SIZE && y>=0 && y<Y_SIZE) {
                        if (grid[x][y] != '.') {
                            overlapCount++;
                        }
                    } else {
                        allInBounds = false;
                    }
                }

                if (allInBounds && hasNeighbour && overlapCount <= 4) {
                    return new XY(k, j);
                }

            }
        }

        return null;
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

        XY validXY = getValidPosition(grid, block);

        if (validXY == null) {
            block.makeVertical();
            validXY = getValidPosition(grid, block);
        }

        return new Move(block, validXY);
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

        String myColor = reader.readLine();
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

            Block myBlock = new Block(myBlockStr);

            Move move = randomMove(grid, myBlock);

            doPlacement(grid, new Move(move.getAsPlacement()));

            String asMove = move.getAsMove();
            System.err.println("Making move: " + asMove);
            System.out.println(asMove);

        }
    }
}
