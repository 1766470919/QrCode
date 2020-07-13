package xyz.symhx.normal.qrcode.common;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * 二维码眼渲染器
 * @author Mr.Liu
 */
public interface QrEyesRenderer {

	/**
	 * 格式渲染
	 * 
	 * @param image
	 * @param format
	 * @param position
	 * @param slave
	 * @param border
	 * @param point
	 */
	void render(BufferedImage image, QrEyesFormat format, QrEyesPosition position, Color slave, Color border,
				Color point);

	/**
	 * 码眼形状
	 * 
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param arcw
	 * @param arch
	 * @return
	 */
	default Shape getPointShape(double x, double y, double w, double h, double arcw, double arch) {
		return new Rectangle2D.Double(x, y, w, h);
	}

	/**
	 * Check the format match target renderer.
	 * 
	 * @param format
	 */
	default void checkFormat(QrEyesFormat format) {
		return;
	}

}
