package nl.jtepoel.AOC.utils;

import java.util.Objects;

public class Triple<T, U, V> {
    public final T x;
    public final U y;
    public V z;

    public Triple(T first, U second, V third) {
        this.x = first;
        this.y = second;
        this.z = third;
    }


    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Triple<?, ?, ?> other)) return false;
        return other.x.equals(x) && other.y.equals(y) && other.z.equals(z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public T getFirst() {
        return x;
    }

    public U getSecond() {
        return y;
    }

    public V getThird() {
        return z;
    }

    public void setThird(V b) {
        this.z = b;
    }
}
