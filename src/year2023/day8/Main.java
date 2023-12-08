package year2023.day8;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.Utils;

public class Main {

    String[] path;

    Utils utils = new Utils();

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Part 1: " + main.part1());
        System.out.println("Part 2: " + main.part2());
    }

    public HashMap<String, Haunted> getInput() {
        List<String> input = utils.getLines("src/year2023/day8/input.txt");


        path = input.get(0).split("");


        HashMap<String, Haunted> map = new HashMap<>();


        for (int i = 2; i < input.size(); i++) {
            String[] split1 = input.get(i).split(" = ");
            Haunted haunted = new Haunted(split1[0]);
            map.put(split1[0], haunted);
        }



        for (int i = 2; i < input.size(); i++) {
            String[] split1 = input.get(i).split(" = ");
            String[] split = split1[1].split(", ");
            String x = split[0].replaceAll("\\(", "");
            String y = split[1].replaceAll("\\)", "");

            Haunted haunted = map.get(split1[0]);
            Haunted haunted2 = map.get(x);
            Haunted haunted3 = map.get(y);

            haunted.setLeft(haunted2);
            haunted.setRight(haunted3);
        }

        return map;

    }

    public String part1() {
        HashMap<String, Haunted> map = getInput();

        HashMap<String, List<Integer>> isInLoop = new HashMap<>();

        Haunted current = map.get("AAA");

        int i = 0;
        int count = 0;
        while (!current.getName().equals("ZZZ")) {

            if (isInLoop.containsKey(current.getName())) {
                List<Integer> list = isInLoop.get(current.getName());
                if (list.contains(i)) {
                    return count + " infinite";
                } else {
                    list.add(i);
                    isInLoop.put(current.getName(), list);
                }
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                isInLoop.put(current.getName(), list);
            }

            if (path[i].equals("L")) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }

            count += 1;
            i = (i + 1) % path.length;

        }

        return count + "";


    }

    public String part2() {
        HashMap<String, Haunted> map = getInput();

        List<Haunted> currents = new ArrayList<>();

        //every not that ends with A
        for (Haunted haunted : map.values()) {
            if (haunted.getName().toCharArray()[2] == 'A') {
                currents.add(haunted);
            }
        }


        List<Integer> steps = new ArrayList<>();
        for (Haunted current : currents) {
            int step = 0;
            int i = 0;
            while (i < path.length) {
                if (path[i].equals("L")) {
                    current = current.getLeft();
                } else {
                    current = current.getRight();
                }

                //if current node ends with Z
                if (current.getName().toCharArray()[2] == 'Z') {
                    step += 1;
                    steps.add(step);
                    break;
                }

                step += 1;
                i = (i + 1) % path.length;
            }

        }


        long output = steps.remove(0);


        for (int step : steps) {
            output = lcm(output, step);
        }

        return output + "";

    }


    //thanks StackOverflow
    private static long lcm(long a, long b)
    {
        return a * (b / gcd(a, b));
    }

    private static long gcd(long a, long b)
    {
        while (b > 0)
        {
            long temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }




}