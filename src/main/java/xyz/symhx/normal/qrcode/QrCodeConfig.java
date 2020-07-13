package xyz.symhx.normal.qrcode;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import xyz.symhx.normal.qrcode.common.Codectx;
import xyz.symhx.normal.qrcode.common.Codectx.BorderStyle;
import xyz.symhx.normal.qrcode.common.Codectx.LogoShape;
import xyz.symhx.normal.qrcode.common.GenericCodeConfig;
import xyz.symhx.normal.qrcode.common.QrEyesFormat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 二维码属性配置
 */
public class QrCodeConfig extends GenericCodeConfig {

    private static final long serialVersionUID = -7994322871273160450L;

    public QrCodeConfig() {
        this(Codectx.DEFAULT_CODE_WIDTH, Codectx.DEFAULT_CODE_HEIGHT);
    }

    public QrCodeConfig(Integer width, Integer height) {
        super(width, height);
    }

    private static ConcurrentHashMap<EncodeHintType, Object> hints = null;

    static {
        hints = new ConcurrentHashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 设置容错
        hints.put(EncodeHintType.MARGIN, Codectx.DEFAULT_CODE_MARGIN);      // 设置前后水平边距
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");                   // 字符集
    }

    // 码外边距
    private int margin = Codectx.DEFAULT_CODE_MARGIN;
    // 码内边距
    private int padding = Codectx.DEFAULT_CODE_PADDING;
    // 码边框大小
    private int borderSize = Codectx.DEFAULT_CODE_BORDER_SIZE;
    // 码边框圆角
    private int borderRadius = Codectx.DEFAULT_CODE_BORDER_RADIUS;
    // 码边框颜色
    private String borderColor = Codectx.DEFAULT_CODE_BORDER_COLOR;
    // 码边框样式（虚线）
    private BorderStyle borderStyle = Codectx.BorderStyle.DASHED;
    // 码边界波折粒度
    private int borderDashGranularity = Codectx.DEFAULT_CODE_BORDER_DASH_GRANULARITY;
    // 码眼边框颜色
    private String codeEyesBorderColor = Codectx.DEFAULT_CODE_MASTER_COLOR;
    // 码眼颜色
    private String codeEyesPointColor = Codectx.DEFAULT_CODE_MASTER_COLOR;
    // 码眼格式化 矩形边框矩形点
    private QrEyesFormat codeEyesFormat = QrEyesFormat.R_BORDER_R_POINT;
    // logo配置
    private final LogoConfig logoConfig = new LogoConfig();

    public int getMargin() {
        return margin;
    }

    public QrCodeConfig setMargin(int margin) {
        this.margin = margin;
        addHint(EncodeHintType.MARGIN, padding);
        return this;
    }

    public int getPadding() {
        return padding;
    }

    public QrCodeConfig setPadding(int padding) {
        this.padding = padding;
        return this;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public QrCodeConfig setBorderSize(int borderSize) {
        this.borderSize = borderSize;
        return this;
    }

    public int getBorderRadius() {
        return borderRadius;
    }

    public QrCodeConfig setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
        return this;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public QrCodeConfig setBorderColor(String borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public BorderStyle getBorderStyle() {
        return borderStyle;
    }

    public QrCodeConfig setBorderStyle(BorderStyle borderStyle) {
        this.borderStyle = borderStyle;
        return this;
    }

    public int getBorderDashGranularity() {
        return borderDashGranularity;
    }

    public QrCodeConfig setBorderDashGranularity(int borderDashGranularity) {
        this.borderDashGranularity = borderDashGranularity;
        return this;
    }

    public String getCodeEyesBorderColor() {
        return codeEyesBorderColor;
    }

    public QrCodeConfig setCodeEyesBorderColor(String codeEyesBorderColor) {
        this.codeEyesBorderColor = codeEyesBorderColor;
        return this;
    }

    public String getCodeEyesPointColor() {
        return codeEyesPointColor;
    }

    public QrCodeConfig setCodeEyesPointColor(String codeEyesPointColor) {
        this.codeEyesPointColor = codeEyesPointColor;
        return this;
    }

    public QrEyesFormat getCodeEyesFormat() {
        return codeEyesFormat;
    }

    public QrCodeConfig setCodeEyesFormat(QrEyesFormat codeEyesFormat) {
        this.codeEyesFormat = codeEyesFormat;
        return this;
    }

    public LogoConfig getLogoConfig() {
        return logoConfig;
    }

    @Override
    public QrCodeConfig setWidth(Integer width) {
        return (QrCodeConfig) super.setWidth(width);
    }

    @Override
    public QrCodeConfig setHeight(Integer height) {
        return (QrCodeConfig)super.setHeight(height);
    }
    @Override
    public QrCodeConfig setMasterColor(String masterColor) {
        super.setMasterColor(masterColor);
        this.setCodeEyesBorderColor(masterColor);
        this.setCodeEyesPointColor(masterColor);
        return this;
    }

    @Override
    public QrCodeConfig setSlaveColor(String slaveColor) {
        return (QrCodeConfig)super.setSlaveColor(slaveColor);
    }

    public QrCodeConfig setLogoRatio(int ratio) {
        getLogoConfig().setRatio(ratio);
        return this;
    }

    public QrCodeConfig setLogoArcWidth(int arcWidth) {
        getLogoConfig().setArcWidth(arcWidth);
        return this;
    }

    public QrCodeConfig setLogoArcHeight(int arcHeight) {
        getLogoConfig().setArcHeight(arcHeight);
        return this;
    }

    public QrCodeConfig setLogoBorderSize(int borderSize) {
        getLogoConfig().setBorderSize(borderSize);
        return this;
    }

    public QrCodeConfig setLogoBorderColor(String borderColor) {
        getLogoConfig().setBorderColor(borderColor);
        return this;
    }

    public QrCodeConfig setLogoPadding(int padding) {
        getLogoConfig().setPadding(padding);
        return this;
    }

    public QrCodeConfig setLogoMargin(int margin) {
        getLogoConfig().setMargin(margin);
        return this;
    }

    public QrCodeConfig setLogoBackgroundColor(String backgroundColor) {
        getLogoConfig().setBackgroundColor(backgroundColor);
        return this;
    }

    public QrCodeConfig setPanelColor(String color) {
        getLogoConfig().setPanelColor(color);
        return this;
    }

    public QrCodeConfig setLogoPanelArcWidth(int arcWidth) {
        getLogoConfig().setPanelArcWidth(arcWidth);
        return this;
    }

    public QrCodeConfig setLogoPanelArcHeight(int arcHeight) {
        getLogoConfig().setPanelArcHeight(arcHeight);
        return this;
    }

    public QrCodeConfig setLogoShape(LogoShape shape) {
        getLogoConfig().setShape(shape);
        return this;
    }

    public Map<EncodeHintType, Object> getHints() {
        return hints;
    }

    public Map<EncodeHintType, Object> addHint(EncodeHintType type, Object value) {
        Map<EncodeHintType, Object> hints = getHints();
        hints.put(type, value);
        return hints;
    }

    // 二维码容错矫正级别
    public void setErrorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
        addHint(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
    }
}
