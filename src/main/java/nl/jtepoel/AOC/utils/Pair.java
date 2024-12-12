package nl.jtepoel.AOC.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pair<A, B> {

    public A x;
    public B y;

    public Pair(A x, B y) {
        this.x = x;
        this.y = y;
    }

    public static List<Pair<Long, Long>> zip(List<Long> n, List<Long> a) {
        List<Pair<Long, Long>> pairs = new ArrayList<>();
        for (int i = 0; i < n.size(); i++) {
            pairs.add(new Pair<>(n.get(i), a.get(i)));
        }

        return pairs;
    }

    public static <A, B> Pair<A, B> of(A x, B y) {
        return new Pair<>(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pair<?, ?> pair)) return false;
        return x.equals(pair.x) && y.equals(pair.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Pair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public A getX() {
        return x;
    }

    public B getY() {
        return y;
    }

    public A getFirst() {
        return x;
    }

    public B getSecond() {
        return y;
    }

    public A getLeft() {
        return x;
    }

    public B getRight() {
        return y;
    }
}
