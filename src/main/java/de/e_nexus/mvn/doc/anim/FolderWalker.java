package de.e_nexus.mvn.doc.anim;

import java.io.File;
import java.util.Set;

public class FolderWalker {

	private static final String DEFAULT_FILE_EXTENSION = ".java";

	public static void recursiveFindJava(File root, Set<File> files) {
		if (root.isDirectory()) {
			for (File file : root.listFiles()) {
				recursiveFindJava(file, files);
			}
		} else {
			if (root.getName().endsWith(DEFAULT_FILE_EXTENSION)) {
				files.add(root);
			}
		}
	}
}
