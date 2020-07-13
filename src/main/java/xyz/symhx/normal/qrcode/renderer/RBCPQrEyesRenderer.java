package xyz.symhx.normal.qrcode.renderer;

import xyz.symhx.normal.qrcode.common.QrEyesFormat;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RBCPQrEyesRenderer extends RBRPQrEyesRenderer {

	@Override
	public void checkFormat(QrEyesFormat format) {
		if (QrEyesFormat.R_BORDER_C_POINT != format) {
			throw new IllegalArgumentException("Can only render R_BORDER_C_POINT, but got " + format);
		}
	}

	@Override
	public Shape getPointShape(double x, double y, double w, double h, double arcw, double arch) {
		return new Ellipse2D.Double(x, y, w, h);
	}

}
