package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class _6 {

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input6.txt"));

        List<Integer> times = null;
        List<Integer> distances = null;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            if (line.startsWith("Time:")) {
                String timesStr = line.substring(6);
                times = Arrays.stream(timesStr.trim().split(" +")).map(v -> Integer.parseInt(v)).collect(Collectors.toList());
            }

            if (line.startsWith("Distance:")) {
                String distanceStr = line.substring(10);
                distances = Arrays.stream(distanceStr.trim().split(" +")).map(v -> Integer.parseInt(v)).collect(Collectors.toList());
            }

        }

        long result = 1;

        for (int k = 0; k < times.size(); k++) {

            int count = 0;

            int time = times.get(k);

            for (int j = 0; j < time; j++) {
                int distanceTravelled = j * (time - j);

                if (distanceTravelled > distances.get(k)) {
                    count++;
                }
            }

            result *= count;
        }

        System.out.println(result);

    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input6.txt"));

        String timeString = null;
        String distanceString = null;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            if (line.startsWith("Time:")) {
                String timesStr = line.substring(6);
                timeString = Arrays.stream(timesStr.trim().split(" +")).collect(Collectors.joining());
            }

            if (line.startsWith("Distance:")) {
                String distanceStr = line.substring(10);
                distanceString = Arrays.stream(distanceStr.trim().split(" +")).collect(Collectors.joining());
            }

        }

        int count = 0;

        long time = Long.parseLong(timeString);
        long distance = Long.parseLong(distanceString);

        for (int j = 0; j < time; j++) {
            long distanceTravelled = j * (time - j);

            if (distanceTravelled > distance) {
                count++;
            }
        }

        System.out.println(count);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
