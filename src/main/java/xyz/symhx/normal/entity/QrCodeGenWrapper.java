package xyz.symhx.normal.entity;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import xyz.symhx.normal.tools.Base64Util;
import xyz.symhx.normal.common.GifDecoder;
import xyz.symhx.normal.common.MediaType;
import xyz.symhx.normal.tools.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QrCodeGenWrapper {

    // 设置二维码主体信息
    public static QrCodeGenerator putContent(String content) {
        return new QrCodeGenerator().setContent(content);
    }

    private static ByteArrayOutputStream asGif(QrCodeOptions qrCodeOptions) throws WriterException {
        try {
            BitMatrixEx bitMatrix = QrCodeGenerateHelper.encode(qrCodeOptions);
            List<ImmutablePair<BufferedImage, Integer>> list =
                    QrCodeGenerateHelper.toGifImages(qrCodeOptions, bitMatrix);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            GifHelper.saveGif(list, outputStream);
            return outputStream;
        } finally {
            QuickQrUtil.clear();
        }
    }

    private static BufferedImage asBufferedImage(QrCodeOptions qrCodeOptions) throws WriterException, IOException {
        try {
            BitMatrixEx bitMatrix = QrCodeGenerateHelper.encode(qrCodeOptions);
            return QrCodeGenerateHelper.toBufferedImage(qrCodeOptions, bitMatrix);
        } finally {
            QuickQrUtil.clear();
        }
    }

    private static String asString(QrCodeOptions qrCodeOptions) throws WriterException, IOException {
        if (qrCodeOptions.gifQrCode()) {
            // 动态二维码生成
            try (ByteArrayOutputStream outputStream = asGif(qrCodeOptions)) {
                return Base64Util.encode(outputStream);
            }
        }

        // 普通二维码，直接输出图
        BufferedImage bufferedImage = asBufferedImage(qrCodeOptions);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, qrCodeOptions.getPicType(), outputStream);
            return Base64Util.encode(outputStream);
        }
    }

    private static boolean asFile(QrCodeOptions qrCodeOptions, String absFileName) throws WriterException, IOException {
        File file = new File(absFileName);
        FileWriteUtil.mkDir(file.getParentFile());

        if (qrCodeOptions.gifQrCode()) {
            // 保存动态二维码
            try (ByteArrayOutputStream output = asGif(qrCodeOptions)) {
                FileOutputStream out = new FileOutputStream(file);
                out.write(output.toByteArray());
                out.flush();
                out.close();
            }

            return true;
        }

        BufferedImage bufferedImage = asBufferedImage(qrCodeOptions);
        if (!ImageIO.write(bufferedImage, qrCodeOptions.getPicType(), file)) {
            throw new IOException("save QrCode image to: " + absFileName + " error!");
        }

        return true;
    }


    @ToString
    @Slf4j
    public static class QrCodeGenerator {
        private static final MatrixToImageConfig DEFAULT_CONFIG = new MatrixToImageConfig();

        /**
         * 主题信息
         */
        private String content;

        /**
         * 二维码宽
         */
        private Integer width;


        /**
         * 二维码高
         */
        private Integer height;


        /**
         * qrcode message's code, default UTF-8
         */
        private String code = "utf-8";


        /**
         * 0 - 4
         */
        private Integer padding;


        /**
         * error level, default H
         */
        private ErrorCorrectionLevel errorCorrection = ErrorCorrectionLevel.H;


        /**
         * output qrcode image type, default png
         */
        private String picType = "png";


        private QrCodeOptions.BgImgOptions.BgImgOptionsBuilder bgImgOptions;

        private QrCodeOptions.LogoOptions.LogoOptionsBuilder logoOptions;

        private QrCodeOptions.DrawOptions.DrawOptionsBuilder drawOptions;

        private QrCodeOptions.DetectOptions.DetectOptionsBuilder detectOptions;


        public QrCodeGenerator() {
            // 背景图默认采用覆盖方式
            bgImgOptions =
                    QrCodeOptions.BgImgOptions.builder().bgImgStyle(QrCodeOptions.BgImgStyle.OVERRIDE).opacity(0.85f);


            // 默认采用普通格式的logo， 无边框
            logoOptions = QrCodeOptions.LogoOptions.builder().logoStyle(QrCodeOptions.LogoStyle.NORMAL).border(false)
                    .rate(12);


            // 绘制信息，默认黑白方块
            drawOptions =
                    QrCodeOptions.DrawOptions.builder().drawStyle(QrCodeOptions.DrawStyle.RECT).bgColor(Color.WHITE)
                            .preColor(Color.BLACK).diaphaneityFill(false).enableScale(false);

            // 探测图形
            detectOptions = QrCodeOptions.DetectOptions.builder();
        }


        public String getContent() {
            return content;
        }

        public QrCodeGenerator setContent(String content) {
            this.content = content;
            return this;
        }

        public Integer getWidth() {
            return width == null ? (height == null ? 200 : height) : width;
        }

        public QrCodeGenerator setWidth(Integer width) {
            if (width != null && width <= 0) {
                throw new IllegalArgumentException("生成二维码的宽必须大于0");
            }
            this.width = width;
            return this;
        }

        public Integer getHeight() {
            return height == null ? (height == null ? 200 : width) : height;
        }

        public QrCodeGenerator setHeight(Integer height) {
            if (height != null && height <= 0) {
                throw new IllegalArgumentException("生成功能二维码的搞必须大于0");
            }
            this.height = height;
            return this;
        }

        public QrCodeGenerator setCode(String code) {
            this.code = code;
            return this;
        }

        public Integer getPadding() {
            if (padding == null) {
                return 1;
            }

            if (padding < 0) {
                return 0;
            }

            if (padding > 4) {
                return 4;
            }

            return padding;
        }

        public QrCodeGenerator setPadding(Integer padding) {
            this.padding = padding;
            return this;
        }

        public QrCodeGenerator setPicType(String picType) {
            this.picType = picType;
            return this;
        }

        public QrCodeGenerator setErrorCorrection(ErrorCorrectionLevel errorCorrection) {
            this.errorCorrection = errorCorrection;
            return this;
        }


        /////////////// logo 相关配置 ///////////////

        public QrCodeGenerator setLogo(String logo) throws IOException {
            try {
                return setLogo(ImageLoadUtil.getImageByPath(logo));
            } catch (IOException e) {
                log.error("load logo error! e:{}", e);
                throw new IOException("load logo error!", e);
            }
        }

        public QrCodeGenerator setLogo(InputStream inputStream) throws IOException {
            try {
                return setLogo(ImageIO.read(inputStream));
            } catch (IOException e) {
                log.error("load backgroundImg error! e:{}", e);
                throw new IOException("load backgroundImg error!", e);
            }
        }

        public QrCodeGenerator setLogo(BufferedImage img) {
            logoOptions.logo(img);
            return this;
        }

        public QrCodeGenerator setLogoStyle(QrCodeOptions.LogoStyle logoStyle) {
            logoOptions.logoStyle(logoStyle);
            return this;
        }

        public QrCodeGenerator setLogoBgColor(Integer color) {
            if (color == null) {
                return this;
            }

            return setLogoBgColor(ColorUtil.int2color(color));
        }

        /**
         * logo 背景颜色
         *
         * @param color
         * @return
         */
        public QrCodeGenerator setLogoBgColor(Color color) {
            logoOptions.border(true);
            logoOptions.borderColor(color);
            return this;
        }

        public QrCodeGenerator setLogoBorderBgColor(Integer color) {
            if (color == null) {
                return this;
            }
            return setLogoBorderBgColor(ColorUtil.int2color(color));
        }

        /**
         * logo 外层边框颜色
         *
         * @param color
         * @return
         */

        public QrCodeGenerator setLogoBorderBgColor(Color color) {
            logoOptions.border(true);
            logoOptions.outerBorderColor(color);
            return this;
        }

        public QrCodeGenerator setLogoBorder(boolean border) {
            logoOptions.border(border);
            return this;
        }

        public QrCodeGenerator setLogoRate(int rate) {
            logoOptions.rate(rate);
            return this;
        }

        /**
         * logo透明度
         *
         * @param opacity
         * @return
         */
        public QrCodeGenerator setLogoOpacity(float opacity) {
            logoOptions.opacity(opacity);
            return this;
        }

        ///////////////// logo配置结束 ///////////////


        // ------------------------------------------


        /////////////// 背景 相关配置 ///////////////

        public QrCodeGenerator setBgImg(String bgImg) throws IOException {
            try {
                return setBgImg(FileReadUtil.getStreamByFileName(bgImg));
            } catch (IOException e) {
                log.error("load backgroundImg error! e:{}", e);
                throw new IOException("load backgroundImg error!", e);
            }
        }


        public QrCodeGenerator setBgImg(InputStream inputStream) throws IOException {
            try {
                ByteArrayInputStream target = IoUtil.toByteArrayInputStream(inputStream);
                MediaType media = MediaType.typeOfMagicNum(FileReadUtil.getMagicNum(target));
                if (media == MediaType.ImageGif) {
                    GifDecoder gifDecoder = new GifDecoder();
                    gifDecoder.read(target);
                    bgImgOptions.gifDecoder(gifDecoder);
                    return this;
                } else {
                    return setBgImg(ImageIO.read(target));
                }
            } catch (IOException e) {
                log.error("load backgroundImg error! e:{}", e);
                throw new IOException("load backgroundImg error!", e);
            }
        }


        public QrCodeGenerator setBgImg(BufferedImage bufferedImage) {
            bgImgOptions.bgImg(bufferedImage);
            return this;
        }


        public QrCodeGenerator setBgStyle(QrCodeOptions.BgImgStyle bgImgStyle) {
            bgImgOptions.bgImgStyle(bgImgStyle);
            return this;
        }


        public QrCodeGenerator setBgW(int w) {
            bgImgOptions.bgW(w);
            return this;
        }

        public QrCodeGenerator setBgH(int h) {
            bgImgOptions.bgH(h);
            return this;
        }


        public QrCodeGenerator setBgOpacity(float opacity) {
            bgImgOptions.opacity(opacity);
            return this;
        }


        public QrCodeGenerator setBgStartX(int startX) {
            bgImgOptions.startX(startX);
            return this;
        }

        public QrCodeGenerator setBgStartY(int startY) {
            bgImgOptions.startY(startY);
            return this;
        }


        /////////////// logo 配置结束 ///////////////


        // ------------------------------------------


        /////////////// 探测图形 相关配置 ///////////////
        public QrCodeGenerator setDetectImg(String detectImg) throws IOException {
            try {
                return setDetectImg(ImageLoadUtil.getImageByPath(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }


        public QrCodeGenerator setDetectImg(InputStream detectImg) throws IOException {
            try {
                return setDetectImg(ImageIO.read(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }


        public QrCodeGenerator setDetectImg(BufferedImage detectImg) {
            detectOptions.detectImg(detectImg);
            detectOptions.special(true);
            return this;
        }

        /**
         * 左上角探测图形
         *
         * @param detectImg
         * @return
         * @throws IOException
         */
        public QrCodeGenerator setLTDetectImg(String detectImg) throws IOException {
            try {
                return setLTDetectImg(ImageLoadUtil.getImageByPath(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }

        /**
         * 左上角探测图形
         *
         * @param detectImg
         * @return
         * @throws IOException
         */
        public QrCodeGenerator setLTDetectImg(InputStream detectImg) throws IOException {
            try {
                return setLTDetectImg(ImageIO.read(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }

        /**
         * 左上角探测图形
         *
         * @param detectImg
         * @return
         * @throws IOException
         */
        public QrCodeGenerator setLTDetectImg(BufferedImage detectImg) {
            detectOptions.detectImgLT(detectImg);
            detectOptions.special(true);
            return this;
        }

        /**
         * 左上角探测图形
         *
         * @param detectImg
         * @return
         * @throws IOException
         */
        public QrCodeGenerator setRTDetectImg(String detectImg) throws IOException {
            try {
                return setRTDetectImg(ImageLoadUtil.getImageByPath(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }

        /**
         * 左上角探测图形
         *
         * @param detectImg
         * @return
         * @throws IOException
         */
        public QrCodeGenerator setRTDetectImg(InputStream detectImg) throws IOException {
            try {
                return setRTDetectImg(ImageIO.read(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }

        /**
         * 右上角探测图形
         *
         * @param detectImg
         * @return
         * @throws IOException
         */
        public QrCodeGenerator setRTDetectImg(BufferedImage detectImg) {
            detectOptions.detectImgRT(detectImg);
            detectOptions.special(true);
            return this;
        }

        /**
         * 左上角探测图形
         *
         * @param detectImg
         * @return
         * @throws IOException
         */
        public QrCodeGenerator setLDDetectImg(String detectImg) throws IOException {
            try {
                return setLDDetectImg(ImageLoadUtil.getImageByPath(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }

        /**
         * 左上角探测图形
         *
         * @param detectImg
         * @return
         * @throws IOException
         */
        public QrCodeGenerator setLDDetectImg(InputStream detectImg) throws IOException {
            try {
                return setLDDetectImg(ImageIO.read(detectImg));
            } catch (IOException e) {
                log.error("load detectImage error! e:{}", e);
                throw new IOException("load detectImage error!", e);
            }
        }

        /**
         * 左上角探测图形
         *
         * @param detectImg
         * @return
         * @throws IOException
         */
        public QrCodeGenerator setLDDetectImg(BufferedImage detectImg) {
            detectOptions.detectImgLD(detectImg);
            detectOptions.special(true);
            return this;
        }

        public QrCodeGenerator setDetectOutColor(Integer outColor) {
            if (outColor == null) {
                return this;
            }

            return setDetectOutColor(ColorUtil.int2color(outColor));
        }

        public QrCodeGenerator setDetectOutColor(Color outColor) {
            detectOptions.outColor(outColor);
            return this;
        }

        public QrCodeGenerator setDetectInColor(Integer inColor) {
            if (inColor == null) {
                return this;
            }

            return setDetectInColor(ColorUtil.int2color(inColor));
        }

        public QrCodeGenerator setDetectInColor(Color inColor) {
            detectOptions.inColor(inColor);
            return this;
        }

        /**
         * 设置探测图形样式，不跟随二维码主样式
         *
         * @return
         */
        public QrCodeGenerator setDetectSpecial() {
            detectOptions.special(true);
            return this;
        }

        /////////////// 探测图形 配置结束 ///////////////


        // ------------------------------------------


        /////////////// 二维码绘制 相关配置 ///////////////

        public QrCodeGenerator setDrawStyle(String style) {
            return setDrawStyle(QrCodeOptions.DrawStyle.getDrawStyle(style));
        }


        public QrCodeGenerator setDrawStyle(QrCodeOptions.DrawStyle drawStyle) {
            drawOptions.drawStyle(drawStyle);
            return this;
        }


        /**
         * 透明度填充，如绘制二维码的图片中存在透明区域，若这个参数为true，则会用bgColor填充透明的区域；若为false，则透明区域依旧是透明的
         */
        public QrCodeGenerator setDiaphaneityFill(boolean fill) {
            drawOptions.diaphaneityFill(fill);
            return this;
        }

        public QrCodeGenerator setDrawPreColor(int color) {
            return setDrawPreColor(ColorUtil.int2color(color));
        }


        public QrCodeGenerator setDrawPreColor(Color color) {
            drawOptions.preColor(color);
            return this;
        }

        public QrCodeGenerator setDrawBgColor(int color) {
            return setDrawBgColor(ColorUtil.int2color(color));
        }

        public QrCodeGenerator setDrawBgColor(Color color) {
            drawOptions.bgColor(color);
            return this;
        }

        public QrCodeGenerator setDrawBgImg(String img) throws IOException {
            try {
                return setDrawBgImg(ImageLoadUtil.getImageByPath(img));
            } catch (IOException e) {
                log.error("load drawBgImg error! e:{}", e);
                throw new IOException("load drawBgImg error!", e);
            }
        }

        public QrCodeGenerator setDrawBgImg(InputStream img) throws IOException {
            try {
                return setDrawBgImg(ImageIO.read(img));
            } catch (IOException e) {
                log.error("load drawBgImg error! e:{}", e);
                throw new IOException("load drawBgImg error!", e);
            }
        }

        public QrCodeGenerator setDrawBgImg(BufferedImage img) {
            drawOptions.bgImg(img);
            return this;
        }

        public QrCodeGenerator setDrawEnableScale(boolean enable) {
            drawOptions.enableScale(enable);
            return this;
        }


        public QrCodeGenerator setDrawImg(String img) throws IOException {
            try {
                return setDrawImg(ImageLoadUtil.getImageByPath(img));
            } catch (IOException e) {
                log.error("load draw img error! e: {}", e);
                throw new IOException("load draw img error!", e);
            }
        }

        public QrCodeGenerator setDrawImg(InputStream input) throws IOException {
            try {
                return setDrawImg(ImageIO.read(input));
            } catch (IOException e) {
                log.error("load draw img error! e: {}", e);
                throw new IOException("load draw img error!", e);
            }
        }

        public QrCodeGenerator setDrawImg(BufferedImage img) {
            addImg(1, 1, img);
            return this;
        }


        public QrCodeGenerator addImg(int row, int col, BufferedImage img) {
            if (img == null) {
                return this;
            }
            drawOptions.enableScale(true);
            drawOptions.drawImg(row, col, img);
            return this;
        }

        public QrCodeGenerator addImg(int row, int col, String img) throws IOException {
            try {
                return addImg(row, col, ImageLoadUtil.getImageByPath(img));
            } catch (IOException e) {
                log.error("load draw size4img error! e: {}", e);
                throw new IOException("load draw row:" + row + ", col:" + col + " img error!", e);
            }
        }

        public QrCodeGenerator addImg(int row, int col, InputStream img) throws IOException {
            try {
                return addImg(row, col, ImageIO.read(img));
            } catch (IOException e) {
                log.error("load draw size4img error! e: {}", e);
                throw new IOException("load draw row:" + row + ", col:" + col + " img error!", e);
            }
        }

        /**
         * 文字二维码的渲染字符串
         *
         * @param text
         * @return
         */
        public QrCodeGenerator setQrText(String text) {
            drawOptions.text(text);
            return this;
        }

        public QrCodeGenerator setQrTxtMode(QrCodeOptions.TxtMode txtMode) {
            drawOptions.txtMode(txtMode);
            return this;
        }

        /**
         * 字体名
         *
         * @param fontName
         * @return
         */
        public QrCodeGenerator setQrDotFontName(String fontName) {
            drawOptions.fontName(fontName);
            return this;
        }

        /**
         * 字体样式
         *
         * @param fontStyle 0 {@link Font#PLAIN} 1 {@link Font#BOLD} 2 {@link Font#ITALIC}
         * @return
         */
        public QrCodeGenerator setQrDotFontStyle(int fontStyle) {
            drawOptions.fontStyle(fontStyle);
            return this;
        }

        /////////////// 二维码绘制 配置结束 ///////////////


        private void validate() {
            if (content == null || content.length() == 0) {
                throw new IllegalArgumentException("生成二维码的内容不能为空!");
            }
        }


        private QrCodeOptions build() {
            this.validate();

            QrCodeOptions qrCodeConfig = new QrCodeOptions();
            qrCodeConfig.setMsg(getContent());
            qrCodeConfig.setH(getHeight());
            qrCodeConfig.setW(getWidth());


            // 设置背景信息
            QrCodeOptions.BgImgOptions bgOp = bgImgOptions.build();
            if (bgOp.getBgImg() == null && bgOp.getGifDecoder() == null) {
                qrCodeConfig.setBgImgOptions(null);
            } else {
                qrCodeConfig.setBgImgOptions(bgOp);
            }


            // 设置logo信息
            QrCodeOptions.LogoOptions logoOp = logoOptions.build();
            if (logoOp.getLogo() == null) {
                qrCodeConfig.setLogoOptions(null);
            } else {
                qrCodeConfig.setLogoOptions(logoOp);
            }


            // 绘制信息
            QrCodeOptions.DrawOptions drawOp = drawOptions.build();
            qrCodeConfig.setDrawOptions(drawOp);


            // 设置detect绘制信息
            QrCodeOptions.DetectOptions detectOp = detectOptions.build();
            if (detectOp.getOutColor() == null && detectOp.getInColor() == null) {
                detectOp.setInColor(drawOp.getPreColor());
                detectOp.setOutColor(drawOp.getPreColor());
            } else if (detectOp.getOutColor() == null) {
                detectOp.setOutColor(detectOp.getOutColor());
            } else if (detectOp.getInColor() == null) {
                detectOp.setInColor(detectOp.getInColor());
            }
            qrCodeConfig.setDetectOptions(detectOp);


            if (qrCodeConfig.getBgImgOptions() != null &&
                    qrCodeConfig.getBgImgOptions().getBgImgStyle() == QrCodeOptions.BgImgStyle.PENETRATE) {
                // 透传，用背景图颜色进行绘制时
                drawOp.setPreColor(ColorUtil.OPACITY);
                qrCodeConfig.getBgImgOptions().setOpacity(1);
                qrCodeConfig.getDetectOptions().setInColor(ColorUtil.OPACITY);
                qrCodeConfig.getDetectOptions().setOutColor(ColorUtil.OPACITY);
            }

            // 设置输出图片格式
            qrCodeConfig.setPicType(picType);

            // 设置精度参数
            Map<EncodeHintType, Object> hints = new HashMap<>(3);
            hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrection);
            hints.put(EncodeHintType.CHARACTER_SET, code);
            hints.put(EncodeHintType.MARGIN, this.getPadding());
            qrCodeConfig.setHints(hints);

            return qrCodeConfig;
        }


        public String asString() throws IOException, WriterException {
            return QrCodeGenWrapper.asString(build());
        }


        public BufferedImage asBufferedImage() throws IOException, WriterException {
            return QrCodeGenWrapper.asBufferedImage(build());
        }

        public ByteArrayOutputStream asStream() throws WriterException, IOException {
            QrCodeOptions options = build();
            if (options.gifQrCode()) {
                return QrCodeGenWrapper.asGif(options);
            } else {
                BufferedImage img = QrCodeGenWrapper.asBufferedImage(options);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(img, options.getPicType(), outputStream);
                return outputStream;
            }
        }

        public boolean asFile(String absFileName) throws IOException, WriterException {
            return QrCodeGenWrapper.asFile(build(), absFileName);
        }
    }
}