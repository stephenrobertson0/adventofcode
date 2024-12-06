package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class _5 {

    private static boolean isListValid(List<Integer> list, List<int[]> pairs) {

        Map<Integer, Integer> indexs = new HashMap<>();

        for (int j = 0; j < list.size(); j++) {
            indexs.put(list.get(j), j);
        }

        for (int[] pair : pairs) {
            int before = pair[0];
            int after = pair[1];

            Integer beforeIndex = indexs.get(before);
            Integer afterIndex = indexs.get(after);

            if (beforeIndex != null && afterIndex != null && beforeIndex > afterIndex) {
                return false;
            }

        }

        return true;

    }

    private static List<Integer> correctListOrder(List<Integer> list, List<int[]> pairs) {

        Map<Integer, Integer> indexs = new HashMap<>();

        for (int[] pair : pairs) {

            for (int j = 0; j < list.size(); j++) {
                indexs.put(list.get(j), j);
            }

            int before = pair[0];
            int after = pair[1];

            Integer beforeIndex = indexs.get(before);
            Integer afterIndex = indexs.get(after);

            if (beforeIndex != null && afterIndex != null && beforeIndex > afterIndex) {
                List<Integer> newList = new ArrayList<>();

                for (int num : list) {
                    if (num != after) {
                        if (num == before) {
                            newList.add(before);
                            newList.add(after);
                        } else {
                            newList.add(num);
                        }
                    }
                }

                list = newList;
            }

        }

        return list;

    }

    private record Input (List<int[]> pairs,  List<List<Integer>> lists){}

    private static Input parseInput() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input5.txt"));

        List<int[]> pairs = new ArrayList<>();

        while (true) {
            String line = fileReader.readLine();

            if (line.isEmpty()) {
                break;
            }

            String[] pairStr = line.split("\\|");

            int[] pair = new int[2];
            pair[0] = Integer.parseInt(pairStr[0]);
            pair[1] = Integer.parseInt(pairStr[1]);

            pairs.add(pair);
        }

        List<List<Integer>> lists = new ArrayList<>();

        while (true) {
            String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String[] listStr = line.split(",");

            List<Integer> list = new ArrayList<>();

            for (String num : listStr) {
                list.add(Integer.parseInt(num));
            }

            lists.add(list);
        }

        return new Input(pairs, lists);
    }

    public static void a() throws Exception {

        Input input = parseInput();
        List<int[]> pairs = input.pairs();
        List<List<Integer>> lists = input.lists();

        long total = 0;

        for (List<Integer> list : lists) {

            if (isListValid(list, pairs)) {
                total += list.get(list.size()/2);
            }

        }

        System.out.println(total);

    }
    
    public static void b() throws Exception {

        Input input = parseInput();
        List<int[]> pairs = input.pairs();
        List<List<Integer>> lists = input.lists();

        long total = 0;

        for (List<Integer> list : lists) {

            if (!isListValid(list, pairs)) {

                while (!isListValid(list, pairs)) {
                    list = correctListOrder(list, pairs);
                }

                total += list.get(list.size()/2);
            }

        }

        System.out.println(total);

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
