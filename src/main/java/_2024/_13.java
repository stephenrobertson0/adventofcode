package _2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class _13 {

    private record XY(long x, long y) {}
    private record Machine(XY button1, XY button2, XY prize) {}

    private static List<Machine> parseInput(boolean adjustedPrize) throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2024/input/input13.txt"));

        List<Machine> machines = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            XY button1 = new XY(Integer.parseInt(line.substring(line.indexOf('X') + 1, line.indexOf(','))), Integer.parseInt(line.substring(line.indexOf('Y') + 1)));

            String line2 = fileReader.readLine();

            XY button2 = new XY(Integer.parseInt(line2.substring(line2.indexOf('X') + 1, line2.indexOf(','))), Integer.parseInt(line2.substring(line2.indexOf('Y') + 1)));

            String line3 = fileReader.readLine();

            XY prize = new XY(
                    Integer.parseInt(line3.substring(line3.indexOf("X=") + 2, line3.indexOf(','))) + (adjustedPrize ? 10000000000000L : 0),
                    Integer.parseInt(line3.substring(line3.indexOf("Y=") + 2)) + (adjustedPrize ? 10000000000000L : 0));

            fileReader.readLine();

            Machine machine = new Machine(button1, button2, prize);

            machines.add(machine);
        }

        return machines;
    }

    public static void a() throws Exception {

        List<Machine> machines = parseInput(false);

        long totalCost = 0;

        for (Machine machine : machines) {

            long minCost = Long.MAX_VALUE;

            for (long j = 0; j <= machine.prize.x/machine.button1.x; j++) {
                XY result = new XY(machine.button1.x * j, machine.button1.y * j);

                XY distanceLeft = new XY(machine.prize.x - result.x, machine.prize.y - result.y);

                if (distanceLeft.x < 0 || distanceLeft.y < 0) {
                    continue;
                }

                if (distanceLeft.x % machine.button2.x == 0 && distanceLeft.y % machine.button2.y == 0) {
                    long button2Count = distanceLeft.x / machine.button2.x;
                    XY finalResult = new XY(machine.button1.x * j + machine.button2.x * button2Count, machine.button1.y * j + machine.button2.y * button2Count);

                    if (finalResult.x == machine.prize.x && finalResult.y == machine.prize.y) {
                        long cost = j*3 + button2Count;

                        /*System.out.println("Machine: " + machine);
                        System.out.println("Button 1 presses: " + j);
                        System.out.println("Button 2 presses: " + button2Count);
                        System.out.println();*/

                        if (cost < minCost) {
                            minCost = cost;
                        }
                    }
                }

            }

            if (minCost != Long.MAX_VALUE) {
                totalCost += minCost;
            }

        }

        System.out.println(totalCost);

    }
    
    public static void b() throws Exception {
        List<Machine> machines = parseInput(true);

        long totalCost = 0;

        for (Machine machine : machines) {

            long min = 0;
            long max = machine.prize.x/machine.button1.x + 1;
            long current = max / 2;

            int step = 0;
            int maxSteps = 1000;

            while (true) {
                XY result = new XY(machine.button1.x * current, machine.button1.y * current);

                XY distanceLeft = new XY(machine.prize.x - result.x, machine.prize.y - result.y);

                //System.out.println("Distance left: " + distanceLeft);

                double gradientLeft = distanceLeft.x <= 0 ? Integer.MAX_VALUE : (double)distanceLeft.y / distanceLeft.x;
                double gradientButton1 = (double)machine.button1.y / machine.button1.x;
                double gradientButton2 = (double)machine.button2.y / machine.button2.x;

                /*System.out.println(machine);
                System.out.println("current: " + current);
                System.out.println("gradient left: " + gradientLeft);
                System.out.println("gradient button 1: " + gradientButton1);
                System.out.println("gradient button 2: " + gradientButton2);
                System.out.println();*/

                if (gradientLeft < gradientButton2 && gradientButton2 < gradientButton1 || (gradientLeft > gradientButton2 && gradientButton2 > gradientButton1) || gradientLeft < 0) {
                    max = current;
                    current = (min + max) / 2;
                } else {
                    min = current;
                    current = (min + max) / 2;
                }

                if (distanceLeft.x % machine.button2.x == 0 && distanceLeft.y % machine.button2.y == 0) {
                    long button2Count = distanceLeft.x / machine.button2.x;
                    XY finalResult = new XY(machine.button1.x * current + machine.button2.x * button2Count, machine.button1.y * current + machine.button2.y * button2Count);

                    if (finalResult.x == machine.prize.x && finalResult.y == machine.prize.y) {
                        long cost = current*3 + button2Count;

                        /*System.out.println("Machine: " + machine);
                        System.out.println("Button 1 presses: " + current);
                        System.out.println("Button 2 presses: " + button2Count);
                        System.out.println();*/

                        totalCost += cost;
                        break;
                    }
                }

                if (step++ > maxSteps) {
                    break;
                }
            }

        }

        System.out.println(totalCost);
    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
