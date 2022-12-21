package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class _21 {
    
    private static class Number {
        
        String name;
        String operationStr;
        
        long value;
        Number number1;
        Number number2;
        char operation;
        
        Number parent;
        
        long calculate() {
            
            if (numberToCheckForEquality == this) {
                return numberToReturnIfEqual.calculate();
            }
            
            if (operation == '+') {
                return number1.calculate() + number2.calculate();
            } else if (operation == '-') {
                return number1.calculate() - number2.calculate();
            } else if (operation == '*') {
                return number1.calculate() * number2.calculate();
            } else if (operation == '/') {
                return number1.calculate() / number2.calculate();
            } else {
                return value;
            }
        }
    }
    
    static Map<String, Number> numbersByName = new HashMap<>();
    
    public static void a() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input21.txt"));
        
        List<Number> allNumbers = new ArrayList<>();
        Number root = null;
        
        while (true) {
            
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String[] split = line.split(":");
            
            Number number = new Number();
            number.name = split[0];
            number.operationStr = split[1].substring(1);
            
            numbersByName.put(number.name, number);
            allNumbers.add(number);
            
            if (number.name.equals("root")) {
                root = number;
            }
        }
        
        for (Number number : allNumbers) {
            if (number.operationStr.contains(" + ")) {
                String[] split = number.operationStr.split(" \\+ ");
                number.number1 = numbersByName.get(split[0]);
                number.number2 = numbersByName.get(split[1]);
                number.operation = '+';
            } else if (number.operationStr.contains(" - ")) {
                String[] split = number.operationStr.split(" - ");
                number.number1 = numbersByName.get(split[0]);
                number.number2 = numbersByName.get(split[1]);
                number.operation = '-';
            } else if (number.operationStr.contains(" * ")) {
                String[] split = number.operationStr.split(" \\* ");
                number.number1 = numbersByName.get(split[0]);
                number.number2 = numbersByName.get(split[1]);
                number.operation = '*';
            } else if (number.operationStr.contains(" / ")) {
                String[] split = number.operationStr.split(" / ");
                number.number1 = numbersByName.get(split[0]);
                number.number2 = numbersByName.get(split[1]);
                number.operation = '/';
            } else {
                number.value = Integer.parseInt(number.operationStr);
            }
        }
    
        System.out.println(root.calculate());
        
    }
    
    private static Number numberToCheckForEquality;
    private static Number numberToReturnIfEqual;
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input21.txt"));
    
        List<Number> allNumbers = new ArrayList<>();
    
        while (true) {
        
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            String[] split = line.split(":");
        
            Number number = new Number();
            number.name = split[0];
            number.operationStr = split[1].substring(1);
        
            numbersByName.put(number.name, number);
            allNumbers.add(number);
        }
    
        for (Number number : allNumbers) {
            if (number.operationStr.contains(" + ")) {
                String[] split = number.operationStr.split(" \\+ ");
                number.number1 = numbersByName.get(split[0]);
                number.number2 = numbersByName.get(split[1]);
                number.operation = '+';
            } else if (number.operationStr.contains(" - ")) {
                String[] split = number.operationStr.split(" - ");
                number.number1 = numbersByName.get(split[0]);
                number.number2 = numbersByName.get(split[1]);
                number.operation = '-';
            } else if (number.operationStr.contains(" * ")) {
                String[] split = number.operationStr.split(" \\* ");
                number.number1 = numbersByName.get(split[0]);
                number.number2 = numbersByName.get(split[1]);
                number.operation = '*';
            } else if (number.operationStr.contains(" / ")) {
                String[] split = number.operationStr.split(" / ");
                number.number1 = numbersByName.get(split[0]);
                number.number2 = numbersByName.get(split[1]);
                number.operation = '/';
            } else {
                number.value = Integer.parseInt(number.operationStr);
            }
            
            if (number.number1 != null) {
                number.number1.parent = number;
            }
    
            if (number.number2 != null) {
                number.number2.parent = number;
            }
        }
    
        Number human = numbersByName.get("humn");
        
        Number numberToModify = human;
        
        while (numberToModify.parent != null) {
            
            Number parent = numberToModify.parent;
            
            Number otherNumber = parent.number1 == numberToModify ? parent.number2 : parent.number1;
            
            if (parent.operation == '+') {
                numberToModify.operation = '-';
                numberToModify.number1 = parent;
                numberToModify.number2 = otherNumber;
            } else if (parent.operation == '*') {
                numberToModify.operation = '/';
                numberToModify.number1 = parent;
                numberToModify.number2 = otherNumber;
            } else if (parent.operation == '/') {
                
                if (otherNumber == parent.number1) {
                    numberToModify.operation = '/';
                    numberToModify.number1 = otherNumber;
                    numberToModify.number2 = parent;
                } else {
                    numberToModify.operation = '*';
                    numberToModify.number1 = parent;
                    numberToModify.number2 = otherNumber;
                }
            } else if (parent.operation == '-') {
    
                if (otherNumber == parent.number1) {
                    numberToModify.operation = '-';
                    numberToModify.number1 = otherNumber;
                    numberToModify.number2 = parent;
                } else {
                    numberToModify.operation = '+';
                    numberToModify.number1 = parent;
                    numberToModify.number2 = otherNumber;
                }
            }
            
            if (numberToModify.parent.parent == null) {
                numberToCheckForEquality = numberToModify == parent.number1 ? parent.number1 : parent.number2;
                numberToReturnIfEqual = numberToModify == parent.number1 ? parent.number2 : parent.number1;
            }
            
            numberToModify = numberToModify.parent;
        }
        
        System.out.println(numbersByName.get("humn").calculate());
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
