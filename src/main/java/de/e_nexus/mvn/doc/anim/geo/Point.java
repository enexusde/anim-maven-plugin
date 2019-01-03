package de.e_nexus.mvn.doc.anim.geo;

import de.e_nexus.mvn.doc.anim.geo.ex.IllegalCoordinatesException;

public class Point {

	public final int x;
	public final int y;

	public Point(String str) throws IllegalCoordinatesException {
		int limiter = str.indexOf("x");
		if (limiter == -1) {
			throw new IllegalCoordinatesException("Coordinate " + str + " does not match the pattern XxY (i.e.: 4x1)");
		}
		String left = str.substring(0, limiter);
		String right = str.substring(limiter + 1);
		x = Integer.parseInt(left);
		y = Integer.parseInt(right);
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Point(" + x + "x" + y + ")";
	}

}
