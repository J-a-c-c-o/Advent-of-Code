package nl.jtepoel.AOC.year2024.day17;


import java.util.*;

import nl.jtepoel.AOC.utils.Pair;
import nl.jtepoel.AOC.utils.Quad;
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

    public Quad<Long,Long,Long,List<Integer>> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day17/input.txt");
        List<Integer> instructions = new ArrayList<>();
        Long A = Long.parseLong(lines.getFirst().split(": ")[1]);
        Long B = Long.parseLong(lines.get(1).split(": ")[1]);
        Long C = Long.parseLong(lines.get(2).split(": ")[1]);

        String split = lines.get(4).split(": ")[1];

        for (String s : split.split(",")) {
            instructions.add(Integer.parseInt(s));
        }


        return new Quad<>(A,B,C,instructions);
    }

    public String part1() {
        Quad<Long,Long,Long,List<Integer>> input = getInput();
        long A = input.getFirst();
        long B = input.getSecond();
        long C = input.getThird();
        List<Integer> instructions = input.getFourth();


        List<Integer> output = run(instructions, A, B, C);


        //out put like 1,1,1
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < output.size(); i++) {
            sb.append(output.get(i));
            if (i != output.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }

    private static List<Integer> run(List<Integer> instructions, long A, long B, long C) {
        List<Integer> output = new ArrayList<>();

        int ip = 0;
        while (ip < instructions.size()) {


            int instruction = instructions.get(ip);
            long s = instructions.get(ip + 1);


            long value = switch ((int) s) {
                case 4 -> A;
                case 5 -> B;
                case 6 -> C;
                default -> s;
            };


            ip += 2;


            switch (instruction) {
                case 0 -> A = (A / (long) Math.pow(2, value));
                //xor
                case 1 -> B = B ^ s;
                case 2 -> B = (value % 8);
                case 3 -> {
                    if (A != 0) {
                        ip = (int) (value);
                    }
                }
                case 4 -> B = B ^ C;
                case 5 -> output.add((int) (value % 8));
                case 6 -> B = (A / (long) Math.pow(2, value));
                case 7 -> C = (A / (long) Math.pow(2, value));

                default -> System.out.println("Invalid instruction");
            }


        }
        return output;
    }

    public String part2() {
        Quad<Long, Long, Long, List<Integer>> input = getInput();
        long B = input.getSecond();
        long C = input.getThird();

        List<Integer> instructions = input.getFourth();

        Queue<Pair<Long, Integer>> queue = new PriorityQueue<>(Comparator.comparingLong(Pair::getFirst));
        queue.add(new Pair<>(0L, 0));

        while (!queue.isEmpty()) {
            Pair<Long, Integer> pair = queue.poll();
            long A = pair.getFirst();
            int i = pair.getSecond();

            if (i >= instructions.size()) {
                return String.valueOf(A);
            }
            for (long j = 0; j < 8; j++) {
                long newA = (A) | (j << (3 * (instructions.size() - 1 - i)));
                List<Integer> output = run(instructions, newA, B, C);
                if (output.size() > i && output.get((output.size() - 1 - i)).equals(instructions.get(instructions.size() - 1 - i))) {
                    queue.add(new Pair<>(newA, i + 1));
                }
            }
        }

        return "No solution found";
    }



}