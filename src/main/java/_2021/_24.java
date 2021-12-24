package _2021;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _24 {
    
    private enum InstType {
        INP,
        ADD,
        MUL,
        DIV,
        MOD,
        EQL
    }
    
    private static class Input {
        long num;
        int index = 0;
    
        public Input(long num) {
            this.num = num;
        }
    
        public int nextInput() {
            return Integer.parseInt(""+ (""+num).charAt(index++));
        }
    }
    
    private static class Instruction {
        
        private InstType instType;
        // 0 = w, x = 1, y = 2, z = 3
        private int var1;
        private int var2;
        private int value;
        
        public void apply(long[] vars, Input input) {
            
            if (instType == InstType.INP) {
                vars[var1] = input.nextInput(); 
            } else {
                long val2 = var2 == -1 ? value : vars[var2];
    
                if (instType == InstType.ADD) {
                    vars[var1] += val2;
                } else if (instType == InstType.MUL) {
                    vars[var1] *= val2;
                } else if (instType == InstType.DIV) {
                    vars[var1] /= val2;
                } else if (instType == InstType.MOD) {
                    vars[var1] %= val2;
                } else if (instType == InstType.EQL) {
                    vars[var1] = vars[var1] == val2 ? 1 : 0;
                }
            }
            
        }
    }
    
    private static int getVarIndexFromString(String str) {
        if (str.equals("w")) {
            return 0;
        } else if (str.equals("x")) {
            return 1;
        } else if (str.equals("y")) {
            return 2;
        } else if (str.equals("z")) {
            return 3;
        }
        return -1;
    }
    
    public static void a() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input24.txt")));
        
        List<Instruction> instructions = new ArrayList<>();
        
        while (true) {
            final String line = fileReader.readLine();
    
            if (line == null) {
                break;
            }
    
            Instruction instruction = new Instruction();
            
            String instr = line.substring(0, 3);
            String rest = line.substring(4);
            
            if (instr.equals("inp")) {
                instruction.instType = InstType.INP;
                instruction.var1 = getVarIndexFromString(rest);
            } else {
                
                String a = rest.substring(0, rest.indexOf(" "));
                String b = rest.substring(rest.indexOf(" ")+1);
    
                instruction.var1 = getVarIndexFromString(a);
                instruction.var2 = getVarIndexFromString(b);
                
                if (instruction.var2 == -1) {
                    instruction.value = Integer.parseInt(b);
                }
                
                if (instr.equals("add")) {
                    instruction.instType = InstType.ADD;
                } else if (instr.equals("mul")) {
                    instruction.instType = InstType.MUL;
                } else if (instr.equals("div")) {
                    instruction.instType = InstType.DIV;
                } else if (instr.equals("mod")) {
                    instruction.instType = InstType.MOD;
                } else if (instr.equals("eql")) {
                    instruction.instType = InstType.EQL;
                }
                
            }
            
            instructions.add(instruction);
        }
        
        long startInput = 99999999999999L;
        
        while (true) {
            long[] vars = new long[4];
            Input input = new Input(startInput);
            
            if ((""+startInput).contains("0")) {
                startInput--;
                continue;
            }
            
            for (Instruction instruction : instructions) {
                instruction.apply(vars, input);
            }
    
            if (vars[3] == 0) {
                System.out.println("yes");
            }
            
            System.out.println("z = " + vars[3] + " input = " + startInput);
            startInput--;
        }
    
        /*long[] vars = new long[4];
        Input input = new Input(99999999999999L);
        
        for (Instruction instruction : instructions) {
            instruction.apply(vars, input);
            System.out.println("input: " + vars[0] + " " + vars[1] + " " + vars[2] + " " + vars[3]);
        }
    
        System.out.println(vars[3]);*/
        
        //System.out.println(count);
    }
    
    public static void b() throws Exception {
        
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}