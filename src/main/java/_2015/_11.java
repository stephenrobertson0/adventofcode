package _2015;

public class _11 {

    private static void increment(char[] chars) {
        int index = 7;

        while (true) {
            chars[index]++;

            if (chars[index] == 'z'+1) {
                chars[index] = 'a';
                index--;
                continue;
            }

            if (chars[index] == 'i' || chars[index] == 'l' || chars[index] == 'o') {
                chars[index]++;
            }

            break;
        }
    }

    private static boolean hasTwoPairs(char[] chars) {
        char match1 = 0;

        for (int j = 0; j < 7; j ++) {
            if (chars[j] == chars[j+1]) {
                match1 = chars[j];
                break;
            }
        }

        if (match1 == 0) {
            return false;
        }

        for (int j = 0; j < 7; j ++) {
            if (chars[j] == chars[j+1] && chars[j] != match1) {
                return true;
            }
        }

        return false;
    }

    private static boolean hasRun(char[] chars) {
        for (int j = 0; j < 6; j ++) {
            if (chars[j] == chars[j+1] -1 && chars[j] == chars[j+2]-2) {
                return true;
            }
        }
        return false;
    }

    private static void a() throws Exception {

        String input = "cqjxjnds";
        char[] chars = input.toCharArray();

        while (true) {
            increment(chars);
            if (hasTwoPairs(chars) && hasRun(chars)) {
                System.out.println(new String(chars));
                break;
            }
        }

    }

    private static void b() throws Exception {
        String input = "cqjxxyzz";
        char[] chars = input.toCharArray();

        while (true) {
            increment(chars);
            if (hasTwoPairs(chars) && hasRun(chars)) {
                System.out.println(new String(chars));
                break;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }

}