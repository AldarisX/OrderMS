package com.everygamer.control;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 验证码的servlet
 */
@WebServlet(name = "vCode", urlPatterns = "/login/vCode.jpg")
public class VCode extends HttpServlet {
    // 图片高度
    private static final int IMG_HEIGHT = 100;
    // 图片宽度
    private static final int IMG_WIDTH = 40;
    // 验证码长度
    private static final int CODE_LEN = 4;
    // 验证码中所使用到的字符
    private static char[] codeChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("image/jpeg");

        // 用于绘制图片，设置图片的长宽和图片类型（RGB)
        BufferedImage bi = new BufferedImage(IMG_HEIGHT, IMG_WIDTH, BufferedImage.TYPE_INT_RGB);
        // 获取绘图工具
        Graphics graphics = bi.getGraphics();
        graphics.setFont(new Font("Tahoma", Font.BOLD, 15));
        graphics.setColor(new Color(255, 255, 255)); // 使用RGB设置背景颜色
        graphics.fillRect(0, 0, IMG_HEIGHT, IMG_WIDTH); // 填充矩形区域


        StringBuilder captcha = new StringBuilder(); // 存放生成的验证码
        Random random = new Random();
        for (int i = 0; i < CODE_LEN; i++) { // 循环将每个验证码字符绘制到图片上
            int index = random.nextInt(codeChar.length);
            // 随机生成验证码颜色
            graphics.setColor(new Color(random.nextInt(150), random.nextInt(200), random.nextInt(255)));
            // 将一个字符绘制到图片上，并制定位置（设置x,y坐标）
            graphics.drawString(codeChar[index] + "", (i * 20) + 15, 25);
            captcha.append(codeChar[index]);
        }
        // 将生成的验证码code放入sessoin中
        req.getSession().setAttribute("vCode", captcha.toString());
        // 通过ImageIO将图片输出
        ImageIO.write(bi, "JPG", resp.getOutputStream());
    }
}
