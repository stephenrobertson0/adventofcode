package _2021;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;


public class _4 {
    
    private static class Board {
        
        private List<List<Integer>> rows = new ArrayList<>();
        private List<List<Boolean>> matches = new ArrayList<>();
        
        
        
        private void add(List<Integer> row) {
            rows.add(row);
            matches.add(Arrays.asList(new Boolean[] { false, false, false, false, false }));
        }
        
        private boolean hasLine() {
            for (List<Boolean> row : matches) {
                if (lineMatch(row)) {
                    return true;
                }
            }
            
            for (List<Boolean> column : columnMatches()) {
                if (lineMatch(column)) {
                    return true;
                }
            }
            
            return false;
        }
        
        private List<List<Boolean>> columnMatches() {
            
            List<List<Boolean>> columns = new ArrayList<>();
            
            for (int j = 0; j < matches.get(0).size(); j++) {
                List<Boolean> column = new ArrayList<>();
                
                for (List<Boolean> row : matches) {
                    column.add(row.get(j));
                }
            }
            
            return columns;
        }
        
        private boolean lineMatch(List<Boolean> row) {
            for (Boolean match : row) {
                if (match == null || !match) {
                    return false;
                }
            }
            return true;
        }
        
        public void matchNumber(int num) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    
                    if (rows.get(j).get(k) == num) {
                        matches.get(j).set(k, true);
                    }
                    
                }
            }
        }
        
        public int sumOfUnmarked() {
            int total = 0;
            
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    
                    if (!matches.get(j).get(k)) {
                        total += rows.get(j).get(k);
                    }
                    
                }
            }
            
            return total;
        }
    }
    
    private static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input4.txt")));
    
        List<Board> boards = new ArrayList<>();
    
        final String firstLine = fileReader.readLine();
    
        List<Integer> numbers =
                Arrays.asList(firstLine.split(",")).stream().map(v -> Integer.parseInt(v)).collect(Collectors.toList());
    
        fileReader.readLine();
    
        Board currentBoard = new Board();
    
        while (true) {
            final String line = fileReader.readLine();
        
            if (line == null) {
                boards.add(currentBoard);
                break;
            }
        
            if (line.isEmpty()) {
                boards.add(currentBoard);
                currentBoard = new Board();
                continue;
            }
        
            List<Integer> row =
                    Arrays.asList(line.trim().split("\\s+"))
                            .stream()
                            .map(v -> Integer.parseInt(v))
                            .collect(Collectors.toList());
        
            currentBoard.add(row);
        }
    
        int winningNumber = -1;
    
        for (int num : numbers) {
            for (Board board : boards) {
                board.matchNumber(num);
            
                if (board.hasLine()) {
                    winningNumber = num;
                    break;
                }
            }
            if (winningNumber != -1) {
                break;
            }
        }
    
        for (Board board : boards) {
            if (board.hasLine()) {
                System.out.println(board.sumOfUnmarked() * winningNumber);
            }
        }
    }
    
    private static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(
                new FileReader(new File("./src/main/java/_2021/input/input4.txt")));
    
        List<Board> boards = new ArrayList<>();
    
        final String firstLine = fileReader.readLine();
    
        List<Integer> numbers =
                Arrays.asList(firstLine.split(",")).stream().map(v -> Integer.parseInt(v)).collect(Collectors.toList());
    
        fileReader.readLine();
    
        Board currentBoard = new Board();
    
        while (true) {
            final String line = fileReader.readLine();
        
            if (line == null) {
                boards.add(currentBoard);
                break;
            }
        
            if (line.isEmpty()) {
                boards.add(currentBoard);
                currentBoard = new Board();
                continue;
            }
        
            List<Integer> row =
                    Arrays.asList(line.trim().split("\\s+"))
                            .stream()
                            .map(v -> Integer.parseInt(v))
                            .collect(Collectors.toList());
        
            currentBoard.add(row);
        }
    
        int winningNumber = -1;
        Board lastWinningBoard = null;
        Set<Board> wonBoards = new HashSet<>();
    
        for (int num : numbers) {
            for (Board board : boards) {
                board.matchNumber(num);
            
                if (board.hasLine()) {
                    wonBoards.add(board);
                }
            
                if (wonBoards.size() == 100) {
                    lastWinningBoard = board;
                    winningNumber = num;
                    break;
                }
            }
            if (winningNumber != -1) {
                break;
            }
        }
    
        System.out.println(lastWinningBoard.sumOfUnmarked() * winningNumber);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}