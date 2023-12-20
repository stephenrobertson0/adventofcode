package _2023;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;


public class _20 {

    private static class Module {

        private String name;
        private List<String> outputs;
        private int type;
        private boolean state;
        private Map<String, Boolean> inputStates = new HashMap<>();

        public Module(String name, List<String> outputs, int type) {
            this.name = name;
            this.outputs = outputs;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public List<String> getOutputs() {
            return outputs;
        }

        public int getType() {
            return type;
        }

        @Override
        public String toString() {
            return "Module{" +
                    "name='" + name + '\'' +
                    ", outputs=" + outputs +
                    ", type=" + type +
                    '}';
        }

        public boolean isState() {
            return state;
        }

        public Map<String, Boolean> getInputStates() {
            return inputStates;
        }

        public void setState(boolean state) {
            this.state = state;
        }

        public void setInputStates(Map<String, Boolean> inputStates) {
            this.inputStates = inputStates;
        }
    }

    private static class Pulse {
        private String destination;
        private String source;
        private boolean isHigh;

        public Pulse(String destination, String source, boolean isHigh) {
            this.destination = destination;
            this.source = source;
            this.isHigh = isHigh;
        }

        public String getDestination() {
            return destination;
        }

        public String getSource() {
            return source;
        }

        public boolean isHigh() {
            return isHigh;
        }
    }

    private static class Counts {
        private int lowCount;
        private int highCount;
        private boolean found;

        public Counts(int lowCount, int highCount, boolean found) {
            this.lowCount = lowCount;
            this.highCount = highCount;
            this.found = found;
        }

        public int getLowCount() {
            return lowCount;
        }

        public int getHighCount() {
            return highCount;
        }

        public boolean isFound() {
            return found;
        }
    }

    private static List<Module> parseModules() throws Exception {
        BufferedReader fileReader = new BufferedReader(new FileReader("./src/main/java/_2023/input/input20.txt"));

        List<Module> modules = new ArrayList<>();

        while (true) {
            final String line = fileReader.readLine();

            if (line == null) {
                break;
            }

            String name = line.split(" -> ")[0];
            String outputs = line.split(" -> ")[1];

            if ("broadcaster".equals(name)) {
                Module module = new Module(name, Arrays.stream(outputs.split(",")).map(v -> v.trim()).collect(Collectors.toList()), 0);
                modules.add(module);
            } else {

                char type = name.charAt(0);
                name = name.substring(1);

                Module module = new Module(name, Arrays.stream(outputs.split(",")).map(v -> v.trim()).collect(Collectors.toList()), type == '%' ? 1 : 2);
                modules.add(module);
            }
        }

        return modules;
    }

    private static Map<String, Module> setupModulesByName(List<Module> modules) {
        Map<String, Module> modulesByName = new HashMap<>();

        for (Module module : modules) {
            modulesByName.put(module.getName(), module);
        }

        for (Module module : modules) {
            for (String out : module.getOutputs()) {
                Module outputModule = modulesByName.get(out);
                if (outputModule == null) {
                    continue;
                }
                outputModule.getInputStates().put(module.getName(), false);
            }
        }

        return modulesByName;
    }

    private static Counts pushButton(Map<String, Module> modulesByName, String destination) {

        int lowCount = 0;
        int highCount = 0;
        boolean found = false;

        Pulse start = new Pulse("broadcaster", "",false);

        Queue<Pulse> pulses = new ArrayDeque<>();
        pulses.add(start);

        while (!pulses.isEmpty()) {

            Pulse pulse = pulses.remove();

            if (pulse.getDestination().equals(destination) && !pulse.isHigh()) {
                found = true;
            }

            lowCount += pulse.isHigh() ? 0 : 1;
            highCount += pulse.isHigh() ? 1 : 0;

            //System.out.println("Sending pulse from " + pulse.getSource() + " to " + pulse.getDestination() + " is high " + pulse.isHigh());

            Module dest = modulesByName.get(pulse.getDestination());

            if (dest == null) {
                continue;
            }

            boolean isHigh = false;

            if (dest.getType() == 1 && pulse.isHigh()) {
                continue;
            } else if (dest.getType() == 1 && !pulse.isHigh()) {
                isHigh = !dest.isState();
                dest.setState(!dest.isState());
            } else if (dest.getType() == 2) {
                dest.getInputStates().put(pulse.getSource(), pulse.isHigh());
                isHigh = !dest.getInputStates().values().stream().reduce(true, (x,y)->x&&y);
            }

            for (String out : dest.getOutputs()) {
                pulses.add(new Pulse(out, dest.getName(), isHigh));
            }

        }

        return new Counts(lowCount, highCount, found);
    }

    public static void a() throws Exception {

        List<Module> modules = parseModules();
        Map<String, Module> modulesByName = setupModulesByName(modules);

        int lowCount = 0;
        int highCount = 0;

        for (int j = 0; j < 1000; j++) {
            Counts counts = pushButton(modulesByName, "");
            lowCount += counts.getLowCount();
            highCount += counts.getHighCount();
        }

        System.out.println(highCount*lowCount);

    }

    public static void b() throws Exception {
        List<Module> modules = parseModules();

        String rxInput = null;

        for (Module module : modules) {
            if (module.getOutputs().get(0).equals("rx")) {
                rxInput = module.getName();
            }
        }

        List<String> conjInputs = new ArrayList<>();

        for (Module module : modules) {
            if (module.getOutputs().contains(rxInput)) {
                conjInputs.add(module.getName());
            }
        }

        long result = 1;

        for (int j = 0; j < conjInputs.size(); j++) {

            modules = parseModules();
            Map<String, Module> modulesByName = setupModulesByName(modules);

            int count = 0;

            while (true) {

                count++;

                Counts counts = pushButton(modulesByName, conjInputs.get(j));
                if (counts.isFound()) {
                    break;
                }
            }

            result *= count;
        }

        System.out.println(result);

    }
    
    public static void main(String[] args) throws Exception {
        a();
        b();
    }
}
