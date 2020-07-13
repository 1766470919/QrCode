package xyz.symhx.normal.qrcode;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import xyz.symhx.normal.qrcode.common.Codectx;
import xyz.symhx.normal.qrcode.common.Codectx.LogoShape;

import java.io.Serializable;


@ToString
public class LogoConfig implements Serializable {

    private static final long serialVersionUID = 869127511613500154L;
    // 比率
    private int ratio = Codectx.DEFAULT_LOGO_RATIO;
    // 弧宽
    private int arcWidth = Codectx.DEFAULT_LOGO_ARCWIDTH;
    // 弧高
    private int arcHeight = Codectx.DEFAULT_LOGO_ARCWIDTH;
    // 边框大小
    private int borderSize = Codectx.DEFAULT_LOGO_BORDER_SIZE;
    // 边框颜色
    private String borderColor = Codectx.DEFAULT_LOGO_BORDER_COLOR;
    // 内边距
    private int padding = Codectx.DEFAULT_LOGO_PADDING;
    // 外边距
    private int margin = Codectx.DEFAULT_LOGO_MARGIN;
    // 背景颜色
    private String backgroundColor = Codectx.DEFAULT_LOGO_BACKGROUND_COLOR;
    // 面板色
    private String panelColor = Codectx.DEFAULT_LOGO_PANEL_COLOR;
    // 面板半径宽
    private int panelArcWidth = Codectx.DEFAULT_LOGO_PANEL_RADIUS;
    // 面板半径高
    private int panelArcHeight = Codectx.DEFAULT_LOGO_PANEL_RADIUS;
    // logo形状
    LogoShape shape = LogoShape.RECTANGLE;

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getArcWidth() {
        return arcWidth;
    }

    public void setArcWidth(int arcWidth) {
        this.arcWidth = arcWidth;
    }

    public int getArcHeight() {
        return arcHeight;
    }

    public void setArcHeight(int arcHeight) {
        this.arcHeight = arcHeight;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public int getPadding() {
        return padding;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getPanelColor() {
        return panelColor;
    }

    public void setPanelColor(String panelColor) {
        this.panelColor = panelColor;
    }

    public int getPanelArcWidth() {
        return panelArcWidth;
    }

    public void setPanelArcWidth(int panelArcWidth) {
        this.panelArcWidth = panelArcWidth;
    }

    public int getPanelArcHeight() {
        return panelArcHeight;
    }

    public void setPanelArcHeight(int panelArcHeight) {
        this.panelArcHeight = panelArcHeight;
    }

    public LogoShape getShape() {
        return shape;
    }

    public void setShape(LogoShape shape) {
        this.shape = shape;
    }

    public final boolean isRectangle() {
        return getShape() == LogoShape.RECTANGLE;
    }

    public final boolean isCircle() {
        return getShape() == LogoShape.CIRCLE;
    }
}
