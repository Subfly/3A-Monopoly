module Yolopoly {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.json;
    requires firebase.admin;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires com.google.gson;
    requires java.mail;

    opens com.yolopoly to javafx.fxml;
    opens com.yolopoly.controllers to javafx.fxml;
    opens com.yolopoly.models.bases to firebase.admin;
    opens com.yolopoly.managers to firebase.admin;

    exports com.yolopoly;
    exports com.yolopoly.controllers;
    exports com.yolopoly.models.cards;
    exports com.yolopoly.models.bases;
    exports com.yolopoly.enumerations;
    exports com.yolopoly.managers;
    exports com.yolopoly.storage;

}