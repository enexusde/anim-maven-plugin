package de.e_nexus.mvn.doc.anim.geo;

import de.e_nexus.mvn.doc.anim.geo.ex.IllegalCoordinatesException;

public class Point {

	public final int x;
	public final int y;

	/**
	 * <img src=".test.gif#
	 * bar at 20x20, size 15x15, fill 0xff0000
	 * bar at 40x20, size 15x15, fill 0xff0000
	 * bar at 60x20, size 15x15, fill 0xff0000
	
	 * bar at 45x40, size 5x5 after 20 frames, at 25x25 after 40 frames, at 25x25 after 20 frames, at 45x0
	 * bar at 45x50, size 5x5 after 20 frames, at 45x25 after 5 frames, at 45x25 after 20 frames, at 45x0
	 * bar at 45x60, size 5x5 after 20 frames, at 65x25 after 85 frames, at 65x25 after 25 frames, at 45x0
	 * bar at 45x70, size 5x5 after 20 frames, at 45x40 after 15 frames, at 45x40 after 20 frames, at 45x25 after 20 frames, at 45x25 after 20 frames, at 45x0
	 * bar at 45x80, size 5x5 after 20 frames, at 45x50 after 15 frames, at 45x50 after 25 frames, at 45x40 after 20 frames, at 25x25 after 5 frames, at 25x25 after 20 frames, at 45x0
	 * bar at 45x90, size 5x5 after 20 frames, at 45x60 after 15 frames, at 45x60 after 20 frames, at 45x50 
	 * bar at 45x100, size 5x5 after 20 frames, at 45x70 after 15 frames, at 45x70 after 20 frames, at 45x60
	 * 
	 * ">
	 * 
	 * @param str
	 * @throws IllegalCoordinatesException
	 */
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
