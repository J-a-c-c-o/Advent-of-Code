package year2023.day22;


import java.util.*;

import utils.Utils;

public class Main {

    Utils utils = new Utils();
    List<Brick> bricks;

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Main main = new Main();

        main.getInput();

        String part1 = main.part1();
        long part1Time = System.currentTimeMillis() - start;
        String part2 = main.part2();
        long part2Time = System.currentTimeMillis() - start - part1Time;

        System.out.println("Part 1: " + part1 + " took " + part1Time + "ms");
        System.out.println("Part 2: " + part2 + " took " + part2Time + "ms");
    }

    public void getInput() {
        List<String> line = utils.getLines("src/year2023/day22/input.txt");
        bricks = new ArrayList<>();
        int id = 1;
        for (String s : line) {
            String[] split = s.split("~");
            String[] start = split[0].split(",");
            String[] end = split[1].split(",");
            bricks.add(new Brick(Integer.parseInt(start[0]), Integer.parseInt(start[1]),  Integer.parseInt(start[2]), Integer.parseInt(end[0]), Integer.parseInt(end[1]),Integer.parseInt(end[2]), id));
            id++;
        }


        //start moving down till they are stable
        simulate(bricks);

    }

    private void simulate(List<Brick> bricks) {
        boolean allMoves = false;
        while (!allMoves) {
            allMoves = true;
            for (Brick box : bricks) {
                box.moveDown(bricks);
            }

            for (Brick box : bricks) {
                if (box.checkIfMoveDown(bricks)) {
                    allMoves = false;
                }
            }
        }
    }

    public String part1() {
        List<Brick> temp = new ArrayList<>(bricks);

        int count = 0;

        //check if any of the boxes would fall we remove them
        for (Brick box : bricks) {
            temp.remove(box);
            boolean canFall = false;
            for (Brick box2 : temp) {
                if (box2.checkIfMoveDown(temp)) {
                    canFall = true;
                }
            }
            if (!canFall) {
                count++;
            }
            temp.add(box);
        }



        return String.valueOf(count);
    }


    public String part2() {

        //set up a map with all the ids for easy access
        HashMap<Integer, Brick> map = new HashMap<>();
        for (Brick box : bricks) {
            map.put(box.getId(), box);
        }

        //for each box remove it and check if any of the boxes can fall
        int count = 0;
        for (Brick removeBox : bricks) {
            List<Brick> boxesCopy = deepCopyBoxes();
            boxesCopy.remove(removeBox);


            //first check if any of the boxes can fall
            boolean canFall = false;
            for (Brick box : boxesCopy) {
                if (box.checkIfMoveDown(boxesCopy)) {
                    canFall = true;
                }
            }

            if (!canFall) {
                continue;
            }



            //simulate the fall after removing the box
            simulate(boxesCopy);

            //check if any of the boxes has changed and if so increase the count
            count += findAmountOfMoved(map, boxesCopy);
        }

        return String.valueOf(count);
    }

    private static int findAmountOfMoved(HashMap<Integer, Brick> map, List<Brick> boxesCopy) {
        int count = 0;
        for (Brick box : boxesCopy) {
            if (map.containsKey(box.getId())) {
                Brick original = map.get(box.getId());
                if (!original.equals(box)) {
                    count++;
                }
            }
        }
        return count;
    }

    private List<Brick> deepCopyBoxes() {

        //deep copy the boxes
        List<Brick> boxesCopy = new ArrayList<>();
        for (Brick box : bricks) {
            boxesCopy.add(new Brick(box));
        }

        return boxesCopy;
    }


}