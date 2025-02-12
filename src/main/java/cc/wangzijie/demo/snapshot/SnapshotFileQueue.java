package cc.wangzijie.demo.snapshot;

import java.io.File;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 截屏图片文件队列
 */
public class SnapshotFileQueue {

    /**
     * 截屏图片文件队列
     */
    private final Queue<File> snapshotQueue;

    public SnapshotFileQueue() {
        this.snapshotQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     * 截屏图片文件加入队列
     *
     * @param snapshot 截屏图片文件
     * @return 操作是否成功
     */
    public synchronized boolean offer(File snapshot) {
        return this.snapshotQueue.offer(snapshot);
    }

    /**
     * 获取首个截屏图片文件，不出队列
     *
     * @return 截屏图片文件
     */
    public synchronized File peek() {
        return this.snapshotQueue.peek();
    }

    /**
     * 获取首个截屏图片文件，出队列
     *
     * @return 截屏图片文件
     */
    public synchronized File poll() {
        return this.snapshotQueue.poll();
    }


}
