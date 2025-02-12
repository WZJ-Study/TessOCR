module cc.wangzijie.ocr {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.boot;
    requires static lombok;
    requires spring.beans;
    requires tess4j;
    requires org.apache.commons.io;
    requires spring.core;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;

    exports cc.wangzijie.ocr.controller;
    opens cc.wangzijie.ocr.controller to javafx.fxml;
    exports cc.wangzijie.ocr;
    opens cc.wangzijie.ocr to javafx.fxml;
}
