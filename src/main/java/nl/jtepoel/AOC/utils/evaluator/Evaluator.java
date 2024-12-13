package nl.jtepoel.AOC.utils.evaluator;

import com.microsoft.z3.*;
import nl.jtepoel.AOC.utils.Pair;
import nl.jtepoel.AOC.utils.evaluator.antlr.Listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Evaluator {

    private final Context context;
    public Evaluator() {
        this.context = new Context();
    }

    public Pair<Expr, List<Expr>> parse(String input) {
        return Listener.getExpr(input, context);
    }

    public HashMap<String, Expr> evaluate(String input, String minimize, String maximize) {
        Optimize optimizer = context.mkOptimize();


        Set<Expr> vars = new HashSet<>();

        Pair<Expr, List<Expr>> expr = parse(input);
        Expr eval = expr.getFirst();
        List<Expr> temp = expr.getSecond();
        vars.addAll(temp);
        optimizer.Add(eval);

        if (minimize != null) {
            Pair<Expr, List<Expr>> exprMin = parse(minimize);
            optimizer.MkMinimize(exprMin.getFirst());
        }

        if (maximize != null) {
            Pair<Expr, List<Expr>> exprMax = parse(maximize);
            optimizer.MkMaximize(exprMax.getFirst());
        }

        if (optimizer.Check() == Status.UNSATISFIABLE) {
            throw new IllegalArgumentException("Unsatisfiable");
        }

        Model model = optimizer.getModel();


        HashMap<String, Expr> res = new HashMap<>();
        for (Expr var : vars) {
            res.put(var.toString(), model.eval(var, true));
        }

        return res;
    }

    public static void main(String[] args) {
        Evaluator evaluator = new Evaluator();
        HashMap<String, Expr> res = evaluator.evaluate("(2*4)%5^2+200 == x:DOUBLE, z:BOOL", null, null);
        System.out.println(res);
    }
}
