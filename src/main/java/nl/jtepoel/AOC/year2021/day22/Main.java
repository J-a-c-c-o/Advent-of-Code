package nl.jtepoel.AOC.year2021.day22;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.jtepoel.AOC.utils.Pair;
import nl.jtepoel.AOC.utils.Quad;
import nl.jtepoel.AOC.utils.Triple;
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

    public List<Quad<Pair<Integer,Integer>,Pair<Integer,Integer>,Pair<Integer,Integer>, Integer>> getInput() {
        List<String> input =utils.getLines(Utils.LOCSRC + "/year2021/day22/input.txt");
        List<Quad<Pair<Integer,Integer>,Pair<Integer,Integer>,Pair<Integer,Integer>, Integer>> steps = new ArrayList<>();
        for (String line : input) {
            String[] parts = line.split(" ");
            Integer sw = parts[0].equals("on") ? 1 : 0;
            String[] pos = parts[1].split(",");
            String dimX = pos[0].split("=")[1];
            String dimY = pos[1].split("=")[1];
            String dimZ = pos[2].split("=")[1];
            Pair<Integer,Integer> dimPairX = new Pair<>(Integer.parseInt(dimX.split("\\.\\.")[0]), Integer.parseInt(dimX.split("\\.\\.")[1]));
            Pair<Integer,Integer> dimPairY = new Pair<>(Integer.parseInt(dimY.split("\\.\\.")[0]), Integer.parseInt(dimY.split("\\.\\.")[1]));
            Pair<Integer,Integer> dimPairZ = new Pair<>(Integer.parseInt(dimZ.split("\\.\\.")[0]), Integer.parseInt(dimZ.split("\\.\\.")[1]));
            steps.add(new Quad<>(dimPairX, dimPairY, dimPairZ, sw));
        }



        return steps;
    }

    public String part1() {
        List<Quad<Pair<Integer,Integer>,Pair<Integer,Integer>,Pair<Integer,Integer>, Integer>> steps = getInput();
        Set<Triple<Integer,Integer,Integer>> active = new HashSet<>();
        for (Quad<Pair<Integer,Integer>,Pair<Integer,Integer>,Pair<Integer,Integer>, Integer> step : steps) {
            if (step.getFourth() == 1) {
                for (int x = step.getFirst().getFirst(); x <= step.getFirst().getSecond(); x++) {
                    if (x < -50) {
                        if (step.getFirst().getFirst() >= -50) {
                            x = -50;
                        } else {
                            break;
                        }
                    } else if (x > 50) {
                        break;
                    }

                    for (int y = step.getSecond().getFirst(); y <= step.getSecond().getSecond(); y++) {
                        if (y < -50) {
                            if (step.getSecond().getFirst() >= -50) {
                                y = -50;
                            } else {
                                break;
                            }
                        } else if (y > 50) {
                            break;
                        }


                        for (int z = step.getThird().getFirst(); z <= step.getThird().getSecond(); z++) {
                            if (z < -50) {
                                if (step.getThird().getFirst() >= -50) {
                                    z = -50;
                                } else {
                                    break;
                                }
                            } else if (z > 50) {
                                break;
                            }

                            active.add(new Triple<>(x,y,z));
                        }
                    }
                }
            } else {
                for (int x = step.getFirst().getFirst(); x <= step.getFirst().getSecond(); x++) {
                    if (x < -50) {
                        if (step.getFirst().getFirst() >= -50) {
                            x = -50;
                        } else {
                            break;
                        }
                    } else if (x > 50) {
                        break;
                    }

                    for (int y = step.getSecond().getFirst(); y <= step.getSecond().getSecond(); y++) {
                        if (y < -50) {
                            if (step.getSecond().getFirst() >= -50) {
                                y = -50;
                            } else {
                                break;
                            }
                        } else if (y > 50) {
                            break;
                        }
                        for (int z = step.getThird().getFirst(); z <= step.getThird().getSecond(); z++) {
                            if (z < -50) {
                                if (step.getThird().getFirst() >= -50) {
                                    z = -50;
                                } else {
                                    break;
                                }
                            } else if (z > 50) {
                                break;
                            }
                            active.remove(new Triple<>(x,y,z));
                        }
                    }
                }
            }
        }

        return String.valueOf(active.size());
    }

    public String part2() {
        List<Quad<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Integer>> cuboids = getInput();

        List<Quad<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Integer>> cores = new ArrayList<>();
        for (Quad<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Integer> cuboid : cuboids) {
            List<Quad<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Integer>> toAdd = new ArrayList<>();
            if (cuboid.getFourth() == 1) {
                toAdd.add(cuboid);
            }

            for (Quad<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Integer> core : cores) {
                Triple<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>> intersection = getIntersection(cuboid, core);

                if (intersection != null) {
                    toAdd.add(new Quad<>(intersection.getFirst(), intersection.getSecond(), intersection.getThird(), -core.getFourth()));
                }
            }

            cores.addAll(toAdd);

        }

        long count = getActiveCount(cores);

        return String.valueOf(count);
    }

    private long getActiveCount(List<Quad<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Integer>> cuboids) {
        long count = 0;
        for (Quad<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Integer> cuboid : cuboids) {
            count += (long) cuboid.getFourth() * (cuboid.getFirst().getSecond() - cuboid.getFirst().getFirst() + 1) * (cuboid.getSecond().getSecond() - cuboid.getSecond().getFirst() + 1) * (cuboid.getThird().getSecond() - cuboid.getThird().getFirst() + 1);
        }

        return count;
    }

    private Triple<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>> getIntersection(Quad<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Integer> cuboid, Quad<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>, Integer> core) {
        int x1 = Math.max(cuboid.getFirst().getFirst(), core.getFirst().getFirst());
        int x2 = Math.min(cuboid.getFirst().getSecond(), core.getFirst().getSecond());
        int y1 = Math.max(cuboid.getSecond().getFirst(), core.getSecond().getFirst());
        int y2 = Math.min(cuboid.getSecond().getSecond(), core.getSecond().getSecond());
        int z1 = Math.max(cuboid.getThird().getFirst(), core.getThird().getFirst());
        int z2 = Math.min(cuboid.getThird().getSecond(), core.getThird().getSecond());

        Triple<Pair<Integer, Integer>, Pair<Integer, Integer>, Pair<Integer, Integer>> intersection = new Triple<>(new Pair<>(x1, x2), new Pair<>(y1, y2), new Pair<>(z1, z2));

        if (x1 > x2 || y1 > y2 || z1 > z2) {
            return null;
        }

        return intersection;

    }


}