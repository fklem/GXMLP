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

import java.util.List;

public abstract class AbstractTreeNavigator<T1, T2> {
	public void visit(T2 _base, TreeVisitor<T1> _visitor) {
		if (_base != null)
			this.processBranch(this.transformBase(_base), _visitor);
	}

	private void processBranch(T1 _base, TreeVisitor<T1> _visitor) {
		if (this.isLeaf(_base))
			_visitor.visitLeaf(_base);
		else {
			_visitor.enterBranch(_base);
			List<T1> _list = this.getChildren(_base);

			for (T1 t1 : _list) {
				this.processBranch(t1, _visitor);
			}
			_visitor.exitBranch(_base);
		}
	}

	protected abstract boolean isLeaf(T1 _base);

	protected abstract T1 transformBase(T2 _base);

	protected abstract List<T1> getChildren(T1 _base);

}
