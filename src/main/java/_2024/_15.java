package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class _15 {

    private record XY(int x, int y) {}

    private static int SIZE = 50;

    private static void printGrid(Character[][] grid) {
        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {
                System.out.print(grid[k][j]);
            }
            System.out.println();
        }
    }

    private static void printGridV2(Character[][] grid) {
        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE*2; k++) {
                System.out.print(grid[k][j]);
            }
            System.out.println();
        }
    }

    private static XY move(Character[][] grid, char direction, XY position) {

        int[] dir;

        if (direction == '^') {
            dir = new int[]{0, -1};
        } else if (direction == '>') {
            dir = new int[]{1, 0};
        } else if (direction == '<') {
            dir = new int[]{-1, 0};
        } else {
            dir = new int[]{0, 1};
        }

        XY gap = null;
        XY firstBoxPos = null;

        for (int j = 1; j < SIZE; j++) {
            XY checkPosition = new XY(position.x+dir[0]*j, position.y+dir[1]*j);

            if (checkPosition.x >= SIZE || checkPosition.x < 0 || checkPosition.y >= SIZE || checkPosition.y < 0) {
                break;
            }

            if (grid[checkPosition.x][checkPosition.y] == '#') {
                break;
            }

            if (grid[checkPosition.x][checkPosition.y] == 'O' && firstBoxPos == null) {
                firstBoxPos = checkPosition;
            }

            if (grid[checkPosition.x][checkPosition.y] == '.') {
                gap = checkPosition;
                break;
            }

        }

        if (firstBoxPos != null && gap != null) {
            grid[firstBoxPos.x][firstBoxPos.y] = '.';
            grid[gap.x][gap.y] = 'O';
        }

        grid[position.x][position.y] = '.';

        XY newPosition = gap != null ? new XY(position.x+dir[0], position.y+dir[1]) : position;

        grid[newPosition.x][newPosition.y] = '@';

        return newPosition;
    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input15.txt"));

        boolean readingGrid = true;
        Character[][] grid = new Character[SIZE][SIZE];
        List<Character> directions = new ArrayList<>();

        int yIndex = 0;

        XY position = null;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            if (line.isEmpty()) {
                readingGrid = false;
                continue;
            }

            if (readingGrid) {
                for (int j = 0; j < SIZE; j++) {
                    char c = line.charAt(j);

                    if (c == '@') {
                        position = new XY(j, yIndex);
                    }

                    grid[j][yIndex] = c;
                }
                yIndex++;
            } else {
                for (int j = 0; j < line.length(); j++) {
                    directions.add(line.charAt(j));
                }
            }

        }

        for (Character direction : directions) {
            position = move(grid, direction, position);
        }

        long totalScore = 0;

        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE; k++) {
                if (grid[k][j] == 'O') {
                    totalScore += j * 100 + k;
                }
            }
        }

        System.out.println(totalScore);

    }

    private static XY moveV2(Character[][] grid, char direction, XY position, Set<XY> boxes) {

        int[] dir;

        if (direction == '^') {
            dir = new int[]{0, -1};
        } else if (direction == '>') {
            dir = new int[]{1, 0};
        } else if (direction == '<') {
            dir = new int[]{-1, 0};
        } else {
            dir = new int[]{0, 1};
        }

        Set<XY> boxesMove = new HashSet<>();

        if (direction == '>' || direction == '<') {
            boolean gapExists = false;

            for (int j = 1; j < SIZE*2; j++) {
                XY checkPosition = new XY(position.x+dir[0]*j, position.y+dir[1]*j);

                if (checkPosition.x >= SIZE*2 || checkPosition.x < 0 || checkPosition.y >= SIZE*2 || checkPosition.y < 0) {
                    break;
                }

                if (grid[checkPosition.x][checkPosition.y] == '#') {
                    break;
                }

                if (grid[checkPosition.x][checkPosition.y] == '[') {
                    boxesMove.add(checkPosition);
                }

                if (grid[checkPosition.x][checkPosition.y] == '.') {
                    gapExists = true;
                    break;
                }

            }

            if (gapExists) {
                for (XY boxToMove : boxesMove) {
                    boxes.remove(boxToMove);
                    boxes.add(new XY(boxToMove.x + dir[0], boxToMove.y+dir[1]));
                }
            }

            grid[position.x][position.y] = '.';
            XY newPosition = gapExists ? new XY(position.x+dir[0], position.y+dir[1]) : position;
            grid[newPosition.x][newPosition.y] = '@';

            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE*2; k++) {
                    if (grid[k][j] == '[' || grid[k][j] == ']') {
                        grid[k][j] = '.';
                    }
                }
            }

            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE*2; k++) {
                    if (boxes.contains(new XY(k, j))) {
                        grid[k][j] = '[';
                        grid[k+1][j] = ']';
                    }
                }
            }

            return newPosition;
        } else {
            boolean gapExists = true;

            List<XY> checkPositions = new ArrayList<>();
            checkPositions.add(new XY(position.x+dir[0]*1, position.y+dir[1]*1));

            for (int j = 1; j < SIZE; j++) {

                List<XY> newCheckPositions = new ArrayList<>();

                for (XY checkPosition : checkPositions) {
                    if (checkPosition.x >= SIZE*2 || checkPosition.x < 0 || checkPosition.y >= SIZE*2 || checkPosition.y < 0) {
                        gapExists = false;
                        break;
                    }

                    if (grid[checkPosition.x][checkPosition.y] == '#') {
                        gapExists = false;
                        break;
                    }

                    if (grid[checkPosition.x][checkPosition.y] == '[') {
                        boxesMove.add(checkPosition);
                        newCheckPositions.add(new XY(checkPosition.x, checkPosition.y+dir[1]));
                        newCheckPositions.add(new XY(checkPosition.x+1, checkPosition.y+dir[1]));
                    }

                    if (grid[checkPosition.x][checkPosition.y] == ']') {
                        boxesMove.add(new XY(checkPosition.x-1, checkPosition.y));
                        newCheckPositions.add(new XY(checkPosition.x, checkPosition.y+dir[1]));
                        newCheckPositions.add(new XY(checkPosition.x-1, checkPosition.y+dir[1]));
                    }
                }

                checkPositions = newCheckPositions;
            }

            if (gapExists) {
                for (XY boxToMove : boxesMove) {
                    boxes.remove(boxToMove);
                }
                for (XY boxToMove : boxesMove) {
                    boxes.add(new XY(boxToMove.x + dir[0], boxToMove.y+dir[1]));
                }
            }

            grid[position.x][position.y] = '.';
            XY newPosition = gapExists ? new XY(position.x+dir[0], position.y+dir[1]) : position;
            grid[newPosition.x][newPosition.y] = '@';

            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE*2; k++) {
                    if (grid[k][j] == '[' || grid[k][j] == ']') {
                        grid[k][j] = '.';
                    }
                }
            }

            for (int j = 0; j < SIZE; j++) {
                for (int k = 0; k < SIZE*2; k++) {
                    if (boxes.contains(new XY(k, j))) {
                        grid[k][j] = '[';
                        grid[k+1][j] = ']';
                    }
                }
            }

            return newPosition;
        }
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input15.txt"));

        boolean readingGrid = true;
        Character[][] grid = new Character[SIZE*2][SIZE];
        List<Character> directions = new ArrayList<>();
        Set<XY> boxes = new HashSet<>();

        int yIndex = 0;

        XY position = null;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            if (line.isEmpty()) {
                readingGrid = false;
                continue;
            }

            if (readingGrid) {
                for (int j = 0; j < SIZE; j++) {
                    char c = line.charAt(j);

                    if (c == '@') {
                        position = new XY(j*2, yIndex);
                        grid[j*2][yIndex] = '@';
                        grid[j*2+1][yIndex] = '.';
                    } else if (c == 'O') {
                        grid[j*2][yIndex] = '[';
                        grid[j*2+1][yIndex] = ']';
                        boxes.add(new XY(j*2, yIndex));
                    } else if (c == '#') {
                        grid[j*2][yIndex] = '#';
                        grid[j*2+1][yIndex] = '#';
                    } else {
                        grid[j*2][yIndex] = '.';
                        grid[j*2+1][yIndex] = '.';
                    }
                }
                yIndex++;
            } else {
                for (int j = 0; j < line.length(); j++) {
                    directions.add(line.charAt(j));
                }
            }

        }

        //printGridV2(grid);

        for (Character direction : directions) {

            position = moveV2(grid, direction, position, boxes);

            //System.out.println("Moving: " + direction);
            //printGridV2(grid);
            //System.out.println();
        }

        long totalScore = 0;

        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < SIZE*2; k++) {
                if (grid[k][j] == '[') {
                    totalScore += j * 100 + k;
                }
            }
        }

        System.out.println(totalScore);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
