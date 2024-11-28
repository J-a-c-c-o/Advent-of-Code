package year2023.day22;

import java.util.List;
import java.util.Objects;

public class Brick {
    private final int startX;
    private final int startY;
    private int startZ;
    private final int endX;
    private final int endY;
    private int endZ;
    private final int id;

    public Brick(int startX, int startY, int startZ, int endX, int endY, int endZ, int id) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.startZ = startZ;
        this.endZ = endZ;
        this.id = id;
    }

    public Brick(Brick box) {
        this.startX = box.getStartX();
        this.startY = box.getStartY();
        this.endX = box.getEndX();
        this.endY = box.getEndY();
        this.startZ = box.getStartZ();
        this.endZ = box.getEndZ();
        this.id = box.getId();
    }

    public boolean checkIfMoveDown(List<Brick> boxes) {

        if (getStartZ() == 1) {
            return false;
        }


        for (Brick box : boxes) {
            if (box != this && (getStartZ() - 1 <= box.getEndZ() && getEndZ() - 1 >= box.getStartZ() && getStartX() <= box.getEndX() && getEndX() >= box.getStartX() && getStartY() <= box.getEndY() && getEndY() >= box.getStartY())) {
                return false;
            }
        }

        return true;
    }

    public void moveDown(List<Brick> boxes) {
        while (checkIfMoveDown(boxes)) {
            startZ = getStartZ() - 1;
            endZ = getEndZ() - 1;
        }
    }


    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getStartZ() {
        return startZ;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getEndZ() {
        return endZ;
    }

    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return "(" + getStartX() + "," + getStartY() + "," + getStartZ() + "," + getEndX() + "," + getEndY() + "," + getEndZ() + ")" ;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Brick other) {
            return getStartX() == other.getStartX() && getStartY() == other.getStartY() && getStartZ() == other.getStartZ() && getEndX() == other.getEndX() && getEndY() == other.getEndY() && getEndZ() == other.getEndZ();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartX(), getStartY(), getStartZ(), getEndX(), getEndY(), getEndZ());
    }
}
