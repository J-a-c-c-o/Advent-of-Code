package nl.jtepoel.AOC.year2024.day23;


import java.util.*;

import nl.jtepoel.AOC.utils.Graph;
import nl.jtepoel.AOC.utils.Node;
import nl.jtepoel.AOC.utils.Utils;

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

    public Graph getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day23/input.txt");
        Graph graph = new Graph();
        for (String line : lines) {
            String[] split = line.split("-");
            Node node1;
            Node node2;
            if (!graph.hasNode(split[0])) {
                node1 = new Node(split[0]);
                graph.addNode(node1);
            } else {
                node1 = graph.getNode(split[0]);
            }

            if (!graph.hasNode(split[1])) {
                node2 = new Node(split[1]);
                graph.addNode(node2);
            } else {
                node2 = graph.getNode(split[1]);
            }


            graph.addEdge(node1, node2,0);
        }

        return graph;
    }

    public String part1() {
        Graph graph = getInput();
        Set<Node> nodes = graph.getNodes();

        // triangles of nodes
        Set<List<Node>> triangles = new HashSet<>();
        for (Node node : nodes) {
            for (Node neighbour : node.getConnected()) {
                for (Node neighbour2 : neighbour.getConnected()) {
                    if (neighbour2.getConnected().contains(node)) {
                        List<Node> triangle = new ArrayList<>();
                        triangle.add(node);
                        triangle.add(neighbour);
                        triangle.add(neighbour2);
                        triangle.sort(Comparator.comparing(Node::getName));
                        triangles.add(triangle);
                    }
                }
            }
        }



        int sumOfT = 0; // count all permutation where 1 of the nodes starts with t
        for (List<Node> triangle : triangles) {
            for (Node node : triangle) {
                if (node.getName().startsWith("t")) {
                    sumOfT++;
                    break;
                }
            }

        }

        return String.valueOf(sumOfT);
    }

    public String part2() {
        Graph graph = getInput();

        List<Node> bronKerbosch = graph.bronKerbosch();

        bronKerbosch.sort(Comparator.comparing(Node::getName));
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bronKerbosch.size(); i++) {
            sb.append(bronKerbosch.get(i).getName());
            if (i != bronKerbosch.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }



}