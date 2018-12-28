package de.e_nexus.mvn.doc.anim.kf;

import de.e_nexus.mvn.doc.anim.geo.Point;

public class PositionalKeyFrame extends KeyFrame {
	private Point point;

	public void setPosition(Point point) {
		this.point = point;
	}

	public int getX() {
		return point.x;
	}

	public int getY() {
		return point.y;
	}

	public Point getPosition() {
		return point;
	}

}
