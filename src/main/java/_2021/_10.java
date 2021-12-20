package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;


public class _10 {
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input10.txt")));
        
        int score = 0;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            Stack<String> stack = new Stack<>();
            
            for (int j = 0; j < line.length(); j++) {
                
                final char c = line.charAt(j);
                
                if (c == '{' || c == '(' || c == '[' || c == '<') {
                    stack.push("" + c);
                } else {
                    String pop = stack.pop();
                    
                    char expectedChar = ' ';
                    
                    switch (pop) {
                        case "{":
                            expectedChar = '}';
                            break;
                        case "[":
                            expectedChar = ']';
                            break;
                        case "<":
                            expectedChar = '>';
                            break;
                        case "(":
                            expectedChar = ')';
                            break;
                    }
                    
                    // Corrupted
                    if (c != expectedChar) {
                        switch (c) {
                            case '}':
                                score += 1197;
                                break;
                            case ']':
                                score += 57;
                                break;
                            case '>':
                                score += 25137;
                                break;
                            case ')':
                                score += 3;
                                break;
                        }
                        
                        break;
                    }
                    
                }
                
            }
            
        }
        
        System.out.println(score);
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input10.txt")));
        
        List<Long> allScores = new ArrayList<>();
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            Stack<String> stack = new Stack<>();
            
            for (int j = 0; j < line.length(); j++) {
                
                final char c = line.charAt(j);
                
                if (c == '{' || c == '(' || c == '[' || c == '<') {
                    stack.push("" + c);
                } else {
                    String pop = stack.pop();
                    
                    char expectedChar = ' ';
                    
                    switch (pop) {
                        case "{":
                            expectedChar = '}';
                            break;
                        case "[":
                            expectedChar = ']';
                            break;
                        case "<":
                            expectedChar = '>';
                            break;
                        case "(":
                            expectedChar = ')';
                            break;
                    }
                    
                    // Corrupted
                    if (c != expectedChar) {
                        break;
                    }
                    
                }
                
                // Incomplete
                
                long lineScore = 0;
                
                if (j == line.length() - 1) {
                    
                    //System.out.println("Incomplete line: " + line);
                    
                    int size = stack.size();
                    
                    for (int k = 0; k < size; k++) {
                        lineScore *= 5;
                        
                        String pop = stack.pop();
                        
                        //System.out.println("popped: " + pop);
                        
                        switch (pop) {
                            case "{":
                                lineScore += 3;
                                break;
                            case "[":
                                lineScore += 2;
                                break;
                            case "<":
                                lineScore += 4;
                                break;
                            case "(":
                                lineScore += 1;
                                break;
                        }
                    }
                    
                    //System.out.println("line score: " + lineScore);
                    
                    allScores.add(lineScore);
                }
            }
            
        }
        
        System.out.println(allScores.stream()
                .sorted(Long::compareTo)
                .collect(Collectors.toList())
                .get(allScores.size() / 2));
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}