package nl.jtepoel.AOC.year2025.day8;


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

    public List<Triple<Long,Long,Long>> getInput() {
        List<String> input = utils.getLines(Utils.LOCSRC + "/year2025/day8/input.txt");
        List<Triple<Long,Long,Long>> triples = new ArrayList<>();
        for (String line : input) {
            String[] parts = line.split(",");
            Triple<Long,Long,Long> t =  new Triple<>(Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2]));
            triples.add(t);
        }

        return triples;
    }

    public String part1() {
        List<Triple<Long,Long,Long>> triples = getInput();
        List<Triple<Triple<Long,Long,Long>, Triple<Long,Long,Long>, Double>> pairs = new ArrayList<>();

        for (int i = 0; i < triples.size(); i++) {
            Triple<Long,Long,Long> triple = triples.get(i);
            for (int j = i + 1; j < triples.size(); j++) {
                Triple<Long,Long,Long> triple2 = triples.get(j);

                pairs.add(new Triple<>(triple,triple2,distance(triple,triple2)));

            }

        }

        pairs.sort(Comparator.comparingDouble(Triple::getThird));

        pairs = pairs.subList(0,1000);


        //combine into sets
        UnionFind<Triple<Long,Long,Long>> uf = new UnionFind<>();
        for (Triple<Triple<Long,Long,Long>, Triple<Long,Long,Long>, Double> pair : pairs) {
            uf.union(pair.x, pair.y);
        }



        List<List<Triple<Long,Long,Long>>> circuits = uf.findAllComponents();

        circuits.sort(Comparator.comparingInt(List::size));


        return "" + (circuits.get(circuits.size() - 1).size() * circuits.get(circuits.size() - 2).size() * circuits.get(circuits.size() - 3).size());
    }





    public String part2() {
        List<Triple<Long,Long,Long>> triples = getInput();
        List<Triple<Triple<Long,Long,Long>, Triple<Long,Long,Long>, Double>> pairs = new ArrayList<>();

        for (int i = 0; i < triples.size(); i++) {
            Triple<Long,Long,Long> triple = triples.get(i);
            for (int j = i + 1; j < triples.size(); j++) {
                Triple<Long,Long,Long> triple2 = triples.get(j);

                pairs.add(new Triple<>(triple,triple2,distance(triple,triple2)));

            }

        }

        pairs.sort(Comparator.comparingDouble(Triple::getThird));


        //combine into sets
        UnionFind<Triple<Long,Long,Long>> uf = new UnionFind<>();

        int i = 0;
        while (uf.findAllComponents().isEmpty() || uf.findAllComponents().getFirst().size() < triples.size()) {
            Triple<Triple<Long,Long,Long>, Triple<Long,Long,Long>, Double> pair = pairs.get(i);
            uf.union(pair.x, pair.y);
            i++;
        }



        return "" + (pairs.get(i-1).x.x * pairs.get(i-1).y.x);
    }


    private double distance(Triple<Long,Long,Long> a, Triple<Long,Long,Long> b) {
        return Math.sqrt(Math.pow((a.x-b.x), 2) +  Math.pow((a.y-b.y), 2)  +  Math.pow((a.z-b.z), 2));
    }



}