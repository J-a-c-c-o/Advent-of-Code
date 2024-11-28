package year2023.day24;


import com.microsoft.z3.*;
import utils.Utils;

import java.util.InvalidPropertiesFormatException;
import java.util.List;

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

    public double[][] getInput() {
        List<String> lines = utils.getLines("src/year2023/day24/input.txt");

        double[][] hailStones = new double[lines.size()][6];
        int i = 0;
        for (String line : lines) {
            String[] split = line.split(", | @ ");
            for (int j = 0; j < split.length; j++) {
                hailStones[i][j] = Double.parseDouble(split[j]);
            }
            i++;
        }



        return hailStones;

    }

    public String part1() {
        double[][] hailStones = getInput();

        int count = 0;


        for (int i = 0; i < hailStones.length; i++) {
            double[] hailStone = hailStones[i];
            double a = (hailStone[4] / hailStone[3]);
            double y = hailStone[1] - (a * hailStone[0]);

            for (int j = i + 1; j < hailStones.length; j++) {
                double[] hailStone2 = hailStones[j];
                double a2 = hailStone2[4] / hailStone2[3];
                double y2 = hailStone2[1] - (a2 * hailStone2[0]);

                if (a == a2) {
                    continue;
                }

                double ix = (y2 - y) / (a - a2);
                double iy = a * ix + y;

                double dir = (ix - hailStone[0]) / hailStone[3];
                double dir2 = (ix - hailStone2[0]) / hailStone2[3];

                if (dir < 0 || dir2 < 0) {
                    continue;
                }

                if (ix >= 200000000000000L && ix <= 400000000000000L && iy >= 200000000000000L && iy <= 400000000000000L) {
                    count++;
                }

            }

        }

        return String.valueOf(count);
    }

    public String part2() {
        double[][] hailStonesDouble = getInput();

        long[][] hailStones = new long[hailStonesDouble.length][6];
        for (int i = 0; i < hailStonesDouble.length; i++) {
            for (int j = 0; j < hailStonesDouble[i].length; j++) {
                hailStones[i][j] = (long) hailStonesDouble[i][j];
            }
        }


        try(Context context = new Context()) {

            Solver solver = context.mkSolver();

            IntExpr pxr = context.mkIntConst("pxr");
            IntExpr pyr = context.mkIntConst("pyr");
            IntExpr pzr = context.mkIntConst("pzr");
            IntExpr vxr = context.mkIntConst("vxr");
            IntExpr vyr = context.mkIntConst("vyr");
            IntExpr vzr = context.mkIntConst("vzr");


            for (int i = 0; i < 3; i++) {
                long[] hailStone = hailStones[i];


                IntExpr tk = context.mkIntConst("tk" + i);

                BoolExpr c = context.mkAnd(
                    context.mkGe(tk, context.mkInt(0)),
                    context.mkEq(context.mkAdd(pxr, context.mkMul(vxr, tk)), context.mkAdd(context.mkInt(hailStone[0]), context.mkMul(context.mkInt(hailStone[3]), tk)))
                );

                BoolExpr c2 = context.mkAnd(
                    context.mkEq(context.mkAdd(pyr, context.mkMul(vyr, tk)), context.mkAdd(context.mkInt(hailStone[1]), context.mkMul(context.mkInt(hailStone[4]), tk))),
                    context.mkEq(context.mkAdd(pzr, context.mkMul(vzr, tk)), context.mkAdd(context.mkInt(hailStone[2]), context.mkMul(context.mkInt(hailStone[5]), tk)))
                );

                BoolExpr c3 = context.mkAnd(c, c2);

                solver.add(c3);


            }

            if (solver.check() != Status.SATISFIABLE) {
                throw new InvalidPropertiesFormatException("Unsatisfiable There is no line that can intersect all the snowballs.");
            }

            Model model = solver.getModel();

            Expr<?> pxr1 = model.evaluate(pxr, false);
            Expr<?> pyr1 = model.evaluate(pyr, false);
            Expr<?> pzr1 = model.evaluate(pzr, false);

            return String.valueOf(Long.parseLong(pxr1.toString()) + Long.parseLong(pyr1.toString()) + Long.parseLong(pzr1.toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";

    }



}