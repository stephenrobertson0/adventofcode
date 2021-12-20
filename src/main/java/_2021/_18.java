package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _18 {
    
    private static class Pair {
        
        boolean isLeaf = false;
        
        Pair left;
        Pair right;
        
        int leafValue;
        
        Pair parent;
        
        public Pair(Pair left, Pair right) {
            this.left = left;
            this.right = right;
            this.left.parent = this;
            this.right.parent = this;
            isLeaf = false;
        }
        
        public Pair(int value) {
            isLeaf = true;
            leafValue = value;
        }
        
        public String toString() {
            if (isLeaf) {
                return "" + leafValue;
            } else {
                return "[" + left.toString() + "," + right.toString() + "]";
            }
        }
        
        public Pair addAnother(Pair pair) {
            return new Pair(this, pair);
        }
        
        public boolean explode(int level) {
            
            if (level == 5) {
                System.out.println("Unexpected depth: " + 5);
            }
            
            if (level == 4 && !isLeaf && left.isLeaf && right.isLeaf) {
                int leftValue = left.leafValue;
                int rightValue = right.leafValue;
                
                this.isLeaf = true;
                this.leafValue = 0;
                this.left = null;
                this.right = null;
                
                Pair leftParent = getParentWhoHasLeftChildNotMe(this);
                Pair rightParent = getParentWhoHasRightChildNotMe(this);
                
                if (leftParent != null) {
                    addToRightMostLeaf(leftValue, leftParent.left);
                }
                
                if (rightParent != null) {
                    addToLeftMostLeaf(rightValue, rightParent.right);
                }
                
                return true;
            } else if (!isLeaf) {
                if (left.explode(level + 1)) {
                    return true;
                }
                
                if (right.explode(level + 1)) {
                    return true;
                }
            }
            
            return false;
        }
        
        public Pair getParentWhoHasLeftChildNotMe(Pair pair) {
            if (pair.parent == null) {
                return null;
            }
            
            if (pair.parent.left == pair) {
                return getParentWhoHasLeftChildNotMe(pair.parent);
            }
            
            return pair.parent;
        }
        
        public Pair getParentWhoHasRightChildNotMe(Pair pair) {
            if (pair.parent == null) {
                return null;
            }
            
            if (pair.parent.right == pair) {
                return getParentWhoHasRightChildNotMe(pair.parent);
            }
            
            return pair.parent;
        }
        
        public void addToRightMostLeaf(int value, Pair pair) {
            if (pair.isLeaf) {
                pair.leafValue += value;
            } else {
                addToRightMostLeaf(value, pair.right);
            }
        }
        
        public void addToLeftMostLeaf(int value, Pair pair) {
            if (pair.isLeaf) {
                pair.leafValue += value;
            } else {
                addToLeftMostLeaf(value, pair.left);
            }
        }
        
        public boolean split() {
            if (isLeaf) {
                
                if (leafValue > 9) {
                    
                    isLeaf = false;
                    left = new Pair(leafValue / 2);
                    right = new Pair(leafValue / 2 + (leafValue % 2 == 0 ? 0 : 1));
                    left.parent = this;
                    right.parent = this;
                    leafValue = 0;
                    
                    return true;
                }
                
            } else {
                if (left.split()) {
                    return true;
                }
                if (right.split()) {
                    return true;
                }
            }
            
            return false;
        }
        
        public int getMagnitude() {
            if (isLeaf) {
                return leafValue;
            } else {
                return 3 * left.getMagnitude() + 2 * right.getMagnitude();
            }
        }
        
        public void reduce() {
            boolean explodedOrSplit = true;
            
            while (explodedOrSplit) {
                
                boolean exploded = this.explode(0);
                
                if (exploded) {
                    continue;
                }
                
                boolean split = this.split();
                
                explodedOrSplit = exploded || split;
            }
        }
    }
    
    private static String subStringTilEndOfExpression(String str, int startPos) {
        int openCount = 0;
        
        int index = startPos;
        
        while (true) {
            char c = str.charAt(index++);
            
            if (c == '[') {
                openCount++;
            }
            
            if (c == ']') {
                openCount--;
                
                if (openCount < 0) {
                    index--;
                    break;
                }
                
                if (openCount == 0) {
                    break;
                }
            }
            
            if (c == ',' && openCount == 0) {
                index--;
                break;
            }
        }
        
        return str.substring(startPos, index);
    }
    
    private static Pair parsePair(String str) {
        
        if (str.startsWith("[")) {
            String left = subStringTilEndOfExpression(str, 1);
            String right = str.substring(left.length() + 2, str.length() - 1);
            
            return new Pair(parsePair(left), parsePair(right));
        } else {
            return new Pair(Integer.parseInt(str));
        }
    }
    
    public static void a() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input18.txt")));
        
        Pair addedPairs = null;
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            final Pair pair = parsePair(line);
            
            if (addedPairs == null) {
                addedPairs = pair;
            } else {
                addedPairs = addedPairs.addAnother(pair);
            }
            
            //System.out.println("Before exploded: " + addedPairs);
            
            boolean explodedOrSplit = true;
            
            while (explodedOrSplit) {
                
                boolean exploded = addedPairs.explode(0);
                
                //System.out.println("After exploded:  " + addedPairs);
                
                if (exploded) {
                    continue;
                }
                
                boolean split = addedPairs.split();
                
                //System.out.println("After split   :  " + addedPairs);
                
                explodedOrSplit = exploded || split;
            }
            
            //System.out.println();
            
        }
        
        //System.out.println(addedPairs);
        
        //System.out.println();
        
        //Pair pair = parsePair("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]");
        
        //pair.explode(0);
        
        //System.out.println(pair);
        
        System.out.println(addedPairs.getMagnitude());
        
    }
    
    public static void b() throws Exception {
        
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input18.txt")));
        
        List<String> allLines = new ArrayList<>();
        
        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            allLines.add(line);
        }
        
        int maxMagnitude = 0;
        
        for (int j = 0; j < allLines.size(); j++) {
            for (int k = 0; k < allLines.size(); k++) {
                
                if (j != k) {
                    Pair pair1 = parsePair(allLines.get(j));
                    Pair pair2 = parsePair(allLines.get(k));
                    
                    Pair sum = pair1.addAnother(pair2);
                    
                    sum.reduce();
                    
                    int magnitude = sum.getMagnitude();
                    
                    if (magnitude > maxMagnitude) {
                        maxMagnitude = magnitude;
                    }
                }
                
            }
        }
        
        System.out.println(maxMagnitude);
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}