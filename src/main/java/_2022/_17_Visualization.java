package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import processing.core.PApplet;


public class _17_Visualization extends PApplet {
    
    private static final int SIZE = 600;
    
    public void settings() {
        size(SIZE, SIZE);
        //background(0);
        smooth();
    }
    
    private static class Coord {
        
        long x;
        long y;
        
        public Coord(long x, long y) {
            this.x = x;
            this.y = y;
        }
        
        public Coord clone() {
            return new Coord(x, y);
        }
    }
    
    
    private static class Directions {
        
        int index;
        String dirStr;
        
        public Directions(int index, String dirStr) {
            this.index = index;
            this.dirStr = dirStr;
        }
        
        private int getNextDir() {
            char c = dirStr.charAt(index++ % dirStr.length());
            return c == '<' ? -1 : 1;
        }
    }
    
    
    private static class Blocks {
        
        List<Coord> block1 = Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0), new Coord(3, 0));
        List<Coord> block2 =
                Arrays.asList(new Coord(1, 0), new Coord(0, 1), new Coord(1, 1), new Coord(2, 1), new Coord(1, 2));
        List<Coord> block3 =
                Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0), new Coord(2, 1), new Coord(2, 2));
        List<Coord> block4 = Arrays.asList(new Coord(0, 0), new Coord(0, 1), new Coord(0, 2), new Coord(0, 3));
        List<Coord> block5 = Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(0, 1), new Coord(1, 1));
        
        List<List<Coord>> blocks = Arrays.asList(block1, block2, block3, block4, block5);
        
        List<Integer> colours = Arrays.asList(0xFFFF0000, 0xFF00FF00, 0x0000FF, 0xFF00FFFF, 0xFFFFFF00);
        
        int index = 0;
        
        private List<Coord> getNextBlock() {
            return blocks.get(index++ % 5);
        }
        
        private int getColor() {
            return colours.get(index % 5);
        }
    }
    
    
    private static Directions directions;
    private static Blocks blocks = new Blocks();
    private static int[][] chamber = new int[7][4000000];
    
    long heighestRock = -1;
    
    public static void a() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input17.txt"));
        
        final String line = fileReader.readLine();
        
        directions = new Directions(0, line);
        
    }
    
    boolean startNewDrop = true;
    List<Coord> currentBlock;
    boolean dropDone = false;
    boolean horizontalTurn = true;
    
    private void startNewDrop() {
        currentBlock = blocks.getNextBlock().stream().map(v -> v.clone()).collect(Collectors.toList());
    
        Coord startPos = new Coord(2, heighestRock + 4);
    
        for (Coord coord : currentBlock) {
            coord.x += startPos.x;
            coord.y += startPos.y;
        }
    }
    
    public void draw() {
        
        if (startNewDrop) {
            startNewDrop();
            startNewDrop = false;
        }
        
        if (dropDone) {
    
            //drawChamber(chamber, currentBlock);
            
            for (Coord coord : currentBlock) {
                chamber[(int)coord.x][(int)coord.y] = blocks.getColor();
            }
    
            for (Coord coord : currentBlock) {
                if (coord.y > heighestRock) {
                    heighestRock = coord.y;
                }
            }
            
            dropDone = false;
            startNewDrop = true;
        } else {
            
            if (horizontalTurn) {
                int currentDir = directions.getNextDir();
    
                boolean canMoveHorizontally = true;
    
                for (Coord coord : currentBlock) {
                    if (coord.x + currentDir >= 7 || coord.x + currentDir < 0
                            || chamber[(int)coord.x + currentDir][(int)coord.y] != 0) {
                        canMoveHorizontally = false;
                        break;
                    }
                }
    
                if (canMoveHorizontally) {
                    for (Coord coord : currentBlock) {
                        coord.x += currentDir;
                    }
                }
    
                drawChamber(chamber, currentBlock);
            } else {
                boolean canMoveDown = true;
    
                for (Coord coord : currentBlock) {
                    if (coord.y - 1 < 0 || chamber[(int)coord.x][(int)coord.y - 1] != 0) {
                        canMoveDown = false;
                        break;
                    }
                }
    
                if (!canMoveDown) {
                    dropDone = true;
                } else {
                    for (Coord coord : currentBlock) {
                        coord.y -= 1;
                    }
                }
    
                drawChamber(chamber, currentBlock);
            }
            
            horizontalTurn = !horizontalTurn;
        }
        
    }
    
    private void drawChamber(int[][] chamber, List<Coord> block) {
    
        fill(0);
        rect(0, 0, 1000, 1000);
        
        for (int k = 200; k >= 0; k--) {
        
            for (int m = 0; m < 7; m++) {
            
                boolean blockIsPresent = false;
            
                for (Coord coord : block) {
                    if (coord.x == m && coord.y == k) {
                        blockIsPresent = true;
                    }
                }
            
                if (blockIsPresent && chamber[m][k] != 0) {
                    throw new RuntimeException("Invalid state");
                }
    
                if (chamber[m][k] == 0 && !blockIsPresent) {
                    continue;
                }
                
                noStroke();
    
                if (chamber[m][k] != 0) {
                    fill(chamber[m][k]);
                }
    
                if (blockIsPresent) {
                    fill(blocks.getColor());
                }
                
                rect(m*10, SIZE - 20 - k*10, 10, 10);
            }
        }
    
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String... args) {
        
        try {
            a();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        PApplet.main("_2022._17_Visualization");
    }
}
