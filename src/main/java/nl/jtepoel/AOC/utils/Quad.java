package nl.jtepoel.AOC.utils;

import java.util.Objects;

public class Quad<T, T1, T2, T3> {
    private T first;
    private T1 second;
    private T2 third;
    private T3 fourth;

    public Quad(T first, T1 second, T2 third, T3 fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T1 getSecond() {
        return second;
    }

    public void setSecond(T1 second) {
        this.second = second;
    }

    public T2 getThird() {
        return third;
    }

    public void setThird(T2 third) {
        this.third = third;
    }

    public T3 getFourth() {
        return fourth;
    }

    public void setFourth(T3 fourth) {
        this.fourth = fourth;
    }

    @Override
    public String toString() {
        return "Quad{" +
                "first=" + first +
                ", second=" + second +
                ", third=" + third +
                ", fourth=" + fourth +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Quad<?, ?, ?, ?> quad)) return false;
        return first.equals(quad.first) && second.equals(quad.second) && third.equals(quad.third) && fourth.equals(quad.fourth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third, fourth);
    }
}
