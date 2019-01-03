package de.e_nexus.mvn.doc.anim;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

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

	private final FolderWalker walker = new FolderWalker();

	@SuppressWarnings("unchecked")
	public void execute() throws MojoExecutionException, MojoFailureException {
		MavenProject p = (MavenProject) getPluginContext().get("project");
		try {
			for (String absolutePath : (List<String>) p.getCompileSourceRoots()) {
				try {
					walker.generateFromSource(Paths.get(absolutePath));
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
}
