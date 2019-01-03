package de.e_nexus.ant.doc;

import java.io.File;
import java.io.IOException;

import de.e_nexus.mvn.doc.anim.FolderWalker;
import de.e_nexus.mvn.doc.anim.ex.AmibuousGifWriterImplementationException;
import de.e_nexus.mvn.doc.anim.geo.ex.IllegalColorException;
import de.e_nexus.mvn.doc.anim.geo.ex.IllegalCoordinatesException;

public class AnimationTask {
	
	private final FolderWalker walker = new FolderWalker();

	public void execute() throws IOException, IllegalCoordinatesException, AmibuousGifWriterImplementationException, IllegalColorException {
		walker.generateFromSource(new File(".").getAbsoluteFile().toPath());
	}
}
