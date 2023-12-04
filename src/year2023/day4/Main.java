package year2023.day4;


import java.util.HashMap;
import java.util.List;

import utils.Pair;
import utils.Utils;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Part 1: " + main.part1());
        System.out.println("Part 2: " + main.part2());
    }

    public List<Pair<List<String>, List<String>>> getInput() {
        List<String> input = utils.getLines("src/year2023/day4/input.txt");
        List<Pair<List<String>, List<String>>> list = new java.util.ArrayList<>();
        for (String s : input) {
            String split = s.split(": ")[1];
            String[] split2 = split.split(" \\| ");
            List<String> list1 = new java.util.ArrayList<>(List.of(split2[0].split(" ")));
            List<String> list2 = new java.util.ArrayList<>(List.of(split2[1].split(" ")));
            //remove empty string
            while (list1.contains("")) {
                list1.remove("");
            }
            while (list2.contains("")) {
                list2.remove("");
            }

            list.add(new Pair<>(list1, list2));

        }
        System.out.println(list);
        return list;
    }

    public String part1() {
        List<Pair<List<String>, List<String>>> input = getInput();
        int sum = 0;
        for (Pair<List<String>, List<String>> pair : input) {
            List<String> list1 = pair.x;
            List<String> list2 = pair.y;
            list1.retainAll(list2);

            if (list1.size() > 0) {
                sum = (int) (sum + Math.pow(2, (list1.size()-1)));
            }

        }
        return String.valueOf(sum);
    }

    public String part2() {
        List<Pair<List<String>, List<String>>> input = getInput();
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 1; i <= input.size(); i++) {
            map.put(i, 1);
        }

        for (int index : map.keySet()) {
            int amount = map.get(index);
            Pair<List<String>, List<String>> pair = input.get(index - 1);
            List<String> list1 = pair.x;
            List<String> list2 = pair.y;
            list1.retainAll(list2);

            if (list1.size() > 0) {
                for (int i = index + 1; i <= index + list1.size(); i++) {
                    map.put(i, map.getOrDefault(i, 0) + amount);
                }
            }


        }


        int sum = 0;
        for (int j = 1; j <= map.size(); j++) {
            sum += map.getOrDefault(j, 0);
        }
        return String.valueOf(sum);
    }



}