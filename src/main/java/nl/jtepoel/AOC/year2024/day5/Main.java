package nl.jtepoel.AOC.year2024.day5;


import java.util.*;
import java.util.stream.Collectors;

import nl.jtepoel.AOC.utils.Pair;
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

    public Pair<List<Pair<Integer, Integer>>, List<List<Integer>>> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day5/input.txt");
        List<Pair<Integer,Integer>> rules = new ArrayList<>();
        List<List<Integer>> pageNumbers = new ArrayList<>();
        boolean s = false;
        for (String line : lines) {
            if (line.isBlank()) {
                s = true;
                continue;
            }

            if(!s) {
                String[] sp = line.split("\\|");
                rules.add(new Pair<>(Integer.parseInt(sp[0]),Integer.parseInt(sp[1])));
            } else {
                String[] sp = line.split(",");
                List<Integer> array = new ArrayList<>();
                for (String ss : sp) {
                    array.add(Integer.parseInt(ss));
                }
                pageNumbers.add(array);

            }

        }

        Pair< List<Pair<Integer,Integer>>,List<List<Integer>>> pair = new Pair<>(rules, pageNumbers);

        return pair;
    }

    private final List<List<Integer>> correct = new ArrayList<>();
    private final List<List<Integer>> incorrect = new ArrayList<>();
    private final HashMap<Integer, HashSet<Integer>> after = new HashMap<>();

    public String part1() {
        Pair< List<Pair<Integer,Integer>>,List<List<Integer>>> pair = getInput();
        for (Pair<Integer,Integer> rule : pair.x) {
            after.putIfAbsent(rule.x, new HashSet<>());
            after.get(rule.x).add(rule.y);
        }

        splitCorrectIncorrect(pair);

        int middle = correct.stream().mapToInt(p -> p.get(p.size()/2)).sum();


        return String.valueOf(middle);

    }



    public String part2() {
        for (List<Integer> page : incorrect) {
            for (int i = 0; i < page.size(); i++) {
                for (int j = i + 1; j < page.size(); j++) {
                    if (!after.containsKey(page.get(i)) || !after.get(page.get(i)).contains(page.get(j))) {
                        Collections.swap(page, i, j);
                    }

                }
            }
        }

        int middle = incorrect.stream().mapToInt(p -> p.get(p.size()/2)).sum();

        return String.valueOf(middle);
    }

    private void splitCorrectIncorrect(Pair<List<Pair<Integer, Integer>>, List<List<Integer>>> pair) {
        for (List<Integer> page : pair.y) {
            boolean cor = true;
            for (int i = 0; i < page.size(); i++) {
                for (int j = i + 1; j < page.size(); j++) {
                    if (!after.containsKey(page.get(i)) || !after.get(page.get(i)).contains(page.get(j))) {
                        cor = false;
                        break;
                    }
                }
                if (!cor) {
                    break;
                }
            }
            if (cor) {
                correct.add(page);
            } else {
                incorrect.add(page);
            }
        }
    }
}