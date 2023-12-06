package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;


public class _2 {
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input2.txt"));

        int gameNum = 1;
        int result = 0;
        Map<String, Integer> counts = new HashMap<>();
        counts.put("red", 12);
        counts.put("green", 13);
        counts.put("blue", 14);

        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }

            String endLine = line.substring(line.indexOf(":")+2);

            //System.out.println(endLine);

            String[] sets = endLine.split(";");

            boolean possible = true;

            for (String set : sets) {
                String[] cubes = set.split(",");

                for (String cubeUntrimmed : cubes) {

                    String cube = cubeUntrimmed.trim();

                    int count = Integer.parseInt(cube.substring(0, cube.indexOf(' ')));
                    String type = cube.substring(cube.indexOf(" ") + 1);

                    System.out.println(count);
                    System.out.println(type);

                    if (count > counts.get(type)) {
                        possible = false;
                        break;
                    }
                }

            }

            if (possible) {
                result += gameNum;
            }

            gameNum++;

        }

        System.out.println(result);

    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input2.txt"));

        int result = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String endLine = line.substring(line.indexOf(":")+2);

            //System.out.println(endLine);

            Map<String, Integer> maximums = new HashMap<>();

            String[] sets = endLine.split(";");

            for (String set : sets) {
                String[] cubes = set.split(",");

                for (String cubeUntrimmed : cubes) {

                    String cube = cubeUntrimmed.trim();

                    int count = Integer.parseInt(cube.substring(0, cube.indexOf(' ')));
                    String type = cube.substring(cube.indexOf(" ") + 1);

                    Integer max = maximums.get(type);
                    if (max == null || count > max) {
                        maximums.put(type, count);
                    }
                }

            }

            System.out.println(maximums);

            result += maximums.get("red") * maximums.get("blue") * maximums.get("green");

        }

        System.out.println(result);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
