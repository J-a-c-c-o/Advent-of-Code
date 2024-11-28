package year2021.day10;


import java.util.ArrayList;
import java.util.List;

import utils.FrequencySet;
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

    public List<String> getInput() {
        return utils.getLines("src/main/java/year2021/day10/input.txt");
    }

    List<String> incomplete = new ArrayList<>();

    public String part1() {
        List<String> lines = getInput();
        FrequencySet<Character> fs = new FrequencySet<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            int newSize = line.length();
            int oldSize = 0;

            while (oldSize-newSize != 0) {
                line = line.replaceAll("\\(\\)", "");
                line = line.replaceAll("\\{}", "");
                line = line.replaceAll("\\[]", "");
                line = line.replaceAll("<>", "");
                oldSize = newSize;
                newSize = line.length();
            }

            char c = findIncorrect(line);

            if (c != ' ') {
                fs.add(c);
            } else {
                incomplete.add(line);
            }



        }

        return String.valueOf(fs.get(')')*3 + fs.get(']')*57 + fs.get('}')*1197 + fs.get('>')*25137);
    }

    private char findIncorrect(String line) {
        char[] chars = line.toCharArray();
        for (int i = 0; i < line.length()-1; i++) {
            char l = chars[i];
            char r = chars[i+1];

            switch (l) {
                case '(':
                    if (checkRight(r, List.of('}', ']', '>'))){
                        return r;
                    }
                    break;

                case '{':
                    if (checkRight(r, List.of(')', ']', '>'))){
                        return r;
                    }
                    break;

                case '[':
                    if (checkRight(r, List.of('}', ')', '>'))){
                        return r;
                    }
                    break;

                case '<':
                    if (checkRight(r, List.of('}', ']', ')'))){
                        return r;
                    }
                    break;
            }
        }

        return ' ';
    }

    private boolean checkRight(char r, List<Character>  chars) {
        return chars.contains(r);
    }

    public String part2() {
        List<Long> scores = new ArrayList<>();
        for (String line : incomplete) {
            long total = 0;
            for (int i = line.length()-1; i >= 0; i--) {
                total*=5;
                if (line.charAt(i) == '(') {
                    total += 1;
                } else if (line.charAt(i) == '[') {
                    total += 2;
                } else if (line.charAt(i) == '{') {
                    total += 3;
                } else if (line.charAt(i) == '<') {
                    total += 4;
                }
            }

            scores.add(total);
        }

        scores.sort(Long::compareTo);

        return String.valueOf(scores.get(scores.size()/2));
    }



}