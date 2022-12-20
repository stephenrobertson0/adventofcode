package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class _20 {
    
    private static class Number {
        
        long value;
        
        public Number(long value) {
            this.value = value;
        }
        
        @Override
        public String toString() {
            return "Number{" +
                    "value=" + value +
                    '}';
        }
    }
    
    
    private static Map<Number, Integer> indexMap = new HashMap<>();
    
    private static int getNewIndex(int index, long moveAmount, int listSize) {
        int newIndex = (int)((index + moveAmount + (listSize - 1) * 10000000000000L) % (listSize - 1));
        
        return newIndex;
    }
    
    private static void updateList(List<Number> list, int oldIndex, int newIndex) {
        Number number = list.remove(oldIndex);
    
        list.add(newIndex, number);
    }
    
    public static void a() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input20.txt"));
        
        List<Number> originalList = new ArrayList<>();
        List<Number> updatedList = new ArrayList<>();
        
        int index = 0;
        
        while (true) {
            
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            Number number = new Number(Integer.parseInt(line));
            originalList.add(number);
            updatedList.add(number);
            indexMap.put(number, index);
        }
        
        for (Number number : originalList) {
            
            Integer i = indexMap.get(number);
            
            int newIndex = getNewIndex(i, number.value, updatedList.size());
            
            updateList(updatedList, i, newIndex);
            
            for (int j = 0; j < updatedList.size(); j++) {
                indexMap.put(updatedList.get(j), j);
            }
            
            //System.out.println(updatedList);
        }
        
        int zeroIndex = -1;
        
        for (Number number : updatedList) {
            if (number.value == 0) {
                zeroIndex = indexMap.get(number);
            }
        }
        
        //System.out.println(zeroIndex);
        
        System.out.println(updatedList.get((zeroIndex + 1000) % updatedList.size()).value + updatedList.get(
                (zeroIndex + 2000) % updatedList.size()).value + updatedList.get(
                (zeroIndex + 3000) % updatedList.size()).value);
    }
    
    public static void b() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input20.txt"));
        
        List<Number> originalList = new ArrayList<>();
        List<Number> updatedList = new ArrayList<>();
        
        int index = 0;
        
        while (true) {
            
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            Number number = new Number(Integer.parseInt(line) * 811589153L);
            originalList.add(number);
            updatedList.add(number);
            indexMap.put(number, index);
        }
        
        for (int k = 0; k < 10; k++) {
            for (Number number : originalList) {
        
                Integer i = indexMap.get(number);
        
                int newIndex = getNewIndex(i, number.value, updatedList.size());
        
                updateList(updatedList, i, newIndex);
        
                for (int j = 0; j < updatedList.size(); j++) {
                    indexMap.put(updatedList.get(j), j);
                }
        
                //System.out.println(updatedList);
            }
        }
        
        int zeroIndex = -1;
        
        for (Number number : updatedList) {
            if (number.value == 0) {
                zeroIndex = indexMap.get(number);
            }
        }
        
        //System.out.println(zeroIndex);
        
        System.out.println(updatedList.get((zeroIndex + 1000) % updatedList.size()).value + updatedList.get(
                (zeroIndex + 2000) % updatedList.size()).value + updatedList.get(
                (zeroIndex + 3000) % updatedList.size()).value);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
        
    }
}
