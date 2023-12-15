package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


public class _15 {

    private static int hashCode(String str) {

        int value = 0;

        for (char c : str.toCharArray()) {
            value += c;
            value *= 17;
            value %= 256;
        }

        return value;

    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input15.txt"));

        final String line = fileReader.readLine();

        List<String> strings = Arrays.stream(line.split(",")).collect(Collectors.toList());

        long total = 0;

        for (String str : strings) {
            total += hashCode(str);
        }

        System.out.println(total);

    }

    private static class Box {
        private String label;
        private int focalLength;

        public Box(String label, int focalLength) {
            this.label = label;
            this.focalLength = focalLength;
        }

        public int getFocalLength() {
            return focalLength;
        }

        public void setFocalLength(int focalLength) {
            this.focalLength = focalLength;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Box box = (Box)o;
            return Objects.equals(label, box.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(label);
        }

        @Override
        public String toString() {
            return "Box{" +
                    "label='" + label + '\'' +
                    ", focalLength=" + focalLength +
                    '}';
        }
    }

    private static int getTotalPower(List<Box> boxes, int boxNumber) {

        int total = 0;

        for (int j = 0; j < boxes.size(); j++) {
            Box box = boxes.get(j);
            total += (j + 1) * (boxNumber + 1) * box.getFocalLength();
        }

        return total;
    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input15.txt"));

        final String line = fileReader.readLine();

        List<String> strings = Arrays.stream(line.split(",")).collect(Collectors.toList());

        Map<Integer, List<Box>> boxes = new HashMap<>();

        for (int j = 0; j < 256; j++) {
            boxes.put(j, new ArrayList<>());
        }

        for (String str : strings) {

            if (str.contains("-")) {
                String label = str.substring(0, str.length() - 1);
                int hash = hashCode(label);
                boxes.get(hash).remove(new Box(label, 0));

            } else {
                String label = str.substring(0, str.indexOf("="));
                int focalLength = Integer.parseInt(str.substring(str.indexOf("=") + 1));
                int hash = hashCode(label);
                List<Box> currentBoxes = boxes.get(hash);
                Box box = new Box(label, focalLength);

                if (!currentBoxes.contains(box)) {
                    currentBoxes.add(box);
                } else {
                    currentBoxes.get(currentBoxes.indexOf(box)).setFocalLength(focalLength);
                }
            }
        }

        long total = 0;

        for (int j = 0; j < 256; j++) {
            total += getTotalPower(boxes.get(j), j);
        }

        System.out.println(total);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
