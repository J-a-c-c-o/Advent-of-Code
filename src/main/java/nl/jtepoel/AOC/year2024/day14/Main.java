package nl.jtepoel.AOC.year2024.day14;
import java.util.ArrayList;
import java.util.List;

import nl.jtepoel.AOC.utils.Grid;
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

    public List<Robot> getInput() {
        List<Robot> robots = new ArrayList<>();
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day14/input.txt");

        for (int i = 0; i < lines.size(); i++) {
            String[] split = lines.get(i).split(" ");
            String[] posistion = split[0].substring(2).split(",");
            String[] volocity = split[1].substring(2).split(",");

            Robot robot = new Robot(Integer.parseInt(posistion[0]), Integer.parseInt(posistion[1]), Integer.parseInt(volocity[0]), Integer.parseInt(volocity[1]));

            robots.add(robot);
        }

        return robots;
    }

    public String part1() {
        List<Robot> robots = getInput();
        int boundsX = 101;
        int boundsY = 103;


        int halfX = boundsX / 2;
        int halfY = boundsY / 2;


        for (int i = 0; i < 100; i++) {
            for (Robot robot : robots) {
                robot.move(boundsX, boundsY);
            }
        }

        int topLeft = 0;
        int topRight = 0;
        int bottomLeft = 0;
        int bottomRight = 0;

        for (Robot robot : robots) {
            if (robot.getX() < halfX && robot.getY() < halfY) {
                topLeft++;
            } else if (robot.getX() > halfX && robot.getY() < halfY) {
                topRight++;
            } else if (robot.getX() < halfX && robot.getY() > halfY) {
                bottomLeft++;
            } else if (robot.getX() > halfX && robot.getY() > halfY) {
                bottomRight++;
            }
        }

        return String.valueOf(topLeft * topRight * bottomLeft * bottomRight);
    }


    int minConnected = Integer.MAX_VALUE;
    int seconds = -1;
    public String part2() {
        List<Robot> robots = getInput();
        int boundsX = 101, boundsY = 103, i = 0;
        while (true) {
            i++;
            for (Robot robot : robots) {
                robot.move(boundsX, boundsY);
            }
            if (checkTree(robots, i)) {
                return String.valueOf(seconds);
            }
        }
    }

    private boolean checkTree(List<Robot> robots, int seconds) {
        Grid<Character> grid = new Grid<>();
        for (Robot robot : robots) {
            grid.set(robot.getY(), robot.getX(), '#');
        }

        int connected = grid.connectedComponents().size();

        if (connected < minConnected) {
            minConnected = connected;
            this.seconds = seconds;
        }

        return seconds - this.seconds > 10000;
    }
}