package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _16 {
    
    private static class Packet {
        
        private List<Packet> subpackets;
        private long value;
        private boolean isLiteral;
        private int finalIndex;
        private int versionTotal;
        private int type;
        
        public Packet(String bits) {
            int version = binaryStringToInt(bits.substring(0, 3));
            this.type = binaryStringToInt(bits.substring(3, 6));
            
            versionTotal = version;
            
            //System.out.println("Packet passed bits: " + bits);
            //System.out.println("Version = " + version);
            
            if (type == 4) {
                // Literal
                
                //System.out.println("Type is literal");
                
                isLiteral = true;
                
                int index = 6;
                
                String digits = "";
                
                while (true) {
                    
                    String digit = bits.substring(index, index + 5);
                    
                    digits += digit.substring(1, 5);
                    
                    index += 5;
                    
                    if (digit.substring(0, 1).equals("0")) {
                        break;
                    }
                }
                
                value = Long.parseLong(digits, 2);
                
                finalIndex = index;
                
            } else {
                // Operator
                
                //System.out.println("Type is Operator");
                
                subpackets = new ArrayList<>();
                
                String lengthTypeId = bits.substring(6, 7);
                
                if (lengthTypeId.equals("0")) {
                    
                    // 15 bits represent the length
                    
                    String lengthBits = bits.substring(7, 7 + 15);
                    
                    int length = Integer.parseInt(lengthBits, 2);
                    
                    String allBits = bits.substring(7 + 15, 7 + 15 + length);
                    int index = 0;
                    
                    while (true) {
                        //System.out.println(
                        //        "Adding subpacket for operator type 0 where length is specified - " + allBits.substring(
                        //                index));
                        
                        Packet subpacket = new Packet(allBits.substring(index));
                        
                        subpackets.add(subpacket);
                        
                        index += subpacket.getFinalIndex();
                        
                        //System.out.println("Index is now: " + index);
                        //System.out.println("Length is now: " + length);
                        
                        versionTotal += subpacket.getVersionTotal();
                        
                        if (index >= length) {
                            break;
                        }
                    }
                    
                    finalIndex = length + 7 + 15;
                    
                } else {
                    
                    // 11 bits represent how many sub elements
                    
                    String countBits = bits.substring(7, 7 + 11);
                    
                    int count = Integer.parseInt(countBits, 2);
                    
                    //System.out.println("count is: " + count);
                    
                    String allBits = bits.substring(7 + 11);
                    int index = 0;
                    
                    for (int j = 0; j < count; j++) {
                        
                        //System.out.println(
                        //        "Adding subpacket for operator type 1 where count is specified - " + allBits.substring(
                        //                index));
                        
                        Packet subpacket = new Packet(allBits.substring(index));
                        
                        subpackets.add(subpacket);
                        
                        index += subpacket.getFinalIndex();
                        versionTotal += subpacket.getVersionTotal();
                    }
                    
                    finalIndex = index + 7 + 11;
                }
                
            }
            
            //System.out.println();
        }
        
        private int binaryStringToInt(String bin) {
            return Integer.parseInt(bin, 2);
        }
        
        public int getFinalIndex() {
            return finalIndex;
        }
        
        public int getVersionTotal() {
            return versionTotal;
        }
        
        public long getExpressionResult() {
            if (isLiteral) {
                return value;
            } else {
                if (type == 0) {
                    long sum = 0;
                    
                    for (Packet packet : subpackets) {
                        sum += packet.getExpressionResult();
                    }
                    
                    return sum;
                } else if (type == 1) {
                    long product = 1;
                    
                    for (Packet packet : subpackets) {
                        product *= packet.getExpressionResult();
                    }
                    
                    return product;
                } else if (type == 2) {
                    long min = Long.MAX_VALUE;
                    
                    for (Packet packet : subpackets) {
                        min = Math.min(min, packet.getExpressionResult());
                    }
                    
                    return min;
                } else if (type == 3) {
                    long max = 0;
                    
                    for (Packet packet : subpackets) {
                        max = Math.max(max, packet.getExpressionResult());
                    }
                    
                    return max;
                } else if (type == 5) {
                    long val1 = subpackets.get(0).getExpressionResult();
                    long val2 = subpackets.get(1).getExpressionResult();
                    
                    return val1 > val2 ? 1 : 0;
                } else if (type == 6) {
                    long val1 = subpackets.get(0).getExpressionResult();
                    long val2 = subpackets.get(1).getExpressionResult();
                    
                    return val1 < val2 ? 1 : 0;
                } else if (type == 7) {
                    long val1 = subpackets.get(0).getExpressionResult();
                    long val2 = subpackets.get(1).getExpressionResult();
                    
                    return val1 == val2 ? 1 : 0;
                } else {
                    throw new RuntimeException("Unknown type: " + type);
                }
                
            }
        }
    }
    
    private static String hexToBin(String hex) {
        String overall = "";
        
        for (int j = 0; j < hex.length(); j++) {
            final String s = Integer.toString(Integer.parseInt(hex.substring(j, j + 1), 16), 2);
            
            // Pad String
            for (int k = 0; k < 4 - s.length(); k++) {
                overall += 0;
            }
            
            overall += s;
        }
        
        return overall;
    }
    
    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input16.txt")));
        
        final String line = fileReader.readLine();
        
        String binString = hexToBin(line);
        
        Packet packet = new Packet(binString);
        
        System.out.println(packet.getVersionTotal());
    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input16.txt")));
        
        final String line = fileReader.readLine();
        
        String binString = hexToBin(line);
        
        Packet packet = new Packet(binString);
        
        System.out.println(packet.getExpressionResult());
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}