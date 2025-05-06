// Ashley Cáceres Pagán, MaximalPoints, 06-13-2024
// Used to launch the JavaFX application, set up the scene, and provide initial data for the points to be displayed and
// interacted with.

package com.ashleycacerespagan.maximalpoints.maximalpoints;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

// Entry point for the Maximal Points application.
public class Main extends Application {
    // Launches the application.
    public static void main(String[] args) {
        launch(args);
    }

    // Initializes the JavaFX stage and scene.
    @Override
    public void start(Stage primaryStage) {
        ArrayList<Point> points = readPointsFromFile();

        MaximalPointsPane pointsPane = new MaximalPointsPane(points);
        Scene scene = new Scene(pointsPane);

        primaryStage.setTitle("Maximal Points");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Reads the points from a file and returns them as an ArrayList.
    private ArrayList<Point> readPointsFromFile() {
        ArrayList<Point> points = new ArrayList<>();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("points");
            if (inputStream != null) {
                Scanner scanner = new Scanner(inputStream);
                while (scanner.hasNext()) {
                    double x = scanner.nextDouble();
                    double y = scanner.nextDouble();
                    points.add(new Point(x, y));
                }
                scanner.close();
            } else {
                System.err.println("File not found: points");
                // Alternatively, you could display an error dialog to the user.
            }
        } catch (Exception e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            // Alternatively, you could display an error dialog to the user.
        }
        return points;
    }
}