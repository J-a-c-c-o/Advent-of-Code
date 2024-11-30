package nl.jtepoel.AOC.year2021.day19;


import java.util.ArrayList;
import java.util.List;

import nl.jtepoel.AOC.utils.Utils;

public class Main {

    Scanner scanner;
    Utils utils = new Utils();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Main main = new Main();
        main.scanner = main.combine();
        String part1 = main.part1();
        long part1Time = System.currentTimeMillis() - start;
        String part2 = main.part2();
        long part2Time = System.currentTimeMillis() - start - part1Time;

        System.out.println("Part 1: " + part1 + " took " + part1Time + "ms");
        System.out.println("Part 2: " + part2 + " took " + part2Time + "ms");
    }

    public List<Scanner> getInput() {
        List<String> lines =  utils.getLines(Utils.LOCSRC + "/year2021/day19/input.txt");
        List<String> scannerLines = new ArrayList<>();
        List<Scanner> scanners = new ArrayList<>();
        int i = 0;
        for (String line : lines) {
            if (line.isEmpty()) {
                scanners.add(new Scanner(i, scannerLines));
                scannerLines = new ArrayList<>();
                i++;
            } else if (line.startsWith("---")) {
            } else {
                scannerLines.add(line);
            }
        }

        scanners.add(new Scanner(i, scannerLines));

        return scanners;
    }

    public String part1() {
        return scanner.getBeacons().size() + "";
    }

    public String part2() {
        long maxDistance = 0;
        for (Scanner s : scanner.getScanners()) {
            for (Scanner other : scanner.getScanners()) {
                long distance = s.distance(other);
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }

        }

        return maxDistance + "";
    }


    private Scanner combine() {
        List<Scanner> scanners = new ArrayList<>(getInput());
        Scanner scanner = scanners.getFirst();
        scanners.remove(scanner);
        while (!scanners.isEmpty()) {
            List<Scanner> temp = new ArrayList<>(scanners);
            for (Scanner other : scanners) {
                if (scanner.combine(other)) {
                    temp.remove(other);
                }

                scanner.removeDuplicates();
            }


            scanners = new ArrayList<>(temp);
        }
        return scanner;
    }





}