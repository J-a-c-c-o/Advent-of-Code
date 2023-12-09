package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphSearch {
    // depth first search
    public static List<GraphNode> depth(GraphNode root, String name) {
        if (root.getName().equals(name)) {
            return new ArrayList<>();
        }

        if (root.hasChildren()) {
            for (GraphNode child : root.getChildren()) {
                List<GraphNode> result = depth(child, name);

                if (result != null) {
                    result.add(child);
                    return result;
                }
            }
        }
        return null;
    }

    public static List<GraphNode> searchDepth(GraphNode root, String name) {
        List<GraphNode> result = depth(root, name);
        if (result != null) {
            result.add(root);
            //reverse the list
            List<GraphNode> reversed = new ArrayList<>();
            for (int i = result.size() - 1; i >= 0; i--) {
                reversed.add(result.get(i));
            }
            return reversed;
        }

        return null;
    }


    // breadth first search
    public static List<GraphNode> breadth(GraphNode root, String name) {
        List<GraphNode[]> queue = new ArrayList<>();
        GraphNode[] rootArray = {root};
        queue.add(rootArray);

        while (!queue.isEmpty()) {
            GraphNode[] path = queue.remove(0);
            GraphNode lastNode = path[path.length - 1];
            if (lastNode.getName().equals(name)) {
                return Arrays.asList(path);
            }

            for (GraphNode child : lastNode.getChildren()) {
                List<GraphNode> newPath = new ArrayList<>(Arrays.asList(path));
                newPath.add(child);
                queue.add(newPath.toArray(new GraphNode[0]));
            }
        }

        return null;
    }


    public static void main(String[] args) {
        // Test
        GraphNode root = new GraphNode("root");
        GraphNode child1 = new GraphNode("child1", root);
        GraphNode child2 = new GraphNode("child2", root);
        GraphNode child3 = new GraphNode("child3", root);
        GraphNode child4 = new GraphNode("child4", child3);
        GraphNode child5 = new GraphNode("child5", child3);
        GraphNode child6 = new GraphNode("child6", child1);
        GraphNode child7 = new GraphNode("child7", child2);
        GraphNode child8 = new GraphNode("child8", child7);
        GraphNode child9 = new GraphNode("child9", child7);
        GraphNode child10 = new GraphNode("child10", child7);
        GraphNode child11 = new GraphNode("child11", child9);
        GraphNode child12 = new GraphNode("final", child9);
        GraphNode child13 = new GraphNode("child13", child6);
        GraphNode child14 = new GraphNode("child14", child6);
        GraphNode child15 = new GraphNode("child15", child14);
        GraphNode child16 = new GraphNode("child16", child15);
        GraphNode child17 = new GraphNode("child17", child16);
        GraphNode child18 = new GraphNode("child18", child17);
        GraphNode child19 = new GraphNode("final", child18);
        root.setChildren(List.of(child1, child2, child3));
        child1.setChildren(List.of(child6));
        child2.setChildren(List.of(child7));
        child3.setChildren(List.of(child4, child5));
        child6.setChildren(List.of(child13, child14));
        child7.setChildren(List.of(child8, child9, child10));
        child9.setChildren(List.of(child11, child12));
        child13.setChildren(List.of(child15));
        child15.setChildren(List.of(child16));
        child16.setChildren(List.of(child17));
        child17.setChildren(List.of(child18));
        child18.setChildren(List.of(child19));


        List<GraphNode> result = searchDepth(root, "final");
        List<GraphNode> result2 = breadth(root, "final");

        System.out.println(result);
        System.out.println(result2);

    }
}
