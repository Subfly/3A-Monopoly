module Yolopoly {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.json;
    requires firebase.admin;
    requires com.google.auth;
    requires com.google.auth.oauth2;

    opens com.yolopoly to javafx.fxml;
    opens com.yolopoly.controllers to javafx.fxml;

    exports com.yolopoly;
    exports com.yolopoly.controllers;
    exports com.yolopoly.models.cards;
    exports com.yolopoly.models.bases;
    exports com.yolopoly.enumerations;
    exports com.yolopoly.managers;
    exports com.yolopoly.storage;

}