package de.e_nexus.mvn.doc.anim.geo;

import java.awt.Color;
import java.awt.Graphics;

import de.e_nexus.mvn.doc.anim.AnimationObject;
import de.e_nexus.mvn.doc.anim.geo.ex.IllegalColorException;
import de.e_nexus.mvn.doc.anim.geo.ex.IllegalCoordinatesException;
import de.e_nexus.mvn.doc.anim.kf.BarKeyFrame;

public class BarObject extends AnimationObject<BarKeyFrame> {
	private final int maxX, maxY;
	private final int frames;

	public BarObject(String line) throws IllegalColorException, IllegalCoordinatesException {
		// bar at 15x15, size 10x10, border 0x000000, fill 0x11ff33, after 10 frames
		line = line.substring(3);
		int absoluteFrame = 0;
		int x = 0;
		int y = 0;
		BarKeyFrame last = null;
		for (String keyframe : line.split(" after ")) {

			BarKeyFrame keyFrame;
			if (last != null)
				keyFrame = new BarKeyFrame(last);
			else {
				keyFrame = new BarKeyFrame();
			}
			last = keyFrame;
			for (String kpi : keyframe.trim().split(",")) {
				kpi = kpi.trim();
				if (kpi.startsWith("at ")) {
					keyFrame.setPosition(new Point(kpi.substring(3)));
				} else if (kpi.startsWith("size ")) {
					keyFrame.setSize(new Point(kpi.substring(5)));
				} else if (kpi.startsWith("border ")) {
					keyFrame.setBorderColor(renderColor(Color.BLACK, kpi.substring(7)));
				} else if (kpi.startsWith("color ")) {
					keyFrame.setBorderColor(renderColor(Color.BLACK, kpi.substring(6)));
				} else if (kpi.startsWith("fill ")) {
					keyFrame.setFillColor(renderColor(Color.BLACK, kpi.substring(5)));
				} else if (kpi.startsWith("bg ")) {
					keyFrame.setFillColor(renderColor(Color.BLACK, kpi.substring(3)));
				} else if (kpi.startsWith("back ")) {
					keyFrame.setFillColor(renderColor(Color.BLACK, kpi.substring(5)));
				} else if (kpi.startsWith("background ")) {
					keyFrame.setFillColor(renderColor(Color.BLACK, kpi.substring(11)));
				} else if (kpi.endsWith(" frames")) {
					kpi = kpi.substring(0, kpi.length() - 7);
					absoluteFrame += Integer.parseInt(kpi);
					keyFrame.setFrame(absoluteFrame);
				}
			}
			keyFrames.add(keyFrame);
			if (keyFrame.getX() + keyFrame.getWidth() > x) {
				x = keyFrame.getX() + keyFrame.getWidth();
			}
			if (keyFrame.getY() + keyFrame.getHeight() > y) {
				y = keyFrame.getY() + keyFrame.getHeight();
			}
		}
		maxX = x;
		maxY = y;
		frames = absoluteFrame;
	}

	private Color renderColor(Color c, String cname) throws IllegalColorException {
		try {
			c = new Color(Integer.decode(cname));
		} catch (NumberFormatException e) {
			throw new IllegalColorException("Color " + cname + " could not be parsed!");
		}
		return c;
	}

	@Override
	public int getWidth() {
		return maxX;
	}

	@Override
	public int getHeight() {
		return maxY;
	}

	/**
	 * Draw the data to one frame (or subframe) of the .gif-file.
	 * 
	 * <p>
	 * A line of : <tt> 
	 * <ul>
	 * <li>bar at 20x20, size 15x15, fill 0xff0000 after 1000 frames, at 100x20
	 * </ul></tt> will be animated as:
	 * 
	 * <pre>
	 * <img src="./doc-files/string-animate.gif#
	 * string 'Move to left', at 100x10 after 100 frames, at 10x10
	 * ">
	 * </pre>
	 * 
	 * @throws IllegalCoordinatesException If the coordinates could not be parsed.
	 */
	@Override
	protected void paintRelative(Graphics graphics, int time, int maxTime) {
		for (BarKeyFrame barKeyFrame : keyFrames) {
			if (barKeyFrame.getFrame() == time) {
				paintRelative(graphics, barKeyFrame);
				return;
			}
		}
		BarKeyFrame before = findBefore(time);
		BarKeyFrame after = findAfter(time);

		if (after == null && before == null) {
		} else if (after == null) {
			paintRelative(graphics, before);
		} else if (before == null) {
			paintRelative(graphics, after);
		} else {

			BarKeyFrame f = new BarKeyFrame();

			int size = after.getFrame() - before.getFrame();
			int b = time - before.getFrame();
			float factor = b * 1f / size;
			f.setBorderColor(Subframe.sub(before.getBorderColor(), after.getBorderColor(), factor));
			f.setFillColor(Subframe.sub(before.getFillColor(), after.getFillColor(), factor));
			f.setSize(Subframe.sub(before.getSize(), after.getSize(), factor));
			f.setPosition(Subframe.sub(before.getPosition(), after.getPosition(), factor));
			paintRelative(graphics, f);
		}
	}

	private void paintRelative(Graphics graphics, BarKeyFrame f) {
		if (f.getFillColor() != null) {
			graphics.setColor(f.getFillColor());
		} else {
			graphics.setColor(Color.WHITE);
		}
		graphics.fillRect(f.getX(), f.getY(), f.getWidth(), f.getHeight());
		if (f.getBorderColor() != null) {
			graphics.setColor(f.getBorderColor());
		} else {
			graphics.setColor(Color.lightGray);
		}
		graphics.drawRect(f.getX(), f.getY(), f.getWidth(), f.getHeight());
	}

	public int getFrames() {
		return frames;
	}
}
