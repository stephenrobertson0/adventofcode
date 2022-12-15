package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;


public class _15 {
    
    private static class Coord {
    
        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    
        int x;
        int y;
    
        @Override
        public String toString() {
            return "Coord{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Coord coord = (Coord)o;
            return x == coord.x && y == coord.y;
        }
    
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    
    private static class Range {
    
        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    
        int start;
        int end;
    
        @Override
        public String toString() {
            return "Range{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }
    }
    
    private static class Row {
        List<Range> ranges = new ArrayList<>();
        
        private void removeRange(Range otherRange) {
    
            //System.out.println("Ranges before: " + ranges);
            //System.out.println("Removing: " + otherRange);
            
            List<Range> newRanges = new ArrayList<>();
            
            for (Range range : ranges) {
                if (otherRange.start <= range.start && otherRange.end >= range.end) {
                    // Don't add to newRanges
                } else if (otherRange.start > range.start && otherRange.end < range.end) {
                    newRanges.add(new Range(range.start, otherRange.start-1));
                    newRanges.add(new Range(otherRange.end+1, range.end));
                } else if (otherRange.start > range.start && otherRange.start <= range.end) {
                    range.end = otherRange.start - 1;
                    newRanges.add(range);
                } else if (otherRange.end < range.end && otherRange.end >= range.start) {
                    range.start = otherRange.end + 1;
                    newRanges.add(range);
                } else {
                    // Not overlapping at all
                    newRanges.add(range);
                }
            }
            
            ranges = newRanges;
    
            //System.out.println("Ranges after: " + ranges);
            
        }
    }
    
    public static void a() throws Exception {
    
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input15.txt"));
    
        Set<Integer> xPointsCovered = new HashSet<>();
        Set<Integer> allBeaconsInRow = new HashSet<>();
        Set<Integer> allSensorsInRow = new HashSet<>();
        
        while (true) {
        
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
            
            String firstPart = line.substring(0, line.indexOf(":"));
            String secondPart = line.substring(line.indexOf(":"));
            
            Coord sensor = new Coord(
                    Integer.parseInt(firstPart.substring(firstPart.indexOf("x=")+2, firstPart.indexOf(","))),
                    Integer.parseInt(firstPart.substring(firstPart.indexOf("y=")+2)));
    
            Coord beacon = new Coord(
                    Integer.parseInt(secondPart.substring(secondPart.indexOf("x=")+2, secondPart.indexOf(","))),
                    Integer.parseInt(secondPart.substring(secondPart.indexOf("y=")+2)));
            
            //System.out.println("sensor: " + sensor);
            //System.out.println("beacon: " + beacon);
            
            int checkRow = 2000000;
            
            if (checkRow == beacon.y) {
                allBeaconsInRow.add(beacon.x);
            }
    
            if (checkRow == sensor.y) {
                allSensorsInRow.add(sensor.x);
            }
            
            int beaconYDistance = Math.abs(sensor.y - beacon.y);
            int beaconXDistance = Math.abs(sensor.x - beacon.x);
    
            int beaconTotalDistance = beaconXDistance + beaconYDistance;
            
            int rowYDistanceFromSensor = Math.abs(sensor.y - checkRow);
            
            if (rowYDistanceFromSensor <= beaconTotalDistance) {
                
                int distanceDifference = Math.abs(beaconTotalDistance - rowYDistanceFromSensor);
                
                int xStartCoverage = sensor.x - distanceDifference;
                int xEndCoverage = sensor.x + distanceDifference;
    
                //System.out.println(xStartCoverage);
                //System.out.println(xEndCoverage);
                
                for (int j = xStartCoverage; j <= xEndCoverage; j++) {
                    xPointsCovered.add(j);
                }
                
            }
            
        }
    
        xPointsCovered.addAll(allSensorsInRow);
        xPointsCovered.removeAll(allBeaconsInRow);
        
        System.out.println(xPointsCovered.size());
        
    }
    
    public static void b() throws Exception {
    
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input15.txt"));
    
        List<Coord> allSensors = new ArrayList<>();
        List<Coord> allBeacons = new ArrayList<>();
        
        int maxSize = 4000000;
        
        while (true) {
    
            final String line = fileReader.readLine();
    
            if (line == null) {
                break;
            }
    
            String firstPart = line.substring(0, line.indexOf(":"));
            String secondPart = line.substring(line.indexOf(":"));
    
            Coord sensor = new Coord(
                    Integer.parseInt(firstPart.substring(firstPart.indexOf("x=") + 2, firstPart.indexOf(","))),
                    Integer.parseInt(firstPart.substring(firstPart.indexOf("y=") + 2)));
    
            Coord beacon = new Coord(
                    Integer.parseInt(secondPart.substring(secondPart.indexOf("x=") + 2, secondPart.indexOf(","))),
                    Integer.parseInt(secondPart.substring(secondPart.indexOf("y=") + 2)));
    
            allSensors.add(sensor);
            allBeacons.add(beacon);
        }
        
        List<Row> rows = new ArrayList<>();
        
        for (int k = 0; k <= maxSize; k++) {
            Row row = new Row();
            row.ranges.add(new Range(0, maxSize));
            rows.add(row);
        }
        
        for (int j = 0; j < allBeacons.size(); j++) {
            
            Coord sensor = allSensors.get(j);
            Coord beacon = allBeacons.get(j);
            
            int beaconYDistance = Math.abs(sensor.y - beacon.y);
            int beaconXDistance = Math.abs(sensor.x - beacon.x);
    
            int beaconTotalDistance = beaconXDistance + beaconYDistance;
            
            for (int k = 0; k <= maxSize; k++) {
                Row row = rows.get(k);
    
                //System.out.println("ROW: " + k);
                
                int checkRow = k;
    
                int rowYDistanceFromSensor = Math.abs(sensor.y - checkRow);
    
                if (rowYDistanceFromSensor <= beaconTotalDistance) {
        
                    int distanceDifference = Math.abs(beaconTotalDistance - rowYDistanceFromSensor);
        
                    int xStartCoverage = sensor.x - distanceDifference;
                    int xEndCoverage = sensor.x + distanceDifference;
        
                    row.removeRange(new Range(xStartCoverage, xEndCoverage));
        
                }
            }
            
        }
    
        for (int k = 0; k <= maxSize; k++) {
            if (!rows.get(k).ranges.isEmpty()) {
                //System.out.println(rows.get(k).ranges + " " + k);
                System.out.println((long)rows.get(k).ranges.get(0).start * 4000000 + k);
            }
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
