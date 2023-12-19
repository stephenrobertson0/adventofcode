package _2015;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _15 {

    private static class Ingredient {
        private int capacity;
        private int durability;
        private int flavor;
        private int texture;
        private int calories;

        public Ingredient(int capacity, int durability, int flavor, int texture, int calories) {
            this.capacity = capacity;
            this.durability = durability;
            this.flavor = flavor;
            this.texture = texture;
            this.calories = calories;
        }

        public int getCapacity() {
            return capacity;
        }

        public int getDurability() {
            return durability;
        }

        public int getFlavor() {
            return flavor;
        }

        public int getTexture() {
            return texture;
        }

        public int getCalories() {
            return calories;
        }
    }

    private static void a() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2015/input/input15.txt"));

        List<Ingredient> ingredients = new ArrayList<>();

        while (true) {

            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String[] split = line.split(",");

            int capacity = Integer.parseInt(split[0].substring(split[0].indexOf("capacity") + 9));
            int durability = Integer.parseInt(split[1].substring(split[1].indexOf("durability") + 11));
            int flavour = Integer.parseInt(split[2].substring(split[2].indexOf("flavor") + 7));
            int texture = Integer.parseInt(split[3].substring(split[3].indexOf("texture") + 8));
            int calories = Integer.parseInt(split[4].substring(split[4].indexOf("calories") + 9));

            ingredients.add(new Ingredient(capacity, durability, flavour, texture, calories));

        }

        int max = 0;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    for (int m = 0; m < 100; m++) {
                        if (i+j+k+m == 100) {

                            int capacity = 0;
                            int durability = 0;
                            int flavor = 0;
                            int texture = 0;

                            for (int n = 0; n < 4; n++) {

                                int count = 0;

                                if (n == 0) {
                                    count = i;
                                } else if (n == 1) {
                                    count = j;
                                } else if (n == 2) {
                                    count = k;
                                } else if (n == 3) {
                                    count = m;
                                }

                                capacity += ingredients.get(n).getCapacity() * count;
                                durability += ingredients.get(n).getDurability() * count;
                                flavor += ingredients.get(n).getFlavor() * count;
                                texture += ingredients.get(n).getTexture() * count;

                            }

                            int count = Math.max(capacity, 0) * Math.max(durability, 0) * Math.max(flavor, 0) * Math.max(texture, 0);

                            if (count > max) {
                                max = count;
                            }

                        }
                    }
                }
            }
        }

        System.out.println(max);

    }

    private static void b() throws Exception {

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2015/input/input15.txt"));

        List<Ingredient> ingredients = new ArrayList<>();

        while (true) {

            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String[] split = line.split(",");

            int capacity = Integer.parseInt(split[0].substring(split[0].indexOf("capacity") + 9));
            int durability = Integer.parseInt(split[1].substring(split[1].indexOf("durability") + 11));
            int flavour = Integer.parseInt(split[2].substring(split[2].indexOf("flavor") + 7));
            int texture = Integer.parseInt(split[3].substring(split[3].indexOf("texture") + 8));
            int calories = Integer.parseInt(split[4].substring(split[4].indexOf("calories") + 9));

            ingredients.add(new Ingredient(capacity, durability, flavour, texture, calories));

        }

        int max = 0;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    for (int m = 0; m < 100; m++) {
                        if (i+j+k+m == 100) {

                            int capacity = 0;
                            int durability = 0;
                            int flavor = 0;
                            int texture = 0;
                            int calories = 0;

                            for (int n = 0; n < 4; n++) {

                                int count = 0;

                                if (n == 0) {
                                    count = i;
                                } else if (n == 1) {
                                    count = j;
                                } else if (n == 2) {
                                    count = k;
                                } else if (n == 3) {
                                    count = m;
                                }

                                capacity += ingredients.get(n).getCapacity() * count;
                                durability += ingredients.get(n).getDurability() * count;
                                flavor += ingredients.get(n).getFlavor() * count;
                                texture += ingredients.get(n).getTexture() * count;
                                calories += ingredients.get(n).getCalories() * count;

                            }

                            if (calories != 500) {
                                continue;
                            }

                            int count = Math.max(capacity, 0) * Math.max(durability, 0) * Math.max(flavor, 0) * Math.max(texture, 0);

                            if (count > max) {
                                max = count;
                            }

                        }
                    }
                }
            }
        }

        System.out.println(max);

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }

}