package xyz.symhx.normal.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;
import xyz.symhx.normal.entity.DiyQrAttribute;
import xyz.symhx.normal.entity.QRCodeInfo;
import xyz.symhx.normal.service.IQrCodeService;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

@RestController
@RequestMapping(value = "/symhx/qr/")
public class QrCodeController {

    @Resource
    IQrCodeService service;

    @RequestMapping(value = "/saleCode", method = RequestMethod.POST)
    public void getSelfQrCode(@RequestBody JSONObject jsonObject, HttpServletResponse response) {
        String linkUrl = jsonObject.getString("linkUrl");
        DiyQrAttribute diyQrAttribute = jsonObject.getObject("diyQrAttribute", DiyQrAttribute.class);

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            BufferedImage image = service.getSelfQrCode(linkUrl, diyQrAttribute);
            ImageIO.write(image, "png", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/qrCode")
    public void getQrCode(@RequestBody QRCodeInfo qrCodeInfo, HttpServletResponse response) {
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/png");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            BufferedImage image = service.getQrCode(qrCodeInfo);
            ImageIO.write(image, "png", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
