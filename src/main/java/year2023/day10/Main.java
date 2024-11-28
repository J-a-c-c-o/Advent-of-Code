package year2023.day10;

import utils.Pair;
import utils.Utils;

import java.util.*;

public class Main {

    private final Map<Character, List<String>> pipeMappings;
    private final Map<String, int[]> directionsMapping;
    private final List<Pair<Integer, Integer>> visited;

    private final char[][] map;



    Main() {
        pipeMappings = new HashMap<>();
        pipeMappings.put('|', Arrays.asList("n", "s"));
        pipeMappings.put('-', Arrays.asList("w", "e"));
        pipeMappings.put('L', Arrays.asList("n", "e"));
        pipeMappings.put('J', Arrays.asList("n", "w"));
        pipeMappings.put('7', Arrays.asList("s", "w"));
        pipeMappings.put('F', Arrays.asList("s", "e"));
        pipeMappings.put('S', Arrays.asList("n", "s", "w", "e"));

        directionsMapping = new HashMap<>();
        directionsMapping.put("n", new int[]{-1, 0, 's'});
        directionsMapping.put("s", new int[]{1, 0, 'n'});
        directionsMapping.put("w", new int[]{0, -1, 'e'});
        directionsMapping.put("e", new int[]{0, 1, 'w'});

        visited = new ArrayList<>();

        map = getInput();

    }


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Main main = new Main();
        String part1 = main.part1();
        long part1Time = System.currentTimeMillis() - start;
        String part2 = main.part2(true);
        long part2Time = System.currentTimeMillis() - start - part1Time;

