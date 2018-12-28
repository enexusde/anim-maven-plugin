package de.e_nexus.mvn.doc.anim.kf;

import java.awt.Color;

import de.e_nexus.mvn.doc.anim.geo.Point;

public class BarKeyFrame extends PositionalKeyFrame {

	private Point size;
	private Color border;
	private Color fillColor;

	public BarKeyFrame() {
	}

	public BarKeyFrame(BarKeyFrame last) {
		size = last.size;
		border = last.border;
		fillColor = last.fillColor;
	}

	public void setSize(Point point) {
		size = point;
	}

	public int getWidth() {
		return size.x;
	}

	public int getHeight() {
		return size.y;
	}

	public Point getSize() {
		return size;
	}

	public void setBorderColor(Color border) {
		this.border = border;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public Color getBorderColor() {
		return border;
	}

	public Color getFillColor() {
		return fillColor;
	}
}
