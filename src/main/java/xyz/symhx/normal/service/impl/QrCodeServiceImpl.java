package xyz.symhx.normal.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.symhx.normal.entity.DiyQrAttribute;
import xyz.symhx.normal.entity.QRCodeInfo;
import xyz.symhx.normal.qrcode.QrCodeConfig;
import xyz.symhx.normal.qrcode.common.Codectx;
import xyz.symhx.normal.qrcode.common.QrCodeGenerator;
import xyz.symhx.normal.qrcode.common.SimpleQrCodeGenerator;
import xyz.symhx.normal.service.IQrCodeService;
import xyz.symhx.normal.tools.QrCodeUtil;

import java.awt.image.BufferedImage;

@Service
public class QrCodeServiceImpl implements IQrCodeService {

    private QrCodeGenerator generator = new SimpleQrCodeGenerator();

    @Override
    public BufferedImage getSelfQrCode(String urlPath, DiyQrAttribute diyQrAttribute) {
        String path = "I:\\WorkSpace\\project\\demo\\src\\test\\resources\\mates\\pig.png";
        return QrCodeUtil.diyQrCode(Boolean.TRUE, diyQrAttribute, urlPath, 0, path, 0);
    }

    @Override
    public BufferedImage getQrCode(QRCodeInfo qrCodeInfo){
        Assert.notNull(qrCodeInfo, "参数错误");

        String content = qrCodeInfo.getContent();
        Assert.notNull(content, "参数错误");
        if (StringUtils.isNotBlank(qrCodeInfo.getMasterColor())) {
            generator.getQrCodeConfig().setMasterColor(qrCodeInfo.getMasterColor());
            if (!qrCodeInfo.getCodeEyeTogether()) {
                generator.getQrCodeConfig().setCodeEyesPointColor(Codectx.DEFAULT_CODE_EYE_POINT_COLOR);
                generator.getQrCodeConfig().setCodeEyesBorderColor(Codectx.DEFAULT_CODE_EYE_BORDER_COLOR);
            }
            if (StringUtils.isNotBlank(qrCodeInfo.getSlaveColor())) {
                generator.getQrCodeConfig().setSlaveColor(qrCodeInfo.getSlaveColor());
            }
        }
        return generator.generate(qrCodeInfo.getContent()).getImage();
    }

    public BufferedImage code() {

        return null;
    }
}
