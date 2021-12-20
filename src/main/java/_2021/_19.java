package _2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;


public class _19 {
    
    static List<Orientation> allOrientations;
    
    static {
        
        int[][] allOrders =
                new int[][] { { 0, 1, 2 }, { 0, 2, 1 }, { 1, 0, 2 }, { 1, 2, 0 }, { 2, 0, 1 }, { 2, 1, 0 } };
        boolean[][] allNegativates = new boolean[8][3];
        
        int count = 0;
        
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 2; k++) {
                for (int m = 0; m < 2; m++) {
                    allNegativates[count++] = new boolean[] { j == 0, k == 0, m == 0 };
                }
            }
        }
        
        allOrientations = new ArrayList<>();
        
        for (int j = 0; j < 6; j++) {
            for (int k = 0; k < 8; k++) {
                allOrientations.add(new Orientation(allOrders[j], allNegativates[k]));
            }
        }
        
    }
    
    
    private static class Orientation {
        
        int[] order;
        boolean[] negative;
        
        public Orientation(int[] order, boolean[] negative) {
            this.order = order;
            this.negative = negative;
        }
        
        @Override
        public String toString() {
            return "Orientation{" +
                    "order=" + Arrays.toString(order) +
                    ", negative=" + Arrays.toString(negative) +
                    '}';
        }
    }
    
    
    private static class Point3d {
        
        private int x;
        private int y;
        private int z;
        
        public Point3d(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        
        public void printWithOrientation(Orientation orientation) {
            
            Point3d newPoint3d = this.changeOrientation(orientation);
            
            int[] coords = new int[] { newPoint3d.x, newPoint3d.y, newPoint3d.z };
            
            for (int j = 0; j < 3; j++) {
                final int coord = coords[j];
                
                System.out.print(coord + " ");
            }
            
            System.out.println();
        }
        
        public Point3d changeOrientation(Orientation orientation) {
            int[] coords = new int[] { x, y, z };
            
            int[] newCoords = new int[3];
            
            for (int j = 0; j < 3; j++) {
                final int coord = coords[orientation.order[j]];
                
                newCoords[j] = orientation.negative[j] ? -coord : coord;
            }
            
            return new Point3d(newCoords[0], newCoords[1], newCoords[2]);
        }
        
        public Point3d shiftPosition(Point3d position) {
            return new Point3d(this.x + position.x, this.y + position.y, this.z + position.z);
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point3d point3d = (Point3d)o;
            return x == point3d.x &&
                    y == point3d.y &&
                    z == point3d.z;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
        
        @Override
        public String toString() {
            return "Point3d{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }
    
    
    private static class Scanner {
        
        private List<Point3d> points;
        private int n;
        
        public Scanner(List<Point3d> points, int n) {
            this.points = points;
            this.n = n;
        }
        
        @Override
        public String toString() {
            return "Scanner{" +
                    "n=" + n +
                    '}';
        }
    }
    
    
    private static class ShiftAndOrientationAndPoints {
        
        private Point3d shift;
        private Orientation orientation;
        private List<Point3d> points;
        
        public ShiftAndOrientationAndPoints(Point3d shift, Orientation orientation, List<Point3d> points) {
            this.shift = shift;
            this.orientation = orientation;
            this.points = points;
        }
        
        @Override
        public String toString() {
            return "ShiftAndOrientationAndPoints{" +
                    "shift=" + shift +
                    ", orientation=" + orientation +
                    ", points=" + points +
                    '}';
        }
    }
    
    private static ShiftAndOrientationAndPoints getShiftAndOrientationOfScanners(Scanner scanner1, Scanner scanner2) {
        
        for (int j = 0; j < scanner1.points.size(); j++) {
            
            for (int k = 0; k < scanner2.points.size(); k++) {
                
                for (Orientation orientation : allOrientations) {
                    
                    Point3d point1 = scanner1.points.get(j);
                    Point3d point2 = scanner2.points.get(k).changeOrientation(orientation);
                    
                    Point3d shift = new Point3d(point1.x - point2.x, point1.y - point2.y, point1.z - point2.z);
                    
                    List<Point3d> matchingPoints = new ArrayList<>();
                    matchingPoints.add(point1);
                    
                    for (int m = 0; m < scanner1.points.size(); m++) {
                        for (int n = 0; n < scanner2.points.size(); n++) {
                            if (m != j && n != k) {
                                
                                Point3d point1Check = scanner1.points.get(m);
                                Point3d point2Check =
                                        scanner2.points.get(n).changeOrientation(orientation).shiftPosition(shift);
                                
                                if (point1Check.equals(point2Check)) {
                                    
                                    matchingPoints.add(point1Check);
                                    //System.out.println("points equal for j " + j + " k = " + k);
                                }
                                
                            }
                        }
                    }
                    
                    //System.out.println(pointEqualCount);
                    
                    if (matchingPoints.size() >= 12) {
                        //System.out.println("yes");
                        //System.out.println(pointEqualCount);
                        //System.out.println("Shift: " + shift);
                        //System.out.println("Orientation: " + orientation);
                        return new ShiftAndOrientationAndPoints(shift, orientation, matchingPoints);
                    }
                    
                }
                
            }
            
        }
        
        return null;
        
    }
    
    private static class TreeNode {
        
        Scanner scanner;
        TreeNode parent;
        Point3d relativePosition;
        Orientation orientation;
        
        public TreeNode(Scanner scanner, TreeNode parent, Point3d relativePosition, Orientation orientation) {
            this.scanner = scanner;
            this.parent = parent;
            this.relativePosition = relativePosition;
            this.orientation = orientation;
        }
        
        @Override
        public String toString() {
            return "I am " + scanner + " relativePosition: " + relativePosition + " my parent is: " + parent;
        }
        
        public Point3d shiftPoint(Point3d point) {
            
            //System.out.println("Shifting point: " + point + " by " + orientation + " shift: " + relativePosition);
            
            /*Point3d negativeRelativePosition =
                    new Point3d(-relativePosition.x, -relativePosition.y, -relativePosition.z);*/
            
            if (parent != null) {
                return parent.shiftPoint(point.changeOrientation(orientation).shiftPosition(relativePosition));
            } else {
                return point.changeOrientation(orientation).shiftPosition(relativePosition);
            }
        }
        
        public Point3d getOverallAbsolutePos() {
            
            Point3d point = new Point3d(0, 0, 0);
            
            //System.out.println("Changing orientation: " + orientation + " relative position: " + relativePosition);
            
            if (parent != null) {
                return parent.shiftPoint(point.changeOrientation(orientation).shiftPosition(relativePosition));
            } else {
                return point.changeOrientation(orientation).shiftPosition(relativePosition);
            }
        }
        
    }
    
    public static void a() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input19.txt")));
        
        List<Scanner> scanners = new ArrayList<>();
        
        List<Point3d> currentList = null;
        
        int count = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                scanners.add(new Scanner(currentList, count++));
                break;
            }
            
            if (line.startsWith("---")) {
                currentList = new ArrayList<>();
            } else if (line.isEmpty()) {
                scanners.add(new Scanner(currentList, count++));
            } else {
                
                String[] elements = line.split(",");
                int x = Integer.parseInt(elements[0]);
                int y = Integer.parseInt(elements[1]);
                int z = Integer.parseInt(elements[2]);
                
                currentList.add(new Point3d(x, y, z));
                
            }
        }
        
        TreeNode root = new TreeNode(
                scanners.get(0),
                null,
                new Point3d(0, 0, 0),
                new Orientation(new int[] { 0, 1, 2 }, new boolean[] { false, false, false }));
        
        Map<String, TreeNode> allNodes = new HashMap<>();
        allNodes.put("0", root);
        
        Stack<Scanner> parentStack = new Stack<>();
        parentStack.push(scanners.get(0));
        
        while (!parentStack.empty()) {
            
            Scanner scanner1 = parentStack.pop();
            
            for (Scanner scanner2 : scanners) {
                
                if (scanner1 == scanner2) {
                    continue;
                }
                
                // Node already exists in the tree
                if (allNodes.containsKey("" + scanner2.n)) {
                    continue;
                }
                
                final ShiftAndOrientationAndPoints shiftAndOrientationOfScanners =
                        getShiftAndOrientationOfScanners(scanner1, scanner2);
                
                if (shiftAndOrientationOfScanners != null) {
                    System.out.println("Found match " + scanner1 + " " + scanner2);
                    
                    TreeNode parent = allNodes.get("" + scanner1.n);
                    
                    final TreeNode treeNode = new TreeNode(
                            scanner2,
                            parent,
                            shiftAndOrientationOfScanners.shift,
                            shiftAndOrientationOfScanners.orientation);
                    
                    allNodes.put(
                            "" + scanner2.n,
                            treeNode);
                    
                    parentStack.push(scanner2);
                    
                }
                
            }
            
        }
        
        for (TreeNode node : allNodes.values()) {
            
            System.out.println("Node: " + node);
            
        }
        
        Set<Point3d> allPoints = new HashSet<>();
        
        for (Scanner scanner : scanners) {
            
            for (Point3d point : scanner.points) {
                
                //System.out.println("Adding point: " + point);
                
                TreeNode treeNode = allNodes.get("" + scanner.n);
                
                final Point3d pointShifted = treeNode.shiftPoint(point);
                
                allPoints.add(pointShifted);
                
            }
            
        }
        
        /*System.out.println("Points found: ");
        
        for (Point3d point3d : allPoints) {
            System.out.println(point3d);
        }*/
        
        System.out.println(allPoints.size());
        
        // System.out.println(getShiftAndOrientationOfScanners(scanner1, scanner2));
        
        //Point3d point3d = new Point3d(50, -70, 88);
        
        //for (Orientation orientation : AdventOfCode.allOrientations) {
        //    point3d.printWithOrientation(orientation);
        //}
    }
    
    public static void b() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input19.txt")));
        
        List<Scanner> scanners = new ArrayList<>();
        
        List<Point3d> currentList = null;
        
        int count = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                scanners.add(new Scanner(currentList, count++));
                break;
            }
            
            if (line.startsWith("---")) {
                currentList = new ArrayList<>();
            } else if (line.isEmpty()) {
                scanners.add(new Scanner(currentList, count++));
            } else {
                
                String[] elements = line.split(",");
                int x = Integer.parseInt(elements[0]);
                int y = Integer.parseInt(elements[1]);
                int z = Integer.parseInt(elements[2]);
                
                currentList.add(new Point3d(x, y, z));
                
            }
        }
        
        TreeNode root = new TreeNode(
                scanners.get(0),
                null,
                new Point3d(0, 0, 0),
                new Orientation(new int[] { 0, 1, 2 }, new boolean[] { false, false, false }));
        
        Map<String, TreeNode> allNodes = new HashMap<>();
        allNodes.put("0", root);
        
        Stack<Scanner> parentStack = new Stack<>();
        parentStack.push(scanners.get(0));
        
        while (!parentStack.empty()) {
            
            Scanner scanner1 = parentStack.pop();
            
            for (Scanner scanner2 : scanners) {
                
                if (scanner1 == scanner2) {
                    continue;
                }
                
                // Node already exists in the tree
                if (allNodes.containsKey("" + scanner2.n)) {
                    continue;
                }
                
                final ShiftAndOrientationAndPoints shiftAndOrientationOfScanners =
                        getShiftAndOrientationOfScanners(scanner1, scanner2);
                
                if (shiftAndOrientationOfScanners != null) {
                    System.out.println("Found match " + scanner1 + " " + scanner2);
                    
                    TreeNode parent = allNodes.get("" + scanner1.n);
                    
                    final TreeNode treeNode = new TreeNode(
                            scanner2,
                            parent,
                            shiftAndOrientationOfScanners.shift,
                            shiftAndOrientationOfScanners.orientation);
                    
                    allNodes.put(
                            "" + scanner2.n,
                            treeNode);
                    
                    parentStack.push(scanner2);
                    
                }
                
            }
            
        }
        
        int maxDistance = 0;
        
        List<TreeNode> nodes = new ArrayList<>(allNodes.values());
        
        for (int j = 0; j < nodes.size(); j ++) {
            
            for (int k = j; k < nodes.size(); k++) {
                
                Point3d pos1 = nodes.get(j).getOverallAbsolutePos();
                Point3d pos2 = nodes.get(k).getOverallAbsolutePos();
                
                int distance = Math.abs(pos1.x - pos2.x) + Math.abs(pos1.y - pos2.y) + Math.abs(pos1.z - pos2.z);
                
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
                
            }
            
        }
        
        System.out.println(maxDistance);
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}