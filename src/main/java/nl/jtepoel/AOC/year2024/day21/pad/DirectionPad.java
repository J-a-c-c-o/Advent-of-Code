package nl.jtepoel.AOC.year2024.day21.pad;

import nl.jtepoel.AOC.utils.Point;

import java.util.*;

public class DirectionPad extends Pad {
    public DirectionPad() {
        super.current = new Point(2, 0);

        super.grid = new HashMap<>() {{
            put(new Point(1, 0), '^');
            put(new Point(2, 0), 'A');
            put(new Point(0, 1), '<');
            put(new Point(1, 1), 'v');
            put(new Point(2, 1), '>');
        }};

        super.keys = new HashMap<>() {{
            put('^', new Point(1, 0));
            put('A', new Point(2, 0));
            put('<', new Point(0, 1));
            put('v', new Point(1, 1));
            put('>', new Point(2, 1));
        }};

    }



}
