package year2023.day8;

public class Haunted {
    private String name;
    private Haunted left;
    private Haunted right;

    public Haunted(String name) {
        this.name = name;
    }


    public void setLeft(Haunted left) {
        this.left = left;
    }

    public void setRight(Haunted right) {
        this.right = right;
    }

    public Haunted getLeft() {
        return left;
    }

    public Haunted getRight() {
        return right;
    }

    public String getName() {
        return name;
    }
}
