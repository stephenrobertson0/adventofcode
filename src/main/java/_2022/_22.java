package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _22 {
    
    private static void printBoard(int[][] board, int currentX, int currentY, int currentDir) {
        
        String dir = " ";
        
        if (currentDir == 0) {
            dir = ">";
        } else if (currentDir == 1) {
            dir = "v";
        } else if (currentDir == 2) {
            dir = "<";
        } else if (currentDir == 3) {
            dir = "^";
        }
        
        for (int k = 0; k < 4; k++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(currentX == j && currentY == k ? dir : board[j][k]);
            }
            System.out.println();
        }
        
        System.out.println();
    }
    
    private static class MoveResult {
        
        int newX;
        int newY;
        int newDir;
        CubeFace cubeFace;
        
        public MoveResult(int newX, int newY, int newDir, CubeFace cubeFace) {
            this.newX = newX;
            this.newY = newY;
            this.newDir = newDir;
            this.cubeFace = cubeFace;
        }
    }
    
    
    private static class CubeFace {
        
        final static int SIZE = 50;
        
        int[][] board = new int[SIZE][SIZE];
        
        String name;
        
        private CubeFace neighbourRight;
        private int entrySideRight;
        private CubeFace neighbourBottom;
        private int entrySideBottom;
        private CubeFace neighbourLeft;
        private int entrySideLeft;
        private CubeFace neighbourTop;
        private int entrySideTop;
        
        public CubeFace(String name, int[][] fullBoard, int startX, int startY) {
            this.name = name;
            
            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE; k++) {
                    board[j][k] = fullBoard[j + startX][k + startY];
                }
            }
        }
        
        public MoveResult move(int x, int y, int dir) {
            int nextX = x;
            int nextY = y;
            int nextDir = dir;
            CubeFace cubeFace = this;
            
            if (dir == 0) {
                nextX++;
                
                if (nextX > SIZE - 1) {
                    
                    cubeFace = neighbourRight;
                    
                    boolean inverse = false;
                    int entrySide = entrySideRight;
                    
                    if (entrySide > 3) {
                        entrySide %= 4;
                        inverse = true;
                    }
                    
                    if (entrySide == 0) {
                        nextX = SIZE - 1;
                        nextY = inverse ? (SIZE - 1 - y) : y;
                        nextDir = 2;
                    } else if (entrySide == 1) {
                        nextX = inverse ? (SIZE - 1 - y) : y;
                        nextY = SIZE - 1;
                        nextDir = 3;
                    } else if (entrySide == 2) {
                        nextX = 0;
                        nextY = inverse ? (SIZE - 1 - y) : y;
                        nextDir = 0;
                    } else if (entrySide == 3) {
                        nextX = inverse ? (SIZE - 1 - y) : y;
                        nextY = 0;
                        nextDir = 1;
                    }
                }
                
            }
            
            if (dir == 2) {
                nextX--;
                
                if (nextX < 0) {
                    cubeFace = neighbourLeft;
    
                    boolean inverse = false;
                    int entrySide = entrySideLeft;
    
                    if (entrySide > 3) {
                        entrySide %= 4;
                        inverse = true;
                    }
    
                    if (entrySide == 0) {
                        nextX = SIZE - 1;
                        nextY = inverse ? (SIZE - 1 - y) : y;
                        nextDir = 2;
                    } else if (entrySide == 1) {
                        nextX = inverse ? (SIZE - 1 - y) : y;
                        nextY = SIZE - 1;
                        nextDir = 3;
                    } else if (entrySide == 2) {
                        nextX = 0;
                        nextY = inverse ? (SIZE - 1 - y) : y;
                        nextDir = 0;
                    } else if (entrySide == 3) {
                        nextX = inverse ? (SIZE - 1 - y) : y;
                        nextY = 0;
                        nextDir = 1;
                    }
                }
            }
            
            if (dir == 1) {
                nextY++;
                
                if (nextY > SIZE - 1) {
                    cubeFace = neighbourBottom;
    
                    boolean inverse = false;
                    int entrySide = entrySideBottom;
    
                    if (entrySide > 3) {
                        entrySide %= 4;
                        inverse = true;
                    }
    
                    if (entrySide == 0) {
                        nextX = SIZE - 1;
                        nextY = inverse ? (SIZE - 1 - x) : x;
                        nextDir = 2;
                    } else if (entrySide == 1) {
                        nextX = inverse ? (SIZE - 1 - x) : x;
                        nextY = SIZE - 1;
                        nextDir = 3;
                    } else if (entrySide == 2) {
                        nextX = 0;
                        nextY = inverse ? (SIZE - 1 - x) : x;
                        nextDir = 0;
                    } else if (entrySide == 3) {
                        nextX = inverse ? (SIZE - 1 - x) : x;
                        nextY = 0;
                        nextDir = 1;
                    }
                }
            }
            
            if (dir == 3) {
                nextY--;
                
                if (nextY < 0) {
                    cubeFace = neighbourTop;
    
                    boolean inverse = false;
                    int entrySide = entrySideTop;
    
                    if (entrySide > 3) {
                        entrySide %= 4;
                        inverse = true;
                    }
    
                    if (entrySide == 0) {
                        nextX = SIZE - 1;
                        nextY = inverse ? (SIZE - 1 - x) : x;
                        nextDir = 2;
                    } else if (entrySide == 1) {
                        nextX = inverse ? (SIZE - 1 - x) : x;
                        nextY = SIZE - 1;
                        nextDir = 3;
                    } else if (entrySide == 2) {
                        nextX = 0;
                        nextY = inverse ? (SIZE - 1 - x) : x;
                        nextDir = 0;
                    } else if (entrySide == 3) {
                        nextX = inverse ? (SIZE - 1 - x) : x;
                        nextY = 0;
                        nextDir = 1;
                    }
                }
            }
    
    
            if (cubeFace.board[nextX][nextY] == 2) {
                return new MoveResult(x, y, dir, this);
            } else {
                return new MoveResult(nextX, nextY, nextDir, cubeFace);
            }
            
        }
    }
    
    public static void a() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input22.txt"));
        
        int xSize = 150;
        int ySize = 200;
        
        // 0 = void, 1 = open, 2 = wall
        // direction 0 >, 1 v, 2 <, 3^
        int[][] board = new int[xSize][ySize];
        
        int y = 0;
        
        while (true) {
            
            final String line = fileReader.readLine();
            
            if (line.isEmpty()) {
                break;
            }
            
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == '.') {
                    board[x][y] = 1;
                }
                if (c == '#') {
                    board[x][y] = 2;
                }
                x++;
            }
            
            y++;
        }
        
        //printBoard(board);
        
        List<Integer> xMins = new ArrayList<>();
        List<Integer> xMaxs = new ArrayList<>();
        List<Integer> yMins = new ArrayList<>();
        List<Integer> yMaxs = new ArrayList<>();
        
        for (int j = 0; j < xSize; j++) {
            boolean minFound = false;
            for (int k = 0; k < ySize; k++) {
                if (board[j][k] != 0 && !minFound) {
                    yMins.add(k);
                    minFound = true;
                }
                
                if ((board[j][k] == 0 || k == ySize - 1) && minFound) {
                    if (k == ySize - 1) {
                        yMaxs.add(k);
                    } else {
                        yMaxs.add(k - 1);
                    }
                    break;
                }
            }
        }
        
        for (int k = 0; k < ySize; k++) {
            boolean minFound = false;
            for (int j = 0; j < xSize; j++) {
                if (board[j][k] != 0 && !minFound) {
                    xMins.add(j);
                    minFound = true;
                }
                
                if ((board[j][k] == 0 || j == xSize - 1) && minFound) {
                    if (j == xSize - 1) {
                        xMaxs.add(j);
                    } else {
                        xMaxs.add(j - 1);
                    }
                    break;
                }
            }
        }
        
        /*System.out.println(xMins);
        System.out.println(xMaxs);
        System.out.println(yMins);
        System.out.println(yMaxs);
        
        System.out.println();*/
        
        final String moves = fileReader.readLine();
        
        List<Integer> distances = new ArrayList<>();
        // L = 0, R = 1
        List<Integer> turns = new ArrayList<>();
        
        String nextNum = "";
        
        for (char c : moves.toCharArray()) {
            if (c == 'L') {
                turns.add(0);
                distances.add(Integer.parseInt(nextNum));
                nextNum = "";
            } else if (c == 'R') {
                turns.add(1);
                distances.add(Integer.parseInt(nextNum));
                nextNum = "";
            } else {
                nextNum += "" + c;
            }
        }
        distances.add(Integer.parseInt(nextNum));
        
        //System.out.println(distances);
        
        //System.out.println(turns);
        
        int currentDir = 0;
        int currentX = 8;
        int currentY = 0;
        
        for (int j = 0; j < distances.size(); j++) {
            
            int distance = distances.get(j);
            
            for (int k = 0; k < distance; k++) {
                
                //printBoard(board, currentX, currentY, currentDir);
                
                int nextX = currentX;
                int nextY = currentY;
                if (currentDir == 0 || currentDir == 2) {
                    if (currentDir == 0) {
                        nextX++;
                    }
                    
                    if (currentDir == 2) {
                        nextX--;
                    }
                    
                    if (nextX > xMaxs.get(nextY)) {
                        nextX = xMins.get(nextY);
                    }
                    
                    if (nextX < xMins.get(nextY)) {
                        nextX = xMaxs.get(nextY);
                    }
                }
                
                if (currentDir == 1 || currentDir == 3) {
                    if (currentDir == 1) {
                        nextY++;
                    }
                    
                    if (currentDir == 3) {
                        nextY--;
                    }
                    
                    if (nextY > yMaxs.get(nextX)) {
                        nextY = yMins.get(nextX);
                    }
                    
                    if (nextY < yMins.get(nextX)) {
                        nextY = yMaxs.get(nextX);
                    }
                }
                
                if (board[nextX][nextY] == 2) {
                    break;
                }
                
                currentX = nextX;
                currentY = nextY;
                
            }
            
            if (j < distances.size() - 1) {
                int turn = turns.get(j);
                
                if (turn == 0) {
                    currentDir--;
                    if (currentDir < 0) {
                        currentDir = 3;
                    }
                } else {
                    currentDir++;
                    if (currentDir > 3) {
                        currentDir = 0;
                    }
                }
                
            }
            
        }
        
        System.out.println(1000 * (currentY + 1) + 4 * (currentX + 1) + currentDir);
        
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input22.txt"));
    
        int xSize = 150;
        int ySize = 200;
        
        // 0 = void, 1 = open, 2 = wall
        // direction 0 >, 1 v, 2 <, 3^
        int[][] board = new int[xSize][ySize];
        
        int y = 0;
        
        while (true) {
            
            final String line = fileReader.readLine();
            
            if (line.isEmpty()) {
                break;
            }
            
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == '.') {
                    board[x][y] = 1;
                }
                if (c == '#') {
                    board[x][y] = 2;
                }
                x++;
            }
            
            y++;
        }
    
        /*CubeFace face0 = new CubeFace("0", board, 8, 0);
        CubeFace face1 = new CubeFace("1", board, 0, 4);
        CubeFace face2 = new CubeFace("2", board, 4, 4);
        CubeFace face3 = new CubeFace("3", board, 8, 4);
        CubeFace face4 = new CubeFace("4", board, 8, 8);
        CubeFace face5 = new CubeFace("5", board, 12, 8);*/
        
        
        CubeFace face0 = new CubeFace("0", board, 50, 0);
        CubeFace face1 = new CubeFace("1", board, 100, 0);
        CubeFace face2 = new CubeFace("2", board, 50, 50);
        CubeFace face3 = new CubeFace("3", board, 0, 100);
        CubeFace face4 = new CubeFace("4", board, 50, 100);
        CubeFace face5 = new CubeFace("5", board, 0, 150);
        
        //printBoard(face0.board, 0, 0, 0);
        //printBoard(face1.board, 0, 0, 0);
        //printBoard(face2.board, 0, 0, 0);
        //printBoard(face3.board, 0, 0, 0);
        //printBoard(face4.board, 0, 0, 0);
        //printBoard(face5.board, 0, 0, 0);
        
        /*face0.neighbourRight = face5;
        face0.entrySideRight = 5;
        face5.neighbourRight = face0;
        face5.entrySideRight = 5;
        
        face0.neighbourBottom = face3;
        face0.entrySideBottom = 3;
        face3.neighbourTop = face0;
        face3.entrySideTop = 1;
        
        face0.neighbourLeft = face2;
        face0.entrySideLeft = 3;
        face2.neighbourTop = face0;
        face2.entrySideTop = 2;
        
        face0.neighbourTop = face1;
        face0.entrySideTop = 7;
        face1.neighbourTop = face0;
        face1.entrySideTop = 7;
        
        face1.neighbourRight = face2;
        face1.entrySideRight = 2;
        face2.neighbourLeft = face1;
        face2.entrySideLeft = 0;
        
        face1.neighbourBottom = face4;
        face1.entrySideBottom = 5;
        face4.neighbourBottom = face1;
        face4.entrySideBottom = 5;
        
        face1.neighbourLeft = face5;
        face1.entrySideLeft = 5;
        face5.neighbourBottom = face1;
        face5.entrySideBottom = 6;
        
        face2.neighbourRight = face3;
        face2.entrySideRight = 2;
        face3.neighbourLeft = face2;
        face3.entrySideLeft = 0;
        
        face2.neighbourBottom = face4;
        face2.entrySideBottom = 6;
        face4.neighbourLeft = face2;
        face4.entrySideLeft = 5;
        
        face3.neighbourRight = face5;
        face3.entrySideRight = 7;
        face5.neighbourTop = face3;
        face5.entrySideTop = 5;
        
        face3.neighbourBottom = face4;
        face3.entrySideBottom = 3;
        face4.neighbourTop = face3;
        face4.entrySideTop = 1;
        
        face4.neighbourRight = face5;
        face4.entrySideRight = 2;
        face5.neighbourLeft = face4;
        face5.entrySideLeft = 0;*/
    
        face0.neighbourRight = face1;
        face0.entrySideRight = 2;
        face1.neighbourLeft = face0;
        face1.entrySideLeft = 0;
        
        face0.neighbourBottom = face2;
        face0.entrySideBottom = 3;
        face2.neighbourTop = face0;
        face2.entrySideTop = 1;
    
        face2.neighbourBottom = face4;
        face2.entrySideBottom = 3;
        face4.neighbourTop = face2;
        face4.entrySideTop = 1;
    
        face3.neighbourRight = face4;
        face3.entrySideRight = 2;
        face4.neighbourLeft = face3;
        face4.entrySideLeft = 0;
    
        face3.neighbourBottom = face5;
        face3.entrySideBottom = 3;
        face5.neighbourTop = face3;
        face5.entrySideTop = 1;
    
        face4.neighbourBottom = face5;
        face4.entrySideBottom = 0;
        face5.neighbourRight = face4;
        face5.entrySideRight = 1;
    
        face2.neighbourLeft = face3;
        face2.entrySideLeft = 3;
        face3.neighbourTop = face2;
        face3.entrySideTop = 2;
    
        face0.neighbourLeft = face3;
        face0.entrySideLeft = 6;
        face3.neighbourLeft = face0;
        face3.entrySideLeft = 6;
        
        face0.neighbourTop = face5;
        face0.entrySideTop = 2;
        face5.neighbourLeft = face0;
        face5.entrySideLeft = 3;
    
        face1.neighbourRight = face4;
        face1.entrySideRight = 4;
        face4.neighbourRight = face1;
        face4.entrySideRight = 4;
        
        face1.neighbourBottom = face2;
        face1.entrySideBottom = 0;
        face2.neighbourRight = face1;
        face2.entrySideRight = 1;
        
        face1.neighbourTop = face5;
        face1.entrySideTop = 1;
        face5.neighbourBottom = face1;
        face5.entrySideBottom = 3;
        
        
        final String moves = fileReader.readLine();
        
        List<Integer> distances = new ArrayList<>();
        // L = 0, R = 1
        List<Integer> turns = new ArrayList<>();
        
        String nextNum = "";
        
        for (char c : moves.toCharArray()) {
            if (c == 'L') {
                turns.add(0);
                distances.add(Integer.parseInt(nextNum));
                nextNum = "";
            } else if (c == 'R') {
                turns.add(1);
                distances.add(Integer.parseInt(nextNum));
                nextNum = "";
            } else {
                nextNum += "" + c;
            }
        }
        distances.add(Integer.parseInt(nextNum));
        
        int currentDir = 0;
        int currentX = 0;
        int currentY = 0;
        
        CubeFace currentFace = face0;
        
        for (int j = 0; j < distances.size(); j++) {
            
            int distance = distances.get(j);
            
            for (int k = 0; k < distance; k++) {
    
                //System.out.println("Current face: " + currentFace.name);
                //printBoard(currentFace.board, currentX, currentY, currentDir);
                
                MoveResult moveResult = currentFace.move(currentX, currentY, currentDir);
                
                currentX = moveResult.newX;
                currentY = moveResult.newY;
                currentDir = moveResult.newDir;
                currentFace = moveResult.cubeFace;
                
            }
            
            if (j < distances.size() - 1) {
                int turn = turns.get(j);
                
                if (turn == 0) {
                    currentDir--;
                    if (currentDir < 0) {
                        currentDir = 3;
                    }
                } else {
                    currentDir++;
                    if (currentDir > 3) {
                        currentDir = 0;
                    }
                }
                
            }
            
        }
    
        /*System.out.println(currentFace.name);
        System.out.println(currentX);
        System.out.println(currentY);
        System.out.println(currentDir);*/
        
        System.out.println(1000 * (100 + currentY + 1) + 4 * (currentX + 50 + 1) + currentDir);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
