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
public class SnapshotCameraConfig {

    public final static String DEFAULT_OUTPUT_FOLDER_PATH = System.getProperty("user.dir");
    public final static String DEFAULT_FILE_NAME_PREFIX = "ScreenShot";
    public final static String DEFAULT_IMAGE_FORMAT = "jpg";

    @Value("${snapshot-camera.output-folder-path}")
    private String outputFolderPath;

    @Value("${snapshot-camera.file-name-prefix}")
    private String fileNamePrefix;

    @Value("${snapshot-camera.image-format}")
    private String imageFormat;

}
