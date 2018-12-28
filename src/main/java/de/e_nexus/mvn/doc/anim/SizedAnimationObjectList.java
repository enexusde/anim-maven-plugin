package de.e_nexus.mvn.doc.anim;

import java.awt.Graphics;
import java.util.LinkedHashSet;

public class SizedAnimationObjectList extends LinkedHashSet<AnimationObject> {

	private int width, height;

	public int getWidth() {
		calculateWidthAndHeight();
		return width;
	}

	private void calculateWidthAndHeight() {
		int width = 1;
		int height = 1;
		for (AnimationObject e : this) {
			int ew = e.getWidth();
			if (ew > width) {
				width = ew;
			}
			int eh = e.getHeight();
			if (eh > height) {
				height = eh;
			}
		}
		this.width = width + AnimationObject.BORDER_WIDTH * 2;
		this.height = height + AnimationObject.BORDER_WIDTH * 2;
	}

	public int getHeight() {
		calculateWidthAndHeight();
		return height;
	}

	public int frames() {
		int maxFrames = 0;
		for (AnimationObject anim : this) {
			int animFrames = anim.getFrames();
			if (animFrames > maxFrames) {
				maxFrames = animFrames;
			}
		}
		return maxFrames;
	}

	public void paintTiming(Graphics graphics, int time) {
		for (AnimationObject ao : this) {
			ao.paintRelative(graphics, AnimationObject.BORDER_WIDTH, AnimationObject.BORDER_WIDTH, time);
		}
	}
}
