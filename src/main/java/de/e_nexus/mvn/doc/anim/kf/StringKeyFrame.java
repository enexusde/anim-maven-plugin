package de.e_nexus.mvn.doc.anim.kf;

import java.awt.Color;

public class StringKeyFrame extends PositionalKeyFrame {
	private String text;
	private Color color = Color.black;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setColor(Color c) {
		this.color = c;
	}

	public Color getColor() {
		return color;
	}
}
