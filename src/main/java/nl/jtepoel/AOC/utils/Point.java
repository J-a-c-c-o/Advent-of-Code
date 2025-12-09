package nl.jtepoel.AOC.utils;

import java.util.Objects;

public class Point implements Comparable<Point> {

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
        return x * 31 + y;
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

    public Point[] getNeighbours() {
        return new Point[]{
                new Point(x, y - 1),
                new Point(x + 1, y),
                new Point(x, y + 1),
                new Point(x - 1, y)
        };
    }

    public Point[] getDiagonals() {
        return new Point[]{
                new Point(x - 1, y - 1),
                new Point(x + 1, y - 1),
                new Point(x + 1, y + 1),
                new Point(x - 1, y + 1)
        };
    }

    public Point add(int i, int i1) {
        return new Point(x + i, y + i1);
    }

    public int manhattan(Point end) {
        return Math.abs(x - end.x) + Math.abs(y - end.y);
    }

    @Override
    public int compareTo(Point o) {
        if (x == o.x) {
            if (y == o.y) {
                return 0;
            }
            return Integer.compare(y, o.y);
        }
        return Integer.compare(x, o.x);
    }
}
