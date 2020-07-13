package xyz.symhx.normal.qrcode.common;

import lombok.ToString;

import java.io.Serializable;

@ToString
public abstract class GenericCodeConfig implements Serializable {

    private static final long serialVersionUID = -7484406163096245622L;

    private Integer width;

    private Integer height;

    private String masterColor = Codectx.DEFAULT_CODE_MASTER_COLOR;

    private String slaveColor = Codectx.DEFAULT_CODE_SLAVE_COLOR;

    public GenericCodeConfig(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }

    public GenericCodeConfig(Integer width, Integer height, String masterColor, String slaveColor) {
        this(width,height);
        this.masterColor = masterColor;
        this.slaveColor = slaveColor;
    }

    public Integer getWidth() {
        return width;
    }

    public GenericCodeConfig setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public Integer getHeight() {
        return height;
    }

    public GenericCodeConfig setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public String getMasterColor() {
        return masterColor;
    }

    public GenericCodeConfig setMasterColor(String masterColor) {
        this.masterColor = masterColor;
        return this;
    }

    public String getSlaveColor() {
        return slaveColor;
    }

    public GenericCodeConfig setSlaveColor(String slaveColor) {
        this.slaveColor = slaveColor;
        return this;
    }
}
