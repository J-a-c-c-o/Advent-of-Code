package nl.jtepoel.AOC.year2023.day2;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.jtepoel.AOC.utils.Utils;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Part 1: " + main.part1());
        System.out.println("Part 2: " + main.part2());
    }

    public List<List<List<List<List<String>>>>> getInput() {
        List<String> list = utils.getLines(Utils.LOCSRC + "/year2023/day2/input.txt");

        List<List<List<List<List<String>>>>> input = new ArrayList<>();
        for (String s : list) {
            List<List<List<List<String>>>> temp = new ArrayList<>();
            String[] split = s.split(";");
            for (String s2 : split) {
                String[] split2 = s2.split(":");
                List<List<List<String>>> temp2 = new ArrayList<>();
                for (String s3 : split2) {
                    String[] split3 = s3.split(",");
                    List<List<String>> temp3 = new ArrayList<>();
                    for (String s4 : split3) {
                        String[] split4 = s4.split(" ");
                        for (int i = 0; i < split4.length; i++) {
                            split4[i] = split4[i].trim();
                        }
                        List<String> temp4 = new ArrayList<>();
                        Collections.addAll(temp4, split4);

                        for (int i = 0; i < temp4.size(); i++) {
                            if (temp4.get(i).equals("")) {
                                temp4.remove(i);
                            }
                        }

                        temp3.add(temp4);
                    }
                    temp2.add(temp3);
                }
                temp.add(temp2);
            }
            input.add(temp);
        }

        return input;

    }

    public String part1() {
        List<List<List<List<List<String>>>>> input = getInput();

        String green = "green";
        String blue = "blue";
        String red = "red";
        long sum_Index = 0;
        int index = 0;
        for (List<List<List<List<String>>>> game : input) {
            index++;
            boolean valid = true;
            for (List<List<List<String>>> player : game) {

                for (List<List<String>> piece : player) {
                    for (List<String> color : piece) {
                        if (color.contains(green)) {
                            if (Integer.parseInt(color.get(0)) > 13) {
                                valid = false;

                            }
                        } else if (color.contains(blue)) {
                            if (Integer.parseInt(color.get(0)) > 14) {
                                valid = false;

                            }
                        } else if (color.contains(red)) {
                            if (Integer.parseInt(color.get(0)) > 12) {
                                valid = false;
                            }
                        }
                        if (!valid) {
                            break;
                        }
                    }
                    if (!valid) {
                        break;
                    }
                }
                if (!valid) {
                    break;
                }
            }
            if (valid) {
                sum_Index += index;
            }



        }

        return String.valueOf(sum_Index);
    }

    public String part2() {
        List<List<List<List<List<String>>>>> input = getInput();

        String green = "green";
        String blue = "blue";
        String red = "red";
        long sum_Index = 0;
        for (List<List<List<List<String>>>> game : input) {
            int redCount = 0;
            int blueCount = 0;
            int greenCount = 0;
            for (List<List<List<String>>> player : game) {

                for (List<List<String>> piece : player) {
                    for (List<String> color : piece) {
                        if (color.contains(green)) {
                            if (Integer.parseInt(color.get(0)) > greenCount) {
                                greenCount = Integer.parseInt(color.get(0));

                            }

                        } else if (color.contains(blue)) {
                            if (Integer.parseInt(color.get(0)) > blueCount) {
                                blueCount = Integer.parseInt(color.get(0));

                            }
                        } else if (color.contains(red)) {
                            if (Integer.parseInt(color.get(0)) > redCount) {
                                redCount = Integer.parseInt(color.get(0));
                            }
                        }

                    }

                }

            }
            sum_Index += (long) redCount * blueCount * greenCount;
        }

        return String.valueOf(sum_Index);
    }




}