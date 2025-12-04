package _2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;


public class _2 {
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input2.txt"));

        final String line = fileReader.readLine();

        List<String> list = Arrays.asList(line.split(","));

        //System.out.println(list);

        long total = 0;

        for (String s : list) {
            long num1 = Long.parseLong(s.split("-")[0]);
            long num2 = Long.parseLong(s.split("-")[1]);

            for (long j = num1; j <= num2; j++) {

                String num = ""+j;

                String s1 = num.substring(0, num.length()/2);
                String s2 = num.substring(num.length()/2);

                //System.out.println(num);
                //System.out.println(s1);
                //System.out.println(s2);

                if (s1.equals(s2)) {
                    total += j;
                }

            }

        }

        System.out.println(total);
    }

    private static boolean sequenceRepeated(String seq, int length) {

        if (seq.length() % length != 0) {
            return false;
        }

        //System.out.println(seq);
        //System.out.println(length);

        String match = seq.substring(0, length);

        for (int j = 1; j < seq.length()/length; j++) {
            String s2 = seq.substring(j*length, j*length+length);

            if (!s2.equals(match)) {
                return false;
            }
        }

        return true;
    }

    private static boolean anySequenceRepeated(String seq) {
        for (int j = 1; j <= seq.length()/2; j++) {
            if (sequenceRepeated(seq, j)) {
                return true;
            }
        }

        return false;
    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input2.txt"));

        final String line = fileReader.readLine();

        List<String> list = Arrays.asList(line.split(","));

        long total = 0;

        for (String s : list) {
            long num1 = Long.parseLong(s.split("-")[0]);
            long num2 = Long.parseLong(s.split("-")[1]);

            for (long j = num1; j <= num2; j++) {
                if (anySequenceRepeated(""+j)) {
                    total += j;
                }
            }

        }

        System.out.println(total);


        //System.out.println(sequenceRepeated("1212", 2));
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
