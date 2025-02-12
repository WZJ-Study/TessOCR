package cc.wangzijie.demo.snapshot;

import cc.wangzijie.demo.component.TaskExecutor;
import cc.wangzijie.demo.main.OCRManager;
import cc.wangzijie.demo.ocr.OcrProcessTask;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@Slf4j
public class SnapshotTask implements Runnable {

    /**
     * OCR任务调度中心
     */
    private final OCRManager ocrManager;

    /**
     * 截屏工具
     */
    private final SnapshotCamera snapshotCamera;

    public SnapshotTask(OCRManager ocrManager, SnapshotCamera snapshotCamera) {
        this.ocrManager = ocrManager;
        this.snapshotCamera = snapshotCamera;
    }

    @Override
    public void run() {
        try {
            File file = this.snapshotCamera.snapshot();
            if (null != file && file.exists()) {
                OcrProcessTask task = this.ocrManager.createTask(file);
                log.info("==== 截屏定时任务 ==== 截屏成功，图片文件【{}】已创建处理任务！", file.getName());
                TaskExecutor.run(task);
            } else {
                log.error("==== 截屏定时任务 ==== 截屏失败，截屏图片文件不存在！");
            }
        } catch (IOException e) {
            log.error("==== 截屏定时任务 ==== 截屏失败，捕获到异常！", e);
        }
    }

}
