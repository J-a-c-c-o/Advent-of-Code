grammar g;


start: expr;

expr: expr '-' term         # Sub_Expr
    | expr '+' term         # Add_Expr
    | expr '==' expr        # Eq_Expr
    | expr '!=' expr        # Neq_Expr
    | expr '<=' expr        # Lte_Expr
    | expr '<' expr         # Lt_Expr
    | expr '>=' expr        # Gte_Expr
    | expr '>' expr         # Gt_Expr
    | expr AND expr        # And_Expr
    | expr '||' expr        # Or_Expr
    | '!' expr              # Not_Expr
    | term                  # Term_Expr
    ;

term: term '*' factor       # Mul_Term
    | term '/' factor       # Div_Term
    | factor                # Factor_Term
    ;

factor: '(' expr ')'        # Paren_Factor
    | <assoc=right> factor '^' factor     # Pow_Factor
    | <assoc=right> factor '%' factor     # Mod_Factor
    | INT                   # Int_Factor
    | '-' INT               # Int_Factor
    | BOOL                  # Bool_Factor
    | ID ':INT'             # IntID_Factor
    | ID ':DOUBLE'          # DoubleID_Factor
    | ID ':BOOL'            # BoolID_Factor
    | DOUBLE                # Double_Factor
    | '-' DOUBLE            # Double_Factor
    ;


BOOL: TRUE | FALSE;
TRUE: 'true';
FALSE: 'false';
AND: '&&' | ',';

ID: CHAR (CHAR | [0-9])*;

CHAR: [a-zA-Z];
DOUBLE: [0-9]+'.'[0-9]+ ;
INT: [0-9]+;
WS: [ \t\r\n]+ -> skip;
