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

public class TransformerTreeVisitor<T1, T2> implements TreeVisitor<T2> {
	private TreeBuilderVisitor<T1> treeBuilderVisitor;
	private NodeTransformer<T1, T2> nodeTransformer;

	public TransformerTreeVisitor(TreeBuilderVisitor<T1> _builder,
			NodeTransformer<T1, T2> _transformer) {
		treeBuilderVisitor = _builder;
		nodeTransformer = _transformer;
	}

	@Override
	public final void visitLeaf(T2 _t) {
		treeBuilderVisitor.visitLeaf(nodeTransformer.transformLeafNode(_t));
	}

	@Override
	public final void enterBranch(T2 _t) {
		treeBuilderVisitor.enterBranch(nodeTransformer
				.transformEnterBranchNode(_t));
	}

	@Override
	public final void exitBranch(T2 _t) {
		treeBuilderVisitor.enterBranch(nodeTransformer
				.transformExitBranchNode(_t));
	}

	public final TreeBuilderVisitor<T1> getTreeBuilderVisitor() {
		return treeBuilderVisitor;
	}
}
