package year2023.day16;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import utils.Pair;
import utils.Utils;

public class Main {

    int maxX;
    int maxY;
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

    public HashMap<Pair<Integer,Integer>, Character> getInput() {

        List<String> lines = utils.getLines("src/year2023/day16/input.txt");
        HashMap<Pair<Integer,Integer>, Character> map = new HashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) != '.') {
                    map.put(new Pair<>(j,i), line.charAt(j));
                }
            }
        }


        maxX = lines.size();
        maxY = lines.get(0).length();

        return map;
    }

    public String part1() {
        return getEnerginzed(0,0, 'E') + "";
    }

    private int getEnerginzed(int x, int y, char sdir) {
        HashMap<Pair<Integer,Integer>, Character> map = getInput();
        List<Pair<Pair<Integer,Integer>, Character>> pairs = new ArrayList<>();

        Pair<Integer,Integer> cord = new Pair<>(x,y);
        char dir = sdir;

        pairs.add(new Pair<>(cord, dir));

        List<Pair<Pair<Integer,Integer>, Character>> queue = new ArrayList<>();
        queue.add(new Pair<>(cord, dir));

        while (!queue.isEmpty()) {
            Pair<Pair<Integer,Integer>, Character> pair = queue.remove(0);
            cord = pair.x;
            dir = pair.y;

            while (true) {
                if (map.containsKey(cord)) {
                    char character = map.get(cord);


                    if (character == '|') {
                        if (dir == 'E' || dir == 'W') {
                            dir = 'N';

                            Character tempDir = 'S';
                            Pair<Integer,Integer> tempCord = new Pair<>(cord.x, cord.y);

                            queue.add(new Pair<>(tempCord, tempDir));
                        }
                    } else if (character == '-') {
                        if (dir == 'N' || dir == 'S') {
                            dir = 'E';

                            Character tempDir = 'W';
                            Pair<Integer,Integer> tempCord = new Pair<>(cord.x, cord.y);

                            queue.add(new Pair<>(tempCord, tempDir));
                        }
                    } else if (character == '/') {
                        if (dir == 'N') {
                            dir = 'E';
                        } else if (dir == 'E') {
                            dir = 'N';
                        } else if (dir == 'S') {
                            dir = 'W';
                        } else if (dir == 'W') {
                            dir = 'S';
                        }
                    } else if (character == '\\') {
                        if (dir == 'N') {
                            dir = 'W';
                        } else if (dir == 'E') {
                            dir = 'S';
                        } else if (dir == 'S') {
                            dir = 'E';
                        } else if (dir == 'W') {
                            dir = 'N';
                        }
                    }

                }
                if (dir == 'N') {
                    cord = new Pair<>(cord.x, cord.y - 1);
                } else if (dir == 'E') {
                    cord = new Pair<>(cord.x + 1, cord.y);
                } else if (dir == 'S') {
                    cord = new Pair<>(cord.x, cord.y + 1);
                } else if (dir == 'W') {
                    cord = new Pair<>(cord.x - 1, cord.y);
                }



                if (cord.x < 0 || cord.x >= maxX || cord.y < 0 || cord.y >= maxY) {
                    break;
                }


                if (pairs.contains(new Pair<>(cord, dir))) {
                    break;
                } else {
                    pairs.add(new Pair<>(cord, dir));
                }

            }
        }

        //remove duplicates
        List<Pair<Integer,Integer>> pairs2 = new ArrayList<>();
        for (Pair<Pair<Integer,Integer>, Character> pair : pairs) {
            if (!pairs2.contains(pair.x)) {
                pairs2.add(pair.x);
            }
        }

        List<Pair<Integer,Integer>> pairs3 = new ArrayList<>();
        for (Pair<Integer,Integer> pair : pairs2) {
            if (!pairs3.contains(pair)) {
                pairs3.add(pair);
            }
        }

        return pairs3.size();
    }

    public String part2() {

        List<Integer> oldlist = new ArrayList<>();
        List<Integer> max = Collections.synchronizedList(oldlist);


        Thread thread1 = new Thread(() -> {
            int maxEnerginzed = 0;
            for (int x = 0; x < maxX; x++) {
                int energinzed = getEnerginzed(x,0, 'S');
                if (energinzed > maxEnerginzed) {
                    maxEnerginzed = energinzed;
                }
            }
            max.add(maxEnerginzed);
        });

        Thread thread2 = new Thread(() -> {
            int maxEnerginzed = 0;
            for (int y = 0; y < maxY; y++) {
                int energinzed = getEnerginzed(0,y, 'E');
                if (energinzed > maxEnerginzed) {
                    maxEnerginzed = energinzed;
                }
            }
            max.add(maxEnerginzed);
        });

        Thread thread3 = new Thread(() -> {
            int maxEnerginzed = 0;
            for (int x = 0; x < maxX; x++) {
                int energinzed = getEnerginzed(x,maxY - 1, 'N');
                if (energinzed > maxEnerginzed) {
                    maxEnerginzed = energinzed;
                }
            }
            max.add(maxEnerginzed);
        });

        Thread thread4 = new Thread(() -> {
            int maxEnerginzed = 0;
            for (int y = 0; y < maxY; y++) {
                int energinzed = getEnerginzed(maxX - 1,y, 'W');
                if (energinzed > maxEnerginzed) {
                    maxEnerginzed = energinzed;
                }
            }
            max.add(maxEnerginzed);
        });


        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int maxEnerginzed = 0;
        for (int i : max) {
            if (i > maxEnerginzed) {
                maxEnerginzed = i;
            }
        }


        return maxEnerginzed + "";
    }



}