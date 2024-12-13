package nl.jtepoel.AOC.utils.evaluator.antlr;

import com.microsoft.z3.Context;
import com.microsoft.z3.Expr;
import nl.jtepoel.AOC.utils.Pair;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Listener extends gBaseListener {

    private final Context context;
    private final ParseTreeProperty<Expr> exprs;
    private final List<Expr> vars;
    private Expr expr;

    public Listener(Context context) {
        this.context = context;
        this.exprs = new ParseTreeProperty<>();
        this.vars = new ArrayList<>();
    }

    public static Pair<Expr, List<Expr>> getExpr(String input, Context context) {
        gLexer lexer = new gLexer(CharStreams.fromString(input));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        gParser parser = new gParser(tokens);
        ParseTree tree = parser.start();
        ParseTreeWalker walker = new ParseTreeWalker();
        Listener listener = new Listener(context);
        walker.walk(listener, tree);

        return Pair.of(listener.expr, listener.vars);
    }


    @Override
    public void exitStart(gParser.StartContext ctx) {
        expr = exprs.get(ctx.expr());
    }

    @Override
    public void exitEq_Expr(gParser.Eq_ExprContext ctx) {
        exprs.put(ctx, context.mkEq(exprs.get(ctx.expr(0)), exprs.get(ctx.expr(1))));
    }

    @Override
    public void exitNeq_Expr(gParser.Neq_ExprContext ctx) {
        exprs.put(ctx, context.mkNot(context.mkEq(exprs.get(ctx.expr(0)), exprs.get(ctx.expr(1)))));
    }

    @Override
    public void exitGt_Expr(gParser.Gt_ExprContext ctx) {
        exprs.put(ctx, context.mkGt(exprs.get(ctx.expr(0)), exprs.get(ctx.expr(1))));
    }

    @Override
    public void exitGte_Expr(gParser.Gte_ExprContext ctx) {
        exprs.put(ctx, context.mkGe(exprs.get(ctx.expr(0)), exprs.get(ctx.expr(1))));
    }

    @Override
    public void exitLt_Expr(gParser.Lt_ExprContext ctx) {
        exprs.put(ctx, context.mkLt(exprs.get(ctx.expr(0)), exprs.get(ctx.expr(1))));
    }

    @Override
    public void exitLte_Expr(gParser.Lte_ExprContext ctx) {
        exprs.put(ctx, context.mkLe(exprs.get(ctx.expr(0)), exprs.get(ctx.expr(1))));
    }

    @Override
    public void exitNot_Expr(gParser.Not_ExprContext ctx) {
        exprs.put(ctx, context.mkNot(exprs.get(ctx.expr())));
    }

    @Override
    public void exitAnd_Expr(gParser.And_ExprContext ctx) {
        exprs.put(ctx, context.mkAnd(exprs.get(ctx.expr(0)), exprs.get(ctx.expr(1))));
    }

    @Override
    public void exitOr_Expr(gParser.Or_ExprContext ctx) {
        exprs.put(ctx, context.mkOr(exprs.get(ctx.expr(0)), exprs.get(ctx.expr(1))));
    }

    @Override
    public void exitTerm_Expr(gParser.Term_ExprContext ctx) {
        exprs.put(ctx, exprs.get(ctx.term()));

    }

    @Override
    public void exitSub_Expr(gParser.Sub_ExprContext ctx) {
        exprs.put(ctx, context.mkSub(exprs.get(ctx.expr()), exprs.get(ctx.term())));
    }


    @Override
    public void exitAdd_Expr(gParser.Add_ExprContext ctx) {
        exprs.put(ctx, context.mkAdd(exprs.get(ctx.expr()), exprs.get(ctx.term())));
    }

    @Override
    public void exitFactor_Term(gParser.Factor_TermContext ctx) {
        exprs.put(ctx, exprs.get(ctx.factor()));
    }

    @Override
    public void exitMul_Term(gParser.Mul_TermContext ctx) {
        exprs.put(ctx, context.mkMul(exprs.get(ctx.term()), exprs.get(ctx.factor())));
    }
    @Override
    public void exitDiv_Term(gParser.Div_TermContext ctx) {
        exprs.put(ctx, context.mkDiv(exprs.get(ctx.term()), exprs.get(ctx.factor())));
    }

    @Override
    public void exitParen_Factor(gParser.Paren_FactorContext ctx) {
        exprs.put(ctx, exprs.get(ctx.expr()));
    }


    @Override
    public void exitInt_Factor(gParser.Int_FactorContext ctx) {
        exprs.put(ctx, context.mkInt(Long.parseLong(ctx.getText())));
    }

    @Override
    public void exitDouble_Factor(gParser.Double_FactorContext ctx) {
        exprs.put(ctx, context.mkReal(ctx.getText()));
    }

    @Override
    public void exitBool_Factor(gParser.Bool_FactorContext ctx) {
        exprs.put(ctx, context.mkBool(ctx.BOOL().getText().equals("true")));
    }


    @Override
    public void exitIntID_Factor(gParser.IntID_FactorContext ctx) {
        Expr var = context.mkIntConst(ctx.ID().getText());
        vars.add(var);
        exprs.put(ctx, var);
    }

    @Override
    public void exitDoubleID_Factor(gParser.DoubleID_FactorContext ctx) {
        Expr var = context.mkRealConst(ctx.ID().getText());
        vars.add(var);
        exprs.put(ctx, var);
    }

    @Override
    public void exitBoolID_Factor(gParser.BoolID_FactorContext ctx) {
        Expr var = context.mkBoolConst(ctx.ID().getText());
        vars.add(var);
        exprs.put(ctx, var);
    }

    @Override
    public void exitPow_Factor(gParser.Pow_FactorContext ctx) {
        exprs.put(ctx, context.mkPower(exprs.get(ctx.factor(0)), exprs.get(ctx.factor(1))));
    }

    @Override
    public void exitMod_Factor(gParser.Mod_FactorContext ctx) {
        exprs.put(ctx, context.mkMod(exprs.get(ctx.factor(0)), exprs.get(ctx.factor(1))));
    }
}
