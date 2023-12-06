package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class _3 {

    private static final class Coord {

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int x;
        int y;

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    static char[][] map = new char[140][140];

    private static final class StartAndEndCoord {

        public StartAndEndCoord(int number, Coord start, Coord end) {
            this.number = number;
            this.start = start;
            this.end = end;
        }

        int number;
        Coord start;
        Coord end;

        public int getNumber() {
            return number;
        }

        public Coord getStart() {
            return start;
        }

        public Coord getEnd() {
            return end;
        }
    }

    private static boolean hasCharAdjacent(StartAndEndCoord startAndEndCoord) {
        int startX = startAndEndCoord.getStart().getX() - 1;
        int endX = startAndEndCoord.getEnd().getX() + 1;

        int startY = startAndEndCoord.getStart().getY() - 1;
        int endY = startAndEndCoord.getEnd().getY() + 1;

        boolean isCharAdjacent = false;

        for (int j = startX; j <= endX; j++) {
            for (int k = startY; k <= endY; k++) {

                if (j < 0 || k < 0 || j >= 140 || k >= 140) {
                    continue;
                }

                if (map[j][k] != '.' && (map[j][k] < '0' || map[j][k] > '9')) {
                    isCharAdjacent = true;
                }
            }
        }

        return isCharAdjacent;

    }

    private static boolean isAdjacentToCoord(StartAndEndCoord startAndEndCoord, Coord coord) {
        int startX = startAndEndCoord.getStart().getX() - 1;
        int endX = startAndEndCoord.getEnd().getX() + 1;

        int startY = startAndEndCoord.getStart().getY() - 1;
        int endY = startAndEndCoord.getEnd().getY() + 1;

        boolean isAdjacent = false;

        for (int j = startX; j <= endX; j++) {
            for (int k = startY; k <= endY; k++) {

                if (j < 0 || k < 0 || j >= 140 || k >= 140) {
                    continue;
                }

                if (j == coord.getX() && k == coord.getY()) {
                    isAdjacent = true;
                }
            }
        }

        return isAdjacent;

    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input3.txt"));

        List<StartAndEndCoord> coordList = new ArrayList<>();

        int yCoord = 0;

        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }

            String currentNumber = "";
            int startXCoord = 0;

            for (int j =0; j < line.length(); j++) {
                char c = line.charAt(j);

                map[j][yCoord] = c;

                if (c >= '0' && c <= '9') {
                    currentNumber += c;
                }

                if (j == line.length() -1 || c < '0' || c > '9') {

                    if (!currentNumber.isEmpty()) {
                        //System.out.println("Adding number: " + currentNumber + " start x coord: " + startXCoord + " end x coord: " + (j-1));
                        coordList.add(new StartAndEndCoord(Integer.parseInt(currentNumber), new Coord(startXCoord, yCoord), new Coord(j-1, yCoord)));
                    }

                    startXCoord = j+1;
                    currentNumber = "";
                }
            }

            yCoord++;

        }

        int total = 0;

        for (StartAndEndCoord startAndEndCoord : coordList) {

            if (hasCharAdjacent(startAndEndCoord)) {
                total += startAndEndCoord.getNumber();
            }

        }

        System.out.println(total);

    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input3.txt"));

        List<StartAndEndCoord> coordList = new ArrayList<>();
        List<Coord> gears = new ArrayList<>();

        int yCoord = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String currentNumber = "";
            int startXCoord = 0;

            for (int j =0; j < line.length(); j++) {
                char c = line.charAt(j);

                map[j][yCoord] = c;

                if (c == '*') {
                    gears.add(new Coord(j, yCoord));
                }

                if (c >= '0' && c <= '9') {
                    currentNumber += c;
                }

                if (j == line.length() -1 || c < '0' || c > '9') {

                    if (!currentNumber.isEmpty()) {
                        //System.out.println("Adding number: " + currentNumber + " start x coord: " + startXCoord + " end x coord: " + (j-1));
                        coordList.add(new StartAndEndCoord(Integer.parseInt(currentNumber), new Coord(startXCoord, yCoord), new Coord(j-1, yCoord)));
                    }

                    startXCoord = j+1;
                    currentNumber = "";
                }
            }

            yCoord++;

        }

        Map<Coord, Set<StartAndEndCoord>> numbersAndGears = new HashMap<>();

        for (StartAndEndCoord startAndEndCoord : coordList) {

            for (Coord coord : gears) {

                if (isAdjacentToCoord(startAndEndCoord, coord)) {

                    Set<StartAndEndCoord> numberSet = numbersAndGears.get(coord);

                    if (numberSet == null) {
                        Set<StartAndEndCoord> set = new HashSet<>();
                        set.add(startAndEndCoord);

                        numbersAndGears.put(coord, set);
                    } else {
                        numberSet.add(startAndEndCoord);
                    }

                }
            }

        }

        long total = 0;

        for (Map.Entry<Coord, Set<StartAndEndCoord>> entry : numbersAndGears.entrySet()) {

            if (entry.getValue().size() == 2) {

                Set<StartAndEndCoord> numbers = entry.getValue();

                int result = 1;

                for (StartAndEndCoord startAndEndCoord : numbers) {
                    result *= startAndEndCoord.getNumber();
                }

                total += result;
            }

        }

        System.out.println(total);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
