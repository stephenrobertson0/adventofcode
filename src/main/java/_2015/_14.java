package _2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class _14 {

    private static void a() throws Exception {

        int seconds = 2503;

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2015/input/input14.txt"));

        int max = 0;

        while (true) {

            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String name = line.split(" ")[0];
            int speed = Integer.parseInt(line.split(" ")[3]);
            int distance = Integer.parseInt(line.split(" ")[6]);
            int rest = Integer.parseInt(line.split(" ")[13]);

            int total = seconds / (distance + rest) * distance * speed;
            int remainder = seconds % (distance + rest);
            total += Math.min(remainder, distance) * speed;

            if (total > max) {
                max = total;
            }

        }

        System.out.println(max);

    }

    private static class Deer {

        private int speed;
        private int distance;
        private int rest;
        private int travelled;
        private int score;

        public Deer(int speed, int distance, int rest, int travelled, int score) {
            this.speed = speed;
            this.distance = distance;
            this.rest = rest;
            this.travelled = travelled;
            this.score = score;
        }

        public int getSpeed() {
            return speed;
        }

        public int getDistance() {
            return distance;
        }

        public int getRest() {
            return rest;
        }

        public int getTravelled() {
            return travelled;
        }

        public int getScore() {
            return score;
        }

        public void setTravelled(int travelled) {
            this.travelled = travelled;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

    private static void b() throws Exception {

        int seconds = 2503;

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2015/input/input14.txt"));

        List<Deer> deers = new ArrayList<>();

        while (true) {

            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String name = line.split(" ")[0];
            int speed = Integer.parseInt(line.split(" ")[3]);
            int distance = Integer.parseInt(line.split(" ")[6]);
            int rest = Integer.parseInt(line.split(" ")[13]);

            deers.add(new Deer(speed, distance, rest, 0, 0));

        }

        for (int j = 0; j < seconds; j++) {

            for (int k = 0; k < deers.size(); k++) {

                Deer deer = deers.get(k);

                if (j % (deer.getDistance() + deer.getRest()) < deer.getDistance()) {
                    deer.setTravelled(deer.getTravelled() + deer.getSpeed());
                }

            }

            int max = 0;

            for (int k = 0; k < deers.size(); k++) {

                Deer deer = deers.get(k);

                if (deer.getTravelled() > max) {
                    max = deer.getTravelled();
                }

            }

            for (int k = 0; k < deers.size(); k++) {

                Deer deer = deers.get(k);

                if (deer.getTravelled() == max) {
                    deer.setScore(deer.getScore()+1);
                }

            }

        }

        int max = 0;

        for (int k = 0; k < deers.size(); k++) {

            Deer deer = deers.get(k);

            if (deer.getScore() > max) {
                max = deer.getScore();
            }

        }

        System.out.println(max);

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }

}