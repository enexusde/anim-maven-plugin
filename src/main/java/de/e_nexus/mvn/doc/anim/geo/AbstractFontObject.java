package de.e_nexus.mvn.doc.anim.geo;

import java.awt.FontMetrics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import de.e_nexus.mvn.doc.anim.AnimationObject;
import de.e_nexus.mvn.doc.anim.kf.StringKeyFrame;

public abstract class AbstractFontObject extends AnimationObject<StringKeyFrame> {

	public static final FontMetrics DEFAULT_FONT_METRICS = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_BINARY)
			.getGraphics().getFontMetrics();

	private Point2D max = new Point2D.Float();

	protected final void calculateBounds() {
		Point2D newMax = new Point2D.Double(0d, 0d);
		String lastText = "";
		for (StringKeyFrame kf : this.keyFrames) {
			if (kf.getText() != null) {
				lastText = kf.getText();
			}
			Point2D textBounds;
			if (lastText != null) {
				textBounds = generateBounds(lastText);
			} else {
				textBounds = new Point2D.Double(0d, 0d);
			}
			int newX = (int) (textBounds.getX() + kf.getX());
			if (newX > newMax.getX()) {
				newMax = new Point2D.Double(newX, newMax.getY());
			}
			int newY = (int) (textBounds.getY() + kf.getY());
			if (newY > newMax.getY()) {
				newMax = new Point2D.Double(newMax.getX(), newY);
			}
		}
		System.out.println("x:" + newMax.getX() + " \t y:" + newMax.getY());
		this.max = newMax;
	}

	public Point2D getMax() {
		calculateBounds();
		return max;
	}

	public Point2D generateBounds(String text) {
		Rectangle2D m = DEFAULT_FONT_METRICS.getFont().getStringBounds(text,
				DEFAULT_FONT_METRICS.getFontRenderContext());
		return new Point2D.Double(m.getWidth(), m.getHeight());
	}

}
