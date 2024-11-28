package year2023.day11;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utils.Pair;
import utils.Utils;

public class Main {

    Utils utils = new Utils();

    List<int[]> rootNodes = new ArrayList<>();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Main main = new Main();
        String part1 = main.parts(2);
        long part1Time = System.currentTimeMillis() - start;
        String part2 = main.parts(1000000);
        long part2Time = System.currentTimeMillis() - start - part1Time;

        System.out.println("Part 1: " + part1 + " took " + part1Time + "ms");
        System.out.println("Part 2: " + part2 + " took " + part2Time + "ms");
    }

    public String[][] getInput() {
        List<String> input = utils.getLines("src/main/java/year2023/day11/input.txt");

        rootNodes = new ArrayList<>();

        // expand the grid every time we see a line with only . then 2x the line
        List<String[]> expandedInput = new ArrayList<>();
        for (String s : input) {
            String[] split = s.split("");
            addVoid(expandedInput, s, split);
        }

        List<String[]> columns = new ArrayList<>();
        for (int i = 0; i < expandedInput.get(0).length; i++) {
            String[] column = new String[expandedInput.size()];
            for (int j = 0; j < expandedInput.size(); j++) {
                column[j] = expandedInput.get(j)[i];
            }
            columns.add(column);
        }

        List<String[]> expandedColumns = new ArrayList<>();
        for (String[] s : columns) {
            String string = Arrays.toString(s);
            addVoid(expandedColumns, string, s);
        }

        String[][] expandedInputArray = new String[expandedColumns.get(0).length][expandedColumns.size()];

        for (int i = 0; i < expandedColumns.size(); i++) {
            String[] column = expandedColumns.get(i);
            for (int j = 0; j < column.length; j++) {
                expandedInputArray[j][i] = column[j];
            }
        }


        for (int i = 0; i < expandedInputArray.length; i++) {
            String[] split = expandedInputArray[i];
            for (int j = 0; j < split.length; j++) {
                String c = split[j];
                if (c.equals("#")) {
                    rootNodes.add(new int[]{i, j});
                }
            }
        }

        return expandedInputArray;
    }

    private void addVoid(List<String[]> expandedInput, String s, String[] split) {
        if (!s.contains("#")) {
            String[] expanded = new String[split.length];
            for (int i = 0; i < split.length; i++) {
                expanded[i] = "void";
            }
            expandedInput.add(expanded);
        } else {
            expandedInput.add(split);
        }
    }

    public String parts(int amountOfVoid) {
        String[][] input = getInput();

        System.out.println("Nodes: " + rootNodes.size());

        long sum = 0;
        List<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> pairs = new ArrayList<>();
        for (int[] node : rootNodes) {
            List<int[]> destinations = new ArrayList<>(rootNodes);
            destinations.remove(node);

            for (int[] destination : destinations) {
                if (pairs.contains(new Pair<>(new Pair<>(node[0], node[1]), new Pair<>(destination[0], destination[1])))) {
                    continue;
                }
                pairs.add(new Pair<>(new Pair<>(node[0], node[1]), new Pair<>(destination[0], destination[1])));
                pairs.add(new Pair<>(new Pair<>(destination[0], destination[1]), new Pair<>(node[0], node[1])));
                int[] startCoords = new int[]{node[0], node[1]};
                int distance = manhattanDistance(startCoords, destination, input, amountOfVoid);
                sum += distance;
            }

        }

        return sum + "";
    }

    public int manhattanDistance(int[] start, int[] end, String[][] input, int amountOfVoid) {

        //if passing through void add amountOfVoid to the distance
        int distance = 0;
        int[] current = new int[]{start[0], start[1]}; // Create a new array for current

        while (current[0] != end[0] || current[1] != end[1]) {
            if (current[0] < end[0]) {
                current[0]++;

            } else if (current[0] > end[0]) {
                current[0]--;
            } else if (current[1] < end[1]) {
                current[1]++;
            } else {
                current[1]--;
            }

            if (input[current[0]][current[1]].equals("void")) {
                distance += amountOfVoid;
            } else {
                distance++;
            }
        }
        return distance;
    }


    public String part2() {
        return "";
    }



}