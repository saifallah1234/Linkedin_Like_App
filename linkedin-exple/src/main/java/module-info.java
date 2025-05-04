module Elkhadema.khadema {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.jsoup;
    requires flying.saucer.pdf;
    requires itextpdf;
    requires transitive javafx.graphics;
	requires kernel;
	requires io;
	requires layout;
	requires javafx.media;
	requires java.desktop;
    opens Elkhadema.khadema to javafx.fxml;
    opens Elkhadema.khadema.controller to javafx.fxml;

    exports Elkhadema.khadema;
    exports Elkhadema.khadema.util;
    exports Elkhadema.khadema.controller;
    exports Elkhadema.khadema.domain;
    exports Elkhadema.khadema.DAO.DAOImplemantation;

}
