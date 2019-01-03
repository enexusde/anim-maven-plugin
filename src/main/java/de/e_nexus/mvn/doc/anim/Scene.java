package de.e_nexus.mvn.doc.anim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import org.w3c.dom.Node;

import de.e_nexus.mvn.doc.anim.ex.AmibuousGifWriterImplementationException;
import de.e_nexus.mvn.doc.anim.geo.BarObject;
import de.e_nexus.mvn.doc.anim.geo.LineObject;
import de.e_nexus.mvn.doc.anim.geo.ex.IllegalColorException;
import de.e_nexus.mvn.doc.anim.geo.ex.IllegalCoordinatesException;

public class Scene extends SizedAnimationObjectList {

	/**
	 * The serial version UID.
	 */
	private static final long serialVersionUID = -579091395120570513L;
	private final File gifFile;

	public Scene(File targetGifFile) {
		this.gifFile = targetGifFile;
	}

	public void consume(String line) throws IllegalCoordinatesException, IllegalColorException {
		line = line.replace('*', ' ').trim();
		if (line.startsWith("line ")) {
			add(new LineObject(line));
		} else if (line.startsWith("bar ")) {
			add(new BarObject(line));

		}
	}

	public void write(File file) throws AmibuousGifWriterImplementationException {
		file = file.toPath().normalize().toFile();
		File folder = file.getParentFile();
		if (!folder.exists() && !folder.mkdirs()) {
			throw new RuntimeException("Folder " + folder + " could not be created!");
		}
		Iterator<ImageWriter> gw = ImageIO.getImageWritersByMIMEType("image/gif");
		ImageWriter writer = gw.next();
		if (gw.hasNext()) {
			throw new AmibuousGifWriterImplementationException(
					"There are at least two image writers for gif-file-format.");
		}
		ImageWriteParam p = writer.getDefaultWriteParam();
		IIOMetadata md = writer.getDefaultImageMetadata(
				ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_ARGB), p);
		IIOMetadataNode gce = new IIOMetadataNode("GraphicControlExtension");
		gce.setAttribute("disposalMethod", "restoreToBackgroundColor");
		gce.setAttribute("userInputFlag", "FALSE");
		gce.setAttribute("transparentColorFlag", "FALSE");
		gce.setAttribute("delayTime", "3");
		gce.setAttribute("transparentColorIndex", "255");
		String nativeMetadataFormatName = md.getNativeMetadataFormatName();
		Node root = md.getAsTree(nativeMetadataFormatName);
		root.appendChild(gce);
		try {
			md.setFromTree(nativeMetadataFormatName, root);
		} catch (IIOInvalidTreeException e1) {
			throw new RuntimeException(e1);
		}
		try {
			ImageOutputStream output = new FileImageOutputStream(file);
			writer.setOutput(output);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			writer.prepareWriteSequence(null);
			if (frames() == 0) {
				BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics graphics = image.getGraphics();
				graphics.setClip(0, 0, getWidth(), getHeight());
				graphics.setColor(new Color(0, 0, 0, 125));
				graphics.fillRect(0, 0, getWidth(), getHeight());
				paintTiming(graphics, 0);
				writer.writeToSequence(new IIOImage(image, null, md), p);
			} else {
				for (int i = 0; i < frames(); i++) {
					BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
					Graphics graphics = image.getGraphics();
					graphics.setClip(0, 0, getWidth(), getHeight());
					graphics.setColor(new Color(0, 0, 0, 125));
					graphics.fillRect(0, 0, getWidth(), getHeight());
					paintTiming(graphics, i);
					writer.writeToSequence(new IIOImage(image, null, md), p);
				}
			}
			writer.endWriteSequence();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		try {
			OutputStream o = (OutputStream) writer.getOutput();
			o.close();
		} catch (Exception e) {
		}
	}

	public File getGifFile() {
		return gifFile;
	}
}
