package com.guli.common.utils;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.InputStream;

/**
 * @Description:
 * @Author: Ryan_Wuyx
 * @Date: 2023/10/23 15:57
 */
@Configuration
@Slf4j
public class QRCodeUtil {

    // 设置二维码的参数，常用的可设置参数包括8项：
    // 长、宽、颜色、底色、logo、边距、版本、纠错级别
    private QrConfig getQrConfig() {
        QrConfig qrConfig = new QrConfig();
        // 不设置的话，长宽默认都是300
        qrConfig.setWidth(400);
        qrConfig.setHeight(400);
        // ForeColor是二维码的颜色，默认为黑；BackColor为底色，默认为白
        qrConfig.setForeColor(Color.BLUE);
        qrConfig.setBackColor(Color.LIGHT_GRAY);
        // 边距指的是二维码本身与二维码的框框之间的距离，可设1-4，默认为2
        qrConfig.setMargin(3);
        // 纠错级别由低到高为L、M、Q、H，默认为M。级别越低，像素块越大，可以远距离
        // 识别，但是不能遮挡；反之，级别越高，像素块越小，像素越密集，允许遮挡一部分
        qrConfig.setErrorCorrection(ErrorCorrectionLevel.H);
        // logo指的是二维码中间的小图片，可为java.awt.Image类型、File、String路径
//        if(file != null) {
//            qrConfig.setImg(file);
//        }
        // version表示二维码中的信息量，可为0-40之间的整数，默认为空
        qrConfig.setQrVersion(20);

        return  qrConfig;
    }

    // TODO 使用ImageUtil.scale()修改logo尺寸比例
    //  （当logo长宽比例非常悬殊时，会造成二维码部分遮挡，从而影响识别）
    // 只设置二维码尺寸与logo
    private QrConfig customConfig(Object obj) {
        QrConfig qrConfig = new QrConfig();
        qrConfig.setWidth(400);
        qrConfig.setHeight(400);
        // obj不需要判空，因为关键字instanceof里面包含了判空逻辑
        // 即 null instanceof Object 结果为false
        if(obj instanceof File) {
            qrConfig.setImg((File) obj);
        }
        if(obj instanceof Image) {
            qrConfig.setImg((Image) obj);
        }
        if(obj instanceof String) {
            qrConfig.setImg((String) obj);
        }

        return qrConfig;
    }

    // 根据上传的logo生成二维码图片流并返回到调用方
    public void generateQRCodeToStream(String content, File file, HttpServletResponse response) {
        if(content != null && content.length() > 0) {
            QrConfig config = this.customConfig(file);
            try {
                // 设置响应body的内容类型为图片，否则前端会乱码
                response.setContentType("image/png");
                // ImageType参数表示生成的文件的后缀名
                QrCodeUtil.generate(content, config, "png", response.getOutputStream());
            } catch (Exception e) {
                log.info("二维码创建失败 >>>>>>>> ");
                e.printStackTrace();
            }
        }
    }

    // 识别二维码
    public String decode(InputStream inputStream) {
        // 当二维码为URL等内容时，需要转为string表示
        return String.valueOf(QrCodeUtil.decode(inputStream));
    }

}
