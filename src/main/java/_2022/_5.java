package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Stack;


public class _5 {
    
    private static void setupData(Stack<String>[] stacks) {
        for (int j = 0; j < 9; j ++) {
            stacks[j] = new Stack<>();
        }
    
        stacks[0].push("S");
        stacks[0].push("C");
        stacks[0].push("V");
        stacks[0].push("N");
    
        stacks[1].push("Z");
        stacks[1].push("M");
        stacks[1].push("J");
        stacks[1].push("H");
        stacks[1].push("N");
        stacks[1].push("S");
    
        stacks[2].push("M");
        stacks[2].push("C");
        stacks[2].push("T");
        stacks[2].push("G");
        stacks[2].push("J");
        stacks[2].push("N");
        stacks[2].push("D");
    
        stacks[3].push("T");
        stacks[3].push("D");
        stacks[3].push("F");
        stacks[3].push("J");
        stacks[3].push("W");
        stacks[3].push("R");
        stacks[3].push("M");
    
        stacks[4].push("P");
        stacks[4].push("F");
        stacks[4].push("H");
    
        stacks[5].push("C");
        stacks[5].push("T");
        stacks[5].push("Z");
        stacks[5].push("H");
        stacks[5].push("J");
    
        stacks[6].push("D");
        stacks[6].push("P");
        stacks[6].push("R");
        stacks[6].push("Q");
        stacks[6].push("F");
        stacks[6].push("S");
        stacks[6].push("L");
        stacks[6].push("Z");
    
        stacks[7].push("C");
        stacks[7].push("S");
        stacks[7].push("L");
        stacks[7].push("H");
        stacks[7].push("D");
        stacks[7].push("F");
        stacks[7].push("P");
        stacks[7].push("W");
    
        stacks[8].push("D");
        stacks[8].push("S");
        stacks[8].push("M");
        stacks[8].push("P");
        stacks[8].push("F");
        stacks[8].push("N");
        stacks[8].push("G");
        stacks[8].push("Z");
    }
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input5.txt"));
        
        Stack<String>[] stacks = new Stack[9];
        
        setupData(stacks);
    
        for (int j = 0; j < 10; j ++) {
            fileReader.readLine();
        }
        
        while (true) {
            
            final String line = fileReader.readLine();
    
            if (line == null) {
                break;
            }
    
            String moveS = line.substring(5);
    
            moveS = moveS.substring(0, moveS.indexOf(' '));
    
            String remaining = line.substring(line.indexOf(" from ") + 6);
    
            String fromS = remaining.substring(0, 1);
            String toS = remaining.substring(5, 6);
            
            int move = Integer.parseInt(moveS);
            int from = Integer.parseInt(fromS);
            int to = Integer.parseInt(toS);
            
            for (int j = 0; j < move; j++) {
                
                Stack<String> stackFrom = stacks[from - 1];
                Stack<String> stackTo = stacks[to - 1];
                
                if (!stackFrom.isEmpty()) {
                    stackTo.push(stackFrom.pop());
                }
                
            }
            
        }
    
        for (int j = 0; j < 9; j ++) {
            System.out.print(stacks[j].pop());
        }
    
        System.out.println();
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input5.txt"));
    
        Stack<String>[] stacks = new Stack[9];
    
        setupData(stacks);
    
        for (int j = 0; j < 10; j ++) {
            fileReader.readLine();
        }
    
        while (true) {
        
            final String line = fileReader.readLine();
        
            if (line == null) {
                break;
            }
        
            String moveS = line.substring(5);
        
            moveS = moveS.substring(0, moveS.indexOf(' '));
        
            String remaining = line.substring(line.indexOf(" from ") + 6);
        
            String fromS = remaining.substring(0, 1);
            String toS = remaining.substring(5, 6);
        
            int move = Integer.parseInt(moveS);
            int from = Integer.parseInt(fromS);
            int to = Integer.parseInt(toS);
    
            Stack<String> poppedItems = new Stack<>();
            
            for (int j = 0; j < move; j++) {
            
                Stack<String> stackFrom = stacks[from - 1];
            
                if (!stackFrom.isEmpty()) {
                    poppedItems.push(stackFrom.pop());
                }
            
            }
    
            for (int j = 0; j < move; j++) {
    
                Stack<String> stackTo = stacks[to - 1];
        
                for (int k = 0; k < poppedItems.size(); k++) {
                    stackTo.push(poppedItems.pop());
                }
        
            }
        
        }
    
        for (int j = 0; j < 9; j ++) {
            System.out.print(stacks[j].pop());
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
