package cc.wangzijie.demo.main;

import cc.wangzijie.demo.component.TaskExecutor;
import cc.wangzijie.demo.config.SnapshotCameraConfig;
import cc.wangzijie.demo.config.TesseractConfig;
import cc.wangzijie.demo.ocr.OcrProcessTask;
import cc.wangzijie.demo.snapshot.SnapshotCamera;
import cc.wangzijie.demo.snapshot.SnapshotFileQueue;
import cc.wangzijie.demo.snapshot.SnapshotTask;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.Tesseract;

import java.awt.*;
import java.io.File;
import java.util.Map;
import java.util.concurrent.*;

/**
 * OCR任务调度中心
 */
@Slf4j
public class OCRManager {

    /**
     * 截屏工具
     */
    private final SnapshotCamera snapshotCamera;

    /**
     * OCR识别引擎
     */
    private final Tesseract tesseract;

    /**
     * OCR识别框选区域
     */
    private final Map<String, Rectangle> ocrRectMap;

    /**
     * 定时截屏采集任务线程Future
     */
    private ScheduledFuture<?> scheduledFuture;

    /**
     * 定时截屏采集任务时间间隔
     */
    private Integer intervalSeconds;

    private volatile boolean running;

    public OCRManager() throws AWTException {
        this.snapshotCamera = new SnapshotCamera(null);
        this.tesseract = this.initTesseract(null);
        // 默认时间间隔：10s
        this.intervalSeconds = 10;
        this.ocrRectMap = new ConcurrentHashMap<>();
        // 设置运行标志=已停止
        this.running = false;
    }

    public OCRManager(SnapshotCameraConfig cameraConfig, TesseractConfig ocrConfig) throws AWTException {
        this.snapshotCamera = new SnapshotCamera(cameraConfig);
        this.tesseract = this.initTesseract(ocrConfig);
        // 默认时间间隔：10s
        this.intervalSeconds = 10;
        this.ocrRectMap = new ConcurrentHashMap<>();
        // 设置运行标志=已停止
        this.running = false;
    }

    /**
     * 开始运行
     */
    public synchronized void start() {
        if (this.running) {
            return;
        }
        if (this.ocrRectMap.isEmpty()) {
            log.error("请至少添加一个识别区域！");
            return;
        }
        // 开始定时截屏采集
        this.scheduledFuture = TaskExecutor.scheduleWithFixedDelay(new SnapshotTask(this, this.snapshotCamera), intervalSeconds);
        // 设置运行标志=运行中
        this.running = true;
    }

    /**
     * 结束运行
     */
    public synchronized void stop() {
        // 停止截屏定时任务
        this.scheduledFuture.cancel(true);
        // 设置运行标志=已停止
        this.running = false;
    }

    public void setIntervalSeconds(int intervalSeconds) {
        if (this.running) {
            log.error("正在运行中，不可修改采集时间间隔！请先停止运行！");
            return;
        }
        this.intervalSeconds = intervalSeconds;
    }

    public synchronized boolean addOcrRect(String key, Rectangle rect) {
        if (this.running) {
            log.error("正在运行中，不可修改识别区域！请先停止运行！");
            return false;
        }
        Rectangle oldRect = this.ocrRectMap.put(key, rect);
        if (oldRect != null) {
            log.info("更新识别区域：key={} \noldRect={} \nnewRect={}", key, oldRect, rect);
        }
        return true;
    }

    public synchronized boolean removeOcrRect(String key) {
        if (this.running) {
            log.error("正在运行中，不可修改识别区域！请先停止运行！");
            return false;
        }
        Rectangle oldRect = this.ocrRectMap.remove(key);
        if (oldRect != null) {
            log.info("删除识别区域：key={} \noldRect={}", key, oldRect);
        }
        return true;
    }

    public OcrProcessTask createTask(File file) {
        return new OcrProcessTask(this.tesseract, file, this.ocrRectMap);
    }

    private Tesseract initTesseract(TesseractConfig ocrConfig) {
        Tesseract tesseract = new Tesseract();
        if (null == ocrConfig) {
            tesseract.setLanguage(TesseractConfig.DEFAULT_LANGUAGE);
            tesseract.setDatapath(TesseractConfig.DEFAULT_DATAPATH);
        } else {
            tesseract.setLanguage(ocrConfig.getLanguage() == null ? TesseractConfig.DEFAULT_LANGUAGE : ocrConfig.getLanguage());
            tesseract.setDatapath(ocrConfig.getDatapath() == null ? TesseractConfig.DEFAULT_DATAPATH : ocrConfig.getDatapath());
        }
        return tesseract;
    }
}
