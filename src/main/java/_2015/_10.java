package _2015;

public class _10 {

    private static String getResult(int iterations) {
        String input = "1321131112";

        for (int k = 0; k < iterations; k++) {

            StringBuilder output = new StringBuilder();

            char lastChar = input.charAt(0);
            int count = 1;

            input += " ";

            for (int j = 1; j < input.length(); j++) {
                char c = input.charAt(j);

                if (lastChar == c) {
                    count++;
                } else {
                    output.append("" + count + lastChar);
                    count = 1;
                }

                lastChar = c;
            }

            input = output.toString();
        }
        return input;
    }

    private static void a() throws Exception {

        String input = getResult(40);

        System.out.println(input.length());

    }

    private static void b() throws Exception {
        String input = getResult(50);

        System.out.println(input.length());
    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }

}