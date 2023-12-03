package year2023.day3;


import java.util.ArrayList;
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

    public String[][] getInput() {
        List<String> input = utils.getLines("src/year2023/day3/input.txt");
        String[][] map = new String[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            String[] split = input.get(i).split("");
            System.arraycopy(split, 0, map[i], 0, split.length);
        }



        return map;
    }

    public String part1() {
        String[][] map = getInput();
        List<Integer> invalid = new ArrayList<>();

        List<Integer> all = new ArrayList<>();
        for (int y = 0; y < map.length; y++) {
            int num = 0;
            boolean valid = false;
            for (int x = 0; x < map[0].length; x++) {
                //check if number
                char c = map[y][x].charAt(0);
                //check if digit
                if (Character.isDigit(c)) {
                    num = num * 10 + Character.getNumericValue(c);
                    if (y > 0 && x > 0) {
                        char c2 = map[y - 1][x - 1].charAt(0);
                        if (!Character.isDigit(c2) && !(c2 == '.')) {
                            valid = true;
                        }
                    }
                    if (y > 0) {
                        char c2 = map[y - 1][x].charAt(0);
                        if (!Character.isDigit(c2) && !(c2 == '.')) {
                            valid = true;
                        }
                    }
                    if (y > 0 && x < map[0].length - 1) {
                        char c2 = map[y - 1][x + 1].charAt(0);
                        if (!Character.isDigit(c2) && !(c2 == '.')) {
                            valid = true;
                        }
                    }
                    if (x > 0) {
                        char c2 = map[y][x - 1].charAt(0);
                        if (!Character.isDigit(c2) && !(c2 == '.')) {
                            valid = true;
                        }
                    }
                    if (x < map[0].length - 1) {
                        char c2 = map[y][x + 1].charAt(0);
                        if (!Character.isDigit(c2) && !(c2 == '.')) {
                            valid = true;
                        }
                    }
                    if (y < map.length - 1 && x > 0) {
                        char c2 = map[y + 1][x - 1].charAt(0);
                        if (!Character.isDigit(c2) && !(c2 == '.')) {
                            valid = true;
                        }
                    }
                    if (y < map.length - 1) {
                        char c2 = map[y + 1][x].charAt(0);
                        if (!Character.isDigit(c2) && !(c2 == '.')) {
                            valid = true;
                        }
                    }
                    if (y < map.length - 1 && x < map[0].length - 1) {
                        char c2 = map[y + 1][x + 1].charAt(0);
                        if (!Character.isDigit(c2) && !(c2 == '.')) {
                            valid = true;
                        }
                    }


                } else {

                    if (!valid && num != 0) {
                        invalid.add(num);
                    }

                    if (num != 0) {
                        all.add(num);
                    }

                    num = 0;
                    valid = false;
                }
            }

            if (!valid && num != 0) {
                invalid.add(num);
            }

            if (num != 0) {
                all.add(num);
            }
        }

        int sum = 0;

        for (int i : all) {
            sum += i;
        }

        for (int i : invalid) {
            sum -= i;
        }

        return String.valueOf(sum);
    }

    public String part2() {
        String[][] map = getInput();
        HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> gear = new HashMap<>();
        for (int y = 0; y < map.length; y++) {
            int num = 0;
            boolean valid = false;
            Integer[] gearPos = new Integer[2];
            for (int x = 0; x < map[0].length; x++) {
                //check if number
                char c = map[y][x].charAt(0);
                //check if digit
                if (Character.isDigit(c)) {
                    num = num * 10 + Character.getNumericValue(c);
                    if (y > 0 && x > 0) {
                        char c2 = map[y - 1][x - 1].charAt(0);
                        if (c2 == '*') {
                            valid = true;
                            gearPos[0] = y - 1;
                            gearPos[1] = x - 1;
                        }
                    }
                    if (y > 0) {
                        char c2 = map[y - 1][x].charAt(0);
                        if (c2 == '*') {
                            valid = true;
                            gearPos[0] = y - 1;
                            gearPos[1] = x;
                        }
                    }
                    if (y > 0 && x < map[0].length - 1) {
                        char c2 = map[y - 1][x + 1].charAt(0);
                        if (c2 == '*') {
                            valid = true;
                            gearPos[0] = y - 1;
                            gearPos[1] = x + 1;
                        }
                    }
                    if (x > 0) {
                        char c2 = map[y][x - 1].charAt(0);
                        if (c2 == '*') {
                            valid = true;
                            gearPos[0] = y;
                            gearPos[1] = x - 1;
                        }
                    }
                    if (x < map[0].length - 1) {
                        char c2 = map[y][x + 1].charAt(0);
                        if (c2 == '*') {
                            valid = true;
                            gearPos[0] = y;
                            gearPos[1] = x + 1;
                        }
                    }
                    if (y < map.length - 1 && x > 0) {
                        char c2 = map[y + 1][x - 1].charAt(0);
                        if (c2 == '*') {
                            valid = true;
                            gearPos[0] = y + 1;
                            gearPos[1] = x - 1;
                        }
                    }
                    if (y < map.length - 1) {
                        char c2 = map[y + 1][x].charAt(0);
                        if (c2 == '*') {
                            valid = true;
                            gearPos[0] = y + 1;
                            gearPos[1] = x;
                        }
                    }
                    if (y < map.length - 1 && x < map[0].length - 1) {
                        char c2 = map[y + 1][x + 1].charAt(0);
                        if (c2 == '*') {
                            valid = true;
                            gearPos[0] = y + 1;
                            gearPos[1] = x + 1;
                        }
                    }


                } else {


                    AdderP2(gear, num, valid, gearPos);

                    num = 0;
                    valid = false;
                }
            }
            AdderP2(gear, num, valid, gearPos);
        }

        int sum = 0;

        for (Pair<Integer, Integer> p : gear.keySet()) {
            Pair<Integer, Integer> p2 = gear.get(p);
            if (p2.y == 2) {
                sum += p2.x;
            }
        }

        return String.valueOf(sum);
    }

    private void AdderP2(HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> gear, int num, boolean valid, Integer[] gearPos) {
        if (valid && num != 0) {
            Pair<Integer, Integer> p = new Pair<>(gearPos[0], gearPos[1]);
            if (gear.containsKey(p)) {
                Pair<Integer, Integer> p2 = gear.get(p);
                gear.put(p, new Pair<>(p2.x * num, p2.y + 1));
            } else {
                gear.put(p, new Pair<>(num, 1));
            }

        }
    }


}