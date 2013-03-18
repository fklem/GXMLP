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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class XMLNode {
	private String name;
	private String contents = "";
	private Map<String, String> attributes = null;

	@Override
	public String toString() {
		return "XMLNode [name=" + name + ", contents=" + contents
				+ ", attributes=" + attributes + "]";
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getContents() {
		return contents;
	}

	private final void checkAttributes() {
		if (attributes == null)
			attributes = new HashMap<String, String>();
	}

	public void addAttribute(String _key, String _value) {
		checkAttributes();
		attributes.put(_key, _value);
	}

	public String getAttribute(String _key) {
		checkAttributes();
		return attributes.get(_key);
	}

	public Set<String> attributeNames() {
		checkAttributes();
		return attributes.keySet();
	}

	@Override
	public boolean equals(Object _obj) {
		XMLNode _node = (XMLNode) _obj;
		boolean _bResult = false;

		if (this.getName().equals(_node.getName())
				&& this.nullCompare(this.getContents(), _node.getContents())) {
			Set<String> _attrs = this.attributeNames();

			_bResult = true;

			for (Iterator<String> _iterator = _attrs.iterator(); _iterator
					.hasNext();) {
				String _key = (String) _iterator.next();

				if ((!_node.attributeNames().contains(_key))
						|| (!this.getAttribute(_key).equals(
								_node.getAttribute(_key)))) {
					_bResult = false;
					break;
				}
			}
		}
		return _bResult;
	}

	private final boolean nullCompare(String _a, String _b) {
		// if both references are null, return true
		if (_a == _b)
			return true;

		// _a or _b could be null (not both, otherwise they're equal)
		if (_a == null || _b == null)
			return false;

		return _a.trim().equals(_b.trim());
	}
}
