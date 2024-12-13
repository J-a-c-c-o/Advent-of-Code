package nl.jtepoel.AOC.utils.evaluator.antlr;// Generated from C:/Users/Jacco/Desktop/Projects/ProjectsJava/AOC/g.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link gParser}.
 */
public interface gListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link gParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(gParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link gParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(gParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Or_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterOr_Expr(gParser.Or_ExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Or_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitOr_Expr(gParser.Or_ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Term_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterTerm_Expr(gParser.Term_ExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Term_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitTerm_Expr(gParser.Term_ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Eq_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterEq_Expr(gParser.Eq_ExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Eq_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitEq_Expr(gParser.Eq_ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Sub_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterSub_Expr(gParser.Sub_ExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Sub_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitSub_Expr(gParser.Sub_ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Lte_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLte_Expr(gParser.Lte_ExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Lte_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLte_Expr(gParser.Lte_ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Gte_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGte_Expr(gParser.Gte_ExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Gte_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGte_Expr(gParser.Gte_ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code And_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAnd_Expr(gParser.And_ExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code And_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAnd_Expr(gParser.And_ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Neq_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNeq_Expr(gParser.Neq_ExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Neq_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNeq_Expr(gParser.Neq_ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Lt_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterLt_Expr(gParser.Lt_ExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Lt_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitLt_Expr(gParser.Lt_ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Not_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNot_Expr(gParser.Not_ExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Not_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNot_Expr(gParser.Not_ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Gt_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGt_Expr(gParser.Gt_ExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Gt_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGt_Expr(gParser.Gt_ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Add_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAdd_Expr(gParser.Add_ExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Add_Expr}
	 * labeled alternative in {@link gParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAdd_Expr(gParser.Add_ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Factor_Term}
	 * labeled alternative in {@link gParser#term}.
	 * @param ctx the parse tree
	 */
	void enterFactor_Term(gParser.Factor_TermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Factor_Term}
	 * labeled alternative in {@link gParser#term}.
	 * @param ctx the parse tree
	 */
	void exitFactor_Term(gParser.Factor_TermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Mul_Term}
	 * labeled alternative in {@link gParser#term}.
	 * @param ctx the parse tree
	 */
	void enterMul_Term(gParser.Mul_TermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Mul_Term}
	 * labeled alternative in {@link gParser#term}.
	 * @param ctx the parse tree
	 */
	void exitMul_Term(gParser.Mul_TermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Div_Term}
	 * labeled alternative in {@link gParser#term}.
	 * @param ctx the parse tree
	 */
	void enterDiv_Term(gParser.Div_TermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Div_Term}
	 * labeled alternative in {@link gParser#term}.
	 * @param ctx the parse tree
	 */
	void exitDiv_Term(gParser.Div_TermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Pow_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterPow_Factor(gParser.Pow_FactorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Pow_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitPow_Factor(gParser.Pow_FactorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntID_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterIntID_Factor(gParser.IntID_FactorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntID_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitIntID_Factor(gParser.IntID_FactorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Int_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterInt_Factor(gParser.Int_FactorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Int_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitInt_Factor(gParser.Int_FactorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DoubleID_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterDoubleID_Factor(gParser.DoubleID_FactorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DoubleID_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitDoubleID_Factor(gParser.DoubleID_FactorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Bool_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterBool_Factor(gParser.Bool_FactorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Bool_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitBool_Factor(gParser.Bool_FactorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Double_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterDouble_Factor(gParser.Double_FactorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Double_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitDouble_Factor(gParser.Double_FactorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BoolID_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterBoolID_Factor(gParser.BoolID_FactorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BoolID_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitBoolID_Factor(gParser.BoolID_FactorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Paren_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterParen_Factor(gParser.Paren_FactorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Paren_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitParen_Factor(gParser.Paren_FactorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Mod_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterMod_Factor(gParser.Mod_FactorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Mod_Factor}
	 * labeled alternative in {@link gParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitMod_Factor(gParser.Mod_FactorContext ctx);
}