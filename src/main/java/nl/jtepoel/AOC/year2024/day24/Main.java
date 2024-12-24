package nl.jtepoel.AOC.year2024.day24;


import java.util.*;

import nl.jtepoel.AOC.utils.*;

public class Main {

    Utils utils = new Utils();

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

    public Pair<HashMap<String, Integer>, HashMap<String, Triple<String, String, Integer>>> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day24/input.txt");
        HashMap<String, Integer> map = new HashMap<>();

        int j = 0;
        for (j = 0; j < lines.size(); j++) {
            String line = lines.get(j);
            if (line.isEmpty()) {
                break;
            }

            String[] split = line.split(": ");
            map.put(split[0], Integer.parseInt(split[1]));
        }

        j++;

        HashMap<String, Triple<String, String, Integer>> opperations = new HashMap<>();

        //x00 AND y00 -> z00
        //x01 XOR y01 -> z01
        //x02 OR y02 -> z02

        for (; j < lines.size(); j++) {
            String line = lines.get(j);
            if (line.isEmpty()) {
                break;
            }

            String[] split = line.split(" -> ");
            String[] split2 = split[0].split(" ");

            if (split2[1].equals("AND")) {
                opperations.put(split[1], new Triple<>(split2[0], split2[2], 0));
            } else if (split2[1].equals("OR")) {
                opperations.put(split[1], new Triple<>(split2[0], split2[2], 1));
            } else if (split2[1].equals("XOR")) {
                opperations.put(split[1], new Triple<>(split2[0], split2[2], 2));
            }
        }

        return Pair.of(map, opperations);
    }

    public String part1() {
        Pair<HashMap<String, Integer>, HashMap<String, Triple<String, String, Integer>>> input = getInput();
        HashMap<String, Integer> map = input.getFirst();
        HashMap<String, Triple<String, String, Integer>> operations = input.getSecond();


        for (String key : operations.keySet()) {
            eval(map, operations, key);
        }

        List<String> keys = new ArrayList<>(map.keySet());
        keys.sort(String::compareTo);
        keys = keys.reversed();
        StringBuilder result = new StringBuilder();
        for (String key : keys) {
            if (key.startsWith("z")) {
                int value = map.get(key);
                result.append(value);
            }
        }

        long resultInt = Long.parseLong(result.toString(), 2);

        return String.valueOf(resultInt);
    }

    private int eval(HashMap<String, Integer> map, HashMap<String, Triple<String, String, Integer>> operations, String z) {
        String x = operations.get(z).getFirst();
        String y = operations.get(z).getSecond();
        int op = operations.get(z).getThird();

        if (map.containsKey(z)) {
            return map.get(z);
        }

        if (!map.containsKey(x)) {
            map.put(x, eval(map, operations, x));
        }

        if (!map.containsKey(y)) {
            map.put(y, eval(map, operations, y));
        }

        int xVal = map.get(x);
        int yVal = map.get(y);
        if (op == 0) {
            map.put(z, xVal & yVal);
        } else if (op == 1) {
            map.put(z, xVal | yVal);
        } else if (op == 2) {
            map.put(z, xVal ^ yVal);
        }

        return map.get(z);
    }

    public String part2() {
        Pair<HashMap<String, Integer>, HashMap<String, Triple<String, String, Integer>>> input = getInput();
        HashMap<String, Triple<String, String, Integer>> operations = input.getSecond();

        Graph graph = new Graph();
        for (String key : operations.keySet()) {
            String x = operations.get(key).getFirst();
            String y = operations.get(key).getSecond();
            if (!graph.hasNode(x)) {
                graph.addNode(x);
            }

            if (!graph.hasNode(y)) {
                graph.addNode(y);
            }

            if (!graph.hasNode(key)) {
                graph.addNode(key);
            }

            if (operations.get(key).getThird() == 0) {
                graph.addNode(x + "AND" + y);
                graph.addEdge(graph.getNode(x), graph.getNode(x + "AND" + y), 0);
                graph.addEdge(graph.getNode(y), graph.getNode(x + "AND" + y), 0);
                graph.addEdge(graph.getNode(x + "AND" + y), graph.getNode(key), 0);
            } else if (operations.get(key).getThird() == 1) {
                graph.addNode(x + "OR" + y);
                graph.addEdge(graph.getNode(x), graph.getNode(x + "OR" + y), 0);
                graph.addEdge(graph.getNode(y), graph.getNode(x + "OR" + y), 0);
                graph.addEdge(graph.getNode(x + "OR" + y), graph.getNode(key), 0);
            } else if (operations.get(key).getThird() == 2) {
                graph.addNode(x + "XOR" + y);
                graph.addEdge(graph.getNode(x), graph.getNode(x + "XOR" + y), 0);
                graph.addEdge(graph.getNode(y), graph.getNode(x + "XOR" + y), 0);
                graph.addEdge(graph.getNode(x + "XOR" + y), graph.getNode(key), 0);
            }
        }

        graph.dotGraph(Utils.LOCSRC + "/year2024/day24/graph.dot");

        // find all wrong nodes in the graph and swap them within adders

        //z33, gqp
        //jmh, hsw
        //z18, qgd
        //z10, mwk

        List<String> arr = new ArrayList<>(Arrays.asList("z33", "gqp", "jmh", "hsw", "z18", "qgd", "z10", "mwk"));

        arr.sort(String::compareTo);

        return String.join(",", arr);
    }



}