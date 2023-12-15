package year2023.day15;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.Utils;

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

    public String[] getInput() {
        List<String> lines = utils.getLines("src/year2023/day15/input.txt");
        String line = lines.get(0);
        return line.split(",");
    }

    public String part1() {
        long total = 0;
        for (String s : getInput()) {
            total += HashAlgo(s);
        }

        return String.valueOf(total);
    }

    private static long HashAlgo(String s) {
        char[] chars = s.toCharArray();
        int temp = 0;
        for (char c : chars) {
            temp += c;
            temp *= 17;
            temp %= 256;
        }

        return temp;
    }

    public String part2() {
        String[] input = getInput();
        HashMap<Long, List<String>> boxes = new HashMap<>();
        HashMap<String, String> map = new HashMap<>();
        for (String s : input) {
            char[] chars = s.toCharArray();
            StringBuilder s1 = new StringBuilder();
            for (char c : chars) {
                if (c == '-') {
                    long box = HashAlgo(s1.toString());

                    if (boxes.containsKey(box)) {
                        String list = map.get(s1.toString());
                        boxes.get(box).remove(list);
                        map.remove(s1.toString());
                    }

                    break;

                } else if (c == '=') {
                    long box = HashAlgo(s1.toString());
                    if (boxes.containsKey(box)) {
                        List<String> list = boxes.get(box);
                        if (map.containsKey(s1.toString())) {
                            String s2 = map.get(s1.toString());
                            int index = list.indexOf(s2);
                            list.remove(index);
                            list.add(index, s);
                        } else {
                            list.add(s);
                        }
                    } else {
                        List<String> list = new ArrayList<>();
                        list.add(s);
                        boxes.put(box, list);
                    }

                    if (map.containsKey(s1.toString())) {
                        map.replace(s1.toString(), s);
                    } else {
                        map.put(s1.toString(), s);
                    }

                    break;
                }

                s1.append(c);

            }
        }

        System.out.println(boxes);

        long total = 0;
        for (long l : boxes.keySet()) {
            List<String> strings = boxes.get(l);
            int slot = 0;
            for (String s : strings) {
                slot++;
                total += (l + 1) * slot * Integer.parseInt(s.charAt(s.length() - 1) + "");
            }
        }


        return String.valueOf(total);
    }



}