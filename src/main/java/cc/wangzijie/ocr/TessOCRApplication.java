package cc.wangzijie.ocr;

import cc.wangzijie.springboot.SpringHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class TessOCRApplication extends Application {

    private static String[] args;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TessOCRApplication.class.getResource("MainWidow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 1024);
        stage.setTitle("PT.OCR");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        TessOCRApplication.args = args;
        launch(args);
    }

    @Override
    public void init() {
        // 启动springboot
        SpringHelper.init(SpringApplication.run(TessOCRApplication.class, args));
    }

    @Override
    public void stop() {
        // 关闭springboot
        SpringHelper.stop();
    }
}
