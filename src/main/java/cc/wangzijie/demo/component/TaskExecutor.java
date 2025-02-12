package cc.wangzijie.demo.component;

import cc.wangzijie.demo.ocr.OcrProcessTask;
import cc.wangzijie.demo.snapshot.SnapshotTask;

import java.util.concurrent.*;

public class TaskExecutor {

    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * 定时任务执行的线程池
     */
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(CORE_POOL_SIZE);

    /**
     * 单线程的线程池
     */
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(CORE_POOL_SIZE);

    /**
     * 运行OCR识别处理任务
     *
     * @param task OCR识别处理任务
     * @return Future结果
     */
    public static Future<?> run(OcrProcessTask task) {
        return EXECUTOR_SERVICE.submit(task);
    }

    /**
     * 运行截图定时任务
     *
     * @param task 截图定时任务
     * @return ScheduledFuture结果
     */
    public static ScheduledFuture<?> scheduleWithFixedDelay(SnapshotTask task, int intervalSeconds) {
        return SCHEDULED_EXECUTOR_SERVICE.scheduleWithFixedDelay(task, intervalSeconds, intervalSeconds, TimeUnit.SECONDS);
    }
}
