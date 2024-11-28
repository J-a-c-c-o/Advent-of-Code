package utils;

import java.util.Objects;

public class Edge {
    private final Node A;
    private final Node B;
    private int weight;

    public Edge(Node A, Node B, int weight) {
        this.A = A;
        this.B = B;
        this.weight = weight;
    }

    public Node getA() {
        return A;
    }

    public Node getB() {
        return B;
    }

    public int getWeight() {
        return weight;
    }

    public boolean hasNode(Node node) {
        return A.equals(node) || B.equals(node);
    }

    public Node getOtherNode(Node node) {
        if (A.equals(node)) {
            return B;
        } else if (B.equals(node)) {
            return A;
        } else {
            return null;
        }
    }

    public void setWeight(int maxValue) {
        weight = maxValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge edge)) return false;
        return (A.equals(edge.A) && B.equals(edge.B)) || (A.equals(edge.B) && B.equals(edge.A));
    }

    @Override
    public String toString() {
        return "Edge{" +
                "A=" + A.getName() +
                ", B=" + B.getName() +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(A, B, weight);
    }
}
