package nl.jtepoel.AOC.year2025.day10;


import java.util.*;
import com.microsoft.z3.*;

import nl.jtepoel.AOC.utils.Pair;
import nl.jtepoel.AOC.utils.Utils;
import nl.jtepoel.AOC.utils.evaluator.Evaluator;

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

    public List<String> getInput() {
        return utils.getLines(Utils.LOCSRC + "/year2025/day10/input.txt");
    }

    public String part1() {
        List<String> input = getInput();
        long sum = 0;

        List<List<List<Integer>>> switches = new ArrayList<>();
        List<List<Character>> lights = new ArrayList<>();
        for (String line : input) {
            String[] parts = line.split(" ");
            List<Character> light = new ArrayList<>();
            for (char l : parts[0].substring(1, parts[0].length() - 1).toCharArray()) {
                light.add(l);
            }
            lights.add(light);


            List<List<Integer>> poslist = new ArrayList<>();
            for (int i = 1; i < parts.length - 1; i++) {
                String pos = parts[i].substring(1,parts[i].length() - 1);
                String[] split = pos.split(",");
                List<Integer> l = new ArrayList<>();
                for (String s : split) {
                    l.add(Integer.parseInt(s));
                }
                poslist.add(l);

            }
            switches.add(poslist);






        }

        for (int i = 0; i < lights.size(); i++) {
            long amount = recurr(new ArrayList<>(lights.get(i)), new HashSet<>(switches.get(i)), new HashMap<>());
            sum += amount;
        }

        return String.format("%d", sum);
    }

    private long recurr(List<Character> lights, Set<List<Integer>> switches, HashMap<Pair<List<Character>, Set<List<Integer>>>, Long> cache) {
        boolean all_off = true;
        for (Character l : lights) {
            if (l == '#') {
                all_off = false;
                break;
            }
        }
        if (all_off) {
            return 0;
        }

        if (cache.containsKey(Pair.of(lights, switches))) {
            return cache.get(Pair.of(lights, switches));
        }



        long min = Integer.MAX_VALUE;
        for (List<Integer> l : switches) {
            List<Character> ls = new ArrayList<>(lights);
            Set<List<Integer>> switch_pos = new HashSet<>(switches);
            switch_pos.remove(l);

            for (int s : l) {
                ls.set(s, ls.get(s) == '.' ? '#' : '.');
            }

            min = Math.min(min, recurr(ls, switch_pos, cache) + 1);
        }

        cache.put(Pair.of(lights, switches), min);

        return min;

    }

    public String part2() {
        List<String> input = getInput();
        long sum = 0;

        List<List<List<Integer>>> switches = new ArrayList<>();
        List<List<Integer>> lights = new ArrayList<>();
        for (String line : input) {
            String[] parts = line.split(" ");
            List<Integer> light = new ArrayList<>();
            for (String l : parts[parts.length - 1].substring(1,parts[parts.length - 1].length() - 1).split(",")) {
                light.add(Integer.parseInt(l));
            }
            lights.add(light);


            List<List<Integer>> poslist = new ArrayList<>();
            for (int i = 1; i < parts.length - 1; i++) {
                String pos = parts[i].substring(1,parts[i].length() - 1);
                String[] split = pos.split(",");
                List<Integer> l = new ArrayList<>();
                for (String s : split) {
                    l.add(Integer.parseInt(s));
                }
                poslist.add(l);

            }
            switches.add(poslist);
        }


        for (int j = 0; j < lights.size(); j++) {
            List<Integer> buttons = lights.get(j);
            List<List<Integer>> switch_pos = switches.get(j);
            String tempEv="";
            for (int i = 0; i < buttons.size(); i++) {
                int button = buttons.get(i);
                String temp = "";
                for (int t = 0; t < switch_pos.size(); t++) {
                    List<Integer> l = switch_pos.get(t);
                    int in = 0;

                    if (l.contains(i)) {
                        in = 1;
                    }
                    temp += in + " * s" + t + ":INT" + (t != switch_pos.size() - 1 ? " + " : "");
                }

                tempEv += temp + " == " + button + (i != buttons.size() - 1 ? " && " : "");


            }

            String aboveExpr = "";
            String minimize = "";
            for (int i = 0; i < switch_pos.size(); i++) {
                aboveExpr += "0 <= s" + i + ":INT" + (i != switch_pos.size() - 1 ? " && " : "");
                minimize += "s" + i + ":INT" + (i != switch_pos.size() - 1 ? " + " : "");
            }

            tempEv += " && " + aboveExpr;

            Evaluator evaluator = new Evaluator();
            HashMap<String, Expr> evaluate = evaluator.evaluate(tempEv, minimize, null);

            for (Expr expr : evaluate.values()) {
                sum += Long.parseLong(expr.toString());
            }

        }

        return String.format("%d", sum);
    }




}