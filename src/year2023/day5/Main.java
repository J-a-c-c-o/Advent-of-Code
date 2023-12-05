package year2023.day5;


import utils.Pair;
import utils.Utils;

import java.util.*;

public class Main {

    private String[] seeds;
    private List<List<String>> maps;

    Utils utils = new Utils();

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Part 1: " + main.part1());
        System.out.println("Part 2: " + main.part2());
    }

    public List<List<String>> getInput() {
        List<String> input = utils.getLines("src/year2023/day5/input.txt");
        String[] tempseeds = input.get(0).split(" ");


        seeds = new String[tempseeds.length - 1];
        for (int i = 0; i < seeds.length; i++) {
            seeds[i] = tempseeds[i+1];
        }






        input = new ArrayList<>(input);

        input.remove(0);
        input.remove(0);
        List<String> row = new ArrayList<>();
        List<List<String>> input2 = new ArrayList<>();
        for (String s : input) {

            if (s.isEmpty()) {
                input2.add(row);
                row = new ArrayList<>();
            } else {

                //split the spaces
                s = s.replace(" map:", "");
                String[] split = s.split(" ");
                Collections.addAll(row, split);
            }
        }
        input2.add(row);

        for (List<String> list : input2) {
            list.remove(0);
        }

        return input2;
    }

    public String part1() {

        List<List<String>> maps = getInput();
        String[] cseed = seeds;
        for (List<String> map : maps) {
            cseed = getLocations(map, cseed);


        }




        // return min value
        long min = Long.MAX_VALUE;
        for (String s : cseed) {
            if (Long.parseLong(s) < min) {
                min = Long.parseLong(s);
            }
        }

        return String.valueOf(min);






    }

    public long part2() {
        List<List<String>> maps = getInput();


        List<Pair<Long,Long>> ranges = new ArrayList<>();
        for (int i = 0; i < seeds.length; i += 2) {
            ranges.add(new Pair<>(Long.parseLong(seeds[i]), Long.parseLong(seeds[i + 1])));
        }


        for (List<String> map : maps) {
            ranges = getLocations2(map, ranges);
        }





        // return min value
        long min = Long.MAX_VALUE;
        for (Pair<Long,Long> s : ranges) {
            if (s.x < min) {
                min = s.x;
            }
        }

        return min;
    }


    private List<Pair<Long,Long>> getLocations2(List<String> map, List<Pair<Long,Long>> ranges) {
        List<Pair<Long,Long>> updatedRanges = new ArrayList<>();
        HashMap<Long, Pair<Long, Long>> locations = new HashMap<>();
        for (int i = 0; i < map.size(); i += 3) {
            long x = Long.parseLong(map.get(i));
            long y = Long.parseLong(map.get(i + 1));
            long z = Long.parseLong(map.get(i + 2));

            locations.put(x, new Pair<>(y, z));

        }

        for (Long x : locations.keySet()) {

            Pair<Long, Long> location = locations.get(x);
            long y = location.x;
            long z = location.y;

            for (int j = 0; j < ranges.size(); j++) {
                Pair<Long, Long> currentRange = ranges.get(j);
                if (currentRange.x < y) {
                    j = handleBelowRange(ranges, y, j, currentRange);
                } else {
                    j = handleAboveRange(ranges, updatedRanges, x, y, z, j, currentRange);
                }
            }
        }


        updatedRanges.addAll(ranges);
        ranges = updatedRanges;
        return ranges;
    }

    private static int handleBelowRange(List<Pair<Long, Long>> ranges, long y, int j, Pair<Long, Long> currentRange) {
        if ((currentRange.x + currentRange.y) > y) {
            long rangeLength = y - currentRange.x;
            ranges.set(j, new Pair<>(currentRange.x, rangeLength));
            ranges.add(j + 1, new Pair<>(currentRange.x + rangeLength, currentRange.y - rangeLength));
            j--;
        }
        return j;
    }

    private static int handleAboveRange(List<Pair<Long, Long>> ranges, List<Pair<Long, Long>> updatedRanges, long x, long y, long z, int j, Pair<Long, Long> currentRange) {
        if (currentRange.x < (y + z)) {
            if ((currentRange.x + currentRange.y) <= (y + z)) {
                updatedRanges.add(new Pair<>(currentRange.x - y + x, currentRange.y));
                ranges.remove(currentRange);
            } else {
                long rangeLength = (y + z) - currentRange.x;
                ranges.set(j, new Pair<>(currentRange.x, rangeLength));
                ranges.add(j + 1, new Pair<>(currentRange.x + rangeLength, currentRange.y - rangeLength));
            }
            j--;
        }
        return j;
    }

    private String[] getLocations(List<String> map, String[] seeds) {
        HashMap<Long, Pair<Long, Long>> locations = new HashMap<>();
        // take 3 numbers at a time
        for (int i = 0; i < map.size(); i += 3) {
            Long x = Long.parseLong(map.get(i));
            Long y = Long.parseLong(map.get(i + 1));
            Long z = Long.parseLong(map.get(i + 2));

            locations.put(x, new Pair<>(y, z));
        }


        seeds = updaters(seeds, locations);

        return seeds;
    }

    private static String[] updaters(String[] seeds, HashMap<Long, Pair<Long, Long>> locations) {
        //return new seeds the hashmap has value startsource -> endsource, range

        String[] newseeds = new String[seeds.length];
        for (int i = 0; i < seeds.length; i++) {
            newseeds[i] = seeds[i];
        }

        for (int i = 0; i < seeds.length; i++) {
            newseeds[i] = iterateMap(Long.parseLong(seeds[i]), locations);
        }

        return newseeds;

    }

    private static String iterateMap(long seed, HashMap<Long, Pair<Long, Long>> locations) {
        long newseed = seed;
        for (Long start : locations.keySet()) {
            Pair<Long, Long> endrange = locations.get(start);
            long startRange = endrange.x;
            long endRange = endrange.y + endrange.x - 1;

            newseed = doCompute(newseed, start, startRange, endRange);
            if (newseed != seed) {
                return String.valueOf(newseed);
            }
        }
        return String.valueOf(newseed);
    }

    private static Long doCompute(long seed, long start, long startRange, long endRange) {

        if (seed >= startRange && seed <= endRange) {
            return seed - startRange + start;
        }
        return seed;
    }


}