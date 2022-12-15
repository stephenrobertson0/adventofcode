package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;


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
    
    
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
