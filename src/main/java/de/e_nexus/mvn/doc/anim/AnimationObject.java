package de.e_nexus.mvn.doc.anim;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import de.e_nexus.mvn.doc.anim.kf.KeyFrame;

public abstract class AnimationObject<T extends KeyFrame> {
	protected final List<T> keyFrames = new ArrayList<T>();
	public static final int BORDER_WIDTH = 5;

	public abstract int getWidth();

	public abstract int getHeight();

	public abstract int getFrames();

	/**
	 * Paint the element to one frame.
	 * 
	 * @param frame   The frame to print on, never <code>null</code> .
	 * @param relX    The relative x-position.
	 * @param relY    The relative y-position.
	 * @param time    The frame number to paint.
	 * @param maxTime The last frame to paint.
	 */
	protected abstract void paintRelative(Graphics frame, int time, int maxTime);

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