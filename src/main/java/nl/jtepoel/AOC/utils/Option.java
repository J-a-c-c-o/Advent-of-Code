package nl.jtepoel.AOC.utils;

public class Option<A, B> {
    private A a;
    private B b;

    public Option(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    public boolean isA() {
        return a != null;
    }

    public boolean isB() {
        return b != null;
    }

    public void setA(A i) {
        a = i;
    }

    public void setB(B i) {
        b = i;
    }

    public void removeA() {
        a = null;
    }

    public void removeB() {
        b = null;
    }

    @Override
    public String toString() {
        return "Option{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }


}
