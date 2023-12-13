package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _13 {

    private static int findHorizontalReflection(List<String> mirror) {

        for (int j = 0; j < mirror.size() - 1; j++) {

            int start1 = j;
            int start2 = j+1;

            boolean reflect = mirror.get(start1).equals(mirror.get(start2));

            while (reflect) {
                start1--;
                start2++;

                if (start1 < 0 || start2 >= mirror.size()) {
                    break;
                }

                reflect = mirror.get(start1).equals(mirror.get(start2));
            }

            if (reflect) {
                return j;
            }

        }

        return -1;

    }

    private static boolean isEqualOutByOne(String s1, String s2) {

        int count = 0;

        for (int j = 0; j < s1.length(); j++) {
            if (s1.charAt(j) != s2.charAt(j)) {
                count++;
            }
            if (count > 1) {
                return false;
            }
        }

        return count == 1;

    }

    private static int findHorizontalReflectionWithSmudge(List<String> mirror, int exclude) {

        for (int j = 0; j < mirror.size() - 1; j++) {

            if (j == exclude) {
                continue;
            }

            int start1 = j;
            int start2 = j+1;

            boolean reflect = true;
            boolean smudgeFound = false;

            while (reflect) {
                if (start1 < 0 || start2 >= mirror.size()) {
                    break;
                }

                //System.out.println("Checking: " + mirror.get(start1) + " and " + mirror.get(start2));
                //System.out.println("Smudge found: " + smudgeFound);

                if (!smudgeFound && isEqualOutByOne(mirror.get(start1), mirror.get(start2))) {
                    //System.out.println("Is equal out by one");
                    reflect = true;
                    smudgeFound = true;
                } else {
                    reflect = mirror.get(start1).equals(mirror.get(start2));
                }

                start1--;
                start2++;
            }

            if (reflect) {
                return j;
            }

        }

        return -1;

    }

    private static List<String> invert(List<String> mirror) {

        List<String> result = new ArrayList<>();

        for (int j = 0; j < mirror.size(); j++) {

            String line = mirror.get(j);

            for (int k = 0; k < line.length(); k++) {

                if (j == 0) {
                    result.add(new String());
                }

                result.set(k, result.get(k) + line.charAt(k));
            }
        }

        return result;

    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input13.txt"));

        List<List<String>> mirrors = new ArrayList<>();

        List<String> mirror = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                mirrors.add(mirror);
                break;
            }

            if (line.isEmpty()) {
                mirrors.add(mirror);
                mirror = new ArrayList<>();
                continue;
            }

            mirror.add(line);

        }

        int total = 0;

        for (List<String> m : mirrors) {
            int horizontal = findHorizontalReflection(m);
            int vertical = findHorizontalReflection(invert(m));

            if (horizontal != -1) {
                total += 100 * (horizontal+1);
            }

            if (vertical != -1) {
                total += vertical+1;
            }

        }

        System.out.println(total);

    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input13.txt"));

        List<List<String>> mirrors = new ArrayList<>();

        List<String> mirror = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                mirrors.add(mirror);
                break;
            }

            if (line.isEmpty()) {
                mirrors.add(mirror);
                mirror = new ArrayList<>();
                continue;
            }

            mirror.add(line);

        }

        int total = 0;

        for (List<String> m : mirrors) {
            int horizontal = findHorizontalReflection(m);
            int horizontalWithSmudge = findHorizontalReflectionWithSmudge(m, horizontal);

            /*
            for (String s : m) {
                System.out.println(s);
            }
            System.out.println();

            System.out.println("horizontal: " + horizontal);
            System.out.println("horizontal smudge: " + horizontalWithSmudge);
            System.out.println();
            System.out.println();
            */

            if (horizontalWithSmudge != -1 && horizontalWithSmudge != horizontal) {

                total += 100 * (horizontalWithSmudge+1);
                continue;
            }

            List<String> invert = invert(m);
            int vertical = findHorizontalReflection(invert);
            int verticalWithSmudge = findHorizontalReflectionWithSmudge(invert, vertical);

            /*
            for (String s : invert) {
                System.out.println(s);
            }
            System.out.println();

            System.out.println("vertical: " + vertical);
            System.out.println("vertical smudge: " + verticalWithSmudge);
            System.out.println();
            System.out.println();
            */

            if (verticalWithSmudge != -1 && verticalWithSmudge != vertical) {

                total += verticalWithSmudge+1;
            }

        }

        System.out.println(total);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
