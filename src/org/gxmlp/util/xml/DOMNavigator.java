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

import java.util.ArrayList;
import java.util.List;

import org.gxmlp.core.AbstractTreeNavigator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMNavigator extends AbstractTreeNavigator<Node, Node> {

	@Override
	protected boolean isLeaf(Node _base) {
		boolean _bResult = false;

		if ((!_base.hasChildNodes())
				|| ((_base.getChildNodes().getLength() == 1) && (_base
						.getChildNodes().item(0).getNodeType() == Node.TEXT_NODE))) {
			_bResult = true;
		}

		return _bResult;
	}

	@Override
	protected Node transformBase(Node _base) {
		return _base;
	}

	@Override
	protected List<Node> getChildren(Node _base) {
		List<Node> _list = new ArrayList<Node>();
		NodeList _nodeList = _base.getChildNodes();
		for (int _i = 0; _i < _nodeList.getLength(); _i++) {
			Node _node = _nodeList.item(_i);
			if (_node.getNodeType() != Node.TEXT_NODE)
				_list.add(_nodeList.item(_i));
		}
		return _list;
	}

}
