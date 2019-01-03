package de.e_nexus.mvn.doc.anim.geo;

import java.awt.Graphics;

import de.e_nexus.mvn.doc.anim.AnimationObject;
import de.e_nexus.mvn.doc.anim.kf.PositionalKeyFrame;

public class ProgressIndicator extends AnimationObject<PositionalKeyFrame> {

	@Override
	public int getWidth() {
		return 0;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public int getFrames() {
		return 0;
	}

	@Override
	protected void paintRelative(Graphics graphics, int relX, int relY, int time, int maxTime) {

	}

}
