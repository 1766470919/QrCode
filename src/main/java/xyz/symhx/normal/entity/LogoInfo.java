package xyz.symhx.normal.entity;

import java.io.Serializable;

/**
 * @author Mr.Liu
 * @date 2020/7/14 15:09
 * @description logo信息
 */
public class LogoInfo implements Serializable {
    private static final long serialVersionUID = -6618987596987508038L;

    // 地址
    private String logoPath;
    // 是否远程地址
    private boolean remote;
    // 样式
    public enum LogoShape {
        RECTANGLE, CIRCLE;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }
}
