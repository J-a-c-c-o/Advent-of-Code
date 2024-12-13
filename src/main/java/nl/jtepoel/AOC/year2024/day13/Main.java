package nl.jtepoel.AOC.year2024.day13;


import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.List;

import com.microsoft.z3.*;
import nl.jtepoel.AOC.utils.Point;
import nl.jtepoel.AOC.utils.Triple;
import nl.jtepoel.AOC.utils.Utils;

public class Main {

    Utils utils = new Utils();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Main main = new Main();
        String part1 = main.part1();
        long part1Time = System.currentTimeMillis() - start;
        String part2 = main.part2();
        long part2Time = System.currentTimeMillis() - start - part1Time;

        System.out.println("Part 1: " + part1 + " took " + part1Time + "ms");
        System.out.println("Part 2: " + part2 + " took " + part2Time + "ms");
    }

    public List<Triple<Point,Point,Point>> getInput() {
        List<String> lines = utils.getLines(Utils.LOCSRC + "/year2024/day13/input.txt");
        List<Triple<Point,Point,Point>> out = new ArrayList<>();
        for (int i = 0; i < lines.size(); i+=4) {
            String[] split1 = lines.get(i).split(": ")[1].split(", ");
            String[] split2 = lines.get(i+1).split(": ")[1].split(", ");
            String[] split3 = lines.get(i+2).split(": ")[1].split(", ");
            Point p1 = new Point(Integer.parseInt(split1[0].substring(2)), Integer.parseInt(split1[1].substring(2)));
            Point p2 = new Point(Integer.parseInt(split2[0].substring(2)), Integer.parseInt(split2[1].substring(2)));
            Point p3 = new Point(Integer.parseInt(split3[0].substring(2)), Integer.parseInt(split3[1].substring(2)));

            out.add(new Triple<>(p1,p2,p3));
        }

        return out;
    }

    public String part1() {
        List<Triple<Point,Point,Point>> input = getInput();

        long res = calculate(input, 0);

        return String.valueOf(res);
    }

    public String part2() {
        List<Triple<Point,Point,Point>> input = getInput();

        long res = calculate(input, 10000000000000L);

        return String.valueOf(res);
    }

    private static long calculate(List<Triple<Point, Point, Point>> input, long reward) {
        long res = 0;
        try(Context context = new Context()) {
            for (Triple<Point,Point,Point> t : input) {
                Point p1 = t.getFirst();
                Point p2 = t.getSecond();
                Point to = t.getThird();

                Solver solver = context.mkSolver();

                IntExpr p1x = context.mkInt(p1.x);
                IntExpr p1y = context.mkInt(p1.y);
                IntExpr p2x = context.mkInt(p2.x);
                IntExpr p2y = context.mkInt(p2.y);
                IntExpr tox = context.mkInt(to.x + reward);
                IntExpr toy = context.mkInt(to.y + reward);

                IntExpr x = context.mkIntConst("x");
                IntExpr y = context.mkIntConst("y");

                solver.add(context.mkEq(context.mkAdd(context.mkMul(p1x, x), context.mkMul(p2x, y)), tox));
                solver.add(context.mkEq(context.mkAdd(context.mkMul(p1y, x), context.mkMul(p2y, y)), toy));

                solver.add(context.mkGe(x, context.mkInt(0)));
                solver.add(context.mkGe(y, context.mkInt(0)));

                if (solver.check() != Status.SATISFIABLE) {
                    continue;
                }

                Model model = solver.getModel();

                Expr<IntSort> pxr1 = model.eval(x, false);
                Expr<IntSort> pyr1 = model.eval(y, false);

                res += Long.parseLong(pxr1.toString()) * 3 + Long.parseLong(pyr1.toString());
            }
        }
        return res;
    }

}