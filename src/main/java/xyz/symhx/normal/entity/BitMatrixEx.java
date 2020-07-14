package xyz.symhx.normal.entity;

import com.google.zxing.qrcode.encoder.ByteMatrix;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BitMatrixEx {
    /**
     * 实际生成二维码的宽
     */
    private int width;


    /**
     * 实际生成二维码的高
     */
    private int height;


    /**
     * 左白边大小
     */
    private int leftPadding;

    /**
     * 上白边大小
     */
    private int topPadding;

    /**
     * 矩阵信息缩放比例
     */
    private int multiple;

    private ByteMatrix byteMatrix;
}