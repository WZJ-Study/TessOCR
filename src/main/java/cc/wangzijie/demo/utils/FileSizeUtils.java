package cc.wangzijie.demo.utils;

import java.io.File;
import java.text.DecimalFormat;

public class FileSizeUtils {

    public static String displayFileSize(File file) {
        if (null == file) {
            return "/";
        }
        return formatFileSize(file.length());
    }

    /**
     * 转换文件大小
     *
     * @param fileSize 文件大小
     */
    public static String formatFileSize(long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String wrongSize = "0B";
        if (fileSize == 0) {
            return wrongSize;
        }
        String _fs = "";
        if (fileSize < 1024) {
            _fs = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            _fs = df.format((double) fileSize / 1024) + "KB";
        } else if (fileSize < 1073741824) {
            _fs = df.format((double) fileSize / 1048576) + "MB";
        } else {
            _fs = df.format((double) fileSize / 1073741824) + "GB";
        }
        return _fs;
    }

}
