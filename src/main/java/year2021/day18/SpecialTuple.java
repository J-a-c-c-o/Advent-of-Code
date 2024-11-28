package year2021.day18;

import utils.Option;

public class SpecialTuple {
    int depth;
    SpecialTuple parent;

    Option<Integer, SpecialTuple> left;
    Option<Integer, SpecialTuple> right;

    public SpecialTuple(int depth, Option<Integer, SpecialTuple> left, Option<Integer, SpecialTuple> right) {
        this.depth = depth;
        this.left = left;
        this.right = right;
    }

    public SpecialTuple(int depth, Option<Integer, SpecialTuple> left, Option<Integer, SpecialTuple> right, SpecialTuple parent) {
        this.depth = depth;
        this.left = left;
        this.right = right;
        this.parent = parent;
    }

    public int getDepth() {
        return depth;
    }

    public Option<Integer, SpecialTuple> getLeft() {
        return left;
    }

    public Option<Integer, SpecialTuple> getRight() {
        return right;
    }

    public boolean isLeaf() {
        return left.isA() && right.isA();
    }

    public void setParent(SpecialTuple parent) {
        this.parent = parent;
    }

    public SpecialTuple getParent() {
        return parent;
    }


    public void incrementDepth() {
        depth++;
        if (left.isB()) {
            left.getB().incrementDepth();
        }
        if (right.isB()) {
            right.getB().incrementDepth();
        }
    }

    public void decrementDepth() {
        depth--;
        if (left.isB()) {
            left.getB().decrementDepth();
        }
        if (right.isB()) {
            right.getB().decrementDepth();
        }
    }

    public boolean hasOption(Option<Integer, SpecialTuple> option) {
        return left.equals(option) || right.equals(option);
    }

    @Override
    public String toString() {
        String result = "[";
        if (left.isA()) {
            result += left.getA();
        } else {
            result += left.getB().toString();
        }

        result += ",";

        if (right.isA()) {
            result += right.getA();
        } else {
            result += right.getB().toString();
        }

        result += "]";

        return result;
    }


    public void replace(Option<Integer, SpecialTuple> explode, int i) {
        if (left.equals(explode)) {
            left.removeB();
            left.setA(i);
        } else if (right.equals(explode)) {
            right.removeB();
            right.setA(i);
        }
    }
}
