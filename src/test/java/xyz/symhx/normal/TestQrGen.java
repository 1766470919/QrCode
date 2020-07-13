package xyz.symhx.normal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import xyz.symhx.normal.qrcode.common.QrCodeGenerator;
import xyz.symhx.normal.qrcode.common.QrEyesFormat;
import xyz.symhx.normal.qrcode.common.SimpleQrCodeGenerator;
import xyz.symhx.normal.tools.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

public class TestQrGen {

	private static String content = "https://www.belinke.com";

	private QrCodeGenerator generator = new SimpleQrCodeGenerator();

	private String localLogoPath;

	@Before
	public void init() {
		URL url = this.getClass().getClassLoader().getResource("mates/AodaCat-1.png");
		this.localLogoPath = url.getFile();
	}

	@Test
	public void testDefault() throws IOException {
		Assert.assertTrue(generator.generate(content).toFile("F:\\AodaCat_default.png"));
		testLocalLogo();
		testRemoteLogo();
		testCustomConfig();
		testCustomCodeEyes();
	}

	/**
	 * 添加本地logo
	 * @throws IOException
	 */
	@Test
	public void testLocalLogo() throws IOException {
		boolean success = generator.setLogo(this.localLogoPath).generate(content).toFile("F:\\AodaCat_local_logo.png");
		Assert.assertTrue(success);
	}

	/**
	 * 添加在线logo
	 * @throws IOException
	 */
	@Test
	public void testRemoteLogo() throws IOException {
		generator.setRemoteLogo("http://www.demlution.com/site_media/media/photos/2014/11/06/3JmYoueyyxS4q4FcxcavgJ.jpg");
		Assert.assertTrue(generator.generate("https://www.apple.com/cn/").toFile("i:\\Apple_remote_logo.png"));
	}

	/**
	 * 自定义二维码配置
	 * @throws IOException
	 */
	@Test
	public void testCustomConfig() throws IOException {
		generator.getQrCodeConfig()
			.setBorderSize(2)
			.setPadding(10)
			.setMasterColor("#00BFFF")
			.setLogoBorderColor("#B0C4DE");
		Assert.assertTrue(generator.setLogo(this.localLogoPath).generate(content).toFile("F:\\AodaCat_custom.png"));
	}
	
	/**
	 * 自定义二维码码眼颜色
	 * @throws IOException
	 */
	@Test
	public void testCustomCodeEyes() throws IOException {
		generator.getQrCodeConfig()
			.setMasterColor("#778899")
			.setLogoBorderColor("#778899")
			.setCodeEyesPointColor("#BC8F8F")
			.setCodeEyesFormat(QrEyesFormat.DR2_BORDER_R_POINT);
		Assert.assertTrue(generator.setLogo(this.localLogoPath).generate(content).toFile("F:\\AodaCat_custom_eyes.png"));
	}
	
	/**
	 * 写入输出流
	 * @throws IOException
	 */
	@Test
	public void testWriteToStream() throws IOException {
		OutputStream ous = null;
		try {
			ous = new FileOutputStream("F:\\Qrcode_out.png");
			Assert.assertTrue(generator.generate(content).toStream(ous));
		} finally {
			IOUtils.closeQuietly(ous);
		}
	}
	
	@Test
	public void testGetImage() throws IOException {
		BufferedImage image = generator.generate(content).getImage();
		ImageIO.write(image, "png", new File("F:\\Qrcode_out.png"));
	}

}