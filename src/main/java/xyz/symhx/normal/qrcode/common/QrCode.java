package xyz.symhx.normal.qrcode.common;

import lombok.ToString;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * 二维码图像实体
 */
@ToString
public class QrCode implements Serializable {
    private static final long serialVersionUID = -4577725656092911563L;

    private BufferedImage image;

    private Logo logo;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    @ToString
    public static final class Logo {
        private String path;

        private Boolean remote;

        public Logo(String path, Boolean remote) {
            this.path = path;
            this.remote = remote;
        }

        public String getPath() {
            return path;
        }

        public boolean isRemote() {
            return remote;
        }
    }
}
