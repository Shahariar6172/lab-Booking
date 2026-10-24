package com.labbooking;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

// JavaFX অ্যাপ বানাতে হলে Application ক্লাস extend করতে হয়।
// C++ এর মতো আমরা নিজে while-loop চালাই না — JavaFX নিজেই event loop চালায়,
// আর দরকার হলে আমাদের start() মেথড কল করে।
public class Main extends Application {

    // Stage = উইন্ডো, Scene = ওই উইন্ডোর ভেতরের পুরো কনটেন্ট।
    // @Override মানে parent ক্লাসের মেথডকে replace করছি (C++ এর virtual override এর মতো)।
    @Override
    public void start(Stage stage) {
        stage.setTitle("ICT Lab Resource Booking System");
        // new দিয়ে বানানো object delete করতে হয় না — Java এর garbage collector নিজেই মুছে দেয়।
        stage.setScene(new Scene(new StackPane(new Label("Lab Booking")), 900, 600));
        stage.show();
    }

    public static void main(String[] args) {
        // launch() JavaFX চালু করে, তারপর উপরের start() কল করে।
        // এই লাইনটা উইন্ডো বন্ধ না হওয়া পর্যন্ত return করে না।
        launch(args);
    }
}
