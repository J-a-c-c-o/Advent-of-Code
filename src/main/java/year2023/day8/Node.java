package year2023.day8;

public class Node {
    private final String name;
    private Node left;
    private Node right;

    public Node(String name) {
        this.name = name;
    }


    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public String getName() {
        return name;
    }
}
