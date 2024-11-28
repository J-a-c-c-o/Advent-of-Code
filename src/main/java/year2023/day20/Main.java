package year2023.day20;


import utils.Pair;
import utils.Triple;
import utils.Utils;

import java.util.*;

public class Main {

    Utils utils = new Utils();

    HashMap<String, HashMap<String, Boolean>> conjunctions = new HashMap<>();
    HashMap<String, Boolean> flipflops = new HashMap<>();
    HashMap<String, List<String>> destinations = new HashMap<>();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Main main = new Main();
        String part1 = main.part1();
        long part1Time = System.currentTimeMillis() - start;
        String part2 = main.part2();
        long part2Time = System.currentTimeMillis() - start - part1Time;

        System.out.println("Part 1: " + part1 + " took " + part1Time + "ms");
        System.out.println("Part 2: " + part2 + " took " + part2Time + "ms");
    }

    public void getInput() {
        List<String> input = utils.getLines("src/main/java/year2023/day20/input.txt");

        for (String line : input) {
            String[] split = line.split("->");
            String a = split[0].trim();
            String[] split2 = split[1].split(",");
            List<String> b = Arrays.asList(split2);
            b.replaceAll(String::trim);

            if (a.startsWith("%")) {
                String newA = a.substring(1);
                flipflops.put(newA, false);
                destinations.put(newA, b);
            } else if (a.startsWith("&")) {
                conjunctions.put(a.substring(1), new HashMap<>());

                destinations.put(a.substring(1), b);

                for (Map.Entry<String, List<String>> entry : destinations.entrySet()) {
                    for (String s : entry.getValue()) {
                        if (conjunctions.containsKey(s)) {
                            conjunctions.get(s).put(entry.getKey(), false);
                        }
                    }
                }
            } else {
                destinations.put(a, b);
            }
        }

        for (Map.Entry<String, List<String>> entry : destinations.entrySet()) {
            for (String s : entry.getValue()) {
                if (conjunctions.containsKey(s)) {
                    conjunctions.get(s).put(entry.getKey(), false);
                }
            }
        }

    }


    public String part1() {

        getInput();

        long low = 0;
        long high = 0;
        for (int i = 0; i < 1000; i++) {
            Pair<Integer, Integer> pair = go();

            low += pair.x;
            high += pair.y;


        }

        return String.valueOf(low * high);
    }

    public Pair<Integer, Integer> go() {

        Queue<Triple<String, String, Boolean>> queue = new LinkedList<>();
        int low = 0;
        int high = 0;

        queue.add(new Triple<>("button", "broadcaster", false));

        while (!queue.isEmpty()) {
            Triple<String, String, Boolean> triple = queue.poll();
            String sender = triple.x;
            String receiver = triple.y;
            boolean signal = triple.z;

            if (signal) {
                high++;
            } else {
                low++;
            }

            simulateReceiver(queue, sender, receiver, signal);

        }

        return new Pair<>(low, high);
    }

    public String part2() {

        getInput();


        Map<String, Integer> first4 = go2();

        //lcm of first 4
        long lcm = lcm(first4);


        return String.valueOf(lcm);
    }


    public Map<String, Integer> go2() {

        HashMap<String, Integer> first4 = new HashMap<>();

        String rxPrev = null;
        Set<String> useful = new HashSet<>();


        for (Map.Entry<String, List<String>> entry : destinations.entrySet()) {
            if (entry.getValue().equals(List.of("rx"))) {
                rxPrev = entry.getKey();
                break;
            }
        }

        for (Map.Entry<String, List<String>> entry : destinations.entrySet()) {
            if (entry.getValue().contains(rxPrev)) {
                useful.add(entry.getKey());
                assert conjunctions.containsKey(entry.getKey());
            }
        }


        for (int i = 1; ; i++) {
            Queue<Triple<String, String, Boolean>> queue = new LinkedList<>();
            queue.add(new Triple<>("button", "broadcaster", false));

            while (!queue.isEmpty()) {
                Triple<String, String, Boolean> triple = queue.poll();
                String sender = triple.x;
                String receiver = triple.y;
                boolean signal = triple.z;

                if (!signal && useful.contains(receiver)) {
                    first4.put(receiver, i);
                    if (first4.size() == 4) {
                        return first4;
                    }
                }

                simulateReceiver(queue, sender, receiver, signal);

            }
        }
    }

    private void simulateReceiver(Queue<Triple<String, String, Boolean>> queue, String sender, String receiver, boolean signal) {
        if (flipflops.containsKey(receiver)) {
            flipflop(queue, receiver, signal);
        } else if (conjunctions.containsKey(receiver)) {
            conjunction(queue, sender, receiver, signal);
        } else {
            init(queue, receiver, signal);
        }
    }

    private void init(Queue<Triple<String, String, Boolean>> queue, String receiver, boolean signal) {
        if (destinations.containsKey(receiver)) {
            for (String destination : destinations.get(receiver)) {
                queue.add(new Triple<>(receiver, destination, signal));
            }
        }
    }

    private void conjunction(Queue<Triple<String, String, Boolean>> queue, String sender, String receiver, boolean signal) {
        HashMap<String, Boolean> conjunction = conjunctions.get(receiver);
        conjunction.put(sender, signal);

        boolean state = true;
        for (boolean currentState : conjunction.values()) {
            if (!currentState) {
                state = false;
                break;
            }
        }

        for (String destination : destinations.get(receiver)) {
            queue.add(new Triple<>(receiver, destination, !state));
        }
    }

    private void flipflop(Queue<Triple<String, String, Boolean>> queue, String receiver, boolean signal) {
        if (!signal) {
            flipflops.put(receiver, !flipflops.get(receiver));
            boolean state = flipflops.get(receiver);


            for (String destination : destinations.get(receiver)) {
                queue.add(new Triple<>(receiver, destination, state));
            }

        }
    }


    private long lcm(Map<String, Integer> first4) {

        long lcm = 1;
        for (int i : first4.values()) {
            lcm = lcm * i / gcd(lcm, i);
        }

        return lcm;

    }

    private long gcd(long n1, long n2) {
        if (n1 == 0) {
            return n2;
        }

        if (n2 == 0) {
            return n1;
        }

        int n;
        for (n = 0; ((n1 | n2) & 1) == 0; n++) {
            n1 >>= 1;
            n2 >>= 1;
        }

        while ((n1 & 1) == 0) {
            n1 >>= 1;
        }

        do {
            while ((n2 & 1) == 0) {
                n2 >>= 1;
            }

            if (n1 > n2) {
                long temp = n1;
                n1 = n2;
                n2 = temp;
            }
            n2 = (n2 - n1);
        } while (n2 != 0);
        return n1 << n;
    }
}