package de.e_nexus.mvn.doc.anim.geo;

import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import de.e_nexus.mvn.doc.anim.AnimationObject;
import de.e_nexus.mvn.doc.anim.kf.StringKeyFrame;

public abstract class AbstractFontObject extends AnimationObject<StringKeyFrame> {

	public static final FontMetrics DEFAULT_FONT_METRICS = new BufferedImage(1, 1, BufferedImage.TYPE_BYTE_BINARY)
			.getGraphics().getFontMetrics();

	private Rectangle2D max = new Rectangle2D.Float();

	protected final void calculateBounds() {
		Rectangle2D newMax = new Rectangle2D.Float();
		for (StringKeyFrame stringKeyFrame : this.keyFrames) {
			String text = stringKeyFrame.getText();
			if (text != null) {
				newMax = newMax.createUnion(generateBounds(text));
			}
		}
		this.max.setRect(newMax);
	}

	public Rectangle2D getMax() {
		calculateBounds();
		return max;
	}

	public Rectangle2D generateBounds(String text) {
		return DEFAULT_FONT_METRICS.getFont().getStringBounds(text, DEFAULT_FONT_METRICS.getFontRenderContext());
	}

}
