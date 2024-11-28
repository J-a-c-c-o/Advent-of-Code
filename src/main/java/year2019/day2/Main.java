package year2019.day2;


import utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Part 1: " + main.part1());
        System.out.println("Part 2: " + main.part2());
    }

    public List<Integer> getInput() {
        List<String> numbers = utils.getLines("src/main/java/year2019/day2/input.txt");
        List<String> split = Arrays.stream(numbers.get(0).split(",")).toList();

        List<Integer> result = new ArrayList<>();

        for (String s : split) {
            result.add(Integer.parseInt(s));
        }

        return result;

    }

    public String part1() {
        List<Integer> input = getInput();

        input.set(1, 12);
        input.set(2, 2);

        List<Integer> result = runOpcode(input);
        return result.get(0).toString();
    }

    public String part2() {

        List<Integer> input = getInput();

        for (int i = 0; i < 99; i++) {
            for (int j = 0; j < 99; j++) {
                List<Integer> copy = new ArrayList<>(List.copyOf(input));

                input.set(1, i);
                input.set(2, j);

                List<Integer> result = runOpcode(input);
                if (result.get(0) == 19690720) {
                    return String.valueOf(100 * result.get(1) + result.get(2));
                }
            }
        }

        return "none";

    }

    public List<Integer> runOpcode(List<Integer> opcode) {

        List<Integer> result = new ArrayList<>(List.copyOf(opcode));
        int i = 0;
        while (true) {
            int command = result.get(i);

            if (command == 99) {
                break;
            } else if (command == 1) {
                int computation = result.get(result.get(i+1)) + result.get(result.get(i+2));
                result.set(result.get(i+3), computation);
            } else if (command == 2) {
                int computation = result.get(result.get(i+1)) * result.get(result.get(i+2));
                result.set(result.get(i+3), computation);
            }


            i += 4;
        }



        return result;
    }





}