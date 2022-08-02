package com.yubo.lox;

import java.util.List;

public abstract class Expr {
	public interface Visitor<R> {
		R visit(Assign assign);
		R visit(Binary binary);
		R visit(Grouping grouping);
		R visit(Literal literal);
		R visit(Unary unary);
		R visit(Variable variable);
		R visit(Logical logical);
		R visit(Set set);
		R visit(ThisKw thiskw);
		R visit(Anonymous anonymous);
		R visit(Call call);
		R visit(Get get);
		R visit(SuperKw superkw);
	}
	
	public abstract <R> R accept(Visitor<R> visitor);
	
	public static class Assign extends Expr {
		public final Token name;
		public final Expr value;
		
		public Assign(Token name, Expr value) {
			this.name = name;
			this.value = value;
		}
		
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}
	
	public static class Binary extends Expr {
		public final Expr left;
		public final Token operator;
		public final Expr right;
		
		public Binary(Expr left, Token operator, Expr right) {
			this.left = left;
			this.operator = operator;
			this.right = right;
		}
		
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}
	
	public static class Grouping extends Expr {
		public final Expr expression;
		
		public Grouping(Expr expression) {
			this.expression = expression;
		}
		
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}
	
	public static class Literal extends Expr {
		public final Object value;
		
		public Literal(Object value) {
			this.value = value;
		}
		
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}
	
	public static class Unary extends Expr {
		public final Token operator;
		public final Expr right;
		
		public Unary(Token operator, Expr right) {
			this.operator = operator;
			this.right = right;
		}
		
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}
	
	public static class Variable extends Expr {
		public final Token name;
		
		public Variable(Token name) {
			this.name = name;
		}
		
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}
	
	public static class Logical extends Expr {
		public final Expr left;
		public final Token operator;
		public final Expr right;
		
		public Logical(Expr left, Token operator, Expr right) {
			this.left = left;
			this.operator = operator;
			this.right = right;
		}
		
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}
	
	public static class Set extends Expr {
		public final Expr object;
		public final Token name;
		public final Expr val;
		
		public Set(Expr object, Token name, Expr val) {
			this.object = object;
			this.name = name;
			this.val = val;
		}
		
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}
	
	public static class ThisKw extends Expr {
		public final Token kw;
		
		public ThisKw(Token kw) {
			this.kw = kw;
		}
		
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}
	
	public static class Anonymous extends Expr {
		public final Token kw;
		public final List<Token> params;
		public final List<Stmt> body;
		
		public Anonymous(Token kw, List<Token> params, List<Stmt> body) {
			this.kw = kw;
			this.params = params;
			this.body = body;
		}
		
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}
	
	public static class Call extends Expr {
		public final Expr callee;
		public final Token paren;
		public final List<Expr> arguments;
		
		public Call(Expr callee, Token paren, List<Expr> arguments) {
			this.callee = callee;
			this.paren = paren;
			this.arguments = arguments;
		}
		
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}
	
	public static class Get extends Expr {
		public final Expr object;
		public final Token name;
		
		public Get(Expr object, Token name) {
			this.object = object;
			this.name = name;
		}
		
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}
	
	public static class SuperKw extends Expr {
		public final Token kw;
		public final Token method;
		
		public SuperKw(Token kw, Token method) {
			this.kw = kw;
			this.method = method;
		}
		
		@Override
		public <R> R accept(Visitor<R> visitor) {
			return visitor.visit(this);
		}
	}
	
}
