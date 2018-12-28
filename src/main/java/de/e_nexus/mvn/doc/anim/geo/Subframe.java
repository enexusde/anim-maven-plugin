package de.e_nexus.mvn.doc.anim.geo;

import java.awt.Color;

public class Subframe {

	public static Color sub(Color from, Color to, float factor) {
		if (from == null && to == null) {
			return null;
		} else if (from == null) {
			return to;
		} else if (to == null) {
			return from;
		} else {
			int r = sub(from.getRed(), to.getRed(), factor);
			int g = sub(from.getGreen(), to.getGreen(), factor);
			int b = sub(from.getBlue(), to.getBlue(), factor);
			int a = sub(from.getAlpha(), to.getAlpha(), factor);
			return new Color(r, g, b, a);
		}
	}

	public static Point sub(Point from, Point to, float factor) {
		return new Point(sub(from.x, to.x, factor), sub(from.y, to.y, factor));
	}

	public static int sub(int from, int to, float factor) {
		return from + Math.round((to - from) * factor);
	}

}
