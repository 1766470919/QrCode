package xyz.symhx.normal.qrcode.common;

import xyz.symhx.normal.qrcode.QrCodeConfig;

import java.awt.image.BufferedImage;

/**
 * 二维码生成器
 */
public interface QrCodeGenerator extends Generator {

    QrCodeConfig getQrCodeConfig();

    QrCodeGenerator generate(String content, String logoPath);

    QrCodeGenerator setLogo(String path, boolean remote);

    BufferedImage getImage(boolean clear);

    default QrCodeGenerator setLogo(String path) {
        return setLogo(path, false);
    }

    default QrCodeGenerator setRemoteLogo(String path) {
        return setLogo(path, true);
    }

    @Override
    default BufferedImage getImage() {
        return getImage(true);
    }

}
