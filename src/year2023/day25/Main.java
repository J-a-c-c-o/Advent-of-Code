package year2023.day25;


import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.graphstream.algorithm.ConnectedComponents;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import utils.Utils;

public class Main {

    Utils utils = new Utils();

    HashMap<String, Node> nodes = new HashMap<>();

    HashMap<String, List<String>> connections = new HashMap<>();


    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");
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
        List<String> lines = utils.getLines("src/year2023/day25/input.txt");

        for (String line : lines) {
            String[] split = line.split(": ");
            String[] split2 = split[1].split(" ");

            connections.put(split[0], List.of(split2));
        }
    }

    public String part1() {
        Graph graph = new SingleGraph("A Long Walk");

        getInput();

        for (String key : connections.keySet()) {
            Node node;
            if (!nodes.containsKey(key)) {
                node = graph.addNode(key);
                nodes.put(key, node);
            } else {
                node = nodes.get(key);
            }
            List<String> list = connections.get(key);
            for (String string : list) {
                Node node2;
                if (!nodes.containsKey(string)) {
                    node2 = graph.addNode(string);
                    nodes.put(string, node2);
                } else {
                    node2 = nodes.get(string);
                }


                graph.addEdge(key + " " + string, node, node2).setAttribute("ui.label", key + " " + string);
            }
        }

        graph.display();


        ConnectedComponents cc = new ConnectedComponents();

        cc.init(graph);

        Scanner scanner = new Scanner(System.in);

        String[] edges = new String[2];
        for (int i = 0; i < 3; i++) {
            System.out.println("input the name of the edge you want to remove");

            String input = scanner.nextLine();

            graph.removeEdge(input);


            edges = input.split(" ");
        }



        System.out.printf("The graph has %d connected component(s)%n", cc.getConnectedComponentsCount());


        //count the number of nodes in all the connected components
        int count = 1;

        for (String edge : edges) {
            count *= cc.getConnectedComponentOf(nodes.get(edge)).getNodeCount();
        }




        return String.valueOf(count);
    }

    public String part2() {
        return "\uD83C\uDF1F\uD83C\uDF1F";
    }



}