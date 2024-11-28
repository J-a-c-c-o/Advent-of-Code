package year2021.day19;

import utils.FrequencySetLong;
import utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
    private int id;
    private int x;
    private int y;
    private int z;
    private List<Beacon> beacons;
    private List<Scanner> combined;

    private int rotate = 0;

    public Scanner(int id, List<String> beacons) {
        this.id = id;
        this.beacons = new ArrayList<>();
        for (String beacon : beacons) {
            String[] split = beacon.split(",");
            this.beacons.add(new Beacon(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
        }
        this.combined = new ArrayList<>();
        combined.add(this);
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void addCombined(Scanner scanner) {
        combined.add(scanner);
    }

    public int getId() {
        return id;
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public boolean combine(Scanner other) {
        for (Beacon beacon : beacons) {
            for (int i = 0; i < 24; i++) {
                other.rotate();
                for (Beacon otherBeacon : other.getBeacons()) {

                    int xOther = otherBeacon.getX();
                    int yOther = otherBeacon.getY();
                    int zOther = otherBeacon.getZ();

                    int x = beacon.getX();
                    int y = beacon.getY();
                    int z = beacon.getZ();

                    int count = 0;
                    boolean found = false;
                    for (Beacon b : beacons) {
                        for (Beacon ob : other.getBeacons()) {
                            int[] xyz = b.getRelative(x, y, z);
                            int[] otherXyz = ob.getRelative(xOther, yOther, zOther);
                            if (xyz[0] == otherXyz[0] && xyz[1] == otherXyz[1] && xyz[2] == otherXyz[2]) {
                                count++;
                            }

                            if (count >= 12) {
                                found = true;
                                break;
                            }
                        }


                        if (found) {
                            break;
                        }
                    }

                    if (found) {
                        for (Beacon ob : other.getBeacons()) {

                            ob.setX(ob.getRelative(xOther, yOther, zOther)[0] + x);
                            ob.setY(ob.getRelative(xOther, yOther, zOther)[1] + y);
                            ob.setZ(ob.getRelative(xOther, yOther, zOther)[2] + z);

                            beacons.add(ob);
                        }

                        other.setX(other.getRelative(xOther, yOther, zOther)[0] + x);
                        other.setY(other.getRelative(xOther, yOther, zOther)[1] + y);
                        other.setZ(other.getRelative(xOther, yOther, zOther)[2] + z);
                        addCombined(other);
                        return true;
                    }


                }
            }
        }

        return false;

    }

    public int[] getRelative(int x, int y, int z) {
        return new int[]{this.x - x, this.y - y, this.z - z};
    }


    int[][][] rotations = {
        {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}},
        {{0, 0, 1}, {0, 1, 0}, {-1, 0, 0}},
        {{-1, 0, 0}, {0, 1, 0}, {0, 0, -1}},
        {{0, 0, -1}, {0, 1, 0}, {1, 0, 0}},

        {{0, -1, 0}, {1, 0, 0}, {0, 0, 1}},
        {{0, 0, 1}, {1, 0, 0}, {0, 1, 0}},
        {{0, 1, 0}, {1, 0, 0}, {0, 0, -1}},
        {{0, 0, -1}, {1, 0, 0}, {0, -1, 0}},

        {{0, 1, 0}, {-1, 0, 0}, {0, 0, 1}},
        {{0, 0, 1}, {-1, 0, 0}, {0, -1, 0}},
        {{0, -1, 0}, {-1, 0, 0}, {0, 0, -1}},
        {{0, 0, -1}, {-1, 0, 0}, {0, 1, 0}},

        {{1, 0, 0}, {0, 0, -1}, {0, 1, 0}},
        {{0, 1, 0}, {0, 0, -1}, {-1, 0, 0}},
        {{-1, 0, 0}, {0, 0, -1}, {0, -1, 0}},
        {{0, -1, 0}, {0, 0, -1}, {1, 0, 0}},

        {{1, 0, 0}, {0, -1, 0}, {0, 0, -1}},
        {{0, 0, -1}, {0, -1, 0}, {-1, 0, 0}},
        {{-1, 0, 0}, {0, -1, 0}, {0, 0, 1}},
        {{0, 0, 1}, {0, -1, 0}, {1, 0, 0}},

        {{1, 0, 0}, {0, 0, 1}, {0, -1, 0}},
        {{0, -1, 0}, {0, 0, 1}, {-1, 0, 0}},
        {{-1, 0, 0}, {0, 0, 1}, {0, 1, 0}},
        {{0, 1, 0}, {0, 0, 1}, {1, 0, 0}},
    };

    private void rotate() {

        rotate++;
        if (rotate == 24) {
            rotate = 0;
        }


        for (Beacon b : beacons) {
            b.rotate(rotations[rotate]);
        }
    }

    public void removeDuplicates() {
        List<Beacon> newBeacons = new ArrayList<>();
        for (Beacon beacon : beacons) {
            boolean found = false;
            for (Beacon newBeacon : newBeacons) {
                if (beacon.equals(newBeacon)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                newBeacons.add(beacon);
            }
        }

        beacons = newBeacons;

    }


    public List<Scanner> getScanners() {
        return combined;
    }

    public long distance(Scanner other) {
        int x = Math.abs(this.x - other.getX());
        int y = Math.abs(this.y - other.getY());
        int z = Math.abs(this.z - other.getZ());

        return x + y + z;
    }
}
