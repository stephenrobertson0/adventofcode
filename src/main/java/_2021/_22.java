package _2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _22 {
    
    
    public static void a() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input22.txt")));
        
        int[][][] cubes = new int[101][101][101];
        
        int offset = 50;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String onOff = line.substring(0, line.indexOf(' '));
            String rest = line.substring(line.indexOf(' ') + 1);
            
            String x = rest.split(",")[0];
            String y = rest.split(",")[1];
            String z = rest.split(",")[2];
            
            int x1 = Integer.parseInt(x.substring(2, x.indexOf(".."))) + offset;
            int x2 = Integer.parseInt(x.substring(x.indexOf("..") + 2)) + offset;
            
            int y1 = Integer.parseInt(y.substring(2, y.indexOf(".."))) + offset;
            int y2 = Integer.parseInt(y.substring(y.indexOf("..") + 2)) + offset;
            
            int z1 = Integer.parseInt(z.substring(2, z.indexOf(".."))) + offset;
            int z2 = Integer.parseInt(z.substring(z.indexOf("..") + 2)) + offset;
            
            if (x1 > 100 || x1 < 0) {
                break;
            }
            
            if (onOff.equals("on")) {
                
                for (int xx = x1; xx <= x2; xx++) {
                    for (int yy = y1; yy <= y2; yy++) {
                        for (int zz = z1; zz <= z2; zz++) {
                            cubes[xx][yy][zz] = 1;
                        }
                    }
                }
                
            } else {
                for (int xx = x1; xx <= x2; xx++) {
                    for (int yy = y1; yy <= y2; yy++) {
                        for (int zz = z1; zz <= z2; zz++) {
                            cubes[xx][yy][zz] = 0;
                        }
                    }
                }
            }
        }
        
        int count = 0;
        
        for (int xx = 0; xx < 101; xx++) {
            for (int yy = 0; yy < 101; yy++) {
                for (int zz = 0; zz < 101; zz++) {
                    count += cubes[xx][yy][zz];
                }
            }
        }
        
        System.out.println(count);
    }
    
    private static class Cube {
        
        int x1;
        int y1;
        int z1;
        int x2;
        int y2;
        int z2;
    
        public Cube(int x1, int y1, int z1, int x2, int y2, int z2) {
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            this.x2 = x2;
            this.y2 = y2;
            this.z2 = z2;
        }
    
        public Cube getIntersection(Cube cube) {
            
            if ((x1 <= cube.x2 && x2 >= cube.x1) && (y1 <= cube.y2 && y2 >= cube.y1) && (z1 <= cube.z2 && z2 >= cube.z1)) {
                
                int newX1 = Math.max(x1, cube.x1);
                int newX2 = Math.min(x2, cube.x2);
                int newY1 = Math.max(y1, cube.y1);
                int newY2 = Math.min(y2, cube.y2);
                int newZ1 = Math.max(z1, cube.z1);
                int newZ2 = Math.min(z2, cube.z2);
                
                return new Cube(newX1, newY1, newZ1, newX2, newY2, newZ2);
            }
            
            return null;
            
        }
        
        public long getSize() {
            return (long)((x2 - x1) + 1) * ((y2 - y1) + 1) * ((z2 - z1) + 1);
        }
    
        @Override
        public String toString() {
            return "Cube{" +
                    "x1=" + x1 +
                    ", y1=" + y1 +
                    ", z1=" + z1 +
                    ", x2=" + x2 +
                    ", y2=" + y2 +
                    ", z2=" + z2 +
                    '}';
        }
    }
    
    
    private static class OnOff {
        
        private long value;
        private Cube cube;
        
        public OnOff(long value, Cube cube) {
            this.value = value;
            this.cube = cube;
        }
        
        @Override
        public String toString() {
            return "OnOff{" +
                    "value=" + value +
                    ", cube=" + cube +
                    '}';
        }
    }
    
    
    private static class Instr {
        
        boolean on;
        Cube cube;
        
        public Instr(boolean on, Cube cube) {
            this.on = on;
            this.cube = cube;
        }
    }
    
    public static void b() throws Exception {
        
        List<OnOff> allCubes = new ArrayList<>();
    
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input22.txt")));
    
        int offset = 0;
    
        while (true) {
            final String line = fileReader.readLine();
    
            if (line == null) {
                break;
            }
    
            String onOff = line.substring(0, line.indexOf(' '));
            String rest = line.substring(line.indexOf(' ') + 1);
    
            String x = rest.split(",")[0];
            String y = rest.split(",")[1];
            String z = rest.split(",")[2];
    
            int x1 = Integer.parseInt(x.substring(2, x.indexOf(".."))) + offset;
            int x2 = Integer.parseInt(x.substring(x.indexOf("..") + 2)) + offset;
    
            int y1 = Integer.parseInt(y.substring(2, y.indexOf(".."))) + offset;
            int y2 = Integer.parseInt(y.substring(y.indexOf("..") + 2)) + offset;
    
            int z1 = Integer.parseInt(z.substring(2, z.indexOf(".."))) + offset;
            int z2 = Integer.parseInt(z.substring(z.indexOf("..") + 2)) + offset;
    
            //if (x1 > 100 || x1 < 100) {
            //    break;
            //}
    
            Cube cube = new Cube(x1, y1, z1, x2, y2, z2);
            
            if (allCubes.isEmpty()) {
                allCubes.add(new OnOff(cube.getSize(), cube));
                continue;
            }
            
            for (OnOff onOffExisting : new ArrayList<>(allCubes)) {
                
                Cube intersection = onOffExisting.cube.getIntersection(cube);
    
                //System.out.println("intersection: " + intersection);
                
                if (intersection != null) {
                    allCubes.add(new OnOff(
                            onOffExisting.value > 0 ? -intersection.getSize() : intersection.getSize(),
                            intersection));
                }
                
            }
    
            if (onOff.equals("on")) {
                allCubes.add(new OnOff(cube.getSize(), cube));
            }
            
            //System.out.println(allCubes.size());
            
        }
        
        long count = 0;
        
        for (OnOff cube : allCubes) {
            
            count += cube.value;
            
        }
        
        System.out.println(count);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}