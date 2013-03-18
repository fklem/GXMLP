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

public class TreeBuilderVisitor<T> implements TreeVisitor<T> {
	private GNode<T> currentNode;
	private GNode<T> rootNode;

	public TreeBuilderVisitor() {
		rootNode = null;
		currentNode = null;
	}

	public void visitLeaf(T _t) {
		if (_t != null) {
			GNode<T> _node = new GNode<T>(_t);

			if (rootNode == null)
				rootNode = _node;
			else
				currentNode.addChild(_node);
		}
	}

	public void enterBranch(T _t) {
		if (_t != null) {
			GNode<T> _node = new GNode<T>(_t);

			if (rootNode == null)
				rootNode = _node;
			else
				currentNode.addChild(_node);

			currentNode = _node;
		}
	}

	public void exitBranch(T _t) {
		if (_t != null)
			currentNode = currentNode.getParent();
	}

	public final GNode<T> getRoot() {
		return rootNode;
	}
}
