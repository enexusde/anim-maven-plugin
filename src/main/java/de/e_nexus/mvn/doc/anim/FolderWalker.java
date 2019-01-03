package de.e_nexus.mvn.doc.anim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

import de.e_nexus.mvn.doc.anim.ex.AmibuousGifWriterImplementationException;
import de.e_nexus.mvn.doc.anim.geo.ex.IllegalColorException;
import de.e_nexus.mvn.doc.anim.geo.ex.IllegalCoordinatesException;

public class FolderWalker {

	private static final String ANIMATION_START_ATTRIBUTE = "src=\"";
	private static final String ANIMATION_FILE_EXTENSION = ".gif";
	private static final String DEFAULT_FILE_EXTENSION = ".java";

	public void generateFromSource(Path absolutePath) throws IOException, IllegalCoordinatesException,
			AmibuousGifWriterImplementationException, IllegalColorException {
		File root = absolutePath.toFile();
		if (root.exists()) {
			Set<File> files = new LinkedHashSet<File>();
			recursiveFindJava(root, files);
			for (File file : files) {
				Set<Scene> anims = createFromFile(file);
				for (Scene scene : anims) {
					scene.write(scene.getGifFile());
				}
			}
		}
	}

	private Set<Scene> createFromFile(File file) throws IOException, IllegalCoordinatesException,
			AmibuousGifWriterImplementationException, IllegalColorException {
		Set<Scene> anims = new LinkedHashSet<Scene>();
		FileReader fis = new FileReader(file);
		BufferedReader br = new BufferedReader(fis);
		try {
			String line;
			Scene currentScene = null;
			while ((line = br.readLine()) != null) {
				int srcStart = line.indexOf(ANIMATION_START_ATTRIBUTE);
				int extStart = line.indexOf(ANIMATION_FILE_EXTENSION);
				if (srcStart != -1 && extStart != -1 && currentScene == null && extStart > srcStart) {
					String filename = line.substring(srcStart + ANIMATION_START_ATTRIBUTE.length());
					if (filename.contains("?")) {
						filename = filename.substring(0, filename.indexOf("?"));
					}
					if (filename.contains("#")) {
						filename = filename.substring(0, filename.indexOf("#"));
					}
					File targetFile = new File(file.getParentFile(), filename);
					currentScene = new Scene(targetFile);
				} else if (currentScene != null && line.contains("\"")) {
					anims.add(currentScene);
					currentScene = null;
				} else if (currentScene != null) {
					currentScene.consume(line);
				}
			}
		} finally {
			try {
				br.close();
			} catch (IOException ex) {
				ex.printStackTrace(System.err);
			}
		}
		return anims;
	}

	private void recursiveFindJava(File root, Set<File> files) {
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
