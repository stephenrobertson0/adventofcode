package _2021;

import java.util.*;


public class _21 {
    
    
    private static int dieValue = 1;
    
    private static int dieRoll () {
        int value = dieValue;
        
        dieValue++;
        
        if (dieValue == 101) {
            dieValue = 1;
        }
    
        System.out.println("Roll: " + value);
        
        return value;
    }
    
    public static void a() throws Exception {
        
        int score1 = 0;
        int score2 = 0;
        int position1 = 3;
        int position2 = 5;
        
        int dieRollCount = 0;
        
        while (score1 < 1000 && score2 < 1000) {
            
            position1 += dieRoll();
            position1 += dieRoll();
            position1 += dieRoll();
            
            if (position1 > 10) {
                position1 = position1 % 10;
                
                if (position1 == 0) {
                    position1 = 10;
                }
            }
            
            score1 += position1;
    
            dieRollCount += 3;
            
            if (score1 >= 1000) {
                break;
            }
            
            position2 += dieRoll();
            position2 += dieRoll();
            position2 += dieRoll();
    
            if (position2 > 10) {
                position2 = position2 % 10;
                
                if (position2 == 0) {
                    position2 = 10;
                }
            }
    
            score2 += position2;
    
            dieRollCount += 3;
    
            System.out.println("score1: " + score1);
            System.out.println("score2: " + score2);
            
        }
    
        System.out.println(dieRollCount);
        
        System.out.println(dieRollCount*Math.min(score1, score2));
        
    }
    
    private static Map<Integer, Integer> countsByTotals = new HashMap<>();
    
    static {
    
        for (int j = 0; j < 3; j++) {
            for (int k = 0; k < 3; k++) {
                for (int m = 0; m < 3; m++) {
    
                    int total = j+k+m+3;
                    
                    Integer count = countsByTotals.get(total);
                    
                    if (count == null) {
                        countsByTotals.put(total, 1);
                    } else {
                        countsByTotals.put(total, count+1);
                    }
                }
            }
        }
    
        System.out.println(countsByTotals);
        
    }
    
    private static class Game {
        private long universes;
        private int score1;
        private int score2;
        private int position1;
        private int position2;
        private boolean player1Turn = true;
        
        public boolean isComplete() {
            return score1 >= 21 || score2 >= 21;
        }
        
        private void move(Game previousGame, int diceTotal, int count) {
            
            int newPos1 = previousGame.position1;
            int newPos2 = previousGame.position2;
    
            score1 = previousGame.score1;
            score2 = previousGame.score2;
            
            if (previousGame.player1Turn) {
                newPos1 += diceTotal;
    
                if (newPos1 > 10) {
                    newPos1 = newPos1 % 10;
        
                    if (newPos1 == 0) {
                        newPos1 = 10;
                    }
                }
                
                score1 += newPos1;
            }
    
            if (!previousGame.player1Turn) {
                newPos2 += diceTotal;
    
                if (newPos2 > 10) {
                    newPos2 = newPos2 % 10;
    
                    if (newPos2 == 0) {
                        newPos2 = 10;
                    }
                }
    
                score2 += newPos2;
            }
            
            position1 = newPos1;
            position2 = newPos2;
            universes = previousGame.universes * count;
            player1Turn = !previousGame.player1Turn;
        }
        
        public List<Game> roll() {
            
            List<Game> nextGames = new ArrayList<>();
            
            for (int total : countsByTotals.keySet()) {
                
                int count = countsByTotals.get(total);
                
                Game game = new Game();
                
                game.move(this, total, count);
                
                nextGames.add(game);
            }
            
            return nextGames;
        }
    }
    
    public static void b() throws Exception {
    
        Game initialGame = new Game();
        initialGame.universes = 1;
        initialGame.position1 = 3;
        initialGame.position2 = 5;
        initialGame.player1Turn = true;
        initialGame.score1 = 0;
        initialGame.score2 = 0;
        
        
        Stack<Game> gamesToPlay = new Stack<>();
        
        gamesToPlay.add(initialGame);
        
        long universeCount1 = 0;
        long universeCount2 = 0;
        
        while (!gamesToPlay.empty()) {
            
            Game gameToPlay = gamesToPlay.pop();
            
            List<Game> games = gameToPlay.roll();
            
            for (Game game : games) {
                if (game.isComplete()) {
                    if (game.score1 > game.score2) {
                        universeCount1 += game.universes;
                    } else {
                        universeCount2 += game.universes;
                    }
                } else {
                    gamesToPlay.add(game);
                }
            }
        }
    
        System.out.println(universeCount1 > universeCount2 ? universeCount1 : universeCount2);
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
    
}