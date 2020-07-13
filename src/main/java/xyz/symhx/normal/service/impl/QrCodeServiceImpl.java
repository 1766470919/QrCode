package xyz.symhx.normal.service.impl;

import org.springframework.stereotype.Service;
import xyz.symhx.normal.entity.DiyQrAttribute;
import xyz.symhx.normal.service.IQrCodeService;
import xyz.symhx.normal.tools.QrCodeUtil;
import java.awt.image.BufferedImage;

@Service
public class QrCodeServiceImpl implements IQrCodeService {

    @Override
    public BufferedImage getSelfQrCode(String urlPath, DiyQrAttribute diyQrAttribute) {
        String path = "I:\\WorkSpace\\project\\demo\\src\\test\\resources\\mates\\pig.png";
        return QrCodeUtil.diyQrCode(Boolean.TRUE, diyQrAttribute, urlPath, 0, path, 0);
    }
}
