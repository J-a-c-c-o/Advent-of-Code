package utils;

import java.util.List;

public class GraphNode {
    private final String name;
    private final GraphNode parent;
    private GraphNode[] children;

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

    public void setChildren(GraphNode[] children) {
        this.children = children;
    }

    public void setChildren(List<GraphNode> children) {
        this.children = children.toArray(new GraphNode[0]);
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
