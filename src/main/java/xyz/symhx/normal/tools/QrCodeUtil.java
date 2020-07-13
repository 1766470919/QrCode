package xyz.symhx.normal.tools;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.symhx.normal.entity.DiyQrAttribute;
import xyz.symhx.normal.qrcode.common.Codectx;
import xyz.symhx.normal.qrcode.common.QrCodeGenerator;
import xyz.symhx.normal.qrcode.common.QrEyesFormat;
import xyz.symhx.normal.qrcode.common.SimpleQrCodeGenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class QrCodeUtil {
    private static Logger log = LoggerFactory.getLogger(QrCodeUtil.class);

    private static String UrlPath = "https://www.belinke.com/";

    private static QrCodeGenerator generator = new SimpleQrCodeGenerator();

    public static void main(String[] args){

    }

    /**
     * 获取默认样式二维码
     * @param content
     * @return
     */
    public static BufferedImage defaultQrCode(String content) {
        return logoQrCode(content, -1, "");
    }

    /**
     * 获取logo二维码
     * @param content   二维码地址
     * @param remote    是否为远程logo   1远程  0本地
     * @param logoPath  图片地址
     * @return
     */
    public static BufferedImage logoQrCode(String content,int remote, String logoPath) {
        return diyQrCode(false, null, content, remote, logoPath, 0);
    }

    /**
     * 自定义二维码样式
     *
     * @param hasDiy       是否自定义样式
     * @param content      二维码地址
     * @param remote       是否为远程logo   1远程  0本地
     * @param logoPath     图片地址
     * @param defaultStyle 是否默认样式
     * @return
     */
    public static BufferedImage diyQrCode(Boolean hasDiy, DiyQrAttribute diyQrAttribute, String content, int remote, String logoPath, int defaultStyle) {
        BufferedImage bufferedImage = null;
        try {
            if (hasDiy) {
                log.info("自定义二维码样式");
//                Assert.isNull(diyQrAttribute, "参数错误,请核对后提交");
                generator.getQrCodeConfig()
                        .setWidth(diyQrAttribute.getWidth())
                        .setHeight(diyQrAttribute.getHeight())
                        .setMargin(diyQrAttribute.getMargin())
                        .setPadding(diyQrAttribute.getPadding())
                        .setBorderSize(diyQrAttribute.getBorder())
                        .setBorderRadius(diyQrAttribute.getBorderRadius())
                        .setBorderColor(diyQrAttribute.getBorderColor())
                        .setMasterColor(diyQrAttribute.getMasterColor())
                        .setSlaveColor(diyQrAttribute.getSlaveColor())
                        .setCodeEyesFormat(QrEyesFormat.C_BORDER_R_POINT)
                        .setCodeEyesPointColor("#3D7EFF");
                if (StringUtils.isNotBlank(logoPath)) {
                    generator.getQrCodeConfig()
                            .setLogoBorderSize(diyQrAttribute.getLogoBorder())
                            .setLogoBorderColor(diyQrAttribute.getLogoBorderColor())
                            .setLogoPadding(diyQrAttribute.getLogoPadding())
                            .setLogoMargin(diyQrAttribute.getLogoMargin())
                            .setLogoRatio(diyQrAttribute.getLogoRatio())
                            .setLogoBackgroundColor(diyQrAttribute.getLogoBackgroundColor());
                    if (diyQrAttribute.getLogoStyle() == 0) {
                        generator.getQrCodeConfig().setLogoShape(Codectx.LogoShape.RECTANGLE);
                    } else {
                        generator.getQrCodeConfig().setLogoShape(Codectx.LogoShape.CIRCLE);
                    }
                }
                if (diyQrAttribute.getBorderStyle() == 0) {
                    generator.getQrCodeConfig().setBorderStyle(Codectx.BorderStyle.SOLID);
                } else {
                    generator.getQrCodeConfig().setBorderStyle(Codectx.BorderStyle.DASHED);
                }
                if (remote > 0) {
                    generator.setRemoteLogo(logoPath);
                } else if (remote == 0) {
                    generator.setLogo(logoPath, Boolean.FALSE);
                } else {
                    throw new Exception("{remote}参数错误: 详情请查看说明");
                }
                bufferedImage = generator.generate(content).getImage();
            } else {
                log.info("第一步: 检测二维码样式-----:{}", defaultStyle);
                if (defaultStyle > 0) {
                    log.info("检测结束: 生成logo二维码");
                    log.info("第二部: 检测二维码logo来源方式：{}", remote);
                    if (remote > 0) {
                        log.info("检测结束: logo地址来源于网络");
                        generator.setRemoteLogo(logoPath);
                    } else if (remote == 0) {
                        log.info("检测结束: logo地址来源于本地");
                        generator.setLogo(logoPath, Boolean.FALSE);
                    } else {
                        throw new Exception("{remote}参数错误: 详情请查看说明");
                    }
                    bufferedImage = generator.generate(content).getImage();
                } else {
                    log.info("检测结束: 生成默认样式二维码");
                    bufferedImage = generator.generate(content).getImage();
                }
            }
//            BufferedImage bufferedImage1 = addBackgroundImg(bufferedImage, "https://t12.baidu.com/it/u=263151386,4106225699&fm=76", 0, 0);
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给二维码添加背景图片
     * @param image
     * @param bgPath
     * @param x 二维码左顶点在背景图片的X坐标
     * @param y 二维码左顶点在背景图片的Y坐标
     * @return
     */
    private static BufferedImage addBackgroundImg(BufferedImage image, String bgPath, int x, int y) {
        Boolean isImg = ImageUtil.checkRemoteUrlIsImage(bgPath);
        if (isImg) {
            try {
                BufferedImage bgImg = ImageIO.read(new URL(bgPath));
                x = Math.max(x, 0);
                y = Math.max(y, 0);
                if(bgImg.getWidth() < image.getWidth() || bgImg.getHeight() <image.getHeight()) {
                    throw new Exception("背景图片尺寸不小于二维码尺寸");
                }
                if(bgImg.getWidth() < x + image.getWidth() || bgImg.getHeight() < y + bgImg.getHeight()) {
                    throw new Exception("以背景的("+x+","+y+")作为二维码左上角不能容下整个二维码");
                }
                Graphics2D graph = bgImg.createGraphics();
                graph.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
                graph.dispose();
                return image;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
