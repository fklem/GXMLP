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

import java.io.File;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class FinderTest extends TestCase {

	public void testDecoupledFinder() throws Exception {
		AbstractTreeNavigator<File, String> _finder = new AbstractTreeNavigator<File, String>() {

			protected boolean isLeaf(File _base) {
				return !_base.isDirectory();
			}

			protected File transformBase(String _base) {
				return new File(_base);
			}

			protected List<File> getChildren(File _base) {
				return Arrays.asList(_base.listFiles());
			}
		};

		_finder.visit("C:\\DATOS\\CVSDownloadDir", new TreeVisitor<File>() {

			public void visitLeaf(File _t) {
				if (_t.getName().contains("xmlvb"))
					System.out.println(_t.getAbsolutePath());
			}

			public void enterBranch(File _t) {
				System.out.println("enter " + _t.getAbsolutePath());
			}

			public void exitBranch(File _t) {
				System.out.println("exit " + _t.getAbsolutePath());
			}
		});
	}

	public void testFinder() throws Exception {
		AbstractTreeNavigator<File, String> _finder = new AbstractTreeNavigator<File, String>() {

			@Override
			protected boolean isLeaf(File _base) {
				return !_base.isDirectory();
			}

			@Override
			protected File transformBase(String _base) {
				return new File(_base);
			}

			@Override
			protected List<File> getChildren(File _base) {
				return Arrays.asList(_base.listFiles());
			}

		};

		TreeBuilderVisitor<File> _visitor = new TreeBuilderVisitor<File>();

		_finder.visit(
				"/Users/fede/Documents/workspace/GXMLP/resources/test1.xml",
				_visitor);

		System.out.println(_visitor.getRoot());
		GNodeTreeNavigator<File> _dumper = new GNodeTreeNavigator<File>();

		_dumper.visit(_visitor.getRoot(), new TreeVisitor<GNode<File>>() {

			@Override
			public void visitLeaf(GNode<File> _t) {
				// TODO Auto-generated method stub

			}

			@Override
			public void exitBranch(GNode<File> _t) {
				// TODO Auto-generated method stub

			}

			@Override
			public void enterBranch(GNode<File> _t) {
				// TODO Auto-generated method stub

			}
		});
	}

}
