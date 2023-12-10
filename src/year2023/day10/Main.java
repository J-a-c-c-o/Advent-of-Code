package year2023.day10;

import utils.Pair;
import utils.Utils;

import java.util.*;

public class Main {

    private final Map<Character, List<String>> pipeMappings;
    private final Map<String, int[]> directionsMapping;
    private final List<Pair<Integer, Integer>> visited;



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

    }


    public static void main(String[] args) {
        Main main = new Main();
        System.out.println("Part 1: " + main.part1());
        System.out.println("Part 2: " + main.part2(true));

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

        char[][] input = getInput();



        //expand map so it can be filled
        char[][] map = bigMap(input);


        //flood fill outside
        int[] start = new int[]{0, 0};
        map = fill(map, start);


        if (visualise) {
            printMap(map);
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

    private char[][] fill(char[][] originalMap, int[] start) {

        char[][] map = new char[originalMap.length][];
        for (int i = 0; i < originalMap.length; i++) {
            map[i] = Arrays.copyOf(originalMap[i], originalMap[i].length);
        }

        //flood fill
        List<Pair<Integer, Integer>> queue = new ArrayList<>();
        queue.add(new Pair<>(start[0], start[1]));
        while (!queue.isEmpty()) {
            Pair<Integer, Integer> current = queue.remove(0);
            int i = current.x;
            int j = current.y;

            if (map[i][j] != '.') {
                continue;
            }

            map[i][j] = 'O';

            for (int[] dir : directionsMapping.values()) {
                int di = dir[0];
                int dj = dir[1];

                int newI = i + di;
                int newJ = j + dj;

                if (inBounds(map, newI, newJ)) {
                    continue;
                }

                char nextPipe = map[newI][newJ];

                if (nextPipe == '+') {
                    continue;
                }

                queue.add(new Pair<>(newI, newJ));
            }




        }

        //replace all . with I
        for (char[] row : map) {
            for (int j = 0; j < row.length; j++) {
                if (row[j] == '.') {
                    row[j] = 'I';
                }
            }
        }


        return map;
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

    private char[][] bigMap(char[][] map) {

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


        //create map where each coordinate is 3x3 and we connect them with `+`
        char[][] bigMap = new char[map.length * 3][map[0].length * 3];

        for (char[] chars : bigMap) {
            Arrays.fill(chars, '.');
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
                    bigMap[x][j] = '+';
                }
            } else {
                //horizontal
                for (int j = Math.min(x, x2); j <= Math.max(x, x2); j++) {
                    bigMap[j][y] = '+';
                }
            }
        }


        return bigMap;


    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_GREEN_BOLD = "\033[1;32m";

    private static void printMap(char[][] map) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : map) {
            //print string
            for (char c : row) {
                if (c == 'O') {
                    sb.append(ANSI_RED);
                    sb.append(c);
                    sb.append(ANSI_RESET);
                } else if (c == 'I') {
                    sb.append(ANSI_CYAN);
                    sb.append(c);
                    sb.append(ANSI_RESET);
                } else if (c == '+') {
                    sb.append(ANSI_GREEN_BOLD);
                    sb.append(c);
                    sb.append(ANSI_RESET);
                } else {
                    sb.append(ANSI_PURPLE);
                    sb.append(c);
                    sb.append(ANSI_RESET);
                }


            }
            sb.append(ANSI_RESET);
            sb.append("\n");
        }

        System.out.println(sb);
    }

    public char[][] getInput() {
        Utils utils = new Utils();
        List<String> lines = utils.getLines("src/year2023/day10/input.txt");
        char[][] map = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            map[i] = lines.get(i).toCharArray();
        }

        return map;

    }






}
