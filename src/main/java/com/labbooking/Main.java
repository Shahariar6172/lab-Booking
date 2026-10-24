package com.labbooking;

import com.labbooking.model.User;
import com.labbooking.ui.LoginView;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// JavaFX অ্যাপ বানাতে হলে Application ক্লাস extend করতে হয়।
// C++ এর মতো আমরা নিজে while-loop চালাই না — JavaFX নিজেই event loop চালায়,
// আর দরকার হলে আমাদের start() মেথড কল করে।
public class Main extends Application {

    // লগইন করা ইউজার = পুরো অ্যাপের সেশন। একটাই উইন্ডো, তাই static ই যথেষ্ট।
    public static User currentUser;

    private static Scene scene;

    // Stage = উইন্ডো, Scene = ওই উইন্ডোর ভেতরের পুরো কনটেন্ট।
    // @Override মানে parent ক্লাসের মেথডকে replace করছি (C++ এর virtual override এর মতো)।
    @Override
    public void start(Stage stage) {
        stage.setTitle("ICT Lab Resource Booking System");
        // new দিয়ে বানানো object delete করতে হয় না — Java এর garbage collector নিজেই মুছে দেয়।
        scene = new Scene(new LoginView(), 900, 600);
        stage.setScene(scene);
        stage.show();
    }

    // স্ক্রিন বদলানো মানে শুধু Scene এর root বদলানো — নতুন উইন্ডো খোলার দরকার নেই।
    public static void show(Parent view) {
        scene.setRoot(view);
    }

    // লগইন বা রেজিস্টার সফল হলে এখানে আসে, তারপর role অনুযায়ী স্ক্রিন।
    public static void enter(User user) {
        currentUser = user;
        show(home(user));
    }

    public static void logout() {
        currentUser = null;
        show(new LoginView());
    }

    // অস্থায়ী হোম স্ক্রিন — Phase 5 এ student আর Phase 6 এ admin এর আসল স্ক্রিন এর জায়গায় বসবে।
    private static Parent home(User user) {
        Button logout = new Button("Logout");
        logout.setOnAction(e -> logout());
        VBox box = new VBox(12,
                new Label(user.getFullName() + "  (" + user.getRole() + ")"),
                new Label(user.isAdmin()
                        ? "Admin screens: pending requests, issue, return, inventory (Phase 6)."
                        : "Student screens: browse, request cart, my bookings (Phase 5)."),
                logout);
        box.setPadding(new Insets(24));
        return box;
    }

    public static void main(String[] args) {
        // launch() JavaFX চালু করে, তারপর উপরের start() কল করে।
        // এই লাইনটা উইন্ডো বন্ধ না হওয়া পর্যন্ত return করে না।
        launch(args);
    }
}
