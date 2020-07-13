package xyz.symhx.normal.qrcode.common;

import java.io.Serializable;

public final class Codectx implements Serializable {

    private static final long serialVersionUID = 7876385594107646257L;

    public static final Integer DEFAULT_CODE_WIDTH = 250;                        // 默认宽度
    public static final Integer DEFAULT_CODE_HEIGHT = 250;                       // 默认高度
    public static final Integer DEFAULT_CODE_MARGIN = 10;                        // 默认外边距
    public static final Integer DEFAULT_CODE_PADDING = 0;                        // 默认内边距
    public static final Integer DEFAULT_CODE_BORDER_SIZE = 0;                    // 默认边框大小
    public static final Integer DEFAULT_CODE_BORDER_RADIUS = 0;                  // 默认边框圆角
    public static final Integer DEFAULT_CODE_BORDER_DASH_GRANULARITY = 5;        // 默认边框虚线粒度
    public static final String  DEFAULT_CODE_BORDER_COLOR = "#808080";           // 默认边框颜色
    public static final String  DEFAULT_CODE_MASTER_COLOR = "#000000";           // 默认主题颜色
    public static final String  DEFAULT_CODE_SLAVE_COLOR = "#FFFFFF";            // 默认从属颜色

    public static final Integer DEFAULT_LOGO_RATIO = 5;                          // 默认logo比率
    public static final Integer DEFAULT_LOGO_BORDER_SIZE = 2;                    // 默认logo边框大小
    public static final Integer DEFAULT_LOGO_PADDING = 0;                        // 默认logo内边距
    public static final Integer DEFAULT_LOGO_MARGIN = 0;                         // 默认logo外边距
    public static final Integer DEFAULT_LOGO_ARCWIDTH = 10;                      // 默认logo弧度
    public static final Integer DEFAULT_LOGO_PANEL_RADIUS = 15;                  // 默认logo圆角
    public static final String  DEFAULT_LOGO_BORDER_COLOR = "#808080";           // 默认log边框颜色
    public static final String  DEFAULT_LOGO_BACKGROUND_COLOR = "#FFFFFF";       // 默认logo背景色
    public static final String  DEFAULT_LOGO_PANEL_COLOR = "#FFFFFF";            // 默认logo面板色

    public static final String IMAGE_TYPE = "png";

    // 边框样式
    public enum BorderStyle {
        SOLID, DASHED;
    }

    // logo形状
    public enum LogoShape{
        RECTANGLE, CIRCLE;
    }
}
