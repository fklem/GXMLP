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
package org.gxmlp.util.xml;

import org.gxmlp.core.NodeTransformer;
import org.gxmlp.core.TransformerTreeVisitor;
import org.gxmlp.core.TreeBuilderVisitor;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;


public class DOMToXMLNodeTransformerTreeVisitor extends
		TransformerTreeVisitor<XMLNode, Node> {

	public DOMToXMLNodeTransformerTreeVisitor() {

		super(new TreeBuilderVisitor<XMLNode>(), new NodeTransformer<XMLNode, Node>() {

			private XMLNode buildNode(Node _t) {
				XMLNode _xmlNode = null;

				if (_t.getNodeType() != Node.TEXT_NODE) {
					_xmlNode = new XMLNode();
					_xmlNode.setName(_t.getNodeName());
					_xmlNode.setContents(_t.getTextContent());

					NamedNodeMap _namedNodeMap = _t.getAttributes();

					if (_namedNodeMap != null)
						for (int _i = 0; _i < _namedNodeMap.getLength(); _i++) {
							Node _attrNode = _namedNodeMap.item(_i);
							_xmlNode.addAttribute(_attrNode.getNodeName(),
									_attrNode.getNodeValue());
						}
				}

				return _xmlNode;
			}

			@Override
			public XMLNode transformLeafNode(Node _t) {
				return this.buildNode(_t);
			}

			@Override
			public XMLNode transformEnterBranchNode(Node _t) {
				return this.buildNode(_t);
			}

			@Override
			public XMLNode transformExitBranchNode(Node _t) {
				return null;
			}

		});
	}

}
