package year2021.day20;

import utils.Grid;
import utils.Point;

public class GridTrench extends Grid<Integer> {
    
    public int calculateNumber(int x, int y) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            binary.append(getOrDefault(x + i % 3 - 1, y + i / 3 - 1, 0));
        }

        return Integer.parseInt(binary.toString(), 2);
    }

    public int getMin() {
        int min = Integer.MAX_VALUE;
        for (int x : super.getGrid().values()) {
            min = Math.min(min, x);
        }
        return min;
    }

    public int getMax() {
        int max = Integer.MIN_VALUE;
        for (int x : super.getGrid().values()) {
            max = Math.max(max, x);
        }
        return max;
    }

    public int LitCount() {
        int count = 0;
        for (int x : super.getGrid().values()) {
            count += x;
        }
        return count;
    }

    public GridTrench copy() {
        GridTrench copy = new GridTrench();
        for (Point p : super.getGrid().keySet()) {
            copy.set(p.x, p.y, super.get(p));
        }
        return copy;
    }
}
