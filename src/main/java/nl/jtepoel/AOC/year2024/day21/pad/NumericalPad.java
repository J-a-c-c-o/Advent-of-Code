package nl.jtepoel.AOC.year2024.day21.pad;

import nl.jtepoel.AOC.utils.Point;

import java.util.*;

public class NumericalPad extends Pad {



    public NumericalPad() {

        super.current = new Point(2, 3);

        super.grid = new HashMap<>() {{
            put(new Point(0, 0), '7');
            put(new Point(1, 0), '8');
            put(new Point(2, 0), '9');
            put(new Point(0, 1), '4');
            put(new Point(1, 1), '5');
            put(new Point(2, 1), '6');
            put(new Point(0, 2), '1');
            put(new Point(1, 2), '2');
            put(new Point(2, 2), '3');
            put(new Point(1, 3), '0');
            put(new Point(2, 3), 'A');
        }};

        super.keys = new HashMap<>() {{
            put('7', new Point(0, 0));
            put('8', new Point(1, 0));
            put('9', new Point(2, 0));
            put('4', new Point(0, 1));
            put('5', new Point(1, 1));
            put('6', new Point(2, 1));
            put('1', new Point(0, 2));
            put('2', new Point(1, 2));
            put('3', new Point(2, 2));
            put('0', new Point(1, 3));
            put('A', new Point(2, 3));
        }};


    }
}
