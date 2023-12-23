package utils;

import java.util.List;

public class GraphNode {
    private final String name;
    private final GraphNode parent;
    private GraphNode[] children;
    private int[] cords;

    public GraphNode(String name, GraphNode parent) {
        this.name = name;
        this.parent = parent;
        this.children = new GraphNode[0];
    }

    public GraphNode(String name) {
        this.name = name;
        this.parent = null;
        this.children = new GraphNode[0];
    }

    public GraphNode(String name, int[] cords) {
        this.name = name;
        this.parent = null;
        this.children = new GraphNode[0];
        this.cords = cords;
    }

    public GraphNode(String name, GraphNode parent, int[] cords) {
        this.name = name;
        this.parent = parent;
        this.children = new GraphNode[0];
        this.cords = cords;
    }

    public GraphNode(String start, GraphNode parent, Pair<Integer, Integer> cords) {
        this.name = start;
        this.parent = parent;
        this.children = new GraphNode[0];
        this.cords = new int[]{cords.x, cords.y};
    }

    public int[] getCords() {
        return cords;
    }

    public void setChildren(GraphNode[] children) {
        this.children = children;
    }

    public void setChildren(List<GraphNode> children) {
        this.children = children.toArray(new GraphNode[0]);
    }

    public void addChild(GraphNode child) {
        GraphNode[] newChildren = new GraphNode[children.length + 1];
        for (int i = 0; i < children.length; i++) {
            newChildren[i] = children[i];
        }
        newChildren[children.length] = child;
        children = newChildren;
    }

    public GraphNode[] getChildren() {
        return children;
    }

    public GraphNode getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public boolean hasChildren() {
        return children.length > 0;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public String toString() {
        return name;
    }
}
