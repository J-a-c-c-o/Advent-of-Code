package year2021.day18;


import utils.Option;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;

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
        return new ArrayList<>(utils.getLines("src/year2021/day18/input.txt"));
    }

    public String part1() {

        List<String> lines = getInput();
        String line1 = lines.getFirst();
        SpecialTuple s1 = createSpecialTuple(line1, 0);
        for (int i = 1; i < lines.size(); i++) {
            String line2 = lines.get(i);
            SpecialTuple s2 = createSpecialTuple(line2, 0);

            s1 = merge(s1, s2);

            reduceAndSplit(s1);


        }


        long magnitude = calculateMagnitude(s1);



        return String.valueOf(magnitude);

    }

    public String part2() {

        List<String> lines = getInput();
        long magnitudeMax = 0;

        for (int i = 0; i < lines.size(); i++) {
            for (int j = i + 1; j < lines.size(); j++) {
                String line1 = lines.get(i);
                String line2 = lines.get(j);
                SpecialTuple s1 = createSpecialTuple(line1, 0);
                SpecialTuple s2 = createSpecialTuple(line2, 0);


                s1 = merge(s1, s2);

                reduceAndSplit(s1);


                long magnitude = calculateMagnitude(s1);
                if (magnitude > magnitudeMax) {
                    magnitudeMax = magnitude;
                }
            }



        }

        return String.valueOf(magnitudeMax);
    }

    private long calculateMagnitude(SpecialTuple s1) {

        long left;
        long right;

        if (s1.left.isA()) {
            left = s1.left.getA();
        } else {
            left = calculateMagnitude(s1.left.getB());
        }

        if (s1.right.isA()) {
            right = s1.right.getA();
        } else {
            right = calculateMagnitude(s1.right.getB());
        }

        return left*3 + right*2;
    }

    private void reduce(SpecialTuple s1) {
        SpecialTuple explode = findExplode(s1);


        if (explode == null) {
            return;
        }

        reduceExplode(s1, explode);
    }

    private void split(SpecialTuple s1) {
        List<Option<Integer, SpecialTuple>> stackInteger = new ArrayList<>();
        List<SpecialTuple> allTuples = new ArrayList<>();
        addTuples(s1, allTuples);
        findInteger(s1, stackInteger);

        for (Option<Integer, SpecialTuple> option : stackInteger) {

            if (option.getA() < 10) {
                continue;
            }


            //find the option in allTuples
            for (SpecialTuple tuple : allTuples) {
                if (tuple.hasOption(option)) {
                    int value = option.getA();
                    int left = value / 2;
                    int right = value - left;

                    Option<Integer, SpecialTuple> l1 = new Option<>(left, null);
                    Option<Integer, SpecialTuple> r1 = new Option<>(right, null);

                    SpecialTuple newTuple = new SpecialTuple(tuple.depth + 1, l1, r1);
                    newTuple.setParent(tuple);

                    option.setB(newTuple);
                    option.removeA();


                    if ((tuple.depth + 1) > 3) {
                        reduceExplode(s1, newTuple);
                        reduceAndSplit(s1);
                    }

                    return;
                }
            }

        }
    }

    private void reduceExplode(SpecialTuple s1, SpecialTuple explode) {
        List<Option<Integer, SpecialTuple>> stackInteger = new ArrayList<>();
        List<SpecialTuple> allTuples = new ArrayList<>();
        addTuples(s1, allTuples);
        findInteger(s1, stackInteger);


//        explode.parent.replace(explode, 0);

        Option<Integer, SpecialTuple> optionLeft = explode.parent.left;
        Option<Integer, SpecialTuple> optionRight = explode.parent.right;
        if (optionLeft.isB() && optionLeft.getB().equals(explode)) {
            optionLeft.setA(0);
            optionLeft.removeB();
        } else {
            optionRight.setA(0);
            optionRight.removeB();
        }

        int leftIndex = stackInteger.indexOf(explode.left);
        int rightIndex = stackInteger.indexOf(explode.right);

        if (leftIndex != 0) {
            int left = stackInteger.get(leftIndex).getA();
            Option<Integer, SpecialTuple> OptionLeft = stackInteger.get(leftIndex - 1);
            int newLeft = OptionLeft.getA() + left;
            for (SpecialTuple tuple : allTuples) {
                if (tuple.hasOption(OptionLeft)) {
                    tuple.replace(OptionLeft, newLeft);
                    break;
                }
            }
        }


        if (rightIndex != stackInteger.size() - 1) {
            int right = stackInteger.get(rightIndex).getA();
            Option<Integer, SpecialTuple> OptionRight = stackInteger.get(rightIndex + 1);
            int newRight = right + OptionRight.getA();


            for (SpecialTuple tuple : allTuples) {
                if (tuple.hasOption(OptionRight)) {
                    tuple.replace(OptionRight, newRight);
                    break;
                }
            }
        }

    }

    private SpecialTuple findExplode(SpecialTuple s1) {
        if (s1.depth > 3 && s1.isLeaf()) {
            return s1;
        }

        if (s1.left.isB()) {
            SpecialTuple option = findExplode(s1.left.getB());
            if (option != null) {
                return option;
            }
        }

        if (s1.right.isB()) {
            SpecialTuple option = findExplode(s1.right.getB());
            if (option != null) {
                return option;
            }
        }

        return null;
    }

    private void addTuples(SpecialTuple s1, List<SpecialTuple> allTuples) {
        allTuples.add(s1);
        if (s1.left.isB()) {
            addTuples(s1.left.getB(), allTuples);
        }
        if (s1.right.isB()) {
            addTuples(s1.right.getB(), allTuples);
        }
    }

    private void findInteger(SpecialTuple s1, List<Option<Integer, SpecialTuple>> stackInteger) {
        if (s1.left.isA()) {
            stackInteger.add(s1.left);
        } else {
            findInteger(s1.left.getB(), stackInteger);
        }

        if (s1.right.isA()) {
            stackInteger.add(s1.right);
        } else {
            findInteger(s1.right.getB(), stackInteger);
        }
    }

    private SpecialTuple merge(SpecialTuple s1, SpecialTuple s2) {
        Option<Integer, SpecialTuple> l1 = new Option<>(null, s1);
        Option<Integer, SpecialTuple> r1 = new Option<>(null, s2);

        s1.incrementDepth();
        s2.incrementDepth();

        SpecialTuple tuple = new SpecialTuple(0, l1, r1);
        l1.getB().setParent(tuple);
        r1.getB().setParent(tuple);

        return tuple;
    }

    private SpecialTuple createSpecialTuple(String line1, int depth) {
        String left = "";
        String right = "";
        int cDepth = 0;
        //split for example [[1,2],[1,[2,3]]] -> [1,2],[1,[2,3]]
        for (int i = 1; i < line1.length() - 1; i++) {
            if (line1.charAt(i) == '[') {
                cDepth++;
            } else if (line1.charAt(i) == ']') {
                cDepth--;
            } else if (line1.charAt(i) == ',' && cDepth == 0) {
                left = line1.substring(1, i);
                right = line1.substring(i + 1, line1.length() - 1);
                break;
            }
        }
        Option<Integer, SpecialTuple> l1 = null;
        if (left.length() == 1) {
            l1 = new Option<>(Integer.parseInt(left), null);
        } else {
            l1 = new Option<>(null, createSpecialTuple(left, depth + 1));
        }

        Option<Integer, SpecialTuple> r1 = null;
        if (right.length() == 1) {
            r1 = new Option<>(Integer.parseInt(right), null);
        } else {
            r1 = new Option<>(null, createSpecialTuple(right, depth + 1));
        }


        SpecialTuple tuple = new SpecialTuple(depth, l1, r1);
        if (l1.isB()) {
            l1.getB().setParent(tuple);
        }
        if (r1.isB()) {
            r1.getB().setParent(tuple);
        }

        return tuple;
    }

    private void reduceAndSplit(SpecialTuple s1) {
        String old = "";
        while (!s1.toString().equals(old)) {
            old = s1.toString();
            reduce(s1);

        }

        String old1 = "";
        while (!old1.equals(s1.toString())) {
            old1 = s1.toString();
            split(s1);
        }
    }


}