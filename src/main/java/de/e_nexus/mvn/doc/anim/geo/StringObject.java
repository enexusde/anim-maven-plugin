package de.e_nexus.mvn.doc.anim.geo;

import java.awt.Color;
import java.awt.Graphics;

import de.e_nexus.mvn.doc.anim.geo.ex.IllegalCoordinatesException;
import de.e_nexus.mvn.doc.anim.kf.StringKeyFrame;

public class StringObject extends AbstractFontObject {

	public static final String STRING_ANIMATION_PREFIX = "string ";

	public StringObject(String line) throws IllegalCoordinatesException {
		line = line.substring(STRING_ANIMATION_PREFIX.length());
		String lastText = "";
		int lastFrame = 0;
		for (String string : line.split(" after ")) {
			string = string.trim();
			StringKeyFrame kf = new StringKeyFrame();
			kf.setText(lastText);
			for (String f : string.split(",")) {
				f = f.trim();
				int p = f.indexOf("frames");
				if (f.startsWith("at ")) {
					kf.setPosition(new Point(f.substring(3)));
				} else if (f.startsWith("color ")) {
					kf.setColor(Color.decode(f.substring(6).trim()));
				} else if (f.startsWith("'") && f.length() > 1) {
					lastText = f.substring(1, f.length() - 1);
					kf.setText(lastText);
				} else if (p > -1) {
					int frame = Integer.parseInt(f.substring(0, p).trim());
					lastFrame += frame;
					kf.setFrame(lastFrame);
				}
			}
			keyFrames.add(kf);
		}
	}

	@Override
	public int getWidth() {
		return (int) getMax().getX();
	}

	@Override
	public int getHeight() {
		return (int) getMax().getY();

	}

	@Override
	public int getFrames() {
		int max = 0;
		for (StringKeyFrame stringKeyFrame : keyFrames) {
			if (stringKeyFrame.getFrame() > max) {
				max = stringKeyFrame.getFrame();
			}
		}
		return max;
	}

	@Override
	protected void paintRelative(Graphics frame, int time, int maxTime) {
		StringKeyFrame self = null;
		for (StringKeyFrame kf : keyFrames) {
			if (kf.getFrame() == time) {
				self = kf;
				break;
			}
		}
		StringKeyFrame before = findBefore(time);
		StringKeyFrame after = findAfter(time);
		int x, y;
		String text;
		Color c;
		if (self != null) {
			x = self.getX();
			y = self.getY();
			text = self.getText();
			c = self.getColor();
		} else if (after != null && before != null) {
			float factor = (1f * time - before.getFrame()) / (after.getFrame() - before.getFrame());
			x = Subframe.sub(before.getX(), after.getX(), factor);
			y = Subframe.sub(before.getY(), after.getY(), factor);
			text = before.getText();
			c = Subframe.sub(before.getColor(), after.getColor(), factor);
		} else if (after == null && before != null) {
			x = before.getX();
			y = before.getY();
			text = before.getText();
			c = before.getColor();
		} else if (after != null && before == null) {
			x = after.getX();
			y = after.getY();
			text = after.getText();
			c = after.getColor();
		} else {
			x = 0;
			y = 0;
			text = "";
			c = Color.BLACK;
		}
		if (text.length() > 0) {
			frame.setColor(c);
			frame.drawString(text, x, y);
		}
	}
}
