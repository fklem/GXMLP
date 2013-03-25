package org.gxmlp.util.file;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.gxmlp.core.AbstractTreeNavigator;

public class FileNavigator extends AbstractTreeNavigator<File, String> {

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

}