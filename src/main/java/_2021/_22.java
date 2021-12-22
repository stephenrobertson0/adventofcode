package _2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;


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
            String rest = line.substring(line.indexOf(' ')+1);
            
            String x = rest.split(",")[0];
            String y = rest.split(",")[1];
            String z = rest.split(",")[2];
            
            int x1 = Integer.parseInt(x.substring(2, x.indexOf(".."))) + offset;
            int x2 = Integer.parseInt(x.substring(x.indexOf("..")+2)) + offset;
    
            int y1 = Integer.parseInt(y.substring(2, y.indexOf(".."))) + offset;
            int y2 = Integer.parseInt(y.substring(y.indexOf("..")+2)) + offset;
    
            int z1 = Integer.parseInt(z.substring(2, z.indexOf(".."))) + offset;
            int z2 = Integer.parseInt(z.substring(z.indexOf("..")+2)) + offset;
            
            if (x1 > 100 || x1 < 0) {
                break;
            }
            
            if (onOff.equals("on")) {
                
                for (int xx = x1; xx <= x2; xx++){
                    for (int yy = y1; yy <= y2; yy++){
                        for (int zz = z1; zz <= z2; zz++){
                            cubes[xx][yy][zz] = 1;
                        }
                    }   
                }
                
            } else {
                for (int xx = x1; xx <= x2; xx++){
                    for (int yy = y1; yy <= y2; yy++){
                        for (int zz = z1; zz <= z2; zz++){
                            cubes[xx][yy][zz] = 0;
                        }
                    }
                }
            }
        }
    
        int count = 0;
        
        for (int xx = 0; xx < 101; xx++){
            for (int yy = 0; yy < 101; yy++){
                for (int zz = 0; zz < 101; zz++){
                    count += cubes[xx][yy][zz];
                }
            }
        }
    
        System.out.println(count);
    }
    
    public static void b() throws Exception {
        
        
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}