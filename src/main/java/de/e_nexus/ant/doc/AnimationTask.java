package de.e_nexus.ant.doc;

import java.io.File;
import java.io.IOException;

import de.e_nexus.mvn.doc.anim.CodeLineWalker;
import de.e_nexus.mvn.doc.anim.ex.AmibuousGifWriterImplementationException;
import de.e_nexus.mvn.doc.anim.geo.ex.IllegalColorException;
import de.e_nexus.mvn.doc.anim.geo.ex.IllegalCoordinatesException;

public class AnimationTask {
	
	private final CodeLineWalker walker = new CodeLineWalker();

	public void execute() throws IOException, IllegalCoordinatesException, AmibuousGifWriterImplementationException, IllegalColorException {
		walker.generateFromSource(new File(".").getAbsoluteFile().toPath());
	}
}
