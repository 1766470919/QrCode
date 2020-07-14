package xyz.symhx.normal.entity;

import java.io.Serializable;

/**
 * @author Mr.Liu
 * @date 2020/7/14 15:18
 * @description 二维码眼信息
 */
public class QrEyesInfo implements Serializable {

    private static final long serialVersionUID = 8008389417280533903L;

    // 边框颜色
    private String borderColor;
    // 点颜色
    private String pointColor;

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getPointColor() {
        return pointColor;
    }

    public void setPointColor(String pointColor) {
        this.pointColor = pointColor;
    }
}
