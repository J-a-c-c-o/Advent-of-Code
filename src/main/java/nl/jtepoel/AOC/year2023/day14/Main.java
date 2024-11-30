package nl.jtepoel.AOC.year2023.day14;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.jtepoel.AOC.utils.Utils;

public class Main {

    Utils utils = new Utils();

    List<Rock> rocks;
    int maxX = 0;
    int maxY = 0;

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

    public void getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2023/day14/input.txt");

        maxY = lines.size();
        maxX = lines.get(0).length();

        rocks = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            char[] chars = line.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                char c = chars[j];
                if (c == 'O' || c == '#') {
                    rocks.add(new Rock(j, i, c, maxX - 1, maxY - 1));
                }
            }

        }


    }

    public String part1() {
        getInput();

        tilt(Direction.UP);


        long total = 0;
        for (Rock rock : rocks) {
            if (rock.c == 'O') {
                total += maxY - rock.getY();
            }
        }

        return String.valueOf(total);
    }

    public String part2() {
        getInput();

        int i = 0;
        Map<Integer, Integer> cachedRocks = new HashMap<>();
        while (i < 1000000000) {

            cycle();


            this.rocks.sort((o1, o2) -> {
                if (o1.y == o2.y) {
                    return o1.x - o2.x;
                }
                return o1.y - o2.y;
            });

            if (cachedRocks.containsKey(this.rocks.hashCode())) {
                System.out.println("Found cycle " + i);

                int cycleStart = cachedRocks.get(this.rocks.hashCode());
                int cycleLength = i - cycleStart;
                int cycleOffset = (1000000000 - 1 - cycleStart) % cycleLength;

                for (int j = 0; j < cycleOffset; j++) {
                    cycle();
                }
                break;
            }


            cachedRocks.put(this.rocks.hashCode(), i);


            i++;


        }


        long total = 0;
        for (Rock rock : rocks) {
            System.out.println(rock);
            if (rock.c == 'O') {
                total += maxY - rock.getY();
            }
        }

        char[][] map = new char[maxY][maxX];
        for (Rock rock : rocks) {
            map[rock.getY()][rock.getX()] = rock.c;
        }

        for (char[] chars : map) {
            System.out.println(chars);
        }


        return String.valueOf(total);
    }

    private void cycle() {
        tilt(Direction.UP);
        tilt(Direction.LEFT);
        tilt(Direction.DOWN);
        tilt(Direction.RIGHT);
    }

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private void tilt(Direction direction) {
        boolean done = false;
        while (!done) {
            boolean movedAny = false;
            for (Rock rock : rocks) {
                if (rock.c == 'O') {
                    switch (direction) {
                        case UP -> {
                            boolean hasMoved = rock.up(rocks);
                            if (hasMoved) {
                                movedAny = true;
                            }
                        }
                        case DOWN -> {
                            boolean hasMoved = rock.down(rocks);
                            if (hasMoved) {
                                movedAny = true;
                            }
                        }
                        case LEFT -> {
                            boolean hasMoved = rock.left(rocks);
                            if (hasMoved) {
                                movedAny = true;
                            }
                        }
                        case RIGHT -> {
                            boolean hasMoved = rock.right(rocks);
                            if (hasMoved) {
                                movedAny = true;
                            }
                        }
                    }
                }
            }

            if (!movedAny) {
                done = true;
            }
        }
    }
}