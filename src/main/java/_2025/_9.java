package _2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _9 {

    private record Point(int x, int y) {

    }

    private record Line(Point point1, Point point2) {

    }

    public static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input9.txt"));

        List<Point> points = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            points.add(new Point(Integer.parseInt(line.split(",")[0]), Integer.parseInt(line.split(",")[1])));

        }

        long max = 0;

        for (int j = 0; j < points.size() - 1; j++) {
            for (int k = j + 1; k < points.size(); k++) {
                int width = Math.abs(points.get(j).x - points.get(k).x);
                int height = Math.abs(points.get(j).y - points.get(k).y);

                long area = (long)(width+1) * (height+1);

                if (area > max) {
                    max = area;
                }
            }
        }

        System.out.println(max);

    }

    private static boolean isBetweenExclusive(int num, int min, int max) {

        if (max < min) {
            int temp;
            temp = min;
            min = max;
            max = temp;
        }

        return num > min && num < max;
    }

    private static boolean isBetweenInclusive(int num, int min, int max) {

        if (max < min) {
            int temp;
            temp = min;
            min = max;
            max = temp;
        }

        return num >= min && num <= max;
    }

    private static boolean isLeftOrTopCorner(int num, int min, int max) {
        return num == Math.min(min, max);
    }

    private static boolean isRightOrBottomCorner(int num, int min, int max) {
        return num == Math.max(min, max);
    }

    private static boolean isPointInside(Point point, List<Line> horizontalLines, List<Line> verticalLines) {

        for (Line verticalLine : verticalLines) {
            if (point.x == verticalLine.point1.x && isBetweenInclusive(point.y, verticalLine.point1.y, verticalLine.point2.y)) {
                return true;
            }
        }

        for (Line horizontalLine : horizontalLines) {
            if (point.y == horizontalLine.point1.y && isBetweenInclusive(point.x, horizontalLine.point1.x, horizontalLine.point2.x)) {
                return true;
            }
        }

        // Right

        int intersectionCount1 = 0;
        int topCornerCount1 = 0;
        int bottomCornerCount1 = 0;

        for (Line verticalLine : verticalLines) {
            if (verticalLine.point1.x > point.x) {

                if (isBetweenExclusive(point.y, verticalLine.point1.y, verticalLine.point2.y)) {
                    intersectionCount1++;
                }

                if (isRightOrBottomCorner(point.y, verticalLine.point1.y, verticalLine.point2.y)) {
                    bottomCornerCount1++;
                }
                if (isLeftOrTopCorner(point.y, verticalLine.point1.y, verticalLine.point2.y)) {
                    topCornerCount1++;
                }
            }
        }

        if (bottomCornerCount1 != 0 && bottomCornerCount1 == topCornerCount1) {
            intersectionCount1++;
        }

        // Left

        int intersectionCount2 = 0;
        int topCornerCount2 = 0;
        int bottomCornerCount2 = 0;

        for (Line verticalLine : verticalLines) {
            if (verticalLine.point1.x < point.x) {
                if (isBetweenExclusive(point.y, verticalLine.point1.y, verticalLine.point2.y)) {
                    intersectionCount2++;
                }

                if (isRightOrBottomCorner(point.y, verticalLine.point1.y, verticalLine.point2.y)) {
                    bottomCornerCount2++;
                }
                if (isLeftOrTopCorner(point.y, verticalLine.point1.y, verticalLine.point2.y)) {
                    topCornerCount2++;
                }
            }
        }

        if (bottomCornerCount2 != 0 && bottomCornerCount2 == topCornerCount2) {
            intersectionCount2++;
        }

        // Down

        int intersectionCount3 = 0;
        int leftCornerCount3 = 0;
        int rightCornerCount3 = 0;

        for (Line horizontalLine : horizontalLines) {
            if (horizontalLine.point1.y > point.y) {
                if (isBetweenExclusive(point.x, horizontalLine.point1.x, horizontalLine.point2.x)) {
                    intersectionCount3++;
                }

                if (isRightOrBottomCorner(point.x, horizontalLine.point1.x, horizontalLine.point2.x)) {
                    rightCornerCount3++;
                }
                if (isLeftOrTopCorner(point.x, horizontalLine.point1.x, horizontalLine.point2.x)) {
                    leftCornerCount3++;
                }
            }
        }

        if (leftCornerCount3 != 0 && leftCornerCount3 == rightCornerCount3) {
            intersectionCount3++;
        }

        // Up

        int intersectionCount4 = 0;
        int leftCornerCount4 = 0;
        int rightCornerCount4 = 0;

        for (Line horizontalLine : horizontalLines) {
            if (horizontalLine.point1.y < point.y) {

                if (isBetweenExclusive(point.x, horizontalLine.point1.x, horizontalLine.point2.x)) {
                    intersectionCount4++;
                }

                if (isRightOrBottomCorner(point.x, horizontalLine.point1.x, horizontalLine.point2.x)) {
                    rightCornerCount4++;
                }
                if (isLeftOrTopCorner(point.x, horizontalLine.point1.x, horizontalLine.point2.x)) {
                    leftCornerCount4++;
                }
            }
        }

        if (leftCornerCount4 != 0 && leftCornerCount4 == rightCornerCount4) {
            intersectionCount4++;
        }

        //System.out.println(intersectionCount1);
        //System.out.println(intersectionCount2);
        //System.out.println(intersectionCount3);
        //System.out.println(intersectionCount4);

        return (intersectionCount1 % 2 == 1) && intersectionCount2 % 2 == 1 && intersectionCount3 % 2 == 1 && intersectionCount4 % 2 == 1;
    }

    public static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input9.txt"));

        List<Point> points = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            points.add(new Point(Integer.parseInt(line.split(",")[0]), Integer.parseInt(line.split(",")[1])));

        }

        List<Line> horizontalLines = new ArrayList<>();
        List<Line> verticalLines = new ArrayList<>();

        boolean lastHorizontal = false;

        for (int j = 0; j < points.size() - 1; j++) {
            Point p1 = points.get(j);
            Point p2 = points.get(j + 1);

            if (p1.y == p2.y) {
                horizontalLines.add(new Line(p1, p2));
                lastHorizontal = true;
            } else {
                verticalLines.add(new Line(p1, p2));
                lastHorizontal = false;
            }
        }

        if (lastHorizontal) {
            verticalLines.add(new Line(points.get(points.size() - 1), points.get(0)));
        } else {
            horizontalLines.add(new Line(points.get(points.size() - 1), points.get(0)));
        }

        //System.out.println(horizontalLines);
        //System.out.println(verticalLines);

        //System.out.println(isPointInside(new Point(7, 4), horizontalLines, verticalLines));

        /*for (int j = 0; j < 20; j++) {
            for (int k = 0; k < 20; k++) {
                if (isPointInside(new Point(k, j), horizontalLines, verticalLines)) {
                    System.out.print("X");
                } else {
                    System.out.print("O");
                }
            }
            System.out.println();
        }*/

        List<AreaAndPoints> areaAndPoints = new ArrayList<>();

        for (int j = 0; j < points.size() - 1; j++) {
            for (int k = j + 1; k < points.size(); k++) {
                int width = Math.abs(points.get(j).x - points.get(k).x);
                int height = Math.abs(points.get(j).y - points.get(k).y);

                long area = (long)(width+1) * (height+1);

                areaAndPoints.add(new AreaAndPoints(points.get(j), points.get(k), area));
            }
        }

        areaAndPoints.sort((x,y)-> Long.compare(y.area(), x.area()));

        for (int j = 0; j < areaAndPoints.size(); j++) {

            AreaAndPoints ap = areaAndPoints.get(j);

            Point p1 = ap.point1;
            Point p2 = ap.point2;

            int x1 = Math.min(p1.x, p2.x);
            int x2 = Math.max(p1.x, p2.x);
            int y1 = Math.min(p1.y, p2.y);
            int y2 = Math.max(p1.y, p2.y);

            boolean allInside = true;

            for (int m = x1; m <= x2; m++) {
                if (!isPointInside(new Point(m, y1), horizontalLines, verticalLines)) {
                    allInside = false;
                    break;
                }

                if (!isPointInside(new Point(m, y1), horizontalLines, verticalLines)) {
                    allInside = false;
                    break;
                }
            }

            if (allInside) {
                for (int m = y1; m <= y2; m++) {
                    if (!isPointInside(new Point(x1, m), horizontalLines, verticalLines)) {
                        allInside = false;
                        break;
                    }

                    if (!isPointInside(new Point(x2, m), horizontalLines, verticalLines)) {
                        allInside = false;
                        break;
                    }
                }
            }

            //System.out.println(j);
            //System.out.println(ap.area);

            if (allInside) {
                System.out.println(ap.area);
                break;
            }
        }
    }

    private record AreaAndPoints(Point point1, Point point2, long area) {}

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
