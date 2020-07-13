package xyz.symhx.normal.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ImageUtil {
    private static Logger log = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 模拟访问远程地址
     * @param remoteAddress
     * @return
     */
    public static Boolean checkRemoteUrlIsImage(String remoteAddress) {
        try {
            URL url = new URL(remoteAddress);
            log.info("访问远程【{}】地址", remoteAddress);
            BufferedImage read = ImageIO.read(url);
            if (null != read) {
                log.info("远程地址为图片");
                return Boolean.TRUE;
            }
            log.info("非图片远程地址");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }
}
