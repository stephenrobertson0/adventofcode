package _2025;

import java.io.BufferedReader;
import java.io.FileReader;

public class _1 {
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input1.txt"));

        int count = 0;

        int pos = 50;

        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }

            char dir = line.charAt(0);
            int num = Integer.parseInt(line.substring(1));

            if (dir == 'L') {
                pos -= num;
            } else {
                pos += num;
            }

            while (pos < 0) {
                pos += 100;
            }

            while (pos > 99) {
                pos -= 100;
            }

            if (pos == 0) {
                count++;
            }
        }


        System.out.println(count);
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input1.txt"));

        int count = 0;

        int pos = 50;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            char dir = line.charAt(0);
            int num = Integer.parseInt(line.substring(1));

            if (dir == 'L') {
                for (int j = 0; j < num; j++) {
                    pos -= 1;
                    if (pos < 0) {
                        pos = 99;
                    }
                    if (pos == 0) {
                        count++;
                    }
                }
            } else {
                for (int j = 0; j < num; j++) {
                    pos += 1;
                    if (pos > 99) {
                        pos = 0;
                    }
                    if (pos == 0) {
                        count++;
                    }
                }
            }
        }

        System.out.println(count);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
