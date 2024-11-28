package year2021.day19;

import java.util.Set;

public class Beacon {
    private int originalX;
    private int originalY;
    private int originalZ;
    private int x;
    private int y;
    private int z;

    public Beacon(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.originalX = x;
        this.originalY = y;
        this.originalZ = z;
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

    public void setX(int x) {
        this.x = x;
        this.originalX = x;
    }

    public void setY(int y) {
        this.y = y;
        this.originalY = y;
    }

    public void setZ(int z) {
        this.z = z;
        this.originalZ = z;
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Beacon beacon = (Beacon) o;

        if (originalX != beacon.originalX) return false;
        if (originalY != beacon.originalY) return false;
        return originalZ == beacon.originalZ;
    }


    public int[] getRelative(int x, int y, int z) {
        return new int[]{this.x - x, this.y - y, this.z - z};
    }


    public void rotate(int[][] rotations) {
        x = originalX * rotations[0][0] + originalY * rotations[0][1] + originalZ * rotations[0][2];
        y = originalX * rotations[1][0] + originalY * rotations[1][1] + originalZ * rotations[1][2];
        z = originalX * rotations[2][0] + originalY * rotations[2][1] + originalZ * rotations[2][2];

    }
}