        System.out.println("Part 1: " + part1 + " took " + part1Time + "ms");
        System.out.println("Part 2: " + part2 + " took " + part2Time + "ms");
    }

    public String part1() {
        char[][] map = getInput();

        int[] start = findStart(map);

        int startI = start[0];
        int startJ = start[1];

        List<int[]> queue = new ArrayList<>();
        queue.add(new int[]{startI, startJ});

        while (!queue.isEmpty()) {
            int[] current = queue.remove(0);
            int i = current[0];
            int j = current[1];

            if (visited.contains(new Pair<>(i, j))) {
                continue;
            }

            visited.add(new Pair<>(i, j));

            List<String> availableDirections = pipeMappings.get(map[i][j]);

            for (String direction : availableDirections) {
                int[] dirInfo = directionsMapping.get(direction);
                int di = dirInfo[0];
                int dj = dirInfo[1];
                int opposite = dirInfo[2];

                int newI = i + di;
                int newJ = j + dj;

                if (inBounds(map, newI, newJ)) {
                    continue;
                }

                char nextPipe = map[newI][newJ];

                if (!pipeMappings.containsKey(nextPipe)) {
                    continue;
                }

                List<String> nextPipeDirections = pipeMappings.get(nextPipe);

                if (nextPipeDirections.contains(String.valueOf((char) opposite))) {
                    queue.add(new int[]{newI, newJ});
                }
            }
        }
        return String.valueOf(visited.size()/2);
    }

    private static boolean inBounds(char[][] map, int newI, int newJ) {

        return (newI < 0 || newI >= map.length) || (newJ < 0 || newJ >= map[newI].length);
    }


    public String part2(boolean visualise) {

        char wallChar = '+';
        char emptyChar = '.';
        char replaceEmptyChar = 'I';
        char fillChar = 'O';

        //expand map so it can be filled
        char[][] map = bigMap(this.map, wallChar, emptyChar);



        //flood fill outside
        int[] start = new int[]{0, 0};
        fill(map, start, wallChar, emptyChar, replaceEmptyChar, fillChar);

        if (visualise) {
            printMap(map, wallChar, replaceEmptyChar, fillChar);
        }




        //count center 3x3
        int centerCount = 0;
        for (int i = 1; i < map.length; i += 3) {
            for (int j = 1; j < map[i].length; j += 3) {
                if (map[i][j] == 'I') {
                    centerCount++;
                }
            }
        }

        return String.valueOf(centerCount);
    }

    private void fill(char[][] map, int[] start, char wallChar, char emptyChar, char replaceEmptyChar, char fillChar) {

        //flood fill
        List<Pair<Integer, Integer>> queue = new ArrayList<>();
        queue.add(new Pair<>(start[0], start[1]));
        while (!queue.isEmpty()) {
            Pair<Integer, Integer> current = queue.remove(0);
            int i = current.x;
            int j = current.y;

            if (map[i][j] != emptyChar) {
                continue;
            }

            map[i][j] = fillChar;

            for (int[] dir : directionsMapping.values()) {
                int di = dir[0];
                int dj = dir[1];

                int newI = i + di;
                int newJ = j + dj;

                if (inBounds(map, newI, newJ)) {
                    continue;
                }

                char nextPipe = map[newI][newJ];

                if (nextPipe == wallChar) {
                    continue;
                }

                queue.add(new Pair<>(newI, newJ));
            }




        }

        //replace all . with I
        for (char[] row : map) {
            for (int j = 0; j < row.length; j++) {
                if (row[j] == emptyChar) {
                    row[j] = replaceEmptyChar;
                }
            }
        }


    }

    private int[] findStart(char[][] map) {
        int[] start = new int[2];
        for (int i = 0; i < map.length; i++) {
            char[] row = map[i];
            for (int j = 0; j < row.length; j++) {
                if (row[j] == 'S') {
                    start[0] = i;
                    start[1] = j;
                    return start;
                }
            }
        }
        return start;
    }

    private char[][] bigMap(char[][] map, char wallChar, char emptyChar) {

        List<Pair<Integer, Integer>> loop = new ArrayList<>();
        List<Pair<Integer, Integer>> left = new ArrayList<>();
        List<Pair<Integer, Integer>> right = new ArrayList<>();

        for (int i = 1; i < visited.size() - 1; i+=2) {
            Pair<Integer, Integer> place = visited.get(i);
            Pair<Integer, Integer> place2 = visited.get(i+1);
            left.add(place);
            right.add(place2);
        }

        //reverse right
        Collections.reverse(right);
        loop.add(visited.get(0));
        loop.addAll(left);
        loop.add(visited.get(visited.size() - 1));
        loop.addAll(right);
        loop.add(visited.get(0));


        //create map where each coordinate is 3x3, and we connect them with `+`
        char[][] bigMap = new char[map.length * 3][map[0].length * 3];

        for (char[] chars : bigMap) {
            Arrays.fill(chars, emptyChar);
        }

        for (int i = 0; i < loop.size() - 1; i++) {
            Pair<Integer, Integer> place = loop.get(i);
            Pair<Integer, Integer> place2 = loop.get(i + 1);

            int x = place.x * 3 + 1;
            int y = place.y * 3 + 1;

            int x2 = place2.x * 3 + 1;
            int y2 = place2.y * 3 + 1;

            if (x == x2) {
                //vertical
                for (int j = Math.min(y, y2); j <= Math.max(y, y2); j++) {
                    bigMap[x][j] = wallChar;
                }
            } else if (y == y2) {
                //horizontal
                for (int j = Math.min(x, x2); j <= Math.max(x, x2); j++) {
                    bigMap[j][y] = wallChar;
                }
            }
        }


        return bigMap;


    }


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN_BOLD = "\033[1;32m";

    private static void printMap(char[][] map, char wallChar, char insideChar, char outsideChar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < map.length; i++) {
            //print string
            char[] row = map[i];
            for (int j = 0; j < row.length; j++) {
                char c = row[j];
                if (c == outsideChar) {
                    sb.append(ANSI_RED);
                    sb.append("O");
                    sb.append(ANSI_RESET);
                } else if (c == insideChar) {
                    sb.append(ANSI_CYAN);
                    sb.append("X");
                    sb.append(ANSI_RESET);
                } else if (c == wallChar) {
                    sb.append(ANSI_GREEN_BOLD);

                    //if corner append ║
                    if (map[i-1][j] == wallChar && map[i][j-1] == wallChar) {
                        sb.append("╝");
                    } else if (map[i-1][j] == wallChar && map[i][j+1] == wallChar) {
                        sb.append("╚");
                    } else if (map[i+1][j] == wallChar && map[i][j-1] == wallChar) {
                        sb.append("╗");
                    } else if (map[i+1][j] == wallChar && map[i][j+1] == wallChar) {
                        sb.append("╔");
                    } else if (map[i-1][j] == wallChar || map[i+1][j] == wallChar) {
                        sb.append("║");
                    } else {
                        sb.append("═");
                    }

                    sb.append(ANSI_RESET);
                } else {
                    sb.append(" ");
                }


            }
            sb.append(ANSI_RESET);
            sb.append("\n");
        }

        System.out.println(sb);
    }

    public char[][] getInput() {
        Utils utils = new Utils();
        List<String> lines = utils.getLines("src/main/java/year2023/day10/example.txt");
        char[][] map = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            map[i] = lines.get(i).toCharArray();
        }

        return map;

    }






}
