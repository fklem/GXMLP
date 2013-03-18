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
import java.util.Set;

import org.gxmlp.util.xml.DOMNavigator;
import org.gxmlp.util.xml.DOMToXMLNodeTransformerTreeVisitor;
import org.gxmlp.util.xml.DocumentParserUtil;
import org.gxmlp.util.xml.XMLNode;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UtilTest {
	String fileNameString;
	String fileNameString2;

	@Before
	public void setUp() throws Exception {

		fileNameString = "C:\\DATOS\\prueba2.psf";
		fileNameString = "/Users/fede/Documents/workspace/GXMLP/resources/artifacts.xml";
		fileNameString2 = "/Users/fede/Documents/workspace/GXMLP/resources/artifacts copia.xml";
	}

	public void testDocumentParser() throws Exception {
		Document _doc = DocumentParserUtil.parseDocument(fileNameString);

		System.out.println(_doc);
	}

	@Test
	public void testGenericXMLComparison() throws Exception {
		DOMNavigator _nav = new DOMNavigator();
		DOMToXMLNodeTransformerTreeVisitor _transformerVisitor1 = new DOMToXMLNodeTransformerTreeVisitor();

		_nav.visit(DocumentParserUtil.parseDocument(fileNameString),
				_transformerVisitor1);

		DOMToXMLNodeTransformerTreeVisitor _transformerVisitor2 = new DOMToXMLNodeTransformerTreeVisitor();

		_nav.visit(DocumentParserUtil.parseDocument(fileNameString2),
				_transformerVisitor2);

		GNodeTreeComparator<XMLNode> _comparator = new GNodeTreeComparator<XMLNode>();

		Pair<GNode<XMLNode>, GNode<XMLNode>> _diff = _comparator.compare(
				_transformerVisitor1.getTreeBuilderVisitor().getRoot(),
				_transformerVisitor2.getTreeBuilderVisitor().getRoot(),
				new GNodeLocator<XMLNode>() {

					@Override
					public GNode<XMLNode> locate(List<GNode<XMLNode>> _list,
							GNode<XMLNode> _node) {
						for (Iterator<GNode<XMLNode>> _iterator = _list
								.iterator(); _iterator.hasNext();) {
							GNode<XMLNode> _gNode = (GNode<XMLNode>) _iterator
									.next();

							XMLNode _e1 = _node.getContents();
							XMLNode _e2 = _gNode.getContents();

							if (_e1.equals(_e2))
								return _gNode;
						}
						return null;
					}
				});

		GNodeTreeNavigator<XMLNode> _v = new GNodeTreeNavigator<XMLNode>();

		TreeVisitor<GNode<XMLNode>> _tv = new TreeVisitor<GNode<XMLNode>>() {

			@Override
			public void visitLeaf(GNode<XMLNode> _t) {
				System.out.println("** " + _t.getContents());
			}

			@Override
			public void exitBranch(GNode<XMLNode> _t) {
				// TODO Auto-generated method stub

			}

			@Override
			public void enterBranch(GNode<XMLNode> _t) {
				System.out.println("* " + _t.getContents());
			}
		};

		System.out.println("LEFT");
		_v.visit(_diff.getElementA(), _tv);

		System.out.println("RIGHT");
		_v.visit(_diff.getElementB(), _tv);
	}

	@Test
	public void testGenericXMLTreeBuilder() throws Exception {
		DOMNavigator _nav = new DOMNavigator();
		DOMToXMLNodeTransformerTreeVisitor _transformerVisitor = new DOMToXMLNodeTransformerTreeVisitor();

		_nav.visit(DocumentParserUtil.parseDocument(fileNameString2),
				_transformerVisitor);

		new GNodeTreeNavigator<XMLNode>().visit(_transformerVisitor
				.getTreeBuilderVisitor().getRoot(),
				new LevelAwareTreeVisitor<GNode<XMLNode>>() {

					private String buildLevelTab(int _iLevel) {
						StringBuffer _sb = new StringBuffer();
						for (int _i = 1; _i < _iLevel; _i++) {
							_sb.append("   ");
						}

						return _sb.toString();
					}

					@Override
					protected void enterLevel(GNode<XMLNode> _t, int _iLevel) {
						System.out.println(this.buildLevelTab(_iLevel) + " "
								+ _t.getContents().getName() + "   "
								+ this.buildAttrString(_t.getContents()) + " "
								+ _t.getContents().getContents());
					}

					private String buildAttrString(XMLNode _contents) {
						StringBuffer _attrs = new StringBuffer();

						Set<String> _keys = _contents.attributeNames();

						for (String _key : _keys) {
							String _value = _contents.getAttribute(_key);
							_attrs.append(_key + "=" + _value + "; ");
						}
						return _attrs.toString();
					}

					@Override
					protected void exitLevel(GNode<XMLNode> _t, int _iLevel) {
						System.out.println(this.buildLevelTab(_iLevel) + " "
								+ _t.getContents().getName());
					}

					@Override
					protected void visitLevel(GNode<XMLNode> _t, int _iLevel) {
						System.out.println(this.buildLevelTab(_iLevel + 1)
								+ " " + _t.getContents().getName() + "   "
								+ this.buildAttrString(_t.getContents()) + " "
								+ _t.getContents().getContents());
					}

				});

	}

	public void testXMLTreeBuilder() throws Exception {
		AbstractTreeNavigator<Node, Document> _visitor = new AbstractTreeNavigator<Node, Document>() {

			@Override
			protected boolean isLeaf(Node _base) {
				return !_base.hasChildNodes();
			}

			@Override
			protected Node transformBase(Document _base) {
				return _base;
			}

			@Override
			protected List<Node> getChildren(Node _base) {
				List<Node> _list = new ArrayList<Node>();
				NodeList _nodeList = _base.getChildNodes();
				for (int _i = 0; _i < _nodeList.getLength(); _i++) {
					_list.add(_nodeList.item(_i));
				}
				return _list;
			}
		};

		_visitor.visit(DocumentParserUtil.parseDocument(fileNameString),
				new TreeVisitor<Node>() {

					@Override
					public void visitLeaf(Node _t) {
						if (_t.getNodeType() != Node.TEXT_NODE) {
							System.out.print(_t.getNodeName() + "  ");
							if ((_t.getTextContent() != null)
									&& (!_t.getTextContent().trim().isEmpty()))
								System.out.print(">" + _t.getTextContent()
										+ "<");

							System.out.println();
						}
					}

					@Override
					public void enterBranch(Node _t) {
						if (_t.getNodeType() != Node.TEXT_NODE) {
							System.out.print(_t.getNodeName() + "  ");
							if ((_t.getTextContent() != null)
									&& (!_t.getTextContent().trim().isEmpty()))
								System.out.print(">" + _t.getTextContent()
										+ "<");

							System.out.println();
						}
					}

					@Override
					public void exitBranch(Node _t) {
						// TODO Apéndice de método generado automáticamente

					}
				});
	}

	public void testDomComparator() throws Exception {
		Document _doc = DocumentParserUtil.parseDocument(fileNameString);
		_doc.normalize();

		NodeList _list = _doc.getChildNodes();

		for (int _i = 0; _i < _list.getLength(); _i++) {
			Node _node = _list.item(_i);
			printNode(_node);
		}
	}

	public void printNode(Node _node) {
		if (_node.getNodeType() == Node.TEXT_NODE)
			return;

		NamedNodeMap _map = _node.getAttributes();

		System.out.println("name: " + _node.getNodeName());
		System.out.println("value: " + _node.getNodeValue());
		if (_map != null)
			for (int _i = 0; _i < _map.getLength(); _i++) {
				Node _node1 = _map.item(_i);
				System.out.println("attr:" + _node1);
			}

		String _contents = _node.getTextContent();
		if ((_contents != null) && !_contents.trim().isEmpty())
			System.out.println("contents: >" + _contents + "<");

		printChildren(_node);
	}

	public void printChildren(Node _node) {
		NodeList _list = _node.getChildNodes();

		for (int _i = 0; _i < _list.getLength(); _i++) {
			Node _node1 = _list.item(_i);
			printNode(_node1);
		}

	}

}
