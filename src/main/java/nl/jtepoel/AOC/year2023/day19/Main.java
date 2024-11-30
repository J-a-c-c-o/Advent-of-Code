package nl.jtepoel.AOC.year2023.day19;


import nl.jtepoel.AOC.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {



    Utils utils = new Utils();

    HashMap<String, String[]> workflows;
    List<HashMap<Character, Integer>> ratings;

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

    public void getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2023/day19/example.txt");

        workflows = new HashMap<>();
        ratings = new ArrayList<>();

        int i = 0;
        while (lines.get(i).length() > 1) {
            String line = lines.get(i);
            //remove last char
            line = line.substring(0, line.length() - 1);
            String[] split = line.split("\\{");

            workflows.put(split[0], split[1].split(","));

            i++;
        }


        i++;

        while (i < lines.size()) {
            String line = lines.get(i);
            line = line.substring(1, line.length() - 1);
            String[] split = line.split(",");
            HashMap<Character, Integer> temp = new HashMap<>();
            for (String s : split) {
                String[] split2 = s.split("=");
                temp.put(split2[0].charAt(0), Integer.parseInt(split2[1]));
            }

            ratings.add(temp);
            i++;
        }


    }


    public String part1() {
        getInput();

        int sum = 0;

        for (HashMap<Character, Integer> rating : ratings) {
            boolean hold = getAllowed(rating, "in");

            if (hold) {
                sum += rating.get('a') + rating.get('s') + rating.get('x') + rating.get('m');
            }

        }


        return String.valueOf(sum);
    }

    private boolean getAllowed(HashMap<Character, Integer> rating, String next) {
        String[] workflow = workflows.get(next);

        for (String it : workflow) {
            if (it.equals("R")) {
                return false;
            }
            if (it.equals("A")) {
                return true;
            }

            if (!it.contains(":")) {
                return getAllowed(rating, it);
            }

            String condition = it.split(":")[0];
            String next2 = it.split(":")[1];
            String[] split = condition.split("", 3);
            char first = split[0].charAt(0);
            int firstNum = rating.get(first);
            int secondNum = Integer.parseInt(split[2]);
            String operator = split[1];

            if (operator.equals(">")) {
                if (firstNum > secondNum) {

                    if (next2.equals("A")) {
                        return true;
                    }
                    if (next2.equals("R")) {
                        return false;
                    }


                    return getAllowed(rating, next2);
                }
            } else if (operator.equals("<") && (firstNum < secondNum)) {
                if (next2.equals("A")) {
                    return true;
                }
                if (next2.equals("R")) {
                    return false;
                }

                return getAllowed(rating, next2);

            }


        }

        return false;
    }

    public String part2() {
        getInput();

        HashMap<String, int[]> initialRange = new HashMap<>();
        initialRange.put("x", new int[]{1, 4000});
        initialRange.put("m", new int[]{1, 4000});
        initialRange.put("a", new int[]{1, 4000});
        initialRange.put("s", new int[]{1, 4000});



        long sum = getSum(initialRange, "in", workflows);




        return String.valueOf(sum);



    }

    private long calculateSize(Map<String, int[]> range) {
        long res = 1;
        for (int[] i : range.values()) {
            res *= i[1] - i[0] + 1;
        }
        return res;
    }

    private long getSum(HashMap<String, int[]> range, String workflow, Map<String, String[]> workflows) {
        long sum = 0;
        for (String instructions : workflows.get(workflow)) {
            if (instructions.contains(":")) {
                String[] condAct = instructions.split(":");
                String condition = condAct[0];
                String goTo = condAct[1];
                if (condition.contains(">")) {
                    String[] compare = condition.split(">");
                    String leftSide = compare[0];
                    int rightSide = Integer.parseInt(compare[1]);
                    HashMap<String, int[]> newRange = deepCopy(range);
                    if (newRange.get(leftSide)[1] > rightSide) {
                        newRange.get(leftSide)[0] = Math.max(newRange.get(leftSide)[0], rightSide + 1);
                        if (goTo.equals("A")) {
                            sum += calculateSize(newRange);
                        } else if (!goTo.equals("R")) {
                            sum += getSum(newRange, goTo, workflows);
                        }
                        range.get(leftSide)[1] = Math.min(range.get(leftSide)[1], rightSide);
                    }
                }
                if (condition.contains("<")) {
                    String[] compare = condition.split("<");
                    String leftSide = compare[0];
                    int rightSide = Integer.parseInt(compare[1]);
                    HashMap<String, int[]> newRange = deepCopy(range);
                    if (newRange.get(leftSide)[0] < rightSide) {
                        newRange.get(leftSide)[1] = Math.min(newRange.get(leftSide)[1], rightSide - 1);
                        if (goTo.equals("A")) {
                            sum += calculateSize(newRange);
                        } else if (!goTo.equals("R")) {
                            sum += getSum(newRange, goTo, workflows);
                        }
                        range.get(leftSide)[0] = Math.max(range.get(leftSide)[0], rightSide);
                    }
                }
            } else {
                if (instructions.equals("A")) {
                    sum += calculateSize(range);
                } else if (!instructions.equals("R")) {
                    sum += getSum(range, instructions, workflows);
                }
            }
        }
        return sum;
    }

    private HashMap<String, int[]> deepCopy(HashMap<String, int[]> original) {
        HashMap<String, int[]> copy = new HashMap<>();

        for (Map.Entry<String, int[]> entry : original.entrySet()) {
            copy.put(entry.getKey(), entry.getValue().clone());
        }
        return copy;
    }



}