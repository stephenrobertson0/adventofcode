package _2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _5 {

    private record Range(long start, long end) {

    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input5.txt"));

        List<Range> ranges = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line.isEmpty()) {
                break;
            }

            long start = Long.parseLong(line.split("-")[0]);
            long end = Long.parseLong(line.split("-")[1]);

            ranges.add(new Range(start, end));
        }

        List<Long> ingredients = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            ingredients.add(Long.parseLong(line));
        }

        int count = 0;

        for (int j = 0; j < ingredients.size(); j++) {

            long ingredient = ingredients.get(j);

            boolean fresh = false;

            for (int k = 0; k < ranges.size(); k++) {
                if (ingredient >= ranges.get(k).start && ingredient <= ranges.get(k).end) {
                    fresh = true;
                    break;
                }
            }

            if (fresh) {
                count++;
            }
        }

        System.out.println(count);

    }

    private record Marker(long pos, boolean start) {}

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input5.txt"));

        List<Marker> markers = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line.isEmpty()) {
                break;
            }

            long start = Long.parseLong(line.split("-")[0]);
            long end = Long.parseLong(line.split("-")[1]);

            markers.add(new Marker(start, true));
            markers.add(new Marker(end, false));
        }

        markers.sort((x,y) -> {
            if (x.pos > y.pos) {
                return 1;
            }

            if (x.pos < y.pos) {
                return -1;
            }

            if (x.start) {
                return -1;
            }

            return 0;

        });

        long count = 0;
        int openCount = 0;
        long lastStart = 0;

        for (int k = 0; k < markers.size(); k++) {

            if (markers.get(k).start) {
                if (openCount == 0) {
                    lastStart = markers.get(k).pos;
                }
                openCount++;
            } else {
                openCount--;
            }

            if (openCount == 0) {
                count += markers.get(k).pos - lastStart + 1;
            }
        }

        System.out.println(count);

        // 344323629240739 wrong
        // 344323629240733

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
