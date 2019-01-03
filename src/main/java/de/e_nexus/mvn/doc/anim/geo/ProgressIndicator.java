package de.e_nexus.mvn.doc.anim.geo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import de.e_nexus.mvn.doc.anim.AnimationObject;
import de.e_nexus.mvn.doc.anim.kf.PositionalKeyFrame;

public class ProgressIndicator extends AnimationObject<PositionalKeyFrame> {

	@Override
	public int getWidth() {
		return 1;
	}

	@Override
	public int getHeight() {
		return 1;
	}

	@Override
	public int getFrames() {
		return 0;
	}

	@Override
	protected void paintRelative(Graphics graphics, int time, int maxTime) {
		Rectangle clipBounds = graphics.getClipBounds();
		graphics.setColor(Color.blue);
		float w = 1f * clipBounds.width / maxTime;
		w *= time;
		graphics.drawLine(1, 2, Math.round(w), 2);
		graphics.drawLine(1, 1, 1, 3);
		graphics.drawLine(clipBounds.width - 1, 1, clipBounds.width - 1, 3);
	}

}
