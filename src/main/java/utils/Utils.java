package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.net.http.HttpRequest.newBuilder;

public class Utils {
    public List<String> getLines(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.lines().toList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Integer> getNumbers(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.lines().map(Integer::parseInt).toList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<Long> getLongs(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.lines().map(Long::parseLong).toList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<List<String>> createMap(int y_size, int x_size) {
        List<List<String>> map = new ArrayList<>();
        for (int i = 0; i < y_size; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < x_size; j++) {
                row.add(".");
            }
            map.add(row);
        }
        return map;
    }

    //api aoc
    public void getInput(int day, int year) {
        String token_path = "src/token.txt";
        //get token from file
        String session = "";
        try {
            Scanner scanner = new Scanner(new File(token_path));
            session = scanner.nextLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }



        if (Files.exists(Path.of(String.format("input%d.txt", day)))) {
            return;
        } else {
            System.out.println("Downloading input file...");
        }

        String url = String.format("https://adventofcode.com/%d/day/%d/input", year, day);

        try {
            var client = HttpClient.newBuilder().build();
            var request = newBuilder()
                    .uri(URI.create(url))
                    .header("Cookie", String.format("session=%s", session))
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Files.writeString(Path.of(String.format("src/year%d/day%d/input.txt", year, day)), response.body());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createClass(String day, String year) {
        try {
            String content = Files.readString(Path.of("src/template.txt")).replace("DAY", day).replace("YEAR", year);
            String dir = String.format("./src/year%s/day%s", year, day);
            File file = new File(dir);
            if (file.mkdirs()) {
                System.out.println("Directory created");
            } else {
                System.out.println("Directory already exists");
            }
            // convert to package


            Files.writeString(Path.of(String.format("./src/year%s/day%s/Main.java", year, day)), content);
            Files.writeString(Path.of(String.format("./src/year%s/day%s/example.txt", year, day)), "");
        } catch (IOException e) {
            System.out.println("error" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        //ask for day and year
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter day: ");
        String day = scanner.nextLine();
        System.out.println("Enter year: ");
        String year = scanner.nextLine();
        Utils utils = new Utils();
        utils.createClass(day, year);
        utils.getInput(Integer.parseInt(day), Integer.parseInt(year));
    }

    public List<String> rotate(List<String> lines) {
        List<String> rotated = new ArrayList<>();

        if (lines.isEmpty()) {
            return rotated;
        }

        for (int i = 0; i < lines.get(0).length(); i++) {
            StringBuilder sb = new StringBuilder();
            for (String line : lines) {
                sb.append(line.charAt(i));
            }
            rotated.add(sb.toString());
        }
        return rotated;
    }


    public int[][] makeGrid(List<String> lines, String del) {
        int[][] grid = new int[lines.size()][];

        int i = 0;
        for (String line : lines) {
            String[] li = line.split(del);
            grid[i] = new int[li.length];
            for (int j = 0; j < li.length; j++) {
                grid[i][j] = Integer.parseInt(li[j]);
            }

            i += 1;
        }

        return grid;
    }

    public int[][] flipGridX(int[][] grid) {
        int[][] flipped = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                flipped[i][j] = grid[i][grid[0].length - j - 1];
            }
        }
        return flipped;
    }

    public int[][] flipGridY(int[][] grid) {
        int[][] flipped = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[grid.length - i - 1], 0, flipped[i], 0, grid[0].length);
        }
        return flipped;
    }
}
