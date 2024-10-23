package year2021.day4;


import java.util.ArrayList;
import java.util.List;

import utils.Utils;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Main main = new Main();
        int part1 = main.part1();
        long part1Time = System.currentTimeMillis() - start;
        int part2 = main.part2();
        long part2Time = System.currentTimeMillis() - start - part1Time;

        System.out.println("Part 1: " + part1 + " took " + part1Time + "ms");
        System.out.println("Part 2: " + part2 + " took " + part2Time + "ms");
    }

    private List<Integer> bingoNum;
    private List<Board> boards;
    public void getInput() {
        List<String> lines = utils.getLines("src/year2021/day4/input.txt");
        bingoNum = new ArrayList<>();
        String[] temp = lines.get(0).split(",");

        boards = new ArrayList<>();

        for (String s : temp) {
            bingoNum.add(Integer.parseInt(s));
        }

        List<String> tempLines = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            if (lines.get(i).isEmpty()) {
                continue;
            }


            tempLines.add(lines.get(i).trim());
            if (tempLines.size() == 5) {
                boards.add(new Board(tempLines));
                tempLines = new ArrayList<>();

            }
        }




    }

    public int part1() {
        getInput();

        for (int num : bingoNum) {
            for (Board board : boards) {
                board.setNumberMinus(num);
                if (board.checkBoard()) {
                    return num*board.sum();
                }
            }
        }
        return 0;
    }

    public int part2() {
        getInput();

        int boardSize = boards.size();

        for (int num : bingoNum) {
            for (Board board : boards) {
                board.setNumberMinus(num);
                if (board.checkBoard()) {
                    if (boardSize == 1) {
                        return num*board.sum();
                    }
                    boardSize--;
                }
            }
        }
        return 0;
    }



}