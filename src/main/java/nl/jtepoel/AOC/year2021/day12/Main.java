package nl.jtepoel.AOC.year2021.day12;


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

    public Graph getInput() {
        List<String> input = utils.getLines(Utils.LOCSRC + "/year2021/day12/input.txt");
        Graph graph = new Graph();

        for (String line : input) {
            String[] parts = line.split("-");
            String nameA = parts[0];
            String nameB = parts[1];

            if (!graph.hasNode(nameA)) {
                graph.addNode(nameA);
            }

            if (!graph.hasNode(nameB)) {
                graph.addNode(nameB);
            }

            Node nodeA = graph.getNode(nameA);
            Node nodeB = graph.getNode(nameB);

            graph.addEdge(nodeA, nodeB, 1);
        }

        return graph;
    }

    public String part1() {
        Graph graph = getInput();
        Set<Node> small_caves = new HashSet<>();
        List<List<Node>> paths = new ArrayList<>();
        Node start = graph.getNode("start");
        Node end = graph.getNode("end");
        for (Node node : graph.getNodes()) {
            if (Objects.equals(node.getName(), node.getName().toLowerCase())) {
                small_caves.add(node);
            }
        }

        List<Node> initPath = new ArrayList<>();
        initPath.add(start);

        Queue<Pair<Node, List<Node>>> queue = new LinkedList<>();
        queue.add(new Pair<>(start, initPath));

        while (!queue.isEmpty()) {
            Pair<Node, List<Node>> pair = queue.poll();
            Node node = pair.x;
            List<Node> path = pair.y;

            if (node.equals(end)) {
                paths.add(path);
                continue;
            }

            for (Edge edge : node.getEdges()) {
                Node next = edge.getOtherNode(node);
                if (!path.contains(next) || !small_caves.contains(next)) {
                    List<Node> newPath = new ArrayList<>(path);
                    newPath.add(next);
                    queue.add(new Pair<>(next, newPath));
                }
            }
        }

        return String.valueOf(paths.size());

    }

    public String part2() {
        Graph graph = getInput();
        Set<Node> small_caves = new HashSet<>();
        List<List<Node>> paths = new ArrayList<>();
        Node start = graph.getNode("start");
        Node end = graph.getNode("end");
        for (Node node : graph.getNodes()) {
            if (Objects.equals(node.getName(), node.getName().toLowerCase())) {
                small_caves.add(node);
            }
        }

        List<Node> initPath = new ArrayList<>();
        initPath.add(start);

        Queue<Triple<Node, List<Node>, Boolean>> queue = new LinkedList<>();
        queue.add(new Triple<>(start, initPath, false));

        while (!queue.isEmpty()) {
            Triple<Node, List<Node>, Boolean> triple = queue.poll();
            Node node = triple.x;
            List<Node> path = triple.y;
            boolean visited_small_2 = triple.z;

            if (node.equals(end)) {
                paths.add(path);
                continue;
            }

            for (Edge edge : node.getEdges()) {
                Node next = edge.getOtherNode(node);
                if (!path.contains(next) || !small_caves.contains(next)) {
                    List<Node> newPath = new ArrayList<>(path);
                    newPath.add(next);
                    queue.add(new Triple<>(next, newPath, visited_small_2));
                }

                if (!visited_small_2 && small_caves.contains(next) && path.contains(next) &&
                        !next.getName().equals("start") && !next.getName().equals("end")) {
                    List<Node> newPath = new ArrayList<>(path);
                    newPath.add(next);
                    queue.add(new Triple<>(next, newPath, true));
                }
            }
        }

        return String.valueOf(paths.size());

    }



}