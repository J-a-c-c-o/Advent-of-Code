package nl.jtepoel.AOC.utils;

import java.util.*;

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
        for (Node other : node.getConnected()) {
            other.removeEdge(node);
        }
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

    public void addEdge(Node A, Node B, int weight, String name) {
        Edge edge = new Edge(A, B, weight, name);
        addEdge(edge);
    }

    public void addEdgeDir(Node A, Node B, int weight) {
        Edge edge = new Edge(A, B, weight);
        addEdgeDir(edge);
    }

    public void addEdgeDir(Node A, Node B, int weight, String name) {
        Edge edge = new Edge(A, B, weight, name);
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

    public List<List<Node>> getConnectedComponents() {
        List<List<Node>> components = new ArrayList<>();
        Set<Node> visited = new HashSet<>();
        for (Node node : nodes) {
            if (!visited.contains(node)) {
                List<Node> component = new ArrayList<>();
                getConnectedComponent(node, visited, component);
                components.add(component);
            }
        }
        return components;
    }

    private void getConnectedComponent(Node node, Set<Node> visited, List<Node> component) {
        if (visited.contains(node)) {
            return;
        }

        visited.add(node);

        component.add(node);
    }

    private List<Node> getDegeneracyOrdering() {

        List<Node> degeneracyOrdering = new ArrayList<>(nodes);
        degeneracyOrdering.sort(Comparator.comparingInt(Node::getDegree));

        return degeneracyOrdering;
    }


    /**
     * algorithm BronKerbosch3(G) is.
     *     P = V(G)
     *     R = X = empty
     *     for each vertex v in a degeneracy ordering of G do
     *         BronKerbosch2({v}, P ⋂ N(v), X ⋂ N(v))
     *         P := P \ {v}
     *         X := X ⋃ {v}
     * @return the biggest clique
     */
    public List<Node> bronKerbosch() {
        List<Node> biggest = new ArrayList<>();
        HashSet<Node> P = new HashSet<>(nodes);
        HashSet<Node> X = new HashSet<>();


        List<Node> degeneracyOrdering = getDegeneracyOrdering();
        for (Node node : degeneracyOrdering) {
            HashSet<Node> newR = new HashSet<>();
            newR.add(node);
            HashSet<Node> newP = new HashSet<>(P);
            newP.retainAll(node.getConnected());
            HashSet<Node> newX = new HashSet<>(X);
            newX.retainAll(node.getConnected());
            HashSet<Node> temp = bronKerbosch2(newR, newP, newX);

            if (temp.size() > biggest.size()) {
                biggest = new ArrayList<>(temp);
            }

            P.remove(node);
            X.add(node);
        }

        return biggest;
    }



    /**
     * algorithm BronKerbosch1(R, P, X) is.
     *     if P and X are both empty then
     *         report R as a maximal clique
     *     for each vertex v in P do
     *         BronKerbosch1(R ⋃ {v}, P ⋂ N(v), X ⋂ N(v))
     *         P := P \ {v}
     *         X := X ⋃ {v}
     * @param R is the set of vertices in the current clique
     * @param P is the set of vertices that can be added to the current clique
     * @param X is the set of vertices that cannot be added to the current clique
     * @return the biggest clique
     */
    private HashSet<Node> bronKerbosch1(HashSet<Node> R, HashSet<Node> P, HashSet<Node> X) {
        if (P.isEmpty() && X.isEmpty()) {
            return R;
        }

        HashSet<Node> biggest = new HashSet<>();
        HashSet<Node> pCopy = new HashSet<>(P);
        for (Node node : pCopy) {
            HashSet<Node> newR = new HashSet<>(R);
            newR.add(node);
            HashSet<Node> newP = new HashSet<>(P);
            newP.retainAll(node.getConnected());
            HashSet<Node> newX = new HashSet<>(X);
            newX.retainAll(node.getConnected());
            HashSet<Node> temp = bronKerbosch1(newR, newP, newX);

            if (temp.size() > biggest.size()) {
                biggest = temp;
            }

            P.remove(node);
            X.add(node);
        }

        return biggest;
    }

    /**
     * algorithm BronKerbosch2(R, P, X) is.
     *     if P and X are both empty then
     *         report R as a maximal clique
     *     choose a pivot vertex u in P ⋃ X
     *     for each vertex v in P \ N(u) do
     *         BronKerbosch2(R ⋃ {v}, P ⋂ N(v), X ⋂ N(v))
     *         P := P \ {v}
     *         X := X ⋃ {v}
     *
     *
     * @param R is the set of vertices in the current clique
     * @param P is the set of vertices that can be added to the current clique
     * @param X is the set of vertices that cannot be added to the current clique
     * @return the biggest clique
     */
    private HashSet<Node> bronKerbosch2(HashSet<Node> R, HashSet<Node> P, HashSet<Node> X) {
        if (P.isEmpty() && X.isEmpty()) {
            return R;
        }

        Node pivot = P.isEmpty() ? X.iterator().next() : P.iterator().next();

        HashSet<Node> biggest = new HashSet<>();
        HashSet<Node> pCopy = new HashSet<>(P);
        for (Node node : pCopy) {
            if (!node.getConnected().contains(pivot)) {
                HashSet<Node> newR = new HashSet<>(R);
                newR.add(node);
                HashSet<Node> newP = new HashSet<>(P);
                newP.retainAll(node.getConnected());
                HashSet<Node> newX = new HashSet<>(X);
                newX.retainAll(node.getConnected());
                HashSet<Node> temp = bronKerbosch2(newR, newP, newX);

                if (temp.size() > biggest.size()) {
                    biggest = temp;
                }

                P.remove(node);
                X.add(node);
            }
        }

        return biggest;
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
            graph.addEdge(A, B, edge.getWeight(), edge.getName());
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

    public Set<String> minCut() {
        Graph graph = this.copy();

        while (graph.size() > 2) {
            Edge edge = graph.getRandomEdge();
            Node A = edge.getA();
            Node B = edge.getB();
            graph.removeNode(A);
            graph.removeNode(B);
            Node newNode = new Node(new ArrayList<>(), A.getName() + B.getName());
            graph.addNode(newNode);

            for (Edge e : A.getEdges()) {
                if (e.getOtherNode(A).equals(B)) {
                    continue;
                }
                Node other = e.getOtherNode(A);
                graph.addEdge(newNode, other, e.getWeight(), e.getName());
            }

            for (Edge e : B.getEdges()) {
                if (e.getOtherNode(B).equals(A)) {
                    continue;
                }
                Node other = e.getOtherNode(B);
                graph.addEdge(newNode, other, e.getWeight(), e.getName());
            }
        }

        Set<String> cuts = new HashSet<>();
        for (Node node : graph.nodes) {
            System.out.println(node.getEdges());
            for (Edge edge : node.getEdges()) {
                cuts.add(edge.getName());
            }
        }

        return cuts;

    }

    private Edge getRandomEdge() {
        Set<Edge> edges = new HashSet<>();
        for (Node node : nodes) {
            edges.addAll(node.getEdges());
        }
        List<Edge> edgeList = new ArrayList<>(edges);
        Collections.shuffle(edgeList);
        return edgeList.getFirst();
    }




    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                '}';
    }
}
