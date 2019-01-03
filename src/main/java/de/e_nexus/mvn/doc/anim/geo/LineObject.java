package de.e_nexus.mvn.doc.anim.geo;

import java.awt.Color;
import java.awt.Graphics;

import de.e_nexus.mvn.doc.anim.AnimationObject;
import de.e_nexus.mvn.doc.anim.geo.ex.IllegalColorException;
import de.e_nexus.mvn.doc.anim.geo.ex.IllegalCoordinatesException;
import de.e_nexus.mvn.doc.anim.kf.KeyFrame;

public class LineObject extends AnimationObject<KeyFrame> {

	private Point start;
	private Point end;
	private Color c;

	public LineObject(String line) throws IllegalColorException, IllegalCoordinatesException {
		line = line.substring(5);
		String[] color = line.split(" in ");
		String[] grp = color[0].split(" to ");
		Point start = new Point(grp[0]);
		Point end = new Point(grp[1]);
		Color c = Color.BLACK;
		if (color.length == 2) {
			String cname = color[1].trim();
			c = Color.getColor(cname);
			if (c == null) {
				try {
					c = new Color(Integer.decode(cname));
				} catch (NumberFormatException e) {
					throw new IllegalColorException("Color " + cname + " could not be parsed!");
				}
			}
		}
		this.start = start;
		this.end = end;
		this.c = c;

	}

	@Override
	public int getWidth() {
		return end.x > start.x ? end.x : start.x;
	}

	@Override
	public int getHeight() {
		return end.y > start.y ? end.y : start.y;
	}

	@Override
	protected void paintRelative(Graphics graphics, int relX, int relY, int time, int maxTime) {
		graphics.setColor(c);
		graphics.drawLine(start.x + relX, start.y + relY, end.x + relX, end.y + relY);
	}

	@Override
	public int getFrames() {
		return 0;
	}

}
