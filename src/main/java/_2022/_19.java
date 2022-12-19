package _2022;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class _19 {
    
    private static class Blueprint {
        int oreRobotOreCost;
        int clayRobotOreCost;
        int obsidianRobotOreCost;
        int obsidianRobotClayCost;
        int geodeRobotOreCost;
        int geodeRobotObsidianCost;
    
        public Blueprint(
                int oreRobotOreCost,
                int clayRobotOreCost,
                int obsidianRobotOreCost,
                int obsidianRobotClayCost,
                int geodeRobotOreCost,
                int geodeRobotObsidianCost) {
            this.oreRobotOreCost = oreRobotOreCost;
            this.clayRobotOreCost = clayRobotOreCost;
            this.obsidianRobotOreCost = obsidianRobotOreCost;
            this.obsidianRobotClayCost = obsidianRobotClayCost;
            this.geodeRobotOreCost = geodeRobotOreCost;
            this.geodeRobotObsidianCost = geodeRobotObsidianCost;
        }
    
        @Override
        public String toString() {
            return "Blueprint{" +
                    "oreRobotOreCost=" + oreRobotOreCost +
                    ", clayRobotOreCost=" + clayRobotOreCost +
                    ", obsidianRobotOreCost=" + obsidianRobotOreCost +
                    ", obsidianRobotClayCost=" + obsidianRobotClayCost +
                    ", geodeRobotOreCost=" + geodeRobotOreCost +
                    ", geodeRobotObsidianCost=" + geodeRobotObsidianCost +
                    '}';
        }
    }
    
    private static class State {
        int minute;
        int ore;
        int clay;
        int obsidian;
        
        int oreRobots;
        int clayRobots;
        int obsidianRobots;
        int geodeRobots;
    
        int geodesCracked;
    
        public State(
                int minute,
                int ore,
                int clay,
                int obsidian,
                int oreRobots,
                int clayRobots,
                int obsidianRobots,
                int geodeRobots,
                int geodesCracked) {
            this.minute = minute;
            this.ore = ore;
            this.clay = clay;
            this.obsidian = obsidian;
            this.oreRobots = oreRobots;
            this.clayRobots = clayRobots;
            this.obsidianRobots = obsidianRobots;
            this.geodeRobots = geodeRobots;
            this.geodesCracked = geodesCracked;
        }
        
        public State clone() {
            return new State(minute, ore, clay, obsidian, oreRobots, clayRobots, obsidianRobots, geodeRobots, geodesCracked);
        }
    
        public State moveTime() {
            State state = new State(minute,
                    ore,
                    clay,
                    obsidian,
                    oreRobots,
                    clayRobots,
                    obsidianRobots,
                    geodeRobots,
                    geodesCracked);
            
            state.minute+=1;
    
            state.ore += oreRobots;
            state.clay += clayRobots;
            state.obsidian += obsidianRobots;
            state.geodesCracked += geodeRobots;
            
            return state;
        }
    }
    
    private static List<Blueprint> parseBlueprints() throws IOException {
    
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2022/input/input19.txt"));
        
        List<Blueprint> blueprints = new ArrayList<>();
        
        while (true) {
            
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }
            
            String[] split = line.split("\\.");
            
            int oreRobotOreCost = Integer.parseInt(split[0].substring(split[0].indexOf("Each ore robot costs")+21, split[0].indexOf("Each ore robot costs")+21+1));
            int clayRobotOreCost = Integer.parseInt(split[1].substring(split[1].indexOf("Each clay robot costs")+22, split[1].indexOf("Each clay robot costs")+22+1));
            int obsidianRobotOreCost = Integer.parseInt(split[2].substring(split[2].indexOf("Each obsidian robot costs")+26, split[2].indexOf("Each obsidian robot costs")+26+1));
            int obsidianRobotClayCost = Integer.parseInt(split[2].substring(split[2].indexOf("and")+4, split[2].indexOf("clay")-1));
            int geodeRobotOreCost = Integer.parseInt(split[3].substring(split[3].indexOf("Each geode robot costs")+23, split[3].indexOf("Each geode robot costs")+23+1));
            int geodeRobotObsidianCost = Integer.parseInt(split[3].substring(split[3].indexOf("and")+4, split[3].indexOf("obsidian")-1));
            
            Blueprint blueprint = new Blueprint(oreRobotOreCost, clayRobotOreCost, obsidianRobotOreCost, obsidianRobotClayCost, geodeRobotOreCost, geodeRobotObsidianCost);
            
            //System.out.println(blueprint);
            
            blueprints.add(blueprint);
        }
        return blueprints;
    }
    
    public static void a() throws Exception {
        
        List<Blueprint> blueprints = parseBlueprints();
    
        //Blueprint blueprint = new Blueprint(4, 2, 3, 14, 2, 7);
        //Blueprint blueprint = new Blueprint(2, 3, 3, 8, 3, 12);
        
        int total = 0;
        int index = 1;
        
        for (Blueprint blueprint : blueprints) {
    
            State startState = new State(0, 0, 0, 0, 1, 0, 0, 0, 0);
    
            Queue<State> allStates = new ArrayDeque<>();
            allStates.add(startState);
    
            List<State> endStates = new ArrayList<>();
    
            while (!allStates.isEmpty()) {
        
                State state = allStates.remove();
        
                if (state.minute == 24) {
                    endStates.add(state);
                    continue;
                }
        
                // Geode robot build
                if (state.obsidian >= blueprint.geodeRobotObsidianCost && state.ore >= blueprint.geodeRobotOreCost) {
                    State nextState = state.moveTime();
    
                    nextState.obsidian -= blueprint.geodeRobotObsidianCost;
                    nextState.ore -= blueprint.geodeRobotOreCost;
                    nextState.geodeRobots += 1;
    
                    allStates.add(nextState);
                    continue;
                }
        
                // Obsidian robot build
                if (state.clay >= blueprint.obsidianRobotClayCost && state.ore >= blueprint.obsidianRobotOreCost) {
                    State nextState = state.moveTime();
            
                    nextState.clay -= blueprint.obsidianRobotClayCost;
                    nextState.ore -= blueprint.obsidianRobotOreCost;
                    nextState.obsidianRobots += 1;
            
                    allStates.add(nextState);
                    continue;
                }
        
                // Clay robot build
                if (state.ore >= blueprint.clayRobotOreCost && state.minute < 16) {
                    State nextState = state.moveTime();
    
                    nextState.ore -= blueprint.clayRobotOreCost;
                    nextState.clayRobots += 1;
            
                    allStates.add(nextState);
                }
        
                // Ore robot build
                if (state.ore >= blueprint.oreRobotOreCost && state.minute < 9) {
                    State nextState = state.moveTime();
            
                    nextState.ore -= blueprint.oreRobotOreCost;
                    nextState.oreRobots += 1;
            
                    allStates.add(nextState);
                }
        
                allStates.add(state.moveTime());
            }
    
            total += endStates.stream().mapToInt(v -> v.geodesCracked).max().getAsInt() * index++;
            
            //System.out.println(endStates.stream().mapToInt(v -> v.geodesCracked).max());
        }
        
        System.out.println(total);
        
    }
    
    public static void b() throws Exception {
        
        List<Blueprint> blueprints = parseBlueprints().subList(0, 3);
    
        int product = 1;
        
        for (Blueprint blueprint : blueprints) {
        
            State startState = new State(0, 0, 0, 0, 1, 0, 0, 0, 0);
        
            Queue<State> allStates = new ArrayDeque<>();
            allStates.add(startState);
        
            List<State> endStates = new ArrayList<>();
        
            while (!allStates.isEmpty()) {
            
                State state = allStates.remove();
            
                if (state.minute == 32) {
                    endStates.add(state);
                    continue;
                }
            
                // Geode robot build
                if (state.obsidian >= blueprint.geodeRobotObsidianCost && state.ore >= blueprint.geodeRobotOreCost) {
                    State nextState = state.moveTime();
                
                    nextState.obsidian -= blueprint.geodeRobotObsidianCost;
                    nextState.ore -= blueprint.geodeRobotOreCost;
                    nextState.geodeRobots += 1;
                
                    allStates.add(nextState);
                    continue;
                }
            
                // Obsidian robot build
                if (state.clay >= blueprint.obsidianRobotClayCost && state.ore >= blueprint.obsidianRobotOreCost) {
                    State nextState = state.moveTime();
                
                    nextState.clay -= blueprint.obsidianRobotClayCost;
                    nextState.ore -= blueprint.obsidianRobotOreCost;
                    nextState.obsidianRobots += 1;
                
                    allStates.add(nextState);
                    continue;
                }
            
                // Clay robot build
                if (state.ore >= blueprint.clayRobotOreCost && state.minute < 18) {
                    State nextState = state.moveTime();
                
                    nextState.ore -= blueprint.clayRobotOreCost;
                    nextState.clayRobots += 1;
                
                    allStates.add(nextState);
                }
            
                // Ore robot build
                if (state.ore >= blueprint.oreRobotOreCost && state.minute < 10) {
                    State nextState = state.moveTime();
                
                    nextState.ore -= blueprint.oreRobotOreCost;
                    nextState.oreRobots += 1;
                
                    allStates.add(nextState);
                }
            
                allStates.add(state.moveTime());
            }
        
            product *= endStates.stream().mapToInt(v -> v.geodesCracked).max().getAsInt();
        
            //System.out.println(endStates.stream().mapToInt(v -> v.geodesCracked).max());
        }
    
        System.out.println(product);
        
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
