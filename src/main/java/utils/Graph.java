package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.SingleGraph;

public class Graph {
    private Set<Node> nodes;

    public Graph(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public Graph() {
        this.nodes = new HashSet<>();
    }

    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addNode(String name) {
        Node node = new Node(new ArrayList<>(), name);
        addNode(node);
    }

    public void removeNode(Node node) {
        nodes.remove(node);
    }

    public boolean hasNode(Node node) {
        return nodes.contains(node);
    }

    public boolean hasNode(String name) {
        for (Node node : nodes) {
            if (node.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Node getNode(String name) {
        for (Node node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    public void addEdge(Edge edge) {
        Node A = edge.getA();
        Node B = edge.getB();
        A.addEdge(edge);
        B.addEdge(edge);
    }

    public void addEdgeDir(Edge edge) {
        Node A = edge.getA();
        A.addEdge(edge);
    }

    public void addEdge(Node A, Node B, int weight) {
        Edge edge = new Edge(A, B, weight);
        addEdge(edge);
    }

    public void addEdgeDir(Node A, Node B, int weight) {
        Edge edge = new Edge(A, B, weight);
        addEdgeDir(edge);
    }

    public void addEdge(String A, String B, int weight) {
        Node nodeA = getNode(A);
        Node nodeB = getNode(B);
        addEdge(nodeA, nodeB, weight);
    }

    public void addEdgeDir(String A, String B, int weight) {
        Node nodeA = getNode(A);
        Node nodeB = getNode(B);
        addEdgeDir(nodeA, nodeB, weight);
    }

    public void removeEdge(Edge edge) {
        Node A = edge.getA();
        Node B = edge.getB();
        A.removeEdge(edge);
        B.removeEdge(edge);
    }

    public void removeEdge(Node A, Node B) {
        for (Edge edge : A.getEdges()) {
            if (edge.hasNode(B)) {
                removeEdge(edge);
                return;
            }
        }
    }

    public void removeEdge(String A, String B) {
        Node nodeA = getNode(A);
        Node nodeB = getNode(B);
        removeEdge(nodeA, nodeB);
    }


    public List<List<Node>> getPathsBreath(Node start, Node end) {
        List<List<Node>> paths = getPaths(start, end, new ArrayList<>());
        // Remove duplicates
        Set<List<Node>> set = new HashSet<>(paths);
        paths.clear();
        paths.addAll(set);
        return paths;
    }

    public List<List<Node>> getPaths(Node current, Node end, List<Node> visited) {
        List<List<Node>> paths = new ArrayList<>();
        visited.add(current);
        if (current.equals(end)) {
            paths.add(new ArrayList<>(visited));
        } else {
            for (Edge edge : current.getEdges()) {
                Node other = edge.getOtherNode(current);
                if (!visited.contains(other)) {
                    paths.addAll(getPaths(other, end, visited));
                }
            }
        }
        visited.remove(current);
        return paths;
    }

    public List<Node> getAPath(Node start, Node end) {
        List<Node> path = new ArrayList<>();
        path.add(start);
        getAPath(start, end, path);
        return path;
    }

    private boolean getAPath(Node current, Node end, List<Node> path) {
        if (current.equals(end)) {
            return true;
        }
        for (Edge edge : current.getEdges()) {
            Node other = edge.getOtherNode(current);
            if (!path.contains(other)) {
                path.add(other);
                if (getAPath(other, end, path)) {
                    return true;
                }
                path.remove(other);
            }
        }
        return false;
    }

    public class PathInfo {
        List<Node> path;
        int distance;

        public PathInfo(List<Node> path, int distance) {
            this.path = path;
            this.distance = distance;
        }
    }

    public List<Node> dijkstra(Node start, Node end) {
        Map<Node, PathInfo> weights = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> weights.get(node).distance));

        for (Node node : nodes) {
            weights.put(node, new PathInfo(new ArrayList<>(), Integer.MAX_VALUE));
        }

        assert nodes.size() == weights.size();

        Set<Edge> edges = new HashSet<>();

        weights.get(start).path.add(start);
        weights.get(start).distance = 0;
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (weights.get(current).distance == Integer.MAX_VALUE) {
                return new ArrayList<>(); // No path found
            }

            if (current.equals(end)) {
                return weights.get(current).path;
            }

            for (Edge edge : current.getEdges()) {

                if (edges.contains(edge)) {
                    continue;
                }


                edges.add(edge);

                Node neighbor = edge.getOtherNode(current);
                int newDistance = weights.get(current).distance + edge.getWeight();

                if (newDistance < weights.get(neighbor).distance) {
                    weights.get(neighbor).path = new ArrayList<>(weights.get(current).path);
                    weights.get(neighbor).path.add(neighbor);
                    weights.get(neighbor).distance = newDistance;
                    queue.remove(neighbor); // Update priority by removing and re-adding
                    queue.add(neighbor);
                }
            }
        }

        return new ArrayList<>(); // No path found to the end node
    }

    private int size() {
        return nodes.size();
    }

    public Graph copy() {
        Graph graph = new Graph();
        for (Node node : nodes) {
            graph.addNode(node.copy());
        }

        Set<Edge> edges = new HashSet<>();
        for (Node node : nodes) {
            edges.addAll(node.getEdges());
        }

        for (Edge edge : edges) {
            Node A = graph.getNode(edge.getA().getName());
            Node B = graph.getNode(edge.getB().getName());
            graph.addEdge(A, B, edge.getWeight());
        }


        return graph;
    }

    public void visualize(boolean displayEdgeNames, boolean displayNodeNames) {
        System.setProperty("org.graphstream.ui", "swing");
        org.graphstream.graph.Graph graph = new SingleGraph("Graph");
        for (Node node : nodes) {
            graph.addNode(node.getName());

            if (displayNodeNames) {
                graph.getNode(node.getName()).setAttribute("ui.label", node.getName());
                // text color red
                graph.getNode(node.getName()).setAttribute("ui.style", "text-color: red;");
                // node color green
                graph.getNode(node.getName()).setAttribute("ui.style", "fill-color: green;");
            }
        }

        for (Node node : nodes) {
            for (Node other : node.getConnected()) {
                if (node.getName().compareTo(other.getName()) < 0) {
                    graph.addEdge(node.getName() + "---" + other.getName(), node.getName(), other.getName());

                    if (displayEdgeNames) {
                        graph.getEdge(node.getName() + "---" + other.getName()).setAttribute("ui.label", node.getName() + "---" + other.getName());
                        // color red
                        graph.getEdge(node.getName() + "---" + other.getName()).setAttribute("ui.style", "text-color: red;");
                    }
                }
            }
        }


        graph.display();
    }

    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                '}';
    }
}
