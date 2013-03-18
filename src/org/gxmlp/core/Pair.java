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

public class Pair<T1, T2> {
	private T1 elementA;
	private T2 elementB;

	public Pair(T1 _a, T2 _b) {
		this.setElementA(_a);
		this.setElementB(_b);
	}

	public void setElementA(T1 elementA) {
		this.elementA = elementA;
	}

	public T1 getElementA() {
		return elementA;
	}

	public void setElementB(T2 elementB) {
		this.elementB = elementB;
	}

	public T2 getElementB() {
		return elementB;
	}

	@Override
	public String toString() {
		return "Pair [elementA=" + elementA + ", elementB=" + elementB + "]";
	}

}
