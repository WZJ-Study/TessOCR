package cc.wangzijie.demo.snapshot;

import cc.wangzijie.demo.config.SnapshotCameraConfig;
import cc.wangzijie.demo.utils.DateFormat;
import cc.wangzijie.demo.utils.DateUtils;
import cc.wangzijie.demo.utils.FileSizeUtils;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

@Slf4j
public class SnapshotCamera {


    private final String outputFolderPath;
    private final String fileNamePrefix;
    private final String imageFormat;
    private Robot robot;
    private Rectangle screenRect;

    public SnapshotCamera(SnapshotCameraConfig cameraConfig) throws AWTException {
        if (cameraConfig == null) {
            this.outputFolderPath = SnapshotCameraConfig.DEFAULT_OUTPUT_FOLDER_PATH;
            this.fileNamePrefix = SnapshotCameraConfig.DEFAULT_FILE_NAME_PREFIX;
            this.imageFormat = SnapshotCameraConfig.DEFAULT_IMAGE_FORMAT;
        } else {
            this.outputFolderPath = cameraConfig.getOutputFolderPath() == null ? SnapshotCameraConfig.DEFAULT_OUTPUT_FOLDER_PATH : cameraConfig.getOutputFolderPath();
            this.fileNamePrefix = cameraConfig.getFileNamePrefix() == null ? SnapshotCameraConfig.DEFAULT_FILE_NAME_PREFIX : cameraConfig.getFileNamePrefix();
            this.imageFormat = cameraConfig.getImageFormat() == null ? SnapshotCameraConfig.DEFAULT_IMAGE_FORMAT : cameraConfig.getImageFormat();
        }
        log.info("==== 初始化 GuiCamera ==== 文件输出目录: {}\n文件命名前缀：{}\n图片格式：{}", this.outputFolderPath, this.fileNamePrefix, this.imageFormat);
        this.initScreenRect();
        this.initRobot();
    }

    private void initScreenRect() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        log.info("==== 初始化 截屏区域 ==== 屏幕尺寸: {}", d);
        this.screenRect = new Rectangle(0, 0, d.width, d.height);
    }

    private void initRobot() throws AWTException {
        this.robot = new Robot();
    }

    public File snapshot() throws IOException {
        // 截屏
        BufferedImage screenshot = this.robot.createScreenCapture(this.screenRect);
        // 保存到文件
        File file = this.prepareFile();
        ImageIO.write(screenshot, imageFormat, file);
        log.info("==== 截屏 GuiCamera ==== 截屏文件大小：{} \n保存为：{}", FileSizeUtils.displayFileSize(file), file.getAbsolutePath());
        return file;
    }

    private File prepareFile() {
        String nowStr = DateUtils.nowStr(DateFormat.DUMMY_CODE);
        String fileName = String.format("%s-%s.%s", this.fileNamePrefix, nowStr, this.imageFormat);
        String filePath = String.format("%s%s%s", this.outputFolderPath, File.separator, fileName);
        return new File(filePath);
    }
}
