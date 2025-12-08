package _2025;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class _8 {

    private record Box(int x, int y, int z) {

    }

    private record Connection(Box box1, Box box2) {}

    private static Set<Box> getAllBoxes(Box startBox, Set<Box> remainingBoxes, Map<Box, Set<Box>> connectionMap) {

        HashSet<Box> boxes = new HashSet<>();
        boxes.add(startBox);

        if (!remainingBoxes.contains(startBox)) {
            return boxes;
        }

        remainingBoxes.remove(startBox);

        Set<Box> neighbours = connectionMap.get(startBox);

        if (neighbours == null) {
            return boxes;
        }

        for (Box box : neighbours) {
            boxes.addAll(getAllBoxes(box, remainingBoxes, connectionMap));
        }

        return boxes;
    }

    public static void a() throws Exception {

        int connectionsToMake = 1000;

        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2025/input/input8.txt"));

        List<Box> boxes = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            boxes.add(new Box(Integer.parseInt(line.split(",")[0]), Integer.parseInt(line.split(",")[1]), Integer.parseInt(line.split(",")[2])));
        }

        Set<Connection> connections = new HashSet<>();

        for (int m = 0; m < connectionsToMake; m++) {
            Box selectedBox1 = null;
            Box selectedBox2 = null;
            double shortestDistance = Double.MAX_VALUE;

            for (int j = 0; j < boxes.size() - 1; j++) {
                for (int k = j+1; k < boxes.size(); k++) {

                    Box box1 = boxes.get(j);
                    Box box2 = boxes.get(k);

                    if (connections.contains(new Connection(box1, box2)) || connections.contains(new Connection(box2, box1))) {
                        continue;
                    }

                    double distance = Math.sqrt(Math.pow(box1.x - box2.x, 2) + Math.pow(box1.y - box2.y, 2) + Math.pow(box1.z - box2.z, 2));

                    if (distance <= shortestDistance) {
                        shortestDistance = distance;
                        selectedBox1 = box1;
                        selectedBox2 = box2;
                    }
                }
            }

            //System.out.print(selectedBox1 + "   ");
            //System.out.println(selectedBox2);

            connections.add(new Connection(selectedBox1, selectedBox2));
        }

        List<Connection> connectionsList = connections.stream().toList();

        Map<Box, Set<Box>> connectionMap = new HashMap<>();

        for (int j = 0; j < connectionsList.size(); j++) {
            Connection connection = connectionsList.get(j);

            Set<Box> set1 = connectionMap.get(connection.box1);
            if (set1 == null) {
                set1 = new HashSet<>();
            }
            set1.add(connection.box2);
            connectionMap.put(connection.box1, set1);

            Set<Box> set2 = connectionMap.get(connection.box2);
            if (set2 == null) {
                set2 = new HashSet<>();
            }
            set2.add(connection.box1);
            connectionMap.put(connection.box2, set2);
        }

        //System.out.println(connectionMap);

        List<Integer> sizes = new ArrayList<>();

        Set<Box> allBoxes = new HashSet<>(boxes);

        while (!allBoxes.isEmpty()) {
            Box box = allBoxes.iterator().next();
            Set<Box> circuit = getAllBoxes(box, new HashSet<>(allBoxes), connectionMap);
            allBoxes.removeAll(circuit);
            sizes.add(circuit.size());
            //System.out.println(circuit.size());
            //System.out.println("Remaining boxes: " + allBoxes);
        }

        sizes.sort(Integer::compareTo);

        System.out.println(sizes.get(sizes.size()-1) * sizes.get(sizes.size()-2) * sizes.get(sizes.size()-3));

    }

    public static void b() throws Exception {

    }

    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
