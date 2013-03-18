/*
Copyright (c) 2013 Federico Ricca

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and 
associated documentation files (the "Software"), to deal in the Software without restriction, 
including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
sell copies of the Software, and to permit persons to whom the Software is furnished to do so, 
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR 
OTHER DEALINGS IN THE SOFTWARE.
 */
package org.gxmlp.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GNodeTreeComparator<T>
		extends
		AbstractTreeNavigator<Pair<GNode<T>, GNode<T>>, Pair<GNode<T>, GNode<T>>>
		implements TreeVisitor<Pair<GNode<T>, GNode<T>>> {

	private GNodeLocator<T> gnodeLocator;
	private GNode<T> leftDiff;
	private GNode<T> rightDiff;
	private GNode<T> currentLeft;
	private GNode<T> currentRight;

	public Pair<GNode<T>, GNode<T>> compare(GNode<T> _treeA, GNode<T> _treeB,
			GNodeLocator<T> _locator) {
		Pair<GNode<T>, GNode<T>> _pair = new Pair<GNode<T>, GNode<T>>(_treeA,
				_treeB);
		gnodeLocator = _locator;

		this.visit(_pair, this);

		return new Pair<GNode<T>, GNode<T>>(leftDiff, rightDiff);
	}

	@Override
	protected boolean isLeaf(Pair<GNode<T>, GNode<T>> _base) {
		if (_base.getElementA() == null || _base.getElementB() == null)
			return true;

		if (_base.getElementA().hasChildren()
				&& _base.getElementB().hasChildren())
			return false;

		return true;
	}

	@Override
	protected Pair<GNode<T>, GNode<T>> transformBase(
			Pair<GNode<T>, GNode<T>> _base) {
		return _base;
	}

	@Override
	protected List<Pair<GNode<T>, GNode<T>>> getChildren(
			Pair<GNode<T>, GNode<T>> _base) {
		List<Pair<GNode<T>, GNode<T>>> _list = new ArrayList<Pair<GNode<T>, GNode<T>>>();

		GNode<T> _left = _base.getElementA();
		GNode<T> _right = _base.getElementB();

		for (Iterator<GNode<T>> _iterator = _left.getChildren().iterator(); _iterator
				.hasNext();) {
			GNode<T> _obj = _iterator.next();

			GNode<T> _node = gnodeLocator.locate(_right.getChildren(), _obj);

			if (_node != null)
				_list.add(new Pair<GNode<T>, GNode<T>>(_obj, _node));
			else
				this.addLeftDiff(_obj);
		}

		for (Iterator<GNode<T>> _iterator = _right.getChildren().iterator(); _iterator
				.hasNext();) {
			GNode<T> _obj = _iterator.next();

			GNode<T> _node = gnodeLocator.locate(_left.getChildren(), _obj);

			if (_node == null)
				this.addRightDiff(_obj);
		}

		return _list;
	}

	@Override
	public void visitLeaf(Pair<GNode<T>, GNode<T>> _t) {
		GNode<T> _nodeLeft = _t.getElementA();
		GNode<T> _nodeRight = _t.getElementB();

		if (!_nodeLeft.equals(_nodeRight)) {
			this.addLeftDiff(_nodeLeft);
			this.addRightDiff(_nodeRight);
		}
	}

	@Override
	public void enterBranch(Pair<GNode<T>, GNode<T>> _t) {
	}

	@Override
	public void exitBranch(Pair<GNode<T>, GNode<T>> _t) {
	}

	private void addLeftDiff(GNode<T> _node) {
		GNode<T> _newNode = new GNode<T>(_node.getContents());

		if (_node.getParent() == null) {
			leftDiff = _newNode;
			currentLeft = _newNode;
		} else {
			if (leftDiff == null) {
				this.createLeftDiff(_node);
			}

			currentLeft.addChild(_newNode);
		}
	}

	private void createLeftDiff(GNode<T> _node) {
		GNode<T> _parent = _node.getParent();
		GNode<T> _child = null;

		while (_parent != null) {
			GNode<T> _copy = new GNode<T>(_parent.getContents());

			leftDiff = _copy;

			if (_child != null)
				leftDiff.addChild(_child);
			else
				currentLeft = _copy;

			_child = _copy;
			_parent = _parent.getParent();
		}
	}

	private void addRightDiff(GNode<T> _node) {
		GNode<T> _newNode = new GNode<T>(_node.getContents());

		if (_node.getParent() == null) {
			rightDiff = _newNode;
			currentRight = _newNode;
		} else {
			if (rightDiff == null) {
				this.createRightDiff(_node);
			}

			currentRight.addChild(_newNode);
		}
	}

	private void createRightDiff(GNode<T> _node) {
		GNode<T> _parent = _node.getParent();
		GNode<T> _child = null;

		while (_parent != null) {
			GNode<T> _copy = new GNode<T>(_parent.getContents());

			rightDiff = _copy;

			if (_child != null)
				rightDiff.addChild(_child);
			else
				currentRight = _copy;

			_child = _copy;
			_parent = _parent.getParent();
		}
	}
}
