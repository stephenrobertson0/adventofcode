package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class _11 {
    
    private interface Item {
        void add(int num);
    
        void multiply(int num);
    
        void square();
    
        boolean divisibleBy(int num);
        
        void endOfInspect();
    }
    
    private static class ItemA implements Item {
        
        private int num;
        
        public ItemA(int num) {
            this.num = num;
        }
    
        @Override
        public void add(int num) {
            this.num += num;
        }
    
        @Override
        public void multiply(int num) {
            this.num *= num;
        }
    
        @Override
        public void square() {
            this.num *= num;
        }
    
        @Override
        public boolean divisibleBy(int num) {
            return this.num % num == 0;
        }
    
        @Override
        public void endOfInspect() {
            num = num / 3;
        }
    }
    
    private static class ItemB implements Item {
        
        private List<Integer> mods = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23);
        private List<Integer> values;
        
        public ItemB(int num) {
            values = new ArrayList<>();
            
            for (Integer mod : mods) {
                values.add(num % mod);
            }
        }
        
        public void add(int num) {
            for (int j = 0; j < mods.size(); j++) {
                values.set(j, (values.get(j) + num) % mods.get(j));
            }
        }
    
        public void multiply(int num) {
            for (int j = 0; j < mods.size(); j++) {
                values.set(j, (values.get(j) * num) % mods.get(j));
            }
        }
    
        public void square() {
            for (int j = 0; j < mods.size(); j++) {
                values.set(j, (values.get(j) * values.get(j)) % mods.get(j));
            }
        }
        
        public boolean divisibleBy(int num) {
            return values.get(mods.indexOf(num)) == 0;
        }
    
        @Override
        public void endOfInspect() {
            // do nothing
        }
    }
    
    
    private static class Monkey {
        
        // 0 = +
        // 1 = *
        // 2 = squared
        int operation;
        
        int operationValue;
        
        int divisibleBy;
        
        List<Item> items;
        
        int trueDestination;
        int falseDestination;
        
        public void performOperation(Item item) {
            if (operation == 0) {
                item.add(operationValue);
            } else if (operation == 1) {
                item.multiply(operationValue);
            } else if (operation == 2) {
                item.square();
            }
            
            item.endOfInspect();
        }
        
        public int getDestination(Item item) {
            
            if (item.divisibleBy(divisibleBy)) {
                return trueDestination;
            } else {
                return falseDestination;
            }
        }
        
        @Override
        public String toString() {
            return "Monkey{" +
                    "operation=" + operation +
                    ", operationValue=" + operationValue +
                    ", divisibleBy=" + divisibleBy +
                    ", items=" + items +
                    ", trueDestination=" + trueDestination +
                    ", falseDestination=" + falseDestination +
                    '}';
        }
    }
    
    private static List<Monkey> parseMonkeys(Function<Integer, Item> itemFunction) throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input11.txt"));
        
        List<Monkey> monkeys = new ArrayList<>();
        
        while (true) {
            
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            Monkey monkey = new Monkey();
            monkeys.add(monkey);
            
            String items = fileReader.readLine();
            String operation = fileReader.readLine();
            String divisible = fileReader.readLine();
            String trueDest = fileReader.readLine();
            String falseDest = fileReader.readLine();
            
            fileReader.readLine();
            
            String itemNums = items.substring(items.indexOf(":") + 2);
            monkey.items = Arrays.stream(itemNums.split(", "))
                    .map(v->Integer.parseInt(v))
                    .map(itemFunction)
                    .collect(Collectors.toList());
            
            String opAndVal = operation.substring(operation.indexOf("old ") + 4);
            
            int op = -1;
            int val = -1;
            
            if (opAndVal.startsWith("+")) {
                op = 0;
            } else if (opAndVal.startsWith("*")) {
                op = 1;
            }
            
            String valS = opAndVal.substring(2);
            
            if (valS.contains("old")) {
                op = 2;
            } else {
                val = Integer.parseInt(valS);
            }
            
            monkey.operation = op;
            monkey.operationValue = val;
            
            monkey.divisibleBy = Integer.parseInt(divisible.substring(divisible.lastIndexOf(" ") + 1));
            monkey.trueDestination = Integer.parseInt(trueDest.substring(trueDest.lastIndexOf(" ") + 1));
            monkey.falseDestination = Integer.parseInt(falseDest.substring(falseDest.lastIndexOf(" ") + 1));
        }
        
        return monkeys;
    }
    
    public static void a() throws Exception {
        List<Monkey> monkeys = parseMonkeys(v->new ItemA(v));
        
        Map<Integer, Integer> inspectCount = new HashMap<>();
        
        for (int m = 0; m < 20; m++) {
            
            for (int j = 0; j < monkeys.size(); j++) {
                
                Monkey monkey = monkeys.get(j);
                
                for (int k = 0; k < monkey.items.size(); k++) {
                    
                    Integer insCount = inspectCount.get(j);
                    if (insCount == null) {
                        insCount = 1;
                        inspectCount.put(j, insCount);
                    } else {
                        inspectCount.put(j, insCount + 1);
                    }
                    
                    Item item = monkey.items.get(k);
                    
                    monkey.performOperation(item);
                    
                    monkeys.get(monkey.getDestination(item)).items.add(item);
                }
                
                monkey.items = new ArrayList<>();
                
            }
        }
    
        List<Integer> counts = inspectCount.values().stream().sorted().collect(Collectors.toList());
    
        Collections.reverse(counts);
        
        System.out.println((long)counts.get(0) * (long)counts.get(1));
        
    }
    
    public static void b() throws Exception {
        List<Monkey> monkeys = parseMonkeys(v->new ItemB(v));
        
        Map<Integer, Integer> inspectCount = new HashMap<>();
        
        for (int m = 0; m < 10000; m++) {
            
            for (int j = 0; j < monkeys.size(); j++) {
                
                Monkey monkey = monkeys.get(j);
                
                for (int k = 0; k < monkey.items.size(); k++) {
                    
                    Integer insCount = inspectCount.get(j);
                    if (insCount == null) {
                        insCount = 1;
                        inspectCount.put(j, insCount);
                    } else {
                        inspectCount.put(j, insCount + 1);
                    }
                    
                    Item item = monkey.items.get(k);
                    
                    monkey.performOperation(item);
                    
                    monkeys.get(monkey.getDestination(item)).items.add(item);
                }
                
                monkey.items = new ArrayList<>();
                
            }
        }
    
        List<Integer> counts = inspectCount.values().stream().sorted().collect(Collectors.toList());
    
        Collections.reverse(counts);
    
        System.out.println((long)counts.get(0) * (long)counts.get(1));
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
