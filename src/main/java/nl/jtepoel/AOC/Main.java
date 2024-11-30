package nl.jtepoel.AOC;

import nl.jtepoel.AOC.utils.Utils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    private final Utils utils;

    public static void main(String[] args) {

        Main main = new Main();
        main.run();
    }

    public Main() {
        utils = new Utils();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Advent of Code 2023");
        System.out.println("Do you want to run, create or continue a day? (run/create/continue)");
        String input = scanner.nextLine();
        switch (input) {
            case "create" -> utils.newDay();
            case "run" -> runDay();
            case "continue" -> System.exit(0);
            default -> System.out.println("Invalid input");
        }
    }

    private void runDay() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the year:");
        String year = scanner.nextLine();
        System.out.println("Enter the day:");
        String day = scanner.nextLine();
        if (fileExist(year, day) && inputIsValid(year, day)) {
            System.out.println("Day " + day + " of year " + year + " exists. Do you want to run it? (y/N)");
            String input = scanner.nextLine();
            if (input.equals("y")) {
                runDay(year, day);
            }
        } else {
            System.out.println("Something went wrong. Do you want to try again? (y/N)");
            String input = scanner.nextLine();
            if (input.equals("y")) {
                runDay();
            }
        }
    }

    private boolean fileExist(String year, String day) {
        String path = "src/main/java/nl/jtepoel/AOC/year" + year + "/day" + day;
        File file = new File(path);

        return file.exists();
    }

    private boolean inputIsValid(String year, String day) {
        //check if input.txt exists and is not empty
        String path = "src/main/java/nl/jtepoel/AOC/year" + year + "/day" + day + "/input.txt";
        File file = new File(path);
        boolean fileExists = file.exists();

        List<String> lines = utils.getLines(path);
        boolean notEmpty = !lines.isEmpty();

        if (!fileExists || !notEmpty) {
            System.out.println("It seems like the input file is missing or empty. Do you want to create it? (y/N)");
        } else {
            return true;
        }

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.equals("y")) {
            utils.getInput(Integer.parseInt(day), Integer.parseInt(year));
            return inputIsValid(year, day);
        } else {
            return false;
        }


    }

    private void runDay(String year, String day) {
        try {
            Class<?> clazz = Class.forName("nl.jtepoel.AOC.year" + year + ".day" + day + ".Main");
            Object instance = clazz.getDeclaredConstructor().newInstance();
            clazz.getMethod("main", String[].class).invoke(instance, (Object) null);

        } catch (ClassNotFoundException e) {
            System.out.println("Day " + day + " of year " + year + " does not exist");
        } catch (NoSuchMethodException e) {
            System.out.println("Day " + day + " of year " + year + " does not have a main method");
        } catch (IllegalAccessException e) {
            System.out.println("Day " + day + " of year " + year + " is not accessible");
        } catch (InstantiationException e) {
            System.out.println("Day " + day + " of year " + year + " could not be instantiated");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
