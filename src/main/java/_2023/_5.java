package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class _5 {

    private static class Range {

        public Range(long source, long destination, long range) {
            this.source = source;
            this.destination = destination;
            this.range = range;
        }

        private long source;
        private long destination;
        private long range;

        public long getSource() {
            return source;
        }

        public long getDestination() {
            return destination;
        }

        public long getRange() {
            return range;
        }

        @Override
        public String toString() {
            return "Range{" +
                    "source=" + source +
                    ", destination=" + destination +
                    ", range=" + range +
                    '}';
        }
    }

    private static class SeedRange {

        private long start;
        private long end;

        public SeedRange(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "SeedRange{" +
                    "start=" + start +
                    ", end=" + end +
                    '}';
        }

        public long getStart() {
            return start;
        }

        public long getEnd() {
            return end;
        }
    }

    public static void a() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input5.txt"));

        List<List<Range>> ranges = new ArrayList<>();

        List<Range> currentRanges = null;

        List<Long> seeds = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();
            
            if (line == null) {
                break;
            }

            if (line.startsWith("seeds:")) {
                String seedsStr = line.substring(line.indexOf("seeds:")+7);
                seeds = Arrays.stream(seedsStr.split(" ")).map(s->Long.parseLong(s.trim())).collect(Collectors.toList());
                continue;
            }

            if (line.endsWith("map:")) {
                if (currentRanges != null) {
                    ranges.add(currentRanges);
                }
                currentRanges = new ArrayList<>();
                continue;
            }

            if (line.isEmpty()) {
                continue;
            }

            List<Long> numbers = Arrays.stream(line.split(" ")).map(s->Long.parseLong(s.trim())).collect(Collectors.toList());

            long mapTo = numbers.get(0);
            long mapFrom = numbers.get(1);
            long range = numbers.get(2);

            currentRanges.add(new Range(mapFrom, mapTo, range));
        }

        ranges.add(currentRanges);

        //System.out.println(ranges);

        List<Long> result = new ArrayList<>();

        for (List<Range> rangeList : ranges) {

            for (long seed : seeds) {

                boolean mapped = false;

                for (Range range : rangeList) {

                    if (seed >= range.getSource() && seed < range.getSource() + range.getRange()) {
                        result.add(range.getDestination() + seed - range.getSource());
                        mapped = true;
                        break;
                    }
                }

                if (!mapped) {
                    result.add(seed);
                }

            }

            seeds = result;
            result = new ArrayList<>();
        }

        System.out.println(seeds.stream().sorted().collect(Collectors.toList()).get(0));

    }
    
    public static void b() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input5.txt"));

        List<List<Range>> ranges = new ArrayList<>();

        List<Range> currentRanges = null;

        List<Long> seeds = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            if (line.startsWith("seeds:")) {
                String seedsStr = line.substring(line.indexOf("seeds:")+7);
                seeds = Arrays.stream(seedsStr.split(" ")).map(s->Long.parseLong(s.trim())).collect(Collectors.toList());
                continue;
            }

            if (line.endsWith("map:")) {
                if (currentRanges != null) {
                    ranges.add(currentRanges);
                }
                currentRanges = new ArrayList<>();
                continue;
            }

            if (line.isEmpty()) {
                continue;
            }

            List<Long> numbers = Arrays.stream(line.split(" ")).map(s->Long.parseLong(s.trim())).collect(Collectors.toList());

            long mapTo = numbers.get(0);
            long mapFrom = numbers.get(1);
            long range = numbers.get(2);

            currentRanges.add(new Range(mapFrom, mapTo, range));
        }

        ranges.add(currentRanges);

        List<SeedRange> seedRanges = new ArrayList<>();

        for (int j = 0; j < seeds.size() / 2; j ++) {
            long seedStart = seeds.get(j*2);
            long count = seeds.get(j*2+1);
            seedRanges.add(new SeedRange(seedStart, seedStart+count-1));
        }

        List<SeedRange> result = new ArrayList<>();

        for (List<Range> rangeList : ranges) {

            for (int j = 0; j < seedRanges.size(); j++) {

                SeedRange seedRange = seedRanges.get(j);

                boolean anyMatch = false;

                for (Range range : rangeList) {

                    long sourceMin = range.getSource();
                    long sourceMax = range.getSource() + range.getRange() - 1;

                    long destinationDifference = range.getDestination() - range.getSource();

                    //System.out.println("destination difference: " + destinationDifference);

                    //System.out.println();
                    //System.out.println("Source min: " + sourceMin);
                    //System.out.println("Source max: " + sourceMax);
                    //System.out.println("Seed Range min: " + seedRange.getStart());
                    //System.out.println("Seed Range max: " + seedRange.getEnd());

                    if (seedRange.getStart() <= sourceMax && seedRange.getStart() >= sourceMin && seedRange.getEnd() > sourceMax) {
                        //System.out.println("If 1");
                        result.add(new SeedRange(seedRange.getStart()+destinationDifference, sourceMax+destinationDifference));

                        seedRanges.add(new SeedRange(sourceMax+1, seedRange.getEnd()));

                        anyMatch = true;

                        //System.out.println("Adding: " + (seedRange.getStart()+destinationDifference) + " to " + (sourceMax+destinationDifference));
                        //System.out.println("Adding: " + (sourceMax+1) + " to " + seedRange.getEnd());


                    } else if (seedRange.getEnd() >= sourceMin && seedRange.getEnd() <= sourceMax && seedRange.getStart() < sourceMin) {
                        //System.out.println("If 2");
                        //System.out.println(seedRange);
                        //System.out.println(sourceMin);
                        //System.out.println(sourceMax);
                        result.add(new SeedRange(sourceMin+destinationDifference, seedRange.getEnd()+destinationDifference));

                        seedRanges.add(new SeedRange(seedRange.getStart(), sourceMin-1));

                        anyMatch = true;

                        //System.out.println("Adding: " + (sourceMin+destinationDifference) + " to " + (seedRange.getEnd()+destinationDifference));
                        //System.out.println("Adding: " + seedRange.getStart() + " to " + (sourceMin-1));

                    } else if (seedRange.getStart() < sourceMin && seedRange.getEnd() > sourceMax) {
                        //System.out.println("If 3");
                        result.add(new SeedRange(sourceMin+destinationDifference, sourceMax+destinationDifference));

                        seedRanges.add(new SeedRange(sourceMax+1, seedRange.getEnd()));
                        seedRanges.add(new SeedRange(seedRange.getStart(), sourceMin-1));

                        anyMatch = true;

                        //System.out.println("Adding: " + (sourceMin+destinationDifference));

                    } else if (seedRange.getStart() >= sourceMin && seedRange.getEnd() <= sourceMax) {
                        //System.out.println("If 4");
                        result.add(new SeedRange(seedRange.getStart()+destinationDifference, seedRange.getEnd()+destinationDifference));

                        //System.out.println("Adding: " + (seedRange.getStart()+destinationDifference));

                        anyMatch = true;
                    }

                }

                if (!anyMatch) {
                    result.add(seedRange);
                }

            }

            //System.out.println(result);

            seedRanges = result;
            result = new ArrayList<>();
        }

        System.out.println(seedRanges.stream().map(v->v.getStart()).sorted().collect(Collectors.toList()).get(0));

    }

    public static void bruteForce() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input5.txt"));

        List<List<Range>> ranges = new ArrayList<>();

        List<Range> currentRanges = null;

        List<Long> seeds = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            if (line.startsWith("seeds:")) {
                String seedsStr = line.substring(line.indexOf("seeds:")+7);
                seeds = Arrays.stream(seedsStr.split(" ")).map(s->Long.parseLong(s.trim())).collect(Collectors.toList());
                continue;
            }

            if (line.endsWith("map:")) {
                if (currentRanges != null) {
                    ranges.add(currentRanges);
                }
                currentRanges = new ArrayList<>();
                continue;
            }

            if (line.isEmpty()) {
                continue;
            }

            List<Long> numbers = Arrays.stream(line.split(" ")).map(s->Long.parseLong(s.trim())).collect(Collectors.toList());

            long mapTo = numbers.get(0);
            long mapFrom = numbers.get(1);
            long range = numbers.get(2);

            currentRanges.add(new Range(mapFrom, mapTo, range));
        }

        ranges.add(currentRanges);

        //System.out.println(ranges);

        List<Long> realSeeds = new ArrayList<>();

        for (int j = 0; j < seeds.size() / 2; j ++) {
            long seedStart = seeds.get(j*2);
            long seedEnd = seeds.get(j*2+1);
            for (long k = seedStart; k < seedStart+seedEnd; k++) {
                realSeeds.add(k);
            }
        }

        //System.out.println(realSeeds);

        List<Long> result = new ArrayList<>();

        for (List<Range> rangeList : ranges) {

            for (long seed : realSeeds) {

                boolean mapped = false;

                for (Range range : rangeList) {

                    if (seed >= range.getSource() && seed < range.getSource() + range.getRange()) {
                        result.add(range.getDestination() + seed - range.getSource());
                        mapped = true;
                        break;
                    }
                }

                if (!mapped) {
                    result.add(seed);
                }

            }

            realSeeds = result;
            result = new ArrayList<>();

            System.out.println(realSeeds);
        }

        System.out.println("Result: " + realSeeds.stream().sorted().collect(Collectors.toList()).get(0));

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
