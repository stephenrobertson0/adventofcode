package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class _20 {
    
    private static class Number {
        int value;
    
        public Number(int value) {
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
    
            Integer i = indexMap.remove(number);
            updatedList.remove((int)i);
            
            int newIndex = ((i + number.value + 1 + (originalList.size() - 1) * 10000)) % (originalList.size() - 1);
            
            if (newIndex > i) {
                newIndex -= 1;
            }
            
            updatedList.add(newIndex, number);
            indexMap.put(number, newIndex);
            
            for (int j = newIndex; j < updatedList.size(); j++) {
                indexMap.put(updatedList.get(j), j);
            }
    
            System.out.println(updatedList);
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        //b();
    }
}
