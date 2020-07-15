package xyz.symhx.normal;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.symhx.normal.entity.QrCodeGenWrapper;
import xyz.symhx.normal.entity.QrCodeOptions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SpringBootTest
class NormalApplicationTests {

	@Test
	void contextLoads() {
	}

	private String content = "https://liuyueyi.github.io/hexblog/2018/03/23/mysql之锁与事务详解/";

	@Test
	public void testGenWxQrcode() {
		String logo = "I:\\WorkSpace\\project\\demo\\src\\test\\resources\\mates\\pig.png";
		String bg = "I:\\1.jpg";

		int size = 300;
		try {
			BufferedImage img = QrCodeGenWrapper.putContent(content)
					.setHeight(600)
					.setWidth(600)
		 			.setDrawPreColor(0xff008e59)
					.setErrorCorrection(ErrorCorrectionLevel.H)
					.setDrawStyle(QrCodeOptions.DrawStyle.CIRCLE)
					.setDrawEnableScale(false)
					.asBufferedImage();
			ImageIO.write(img, "png", new File("I:\\style.png"));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
}
