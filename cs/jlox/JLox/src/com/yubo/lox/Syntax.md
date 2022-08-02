expreesion -> literal | unary | binary | grouping

literal -> NUMBER | STRING | TRUE | FALSE | NIL
grouping -> "(" expression ")";

unary -> ("-" | "+" | "!" ) expression;

binary -> expression operator expression;

- Notice that is ambiguous due to expression
- does not force it to be right associative
- precedence may need to be defined for each operator

operator ->  "=" | "!=" | "<" | "<=" | ">" | ">="
| "+"  | "-"  | "*" | "/" ;

expr -> expr grouping

comma_expr -> "," expr
comma_expr -> 

opt_expr -> expr comma_expr
opt_expr ->

paren_opt_expr -> "(" opt_expr ")"



grouping ->  | "." IDENTIFIER;

## Remove ambiguity of our grammar

- Precedence and associativity

- `expression -> equality;`
- `equality -> comparison ( ( "!=" | "==" ) comparison )*;`
- `comparison -> term ( ( ">" | ">=" | "<" | "<=" ) term )*;`
- `term -> factor ( ("-" | "+") factor)*;`
- `factor -> factor ( "/" | "*" ) unary | unary;`
  - Use unary to get rid of some token on right. Then it becomes left-associative and no ambiguity can arise.
  - However, it is left-recursive. And recursive descent parser won't do it. So rewrite as
  `factor -> unary ( ("/" | "*") unary)*`
- `unary -> ( "!" | "-" | "+" ) unary | primary`
  - We need to use primary as to make it terminate appropriately
- `primary -> NUMBER | STRING | "true" | "false" | "nil" | "(" expression ")";`

This grammar, being more complex, eliminated ambiguity that would arise previously.

We claim that declaration goes lower precedence. Therefore,

- `program -> declaration* EOF`
- `declaration -> "var" IDENTIFIER ( "=" expression )? ";" | stmt`
- `stmt -> exprStmt | printStmt`

And we will extend the `primary` with `IDENTIFIER`

Some control flow juices

- `ifStmt -> "if" "(" expression ")" stmt ( "else" stmt) ?;`
