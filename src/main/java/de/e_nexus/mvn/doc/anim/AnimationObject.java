package de.e_nexus.mvn.doc.anim;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import de.e_nexus.mvn.doc.anim.kf.KeyFrame;

public abstract class AnimationObject<T extends KeyFrame> {
	protected final List<T> keyFrames = new ArrayList();
	public static final int BORDER_WIDTH = 5;

	public abstract int getWidth();

	public abstract int getHeight();

	public abstract int getFrames();

	protected abstract void paintRelative(Graphics graphics, int relX, int relY, int time);

	protected T findBefore(int time) {
		T clossest = null;
		for (T me : keyFrames) {
			if (me.getFrame() < time) {
				if (clossest == null) {
					clossest = me;
				} else {
					if (clossest.getFrame() < me.getFrame()) {
						clossest = me;
					}
				}
			}
		}
		return clossest;
	}

	protected T findAfter(int time) {
		T clossest = null;
		for (T me : keyFrames) {
			if (me.getFrame() > time) {
				if (clossest == null) {
					clossest = me;
				} else {
					if (clossest.getFrame() > me.getFrame()) {
						clossest = me;
					}
				}
			}
		}
		return clossest;
	}

}
