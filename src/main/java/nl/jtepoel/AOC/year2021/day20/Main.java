package nl.jtepoel.AOC.year2021.day20;


import java.util.HashMap;
import java.util.List;

import nl.jtepoel.AOC.utils.Pair;
import nl.jtepoel.AOC.utils.Point;
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

    public Pair<String, GridTrench> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2021/day20/input.txt");
        String algorithm = lines.getFirst();
        List<String> rest = lines.subList(2, lines.size());
        GridTrench grid = new GridTrench();

        for (int y = 0; y < rest.size(); y++) {
            String line = rest.get(y);
            for (int x = 0; x < line.length(); x++) {
                grid.set(x, y, line.charAt(x) == '#' ? 1 : 0);
            }
        }


        return new Pair<>(algorithm, grid);
    }

    public String part1() {
        Pair<String, GridTrench> input = getInput();
        String algorithm = input.x;
        GridTrench grid = input.y;


        expand(grid, algorithm, 2);

        return grid.LitCount() + "";
    }

    private static void expand(GridTrench grid, String algorithm, int iterations) {
        int pad = 3;
        for (int r = 0; r < iterations; r++) {
            int minY = grid.getMinY() - pad;
            int maxY = grid.getMaxY() + pad;
            int minX = grid.getMinX() - pad;
            int maxX = grid.getMaxX() + pad;
            HashMap<Point, Character> map = new HashMap<>();
            for (int x = minX; x <= maxX ; x++) {
                for (int y = minY; y <= maxY ; y++) {
                    int surrounding = grid.calculateNumber(x, y);
                    char c = algorithm.charAt(surrounding);
                    if (r % 2 == 1 && (x <= minX + pad || x >= maxX - pad || y <= minY + pad || y >= maxY - pad)) {
                        map.put(new Point(x, y), '.');
                    } else {
                        map.put(new Point(x, y), c);
                    }
                }
            }


            for (Point p : map.keySet()) {
                grid.set(p.x, p.y, map.get(p) == '#' ? 1 : 0);
            }
        }
    }

    public String part2() {
        Pair<String, GridTrench> input = getInput();
        String algorithm = input.x;
        GridTrench grid = input.y;


        expand(grid, algorithm, 50);


        return grid.LitCount() + "";
    }



}