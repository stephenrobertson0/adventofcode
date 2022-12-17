package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class _17 {
    
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
    
        @Override
        public String toString() {
            return "Coord{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
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
    
    /*private static class Block {
        List<Coord>
    }*/
    
    private static class Blocks {
        
        List<Coord> block1 = Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0), new Coord(3, 0));
        List<Coord> block2 = Arrays.asList(new Coord(1, 0), new Coord(0, 1), new Coord(1, 1), new Coord(2, 1), new Coord(1, 2));
        List<Coord> block3 = Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0), new Coord(2, 1), new Coord(2, 2));
        List<Coord> block4 = Arrays.asList(new Coord(0, 0), new Coord(0, 1), new Coord(0, 2), new Coord(0, 3));
        List<Coord> block5 = Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(0, 1), new Coord(1, 1));
        
        List<List<Coord>> blocks = Arrays.asList(block1, block2, block3, block4, block5);
        
        int index = 0;
        
        private List<Coord> getNextBlock() {
            return blocks.get(index++ % 5);
        }
    }
    
    public static void a() throws Exception {
    
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input17.txt"));
        
        final String line = fileReader.readLine();
        
        Directions directions = new Directions(0, line);
        Blocks blocks = new Blocks();
        int[][] chamber = new int[7][4000000];
        
        long heighestRock = -1;
        
        for (int j = 0; j < 2000000; j++) {
        
            List<Coord> currentBlock = blocks.getNextBlock().stream().map(v->v.clone()).collect(Collectors.toList());
            
            Coord startPos = new Coord(2, heighestRock + 4);
            
            for (Coord coord : currentBlock) {
                coord.x += startPos.x;
                coord.y += startPos.y;
            }
            
            while (true) {
    
                int currentDir = directions.getNextDir();
                
                boolean canMoveHorizontally = true;
    
                for (Coord coord : currentBlock) {
                    if (coord.x + currentDir >= 7 || coord.x + currentDir < 0 || chamber[(int)coord.x + currentDir][(int)coord.y] == 1) {
                        canMoveHorizontally = false;
                        break;
                    }
                }
    
                if (canMoveHorizontally) {
                    for (Coord coord : currentBlock) {
                        coord.x += currentDir;
                    }
                }
    
                //System.out.println("Moved horizontally: " + currentDir);
                //printChamber(chamber, currentBlock);
    
                boolean canMoveDown = true;
    
                for (Coord coord : currentBlock) {
                    if (coord.y - 1 < 0 || chamber[(int)coord.x][(int)coord.y - 1] == 1) {
                        canMoveDown = false;
                        break;
                    }
                }
                
                if (!canMoveDown) {
                    break;
                }
    
                for (Coord coord : currentBlock) {
                    coord.y -= 1;
                }
    
                //System.out.println("Moved down: ");
                //printChamber(chamber, currentBlock);
            }
    
            for (Coord coord : currentBlock) {
                chamber[(int)coord.x][(int)coord.y] = 1;
            }
    
            for (Coord coord : currentBlock) {
                if (coord.y > heighestRock) {
                    heighestRock = coord.y;
                }
            }
            
        }
    
        System.out.println(heighestRock+1);
        
    }
    
    private static void printChamber(int[][] chamber, List<Coord> block) {
        for (int k = 20; k >= 0; k--) {
            System.out.print("|");
            
            for (int m = 0; m < 7; m++) {
                
                boolean blockIsPresent = false;
                
                for (Coord coord : block) {
                    if (coord.x == m && coord.y == k) {
                        blockIsPresent = true;
                    }
                }
                
                if (blockIsPresent && chamber[m][k] == 1) {
                    //throw new RuntimeException("Invalid state");
                }
                
                System.out.print(chamber[m][k] == 0 ? (blockIsPresent ? "@" : ".") : "#");
            }
    
            System.out.println("|");
        }
    
        System.out.println("---------");
    }
    
    private static void printChamber(int[][] chamber, int top, int bottom) {
        for (int k = top; k >= bottom; k--) {
            System.out.print("|");
            
            for (int m = 0; m < 7; m++) {
                
                System.out.print(chamber[m][k] == 0 ? "." : "#");
            }
            
            System.out.println("|");
        }
        
        System.out.println("---------");
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input17.txt"));
    
        final String line = fileReader.readLine();
    
        Directions directions = new Directions(0, line);
        Blocks blocks = new Blocks();
        int[][] chamber = new int[7][100_000_000];
    
        long heighestRock = -1;
        long yShift = 0;
    
        for (int j = 0; j < 1_000_000_000_000L; j++) {
        
            List<Coord> currentBlock = blocks.getNextBlock().stream().map(v->v.clone()).collect(Collectors.toList());
        
            Coord startPos = new Coord(2, heighestRock - yShift + 4);
        
            for (Coord coord : currentBlock) {
                coord.x += startPos.x;
                coord.y += startPos.y;
            }
        
            while (true) {
            
                int currentDir = directions.getNextDir();
            
                boolean canMoveHorizontally = true;
            
                for (Coord coord : currentBlock) {
                    if (coord.x + currentDir >= 7 || coord.x + currentDir < 0 || chamber[(int)coord.x + currentDir][(int)coord.y] == 1) {
                        canMoveHorizontally = false;
                        break;
                    }
                }
            
                if (canMoveHorizontally) {
                    for (Coord coord : currentBlock) {
                        coord.x += currentDir;
                    }
                }
            
                //System.out.println("Moved horizontally: " + currentDir);
                //printChamber(chamber, currentBlock);
            
                boolean canMoveDown = true;
            
                for (Coord coord : currentBlock) {
                    if (coord.y - 1 < 0 || chamber[(int)coord.x][(int)coord.y - 1] == 1) {
                        canMoveDown = false;
                        break;
                    }
                }
            
                if (!canMoveDown) {
                    break;
                }
            
                for (Coord coord : currentBlock) {
                    coord.y -= 1;
                }
            
                //System.out.println("Moved down: ");
                //printChamber(chamber, currentBlock);
            }
        
            for (Coord coord : currentBlock) {
                chamber[(int)coord.x][(int)coord.y] = 1;
            }
        
            for (Coord coord : currentBlock) {
                if (coord.y > heighestRock - yShift) {
                    heighestRock = coord.y + yShift;
                }
            }
    
            //printChamber(chamber, currentBlock);
            
            if (heighestRock - yShift > (100_000_000-10)) {
    
                int[][] newChamber = new int[7][100_000_000];
                
                for (int m = 0; m < 100; m++) {
                    for (int n = 0; n < 7; n++) {
                        newChamber[n][m] = chamber[n][(int)(heighestRock - yShift - 99 + m)];
                    }
                }
    
                //System.out.println("Chamber");
                //printChamber(chamber, 1000-1, 1000-1 - 100);
                //System.out.println("New Chamber");
                //printChamber(newChamber, 100, 0);
                
                chamber = newChamber;
                yShift += (heighestRock - yShift) - 99;
            }
            
        }
    
        System.out.println(heighestRock+1);
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
