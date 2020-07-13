package xyz.symhx.normal.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import xyz.symhx.normal.qrcode.common.Codectx;

import java.io.Serializable;

/**
 * 二维码对象属性
 */
@Getter
@Setter
@ToString
public class DiyQrAttribute implements Serializable {

    private static final long serialVersionUID = -2334067891696381612L;
    /**
     * 长度
     */
    private Integer width = 250;

    /**
     * 宽度
     */
    private Integer height = 250;

    /**
     * 边框值
     */
    private Integer border = 0;

    /**
     * 边框圆角
     */
    private Integer borderRadius = 0;

    /**
     * 边框样式（实线、虚线）
     */
    private Integer borderStyle = 0;

    /**
     * 二维码外边距
     */
    private Integer margin = 10;

    /**
     * 二维码内边距
     */
    private Integer padding = 0;

    /**
     *  默认主色 黑色
     */
    private String masterColor = Codectx.DEFAULT_CODE_MASTER_COLOR;

    /**
     *  默认底色 白色
     */
    private String slaveColor = Codectx.DEFAULT_CODE_SLAVE_COLOR;

    /**
     * 默认边框颜色
     */
    private String borderColor = Codectx.DEFAULT_CODE_BORDER_COLOR;

    /******************************************************************/

    /**
     * 比率
     */
    private Integer logoRatio = 5;

    /**
     * logo样式（方形、圆形）
     */
    private Integer logoStyle = 0;

    /**
     * logo边框size
     */
    private Integer logoBorder = 2;

    /**
     * logo内边距
     */
    private Integer logoPadding = 5;

    /**
     * logo外边距
     */
    private Integer logoMargin = 5;

    /**
     * logo半径
     */
    private Integer logoPanelRadius = 15;

    /**
     * 默认logo边框颜色
     */
    private String logoBorderColor = Codectx.DEFAULT_LOGO_BORDER_COLOR;

    /**
     * 默认logo背景颜色
     */
    private String logoBackgroundColor = Codectx.DEFAULT_LOGO_BACKGROUND_COLOR;

    /**
     * 默认logo面板颜色
     */
    private String logoPanelColor = Codectx.DEFAULT_LOGO_PANEL_COLOR;

    public DiyQrAttribute() {
    }

    public DiyQrAttribute(Integer width, Integer length) {
        this.width = width;
        this.height = length;
    }

    public DiyQrAttribute(Integer width, Integer length, String masterColor, String slaveColor) {
        this(width, length);
        this.masterColor = masterColor;
        this.slaveColor = slaveColor;
    }

    public static enum LogoShape {
        RECTANGLE, CIRCLE;
    }
}
