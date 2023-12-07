package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class _7 {

    private static class Hand {
        String cards;
        int bid;

        public Hand(String cards, int bid) {
            this.cards = cards;
            this.bid = bid;
        }

        public String getCards() {
            return cards;
        }

        public int getBid() {
            return bid;
        }

        private Map<Character, Integer> getCardCounts() {
            Map<Character, Integer> counts = new HashMap<>();

            for (int j = 0; j < 5; j++) {
                char c = cards.charAt(j);

                Integer count = counts.get(c);

                if (count == null) {
                    counts.put(c, 1);
                } else {
                    counts.put(c, count+1);
                }
            }
            return counts;
        }

        public boolean isFive() {

            Map<Character, Integer> counts = getCardCounts();

            for (int count : counts.values()) {
                if (count == 5) {
                    return true;
                }
            }

            return false;
        }

        public boolean isFour() {

            Map<Character, Integer> counts = getCardCounts();

            for (int count : counts.values()) {
                if (count == 4) {
                    return true;
                }
            }

            return false;
        }

        public boolean isFull() {

            Map<Character, Integer> counts = getCardCounts();

            boolean two = false;
            boolean three = false;

            for (int count : counts.values()) {
                if (count == 3) {
                    three = true;
                }

                if (count == 2) {
                    two = true;
                }
            }

            return two && three;
        }

        public boolean isThree() {

            Map<Character, Integer> counts = getCardCounts();

            for (int count : counts.values()) {
                if (count == 3) {
                    return true;
                }
            }

            return false;
        }

        public boolean isTwoPair() {

            Map<Character, Integer> counts = getCardCounts();

            int twoCount = 0;

            for (int count : counts.values()) {
                if (count == 2) {
                    twoCount++;
                }
            }

            return twoCount == 2;
        }

        public boolean isPair() {

            Map<Character, Integer> counts = getCardCounts();

            int twoCount = 0;

            for (int count : counts.values()) {
                if (count == 2) {
                    twoCount++;
                }
            }

            return twoCount == 1;
        }

        public boolean isFiveWithJ() {

            Map<Character, Integer> counts = getCardCounts();

            int jokerCount = Optional.ofNullable(counts.get('J')).orElse(0);

            for (Map.Entry<Character, Integer> count : counts.entrySet()) {

                int cardAndJokerCount = count.getKey() == 'J' ? count.getValue() : count.getValue()+jokerCount;

                if (cardAndJokerCount >= 5) {
                    return true;
                }
            }

            return false;
        }

        public boolean isFourWithJ() {

            Map<Character, Integer> counts = getCardCounts();

            int jokerCount = Optional.ofNullable(counts.get('J')).orElse(0);

            for (Map.Entry<Character, Integer> count : counts.entrySet()) {

                int cardAndJokerCount = count.getKey() == 'J' ? count.getValue() : count.getValue()+jokerCount;

                if (cardAndJokerCount >= 4) {
                    return true;
                }
            }

            return false;
        }

        public boolean isFullWithJ() {

            Map<Character, Integer> counts = getCardCounts();

            boolean two = false;
            boolean three = false;

            int jokerCount = Optional.ofNullable(counts.get('J')).orElse(0);

            Character match = null;

            for (Map.Entry<Character, Integer> count : counts.entrySet()) {

                int cardAndJokerCount = count.getKey() == 'J' ? count.getValue() : count.getValue() + jokerCount;

                if (cardAndJokerCount >= 3) {
                    three = true;

                    jokerCount -= 3 - (count.getKey() == 'J' ? 0 : count.getValue());

                    match = count.getKey();
                }
            }

            if (match != null) {
                counts.remove(match);
            }

            for (Map.Entry<Character, Integer> count : counts.entrySet()) {

                int cardAndJokerCount = count.getKey() == 'J' ? jokerCount : count.getValue()+jokerCount;

                if (cardAndJokerCount >= 2) {
                    two = true;
                }
            }

            return two && three;
        }

        public boolean isThreeWithJ() {

            Map<Character, Integer> counts = getCardCounts();

            int jokerCount = Optional.ofNullable(counts.get('J')).orElse(0);

            for (Map.Entry<Character, Integer> count : counts.entrySet()) {

                int cardAndJokerCount = count.getKey() == 'J' ? count.getValue() : count.getValue()+jokerCount;

                if (cardAndJokerCount >= 3) {
                    return true;
                }
            }

            return false;
        }

        public boolean isTwoPairWithJ() {

            Map<Character, Integer> counts = getCardCounts();

            int jokerCount = Optional.ofNullable(counts.get('J')).orElse(0);

            int twoCount = 0;

            for (Map.Entry<Character, Integer> count : counts.entrySet()) {

                int cardAndJokerCount = count.getKey() == 'J' ? count.getValue() : count.getValue()+jokerCount;

                if (cardAndJokerCount >=2) {
                    twoCount++;
                    jokerCount-= 2- (count.getKey() == 'J' ? 0 : count.getValue());
                }
            }

            return twoCount >= 2;
        }

        public boolean isPairWithJ() {

            Map<Character, Integer> counts = getCardCounts();

            int jokerCount = Optional.ofNullable(counts.get('J')).orElse(0);

            int twoCount = 0;

            for (Map.Entry<Character, Integer> count : counts.entrySet()) {

                int cardAndJokerCount = count.getKey() == 'J' ? count.getValue() : count.getValue()+jokerCount;

                if (cardAndJokerCount >= 2) {
                    twoCount++;
                    jokerCount-= 2- (count.getKey() == 'J' ? 0 : count.getValue());
                }
            }

            return twoCount >= 1;
        }

        @Override
        public String toString() {
            return "Hand{" +
                    "cards='" + cards + '\'' +
                    ", bid=" + bid +
                    '}';
        }
    }

    static Map<Character, Integer> cardToValue = new HashMap<>();

    static {

        cardToValue.put('2', 1);
        cardToValue.put('3', 2);
        cardToValue.put('4', 3);
        cardToValue.put('5', 4);
        cardToValue.put('6', 5);
        cardToValue.put('7', 6);
        cardToValue.put('8', 7);
        cardToValue.put('9', 8);

        cardToValue.put('T', 9);
        cardToValue.put('J', 10);
        cardToValue.put('Q', 11);
        cardToValue.put('K', 12);
        cardToValue.put('A', 13);

    }

    static Map<Character, Integer> cardToValueWithJ = new HashMap<>();

    static {

        cardToValueWithJ.put('J', 1);
        cardToValueWithJ.put('2', 2);
        cardToValueWithJ.put('3', 3);
        cardToValueWithJ.put('4', 4);
        cardToValueWithJ.put('5', 5);
        cardToValueWithJ.put('6', 6);
        cardToValueWithJ.put('7', 7);
        cardToValueWithJ.put('8', 8);
        cardToValueWithJ.put('9', 9);

        cardToValueWithJ.put('T', 10);
        cardToValueWithJ.put('Q', 11);
        cardToValueWithJ.put('K', 12);
        cardToValueWithJ.put('A', 13);

    }

    private static int compareSame(String hand1, String hand2) {

        for (int j = 0; j < 5; j++) {
            int card1 = cardToValue.get(hand1.charAt(j));
            int card2 = cardToValue.get(hand2.charAt(j));

            if (card1 > card2) {
                return 1;
            } else if (card1 < card2) {
                return -1;
            }
        }

        return 0;

    }

    private static int compareSameWithJ(String hand1, String hand2) {

        for (int j = 0; j < 5; j++) {
            int card1 = cardToValueWithJ.get(hand1.charAt(j));
            int card2 = cardToValueWithJ.get(hand2.charAt(j));

            if (card1 > card2) {
                return 1;
            } else if (card1 < card2) {
                return -1;
            }
        }

        return 0;

    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input7.txt"));

        List<Hand> hands = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            hands.add(new Hand(line.split(" ")[0], Integer.parseInt(line.split(" ")[1])));

        }

        hands.sort(new Comparator<Hand>() {

            @Override
            public int compare(Hand o1, Hand o2) {
                if (o1.isFive() || o2.isFive()) {

                    if (o1.isFive() && o2.isFive()) {
                        return compareSame(o1.getCards(), o2.getCards());
                    } else if (o1.isFive()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                if (o1.isFour() || o2.isFour()) {

                    if (o1.isFour() && o2.isFour()) {
                        return compareSame(o1.getCards(), o2.getCards());
                    } else if (o1.isFour()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                if (o1.isFull() || o2.isFull()) {

                    if (o1.isFull() && o2.isFull()) {
                        return compareSame(o1.getCards(), o2.getCards());
                    } else if (o1.isFull()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                if (o1.isThree() || o2.isThree()) {

                    if (o1.isThree() && o2.isThree()) {
                        return compareSame(o1.getCards(), o2.getCards());
                    } else if (o1.isThree()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                if (o1.isTwoPair() || o2.isTwoPair()) {
                    if (o1.isTwoPair() && o2.isTwoPair()) {
                        return compareSame(o1.getCards(), o2.getCards());
                    } else if (o1.isTwoPair()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                if (o1.isPair() || o2.isPair()) {
                    if (o1.isPair() && o2.isPair()) {
                        return compareSame(o1.getCards(), o2.getCards());
                    } else if (o1.isPair()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                return compareSame(o1.getCards(), o2.getCards());
            }
        });

        long total = 0;

        for (int j = 0; j < hands.size(); j++) {

            Hand hand = hands.get(j);

            //System.out.println(hand.getCards());

            total += hand.getBid() * (j+1);
        }

        System.out.println(total);

    }

    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input7.txt"));

        List<Hand> hands = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            hands.add(new Hand(line.split(" ")[0], Integer.parseInt(line.split(" ")[1])));

        }

        hands.sort(new Comparator<Hand>() {

            @Override
            public int compare(Hand o1, Hand o2) {
                if (o1.isFiveWithJ() || o2.isFiveWithJ()) {

                    if (o1.isFiveWithJ() && o2.isFiveWithJ()) {
                        return compareSameWithJ(o1.getCards(), o2.getCards());
                    } else if (o1.isFiveWithJ()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                if (o1.isFourWithJ() || o2.isFourWithJ()) {

                    if (o1.isFourWithJ() && o2.isFourWithJ()) {
                        return compareSameWithJ(o1.getCards(), o2.getCards());
                    } else if (o1.isFourWithJ()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                if (o1.isFullWithJ() || o2.isFullWithJ()) {

                    if (o1.isFullWithJ() && o2.isFullWithJ()) {
                        return compareSameWithJ(o1.getCards(), o2.getCards());
                    } else if (o1.isFullWithJ()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                if (o1.isThreeWithJ() || o2.isThreeWithJ()) {

                    if (o1.isThreeWithJ() && o2.isThreeWithJ()) {
                        return compareSameWithJ(o1.getCards(), o2.getCards());
                    } else if (o1.isThreeWithJ()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                if (o1.isTwoPairWithJ() || o2.isTwoPairWithJ()) {
                    if (o1.isTwoPairWithJ() && o2.isTwoPairWithJ()) {
                        return compareSameWithJ(o1.getCards(), o2.getCards());
                    } else if (o1.isTwoPairWithJ()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                if (o1.isPairWithJ() || o2.isPairWithJ()) {
                    if (o1.isPairWithJ() && o2.isPairWithJ()) {
                        return compareSameWithJ(o1.getCards(), o2.getCards());
                    } else if (o1.isPairWithJ()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }

                return compareSameWithJ(o1.getCards(), o2.getCards());
            }
        });

        long total = 0;

        for (int j = 0; j < hands.size(); j++) {

            Hand hand = hands.get(j);

            //System.out.println(hand.getCards());

            total += hand.getBid() * (j+1);
        }

        System.out.println(total);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
