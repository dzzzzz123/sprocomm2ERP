package ext.kingdeeERP.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 给图片添加水印的工具类
 * @author lvtian
 * @date 2024/4/30 16:39
 */
public class WatermarkUtil {

    /**
     * 给图片添加水印的方法
     * @param srcImgFile 原文件
     * @param waterMarkStr 水印信息
     * @return 添加水印后的图片对象
     * @throws IOException
     */
    public static void addWatermark(File srcImgFile, String waterMarkStr) throws IOException {
        //将文件对象转化为图片对象
        Image srcImg = ImageIO.read(srcImgFile);
        //获取图片的宽
        int srcImgWidth = srcImg.getWidth(null);
        //获取图片的高
        int srcImgHeight = srcImg.getHeight(null);
        System.out.println("图片的宽:"+srcImgWidth);
        System.out.println("图片的高:"+srcImgHeight);

        BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        // 加水印
        //创建画笔
        Graphics2D g = bufImg.createGraphics();
        //绘制原始图片
        g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
        //-------------------------文字水印 start----------------------------
        //根据图片的背景设置水印颜色
        g.setColor(new Color(255,255,255,128));
        //设置字体  画笔字体样式为微软雅黑，加粗，文字大小为60pt
        g.setFont(new Font("微软雅黑", Font.BOLD, 60));
        //设置水印的坐标(为原图片中间位置)
        int x=(srcImgWidth - getWatermarkLength(waterMarkStr, g)) / 2;
        int y=srcImgHeight / 2;
        //画出水印 第一个参数是水印内容，第二个参数是x轴坐标，第三个参数是y轴坐标
        g.drawString(waterMarkStr, x, y);
        g.dispose();
        //-------------------------文字水印 end----------------------------
        // 输出图片
        FileOutputStream outImgStream = new FileOutputStream(srcImgFile.getPath());
        ImageIO.write(bufImg, "png", outImgStream);
        System.out.println("添加水印完成");
        outImgStream.flush();
        outImgStream.close();
    }

    /**
     * 获取水印文字的长度
     * @param waterMarkContent 水印内容
     * @param g 画笔
     * @return
     */
    public static int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }
}
