package nl.jtepoel.AOC.utils;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private List<Edge> edges;

    private final String name;

    public Node(List<Edge> edges, String name) {
        this.edges = edges;
        this.name = name;
    }

    public Node(String name) {
        this(new ArrayList<>(), name);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void addEdge(Node node) {
        Edge edge = new Edge(this, node, 1);
        edges.add(edge);
    }

    public void addEdge(Node node, int weight) {
        Edge edge = new Edge(this, node, weight);
        edges.add(edge);
    }


    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public void removeEdge(Node node) {
        for (Edge edge : edges) {
            if (edge.hasNode(node)) {
                edges.remove(edge);
                return;
            }
        }
    }

    public boolean hasEdge(Edge edge) {
        return edges.contains(edge);
    }

    public boolean hasEdge(Node node) {
        for (Edge edge : edges) {
            if (edge.hasNode(node)) {
                return true;
            }
        }
        return false;
    }


    public List<Node> getConnected() {
        List<Node> connected = new ArrayList<>();
        for (Edge edge : edges) {
            connected.add(edge.getOtherNode(this));
        }
        return connected;
    }

    public Edge getEdge(Node node) {
        for (Edge edge : edges) {
            if (edge.hasNode(node)) {
                return edge;
            }
        }
        return null;
    }

    public void setWeight(int maxValue) {
        for (Edge edge : edges) {
            edge.setWeight(maxValue);
        }
    }

    public int getDegree() {
        return edges.size();
    }

    public String getName() {
        return name;
    }

    public Node copy() {
        return new Node(new ArrayList<>(), name);
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Node node)) {
            return false;
        }
        return name.equals(node.name);
    }


}
