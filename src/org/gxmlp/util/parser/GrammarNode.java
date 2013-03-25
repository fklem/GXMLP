package org.gxmlp.util.parser;

public class GrammarNode {
	private String varName;
	private String expr = "";
	private String notExpr = "";
	private ExpressionType expressionType;
	private String fullExpr = "";

	public GrammarNode(String _token) {
		this.setExpr(_token);
	}

	public boolean match(String _test) {
		if (this.getExpressionType().equals(ExpressionType.REGULAR_EXPRESSION)) {
			return _test.matches(this.getExpr());
		} else if (!this.getExpr().isEmpty()) {
			return _test.matches(this.getExpr());
		} else if (!this.getNotExpr().isEmpty()) {
			return !_test.matches(this.getNotExpr());
		}

		return true;
	}

	public void setExpr(String _e) {
		setFullExpr(_e);
		expr = _e;
		if (expr.startsWith("$")) {
			this.setExpressionType(ExpressionType.VARIABLE);
			int _iIndex = expr.indexOf('[');
			if (_iIndex != -1) {
				// handle exclusions
				setNotExpr(expr.substring(_iIndex + 1));
				setVarName(expr.substring(0, _iIndex));
				expr = "";
			} else {
				_iIndex = expr.indexOf('=');
				if (_iIndex != -1) {
					// handle inclusions
					setNotExpr("");
					setVarName(expr.substring(0, _iIndex));
					expr = expr.substring(_iIndex + 1);
				} else {
					setVarName(expr);
					expr = "";
					notExpr = "";
				}
			}
		} else {
			this.setExpressionType(ExpressionType.REGULAR_EXPRESSION);
			this.setVarName("");
			this.setNotExpr("");
		}
	}

	@Override
	public String toString() {
		return "GrammarNode [varName=" + varName + ", expr=" + expr
				+ ", notExpr=" + notExpr + ", expressionType=" + expressionType
				+ ", fullExpr=" + fullExpr + "]";
	}

	public String getExpr() {
		return expr;
	}

	public ExpressionType getExpressionType() {
		return expressionType;
	}

	public void setExpressionType(ExpressionType _expressionType) {
		expressionType = _expressionType;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getVarName() {
		return varName;
	}

	public void setNotExpr(String notExpr) {
		this.notExpr = notExpr;
	}

	public String getNotExpr() {
		return notExpr;
	}

	public void setFullExpr(String fullExpr) {
		this.fullExpr = fullExpr;
	}

	public String getFullExpr() {
		return fullExpr;
	}
}
