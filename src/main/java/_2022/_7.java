package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class _7 {
    
    private abstract static class FSO {
        
        protected Long size;
        protected String name;
    
        public abstract long determineSize();
        
    }
    
    private static class Dir extends FSO {
        
        Dir parent;
        
        private List<FSO> contents = new ArrayList<>();
        
        public long determineSize() {
            if (size != null) {
                return size;
            } else {
                
                size = contents.stream().map(v->v.determineSize()).reduce(0L, (x,y)->x+y);
                
                return size;
            }
        }
        
    }
    
    private static class File extends FSO {
        public long determineSize() {
            return size;
        }
    }
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input7.txt"));
    
        Set<Dir> allDirs = new HashSet<>();
        
        Dir root = new Dir();
        
        allDirs.add(root);
        
        fileReader.readLine();
        
        Dir currentDir = root;
        
        while (true) {
        
            final String line = fileReader.readLine();
    
            if (line == null) {
                break;
            }
            
            if ("$ ls".equals(line)) {
                continue;
            }
    
            if (line.startsWith("$ cd")) {
                String dirName = line.substring(5);
    
                if ("..".equals(dirName)) {
                    currentDir = currentDir.parent;
                } else {
                    for (int j = 0; j < currentDir.contents.size(); j++) {
                        if (dirName.equals(currentDir.contents.get(j).name)) {
                            currentDir = (Dir)currentDir.contents.get(j);
                        }
                    }
                }
                
                //System.out.println("Switching to dir: " + dirName);
            } else {
    
                if (line.startsWith("dir")) {
                    Dir dir = new Dir();
                    dir.parent = currentDir;
                    dir.name = line.substring(4);
                    currentDir.contents.add(dir);
                    allDirs.add(dir);
                } else {
                    
                    String[] split = line.split(" ");
                    
                    long size = Long.parseLong(split[0]);
                    
                    File file = new File();
                    file.name = split[1];
                    file.size = size;
    
                    currentDir.contents.add(file);
                }
                
            }
        }
        
        root.determineSize();
        
        long totalSize = 0;
        
        for (Dir dir : allDirs) {
            
            if (dir.size <= 100000) {
                totalSize += dir.size;
            }
        }
    
        System.out.println(totalSize);
        
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input7.txt"));
    
        Set<Dir> allDirs = new HashSet<>();
    
        Dir root = new Dir();
    
        allDirs.add(root);
    
        fileReader.readLine();
    
        Dir currentDir = root;
        
        long totalSpaceTaken = 0;
    
        while (true) {
        
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            if ("$ ls".equals(line)) {
                continue;
            }
        
            if (line.startsWith("$ cd")) {
                String dirName = line.substring(5);
            
                if ("..".equals(dirName)) {
                    currentDir = currentDir.parent;
                } else {
                    for (int j = 0; j < currentDir.contents.size(); j++) {
                        if (dirName.equals(currentDir.contents.get(j).name)) {
                            currentDir = (Dir)currentDir.contents.get(j);
                        }
                    }
                }
            
                //System.out.println("Switching to dir: " + dirName);
            } else {
            
                if (line.startsWith("dir")) {
                    Dir dir = new Dir();
                    dir.parent = currentDir;
                    dir.name = line.substring(4);
                    currentDir.contents.add(dir);
                    allDirs.add(dir);
                } else {
                
                    String[] split = line.split(" ");
                
                    long size = Long.parseLong(split[0]);
                
                    File file = new File();
                    file.name = split[1];
                    file.size = size;
                
                    currentDir.contents.add(file);
                    
                    totalSpaceTaken += size;
                }
            
            }
        }
    
        root.determineSize();
    
        //System.out.println(totalSpaceTaken);
        
        long goalDeleteSize = totalSpaceTaken - 40_000_000L;
    
        long minDifference = Integer.MAX_VALUE;
        long selectedSize = 0;
    
        for (Dir dir : allDirs) {
        
            if (dir.size - goalDeleteSize > 0 && dir.size - goalDeleteSize < minDifference) {
                minDifference = dir.size - goalDeleteSize;
                selectedSize = dir.size;
            }
        }
    
        System.out.println(selectedSize);
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
