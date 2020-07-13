package xyz.symhx.normal.qrcode.common;


import xyz.symhx.normal.qrcode.renderer.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public final class MultiFormatQrEyesRenderer implements QrEyesRenderer {

	@Override
	public void render(BufferedImage image, QrEyesFormat format, QrEyesPosition position, Color slave, Color border,
					   Color point) {

		QrEyesRenderer renderer;

		switch (format) {

		case R_BORDER_R_POINT:
			renderer = new RBRPQrEyesRenderer();
			break;
		case R_BORDER_C_POINT:
			renderer = new RBCPQrEyesRenderer();
			break;
		case C_BORDER_R_POINT:
			renderer = new CBRPQrEyesRenderer();
			break;
		case C_BORDER_C_POINT:
			renderer = new CBCPQrEyesRenderer();
			break;
		case R2_BORDER_R_POINT:
			renderer = new R2BRPQrEyesRenderer();
			break;
		case R2_BORDER_C_POINT:
			renderer = new R2BCPQrEyesRenderer();
			break;
		case DR2_BORDER_R_POINT:
			renderer = new DR2BRPQrEyesRenderer();
			break;
		case DR2_BORDER_C_POINT:
			renderer = new DR2BCPQrEyesRenderer();
			break;
		default:
			throw new IllegalArgumentException("No encoder available for format " + format);
		}

		renderer.render(image, format, position, slave, border, point);
	}

}
