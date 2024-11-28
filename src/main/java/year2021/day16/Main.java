package year2021.day16;


import java.util.List;

import utils.Pair;
import utils.Utils;

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

    public List<String> getInput() {
        return utils.getLines("src/year2021/day16/input.txt");
    }

    public String part1() {
        String input = getInput().get(0);
        StringBuilder converted = new StringBuilder();
        for (char c : input.toCharArray()) {
            int hex = Integer.parseInt(String.valueOf(c), 16);
            StringBuilder binary = new StringBuilder(Integer.toBinaryString(hex));
            while (binary.length() < 4) {
                binary.insert(0, "0");
            }
            converted.append(binary);
        }

        long i = 0;
        while (!converted.isEmpty()) {
            Pair<Long, String> getPacket = getPacketP1(converted.toString());

            i += getPacket.x;
            converted = new StringBuilder(getPacket.y);
        }

        System.out.println();

        return String.valueOf(i);


    }



    private Pair<Long, String> getPacketP1(String string) {

        if (!string.contains("1")) {
            return new Pair<>(0L, "");
        }


        String version = string.substring(0, 3);
        String ID = string.substring(3, 6);


        long versionInt = Integer.parseInt(version, 2);
        long IDInt = Integer.parseInt(ID, 2);





        if (IDInt == 4) {
            Pair<Long, String> t = getPacketLiteral(string.substring(6));
            return new Pair<>(versionInt, t.y);
        } else {
            Pair<Long, String> t = getPacketOperand(string.substring(6), -1);
            return new Pair<>(versionInt + t.x, t.y);
        }

    }




    private Pair<Long, String> getPacketP2(String string) {


        if (!string.contains("1")) {
            return new Pair<>(0L, "");
        }

        String ID = string.substring(3, 6);


        int IDInt = Integer.parseInt(ID, 2);






        if (IDInt == 4) {
            Pair<Long, String> t = getPacketLiteral(string.substring(6));
            return new Pair<>(t.x, t.y);
        } else {
            Pair<Long, String> t = getPacketOperand(string.substring(6), IDInt);
            return new Pair<>(t.x, t.y);
        }

    }

    private Pair<Long, String> getPacketOperand(String substring, int IDInt) {
        String lengthTypeID = substring.substring(0, 1);
        if (lengthTypeID.equals("0")) {
            String length = substring.substring(1, 16);
            String subpackets = substring.substring(16, 16 + Integer.parseInt(length, 2));

            String substringed = substring.substring(16 + Integer.parseInt(length, 2));

            if (IDInt == -1) {
                long i = 0;
                while (!subpackets.isEmpty()) {
                    Pair<Long, String> t = getPacketP1(subpackets);
                    subpackets = t.y;
                    i += t.x;
                }

                return new Pair<>(i, substringed);
            }

            if (IDInt == 0) {
                long i = 0;
                while (!subpackets.isEmpty()) {
                    Pair<Long, String> t = getPacketP2(subpackets);
                    subpackets = t.y;
                    i += t.x;
                }

                return new Pair<>(i, substringed);
            }

            if (IDInt == 1) {
                long i = 1;
                while (!subpackets.isEmpty()) {
                    Pair<Long, String> t = getPacketP2(subpackets);
                    subpackets = t.y;
                    i *= t.x;
                }

                return new Pair<>(i, substringed);
            }

            if (IDInt == 2) {
                long i = Long.MAX_VALUE;
                while (!subpackets.isEmpty()) {
                    Pair<Long, String> t = getPacketP2(subpackets);
                    subpackets = t.y;
                    i = Math.min(i, t.x);
                }

                return new Pair<>(i, substringed);
            }

            if (IDInt == 3) {
                long i = 0;
                while (!subpackets.isEmpty()) {
                    Pair<Long, String> t = getPacketP2(subpackets);
                    subpackets = t.y;
                    i = Math.max(i, t.x);
                }

                return new Pair<>(i, substringed);
            }

            if (IDInt == 5) {
                Pair<Long, String> t = getPacketP2(subpackets);
                Pair<Long, String> t2 = getPacketP2(t.y);

                if (t.x > t2.x) {
                    return new Pair<>(1L, substringed);
                } else {
                    return new Pair<>(0L, substringed);
                }
            }

            if (IDInt == 6) {
                Pair<Long, String> t = getPacketP2(subpackets);
                Pair<Long, String> t2 = getPacketP2(t.y);

                if (t.x < t2.x) {
                    return new Pair<>(1L, substringed);
                } else {
                    return new Pair<>(0L, substringed);
                }
            }

            if (IDInt == 7) {
                Pair<Long, String> t = getPacketP2(subpackets);
                Pair<Long, String> t2 = getPacketP2(t.y);

                if (t.x.equals(t2.x)) {
                    return new Pair<>(1L, substringed);
                } else {
                    return new Pair<>(0L, substringed);
                }
            }

            System.out.println("ERROR");
            return new Pair<>(0L, substringed);

        } else {
            String length = substring.substring(1, 12);
            int packets = Integer.parseInt(length, 2);

            String subpackets = substring.substring(12);

            if (IDInt == -1) {
                long i = 0;
                for (int j = 0; j < packets; j++) {
                    Pair<Long, String> t = getPacketP1(subpackets);
                    subpackets = t.y;
                    i += t.x;
                }

                return new Pair<>(i, subpackets);
            }

            if (IDInt == 0) {
                long i = 0;
                for (int j = 0; j < packets; j++) {
                    Pair<Long, String> t = getPacketP2(subpackets);
                    subpackets = t.y;
                    i += t.x;
                }

                return new Pair<>(i, subpackets);
            }

            if (IDInt == 1) {
                long i = 1;
                for (int j = 0; j < packets; j++) {
                    Pair<Long, String> t = getPacketP2(subpackets);
                    subpackets = t.y;
                    i *= t.x;
                }

                return new Pair<>(i, subpackets);
            }

            if (IDInt == 2) {
                long i = Long.MAX_VALUE;
                for (int j = 0; j < packets; j++) {
                    Pair<Long, String> t = getPacketP2(subpackets);
                    subpackets = t.y;
                    i = Math.min(i, t.x);
                }

                return new Pair<>(i, subpackets);
            }

            if (IDInt == 3) {
                long i = 0;
                for (int j = 0; j < packets; j++) {
                    Pair<Long, String> t = getPacketP2(subpackets);
                    subpackets = t.y;
                    i = Math.max(i, t.x);
                }

                return new Pair<>(i, subpackets);
            }

            if (IDInt == 5) {
                Pair<Long, String> t = getPacketP2(subpackets);
                Pair<Long, String> t2 = getPacketP2(t.y);

                if (t.x > t2.x) {
                    return new Pair<>(1L, t2.y);
                } else {
                    return new Pair<>(0L, t2.y);
                }
            }

            if (IDInt == 6) {
                Pair<Long, String> t = getPacketP2(subpackets);
                Pair<Long, String> t2 = getPacketP2(t.y);

                if (t.x < t2.x) {
                    return new Pair<>(1L, t2.y);
                } else {
                    return new Pair<>(0L, t2.y);
                }
            }

            if (IDInt == 7) {
                Pair<Long, String> t = getPacketP2(subpackets);
                Pair<Long, String> t2 = getPacketP2(t.y);

                if (t.x.equals(t2.x)) {
                    return new Pair<>(1L, t2.y);
                } else {
                    return new Pair<>(0L, t2.y);
                }
            }

            System.out.println("ERROR");
            return new Pair<>(0L, subpackets);


        }


    }

    private Pair<Long, String> getPacketLiteral(String string) {
        StringBuilder data = new StringBuilder();
        int i = 0;
        while (true) {
            String part = string.substring(i, i + 5);

            if (part.charAt(0) == '0') {
                data.append(part.substring(1));
                i += 5;
                break;
            }

            data.append(part.substring(1));

            i += 5;
        }

        String remainder = string.substring(i);

        return new Pair<>(Long.parseLong(data.toString(), 2), remainder);
    }


    public String part2() {
        String input = getInput().get(0);
        StringBuilder converted = new StringBuilder();
        for (char c : input.toCharArray()) {
            int hex = Integer.parseInt(String.valueOf(c), 16);
            StringBuilder binary = new StringBuilder(Integer.toBinaryString(hex));
            while (binary.length() < 4) {
                binary.insert(0, "0");
            }
            converted.append(binary);
        }

        Pair<Long, String> getPacket = getPacketP2(converted.toString());


        return String.valueOf(getPacket.x);


    }



}