package xyz.symhx.normal.entity;

import java.io.Serializable;

/**
 * @author Mr.Liu
 * @date 2020/7/14 15:03
 * description 二维码对象信息
 */
public class QRCodeInfo implements Serializable {

    private static final long serialVersionUID = 4749931985687942006L;
    // 主体信息
    private String content;
    // 外边距
    private Integer margin;
    // 主属颜色
    private String masterColor;
    // 码眼是否同步,默认同步
    private Boolean codeEyeTogether = true;
    // 从属颜色
    private String slaveColor;
    // logo信息
    private LogoInfo logoInfo;
    // 码眼信息
    private QrEyesInfo qrEyesInfo;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMargin() {
        return margin;
    }

    public void setMargin(Integer margin) {
        this.margin = margin;
    }

    public String getMasterColor() {
        return masterColor;
    }

    public void setMasterColor(String masterColor) {
        this.masterColor = masterColor;
    }

    public Boolean getCodeEyeTogether() {
        return codeEyeTogether;
    }

    public void setCodeEyeTogether(Boolean codeEyeTogether) {
        this.codeEyeTogether = codeEyeTogether;
    }

    public String getSlaveColor() {
        return slaveColor;
    }

    public void setSlaveColor(String slaveColor) {
        this.slaveColor = slaveColor;
    }

    public LogoInfo getLogoInfo() {
        return logoInfo;
    }

    public void setLogoInfo(LogoInfo logoInfo) {
        this.logoInfo = logoInfo;
    }

    public QrEyesInfo getQrEyesInfo() {
        return qrEyesInfo;
    }

    public void setQrEyesInfo(QrEyesInfo qrEyesInfo) {
        this.qrEyesInfo = qrEyesInfo;
    }
}
