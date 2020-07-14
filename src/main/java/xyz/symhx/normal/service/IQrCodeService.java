package xyz.symhx.normal.service;

import xyz.symhx.normal.entity.DiyQrAttribute;
import xyz.symhx.normal.entity.QRCodeInfo;

import java.awt.image.BufferedImage;
import java.nio.Buffer;

public interface IQrCodeService {

    BufferedImage getSelfQrCode(String urlPath, DiyQrAttribute diyQrAttribute);

    /**
     * get qr-code
     * @param qrCodeInfo
     * @return
     */
    BufferedImage getQrCode(QRCodeInfo qrCodeInfo);
}
