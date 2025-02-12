import cc.wangzijie.demo.snapshot.SnapshotCamera;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GuiCameraTest {

    public static void main(String[] args) throws AWTException, IOException, InterruptedException {
        SnapshotCamera cam = new SnapshotCamera(null);
        cam.snapshot();
        TimeUnit.SECONDS.sleep(10L);
        cam.snapshot();
    }
}
