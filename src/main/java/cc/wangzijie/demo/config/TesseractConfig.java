package cc.wangzijie.demo.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
public class TesseractConfig {
    public final static String DEFAULT_LANGUAGE = "chi_sim";
    public final static String DEFAULT_DATAPATH = "D:/OCR/Tesseract-OCR/tessdata";

    @Value("${tesseract.language}")
    private String language;

    @Value("${tesseract.datapath}")
    private String datapath;

}
