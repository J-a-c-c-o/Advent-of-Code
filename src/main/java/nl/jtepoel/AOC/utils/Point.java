package nl.jtepoel.AOC.utils;

import java.util.Arrays;
import java.util.Objects;

public class Point {

    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char turn(char direction) {
        switch (direction) {
            case '^' -> {
                return '>';
            }
            case '>' -> {
                return 'v';
            }
            case 'v' -> {
                return '<';
            }
            case '<' -> {
                return '^';
            }
            default -> {
                return ' ';
            }
        }
    }

    public Point move(char direction) {
        switch (direction) {
            case '^' -> {
                return new Point(x, y - 1);
            }
            case '>' -> {
                return new Point(x + 1, y);
            }
            case 'v' -> {
                return new Point(x, y + 1);
            }
            case '<' -> {
                return new Point(x - 1, y);
            }
            default -> {
                return new Point(x, y);
            }
        }
    }

    public Point forward(char direction, boolean right) {
        switch (direction) {
            case '^' -> {
                return right ? new Point(x, y - 1) : new Point(x, y + 1);
            }
            case '>' -> {
                return right ? new Point(x + 1, y) : new Point(x - 1, y);
            }
            case 'v' -> {
                return right ? new Point(x, y + 1) : new Point(x, y - 1);
            }
            case '<' -> {
                return right ? new Point(x - 1, y) : new Point(x + 1, y);
            }
            default -> {
                return new Point(x, y);
            }
        }

    }
}
