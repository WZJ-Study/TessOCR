import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tess4jTest {

    public static void main(String[] args) throws TesseractException, IOException {
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("chi_sim");
        tesseract.setDatapath("D:\\OCR\\Tesseract-OCR\\tessdata");
        File file = new File("D:\\OCR\\ocr_a.png");
        BufferedImage image = ImageIO.read(file);
        int height = image.getHeight();
        int width = image.getWidth();
        System.out.println("height:" + height + " width:" + width);

//        String output = tesseract.doOCR(file);
//        System.out.println(output);

        // x,y是以左上角为原点，width和height是以xy为基础
        Rectangle rect = new Rectangle(32, 8, 100, 18);
        String result = tesseract.doOCR(image, rect);
        System.out.println(result);
    }

}
