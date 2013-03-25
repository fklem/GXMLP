package org.gxmlp.util.parser;

import java.io.File;

import org.gxmlp.core.GNode;
import org.gxmlp.core.TreeVisitor;
import org.junit.Test;

public abstract class AbstractParser<T> implements TreeVisitor<T> {
	private GNode<GrammarNode> _curNode;

	public abstract String stringRepresentation(T _t);

	@Override
	public void visitLeaf(T _t) {
		String _elementName = this.stringRepresentation(_t);

		GNode<GrammarNode> _matchNode = this.matchNode(_curNode, _elementName);

		if (_matchNode != null) {
			System.out.println("RULE MATCH: " + this.stringRepresentation(_t));
		}
	}

	@Override
	public void exitBranch(T _t) {
		_curNode = _curNode.getParent();
	}

	@Override
	public boolean enterBranch(T _t) {
		String _elementName = this.stringRepresentation(_t);

		GNode<GrammarNode> _matchNode = this.matchNode(_curNode, _elementName);
		if (_matchNode != null) {
			_curNode = _matchNode;
			return true;
		}

		return false;
	}

	private GNode<GrammarNode> matchNode(GNode<GrammarNode> _node,
			String _testString) {

		for (GNode<GrammarNode> _gNode : _node.getChildren()) {
			if (_gNode.getContents().match(_testString)) {
				return _gNode;
			}
		}
		return null;
	}

	@Test
	public void testParser() {
		AbstractParser<File> _ap = new AbstractParser<File>() {

			@Override
			public String stringRepresentation(File _t) {
				return _t.getName();
			}

		};
	}

	@Test
	public void testGrammarNode() {
		GrammarNode _gn = null;
		_gn = new GrammarNode("$app=CVS|test");
		System.out.println(_gn.match("test"));

		_gn = new GrammarNode("$app");
		System.out.println(_gn.match("test"));

	}
}