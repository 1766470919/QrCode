package xyz.symhx.normal.qrcode.common;

import java.awt.image.BufferedImage;

public class QrCodeThread extends ThreadLocal<QrCode> {

    @Override
    protected QrCode initialValue() {
        return new QrCode();
    }

    public void setImage(BufferedImage image) {
        get().setImage(image);
    }
    public void setLogo(String path,Boolean remote) {
        get().setLogo(new QrCode.Logo(path, remote));
    }

    public BufferedImage getImage() {
        return get().getImage();
    }

    public QrCode.Logo getLogo() {
        return get().getLogo();
    }
}
