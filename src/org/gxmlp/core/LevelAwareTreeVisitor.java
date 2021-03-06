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

public abstract class LevelAwareTreeVisitor<T> implements TreeVisitor<T> {
	private int iLevel = 0;

	@Override
	public final boolean enterBranch(T _t) {
		iLevel++;
		return enterLevel(_t, iLevel);
	}

	@Override
	public final void exitBranch(T _t) {
		exitLevel(_t, iLevel);
		iLevel--;
	}

	@Override
	public final void visitLeaf(T _t) {
		visitLevel(_t, iLevel);
	}

	protected abstract boolean enterLevel(T _t, int _iLevel);

	protected abstract void visitLevel(T _t, int _iLevel);

	protected abstract void exitLevel(T _t, int _iLevel);
}
