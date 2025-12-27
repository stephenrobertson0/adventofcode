package _2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class _12 {

    private record Condition(int xSize, int ySize, List<Integer> counts) {

    }

    private static void printBlock(char[][] block) {
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                System.out.print(block[i][j]);
            }
            System.out.println();
        }
    }

    public static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input12.txt"));

        List<char[][]> blocks = new ArrayList<>();
        List<Integer> sizes = new ArrayList<>();

        for (int j = 0; j < 6; j++) {
            fileReader.readLine();
            final String line1 = fileReader.readLine();
            final String line2 = fileReader.readLine();
            final String line3 = fileReader.readLine();

            char[][] block = new char[3][3];
            List<String> lines = List.of(line1, line2, line3);

            int size = 0;

            for (int m = 0; m < 3; m++) {
                String str = lines.get(m);
                for (int n = 0; n < 3; n++) {
                    block[m][n] = str.charAt(n);
                    if (str.charAt(n) == '#') {
                        size++;
                    }
                }
            }

            blocks.add(block);
            sizes.add(size);

            fileReader.readLine();
        }

        /*for (char[][] block : blocks) {
            printBlock(block);
            System.out.println();
        }*/

        List<Condition> conditions = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String s1 = line.split(": ")[0];
            String s2 = line.split(": ")[1];

            int x = Integer.parseInt(s1.split("x")[0]);
            int y = Integer.parseInt(s1.split("x")[1]);

            List<Integer> counts = Arrays.stream(s2.split(" ")).map(Integer::parseInt).toList();

            conditions.add(new Condition(x, y, counts));
        }

        //System.out.println(conditions);

        int count = 0;

        for (Condition condition : conditions) {

            int area1 = condition.xSize * condition.ySize;

            int totalSize = 0;

            for (int j = 0; j < sizes.size(); j++) {
                totalSize += sizes.get(j) * condition.counts.get(j);
            }

            if (totalSize < area1) {
                count++;
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
