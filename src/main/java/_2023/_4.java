package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


public class _4 {

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input4.txt"));

        int score = 0;

        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }

            String numbers = line.split(":")[1];

            String cardNumbers = numbers.split("\\|")[0].trim();
            String winningNumbers = numbers.split("\\|")[1].trim();

            Set<Integer> cardNums = Arrays.stream(cardNumbers.split(" +")).map(v->Integer.parseInt(v)).collect(Collectors.toSet());
            Set<Integer> winningNums = Arrays.stream(winningNumbers.split(" +")).map(v->Integer.parseInt(v)).collect(Collectors.toSet());

            int count = 0;

            for (Integer winningNum : winningNums) {
                if (cardNums.contains(winningNum)) {
                    count++;
                }
            }

            if (count != 0) {
                score += Math.pow(2, count - 1);
            }
        }

        System.out.println(score);

    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input4.txt"));

        int[] cardCounts = new int[215];

        for (int j = 0; j < 215; j++) {
            cardCounts[j] = 1;
        }

        int cardNum = 0;

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String numbers = line.split(":")[1];

            String cardNumbers = numbers.split("\\|")[0].trim();
            String winningNumbers = numbers.split("\\|")[1].trim();

            Set<Integer> cardNums = Arrays.stream(cardNumbers.split(" +")).map(v->Integer.parseInt(v)).collect(Collectors.toSet());
            Set<Integer> winningNums = Arrays.stream(winningNumbers.split(" +")).map(v->Integer.parseInt(v)).collect(Collectors.toSet());

            int count = 0;

            for (Integer winningNum : winningNums) {
                if (cardNums.contains(winningNum)) {
                    count++;
                }
            }

            if (count != 0) {
                for (int j = cardNum+1; j < cardNum+1+count; j++) {
                    if (j < 215) {
                        cardCounts[j] += cardCounts[cardNum];
                    }
                }
            }

            cardNum++;

        }

        int total = 0;

        for (int j =0; j < 215; j++) {
            total += cardCounts[j];
        }

        System.out.println(total);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
