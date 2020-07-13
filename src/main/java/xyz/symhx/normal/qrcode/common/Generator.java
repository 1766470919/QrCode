package xyz.symhx.normal.qrcode.common;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 生成器
 */
public interface Generator {
    /**
     * 扫码展示内容
     * @param content
     * @return
     */
    Generator generate(String content);

    /**
     * 获取生成缓冲图像
     * @return
     */
    BufferedImage getImage();

    /**
     * 转换文件
     * @param path
     * @return
     * @throws IOException
     */
    Boolean toFile(String path) throws IOException;

    /**
     * 转换流
     * @param os
     * @return
     * @throws IOException
     */
    Boolean toStream(OutputStream os) throws IOException;

    /**
     * 清除
     */
    void clear();
}
