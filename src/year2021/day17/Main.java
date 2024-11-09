package year2021.day17;


import java.util.Arrays;
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

    public List<String> getInput() {
        return utils.getLines("src/year2021/day17/input.txt");
    }

    public String part1() {
        String input = getInput().get(0);
        String targetArea = input.split(": ")[1];
        String[] xy = targetArea.split(", ");
        String areaXS = xy[0].substring(2);
        String areaYS = xy[1].substring(2);

        int[] areaX = new int[2];
        int[] areaY = new int[2];
        areaX[0] = Integer.parseInt(areaXS.split("\\.\\.")[0]);
        areaX[1] = Integer.parseInt(areaXS.split("\\.\\.")[1]);
        areaY[0] = Integer.parseInt(areaYS.split("\\.\\.")[0]);
        areaY[1] = Integer.parseInt(areaYS.split("\\.\\.")[1]);

        int max = Integer.MIN_VALUE;
        for (int i = areaY[0]; i < areaX[1] + 1; i++) {
            for (int j = 0; j < areaX[1] + 1; j++) {
                int maxY = simulate(areaY, areaX, j, i);
                if (maxY > max) {
                    max = maxY;
                }
            }
        }

        return String.valueOf(max);

    }

    private int simulate(int[] areaY, int[] areaX, int vX, int vY) {
        int x = 0;
        int y = 0;
        int MaxY = 0;
        while (y > areaY[0] && x < areaX[1]) {
            x += vX;
            y += vY;

            if (y > MaxY) {
                MaxY = y;
            }

            vX = vX > 0 ? vX-1 : 0;
            vY--;

            if (x >= areaX[0] && x <= areaX[1] && y >= areaY[0] && y <= areaY[1]) {
                return MaxY;
            }
        }

        return -1;
    }

    public String part2() {
        String input = getInput().get(0);
        String targetArea = input.split(": ")[1];
        String[] xy = targetArea.split(", ");
        String areaXS = xy[0].substring(2);
        String areaYS = xy[1].substring(2);

        int[] areaX = new int[2];
        int[] areaY = new int[2];
        areaX[0] = Integer.parseInt(areaXS.split("\\.\\.")[0]);
        areaX[1] = Integer.parseInt(areaXS.split("\\.\\.")[1]);
        areaY[0] = Integer.parseInt(areaYS.split("\\.\\.")[0]);
        areaY[1] = Integer.parseInt(areaYS.split("\\.\\.")[1]);

        int count = 0;
        for (int i = areaY[0]; i < areaX[1] + 1; i++) {
            for (int j = 0; j < areaX[1] + 1; j++) {
                int bool = simulate(areaY, areaX, j, i);


                if (bool != -1) {
                    count++;
                }
            }
        }

        return String.valueOf(count);

    }



}