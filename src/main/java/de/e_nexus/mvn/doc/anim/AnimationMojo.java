package de.e_nexus.mvn.doc.anim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

import de.e_nexus.mvn.doc.anim.ex.AmibuousGifWriterImplementationException;
import de.e_nexus.mvn.doc.anim.geo.ex.IllegalColorException;
import de.e_nexus.mvn.doc.anim.geo.ex.IllegalCoordinatesException;

@Mojo(name = "generate")
public class AnimationMojo extends AbstractMojo {

	public void execute() throws MojoExecutionException, MojoFailureException {
		MavenProject p = (MavenProject) getPluginContext().get("project");
		File basedir = p.getBasedir();
		try {
			for (String absolutePath : (List<String>) p.getCompileSourceRoots()) {
				try {
					generateFromSource(absolutePath);
				} catch (IOException e) {
					getLog().error(e);
				} catch (IllegalCoordinatesException e) {
					getLog().warn(e);
				} catch (IllegalColorException e) {
					getLog().warn(e);
				}
			}
		} catch (AmibuousGifWriterImplementationException e) {
			getLog().error(e);
		}
	}

	private void generateFromSource(String absolutePath)
			throws IOException, IllegalCoordinatesException, AmibuousGifWriterImplementationException, IllegalColorException {
		File root = new File(absolutePath);
		if (root.exists()) {
			Set<File> files = new LinkedHashSet();
			recursiveFindJava(root, files);
			for (File file : files) {
				Set<Scene> anims = createFromFile(file);

			}
		}
	}

	private Set<Scene> createFromFile(File file)
			throws IOException, IllegalCoordinatesException, AmibuousGifWriterImplementationException, IllegalColorException {
		Set<Scene> anims = new LinkedHashSet();
		FileReader fis = new FileReader(file);
		BufferedReader br = new BufferedReader(fis);
		try {
			String line;
			Scene currentScene = null;
			int lineNo = 0;
			while ((line = br.readLine()) != null) {
				lineNo++;
				if (line.contains("<pre>")) {
					if (currentScene != null) {
						throw new IllegalStateException(
								"Gif already open (" + file.getAbsolutePath() + ":" + lineNo + ")");
					} else {
						currentScene = new Scene();
					}
				} else if (line.contains("</pre>")) {
					if (currentScene == null) {
						throw new IllegalStateException(
								"Gif already closed (" + file.getAbsolutePath() + ":" + lineNo + ")");
					} else {
						anims.add(currentScene);
						currentScene = null;
					}
				} else if (currentScene != null) {
					currentScene.consume(line);
				}
			}

		} finally {
			br.close();
		}
		for (Scene scene : anims) {
			scene.write(new File("animation.gif"));
		}
		return null;
	}

	private void recursiveFindJava(File root, Set<File> files) {
		if (root.isDirectory()) {
			for (File file : root.listFiles()) {
				recursiveFindJava(file, files);
			}
		} else {
			if (root.getName().endsWith(".java")) {
				files.add(root);
			}
		}
	}

}
