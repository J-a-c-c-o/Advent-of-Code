package year2019.day3;


import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Part 1: " + main.part1());
        System.out.println("Part 2: " + main.part2());
    }

    public List<List<String>> getInput() {
        List<String> numbers = utils.getLines("src/year2019/day3/input.txt");
        List<String> split = Arrays.stream(numbers.get(0).split(",")).toList();
        List<String> split2 = Arrays.stream(numbers.get(1).split(",")).toList();



        List<List<String>> resultList = new ArrayList<>();

        resultList.add(split);
        resultList.add(split2);



        return resultList;

    }

    public String part1() {
        Wire wire = new Wire(getInput().get(0));
        Wire wire2 = new Wire(getInput().get(1));

        wire.getIntersection(wire2, true);

        return String.valueOf(wire.getDistance());
    }

    public String part2() {
        Wire wire = new Wire(getInput().get(0));
        Wire wire2 = new Wire(getInput().get(1));

        wire.getIntersection(wire2, false);

        return String.valueOf(wire.getDistance2(wire2));
    }



}