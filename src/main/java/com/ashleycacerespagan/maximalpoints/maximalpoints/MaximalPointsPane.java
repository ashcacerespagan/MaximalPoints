// Ashley Cáceres Pagán, MaximalPoints, 06-13-2024
// Used as the main pane in a JavaFX application to visualize and interact with points, highlighting the maximal points
// dynamically as they are added or removed.

package com.ashleycacerespagan.maximalpoints.maximalpoints;

import javafx.scene.layout.Pane;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

// A JavaFX pane for displaying a set of points and connecting maximal points with lines.
public class MaximalPointsPane extends Pane {
    // List to store all points displayed in the pane.
    private final List<Point> points = new ArrayList<>();

    // Constructor to initialize the pane with a list of initial points.
    public MaximalPointsPane(ArrayList<Point> initialPoints) {
        this.points.addAll(initialPoints);
        setPrefSize(500, 500);
        drawPointsAndLines();

        // Set up mouse click events to add or remove points.
        setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            Point point = new Point(x, y);

            if (event.getButton() == MouseButton.PRIMARY) {
                points.add(point); // Add point on primary button click.
            } else if (event.getButton() == MouseButton.SECONDARY) {
                removeNearestPoint(point); // Remove nearest point on secondary button click.
            }
            drawPointsAndLines(); // Redraw points and lines after modification.
        });
    }

    // Removes the nearest point to the given point.
    private void removeNearestPoint(Point point) {
        Point closestPoint = findClosestPoint(point);
        if (closestPoint != null) {
            points.remove(closestPoint);
        }
    }

    // Finds the point closest to the given point.
    private Point findClosestPoint(Point point) {
        Point closestPoint = null;
        double minDistance = Double.MAX_VALUE;

        for (Point p : points) {
            double distance = distance(p, point);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = p;
            }
        }
        return closestPoint;
    }

    // Calculates the Euclidean distance between two points.
    private double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.x() - p1.x(), 2) + Math.pow(p2.y() - p1.y(), 2));
    }

    // Draws all points and lines connecting the maximal points.
    private void drawPointsAndLines() {
        getChildren().clear(); // Clear existing shapes.

        // Draw all points as circles.
        for (Point point : points) {
            Circle circle = new Circle(point.x(), point.y(), 5, Color.BLACK);
            getChildren().add(circle);
        }

        // Find and draw the convex hull (maximal points).
        List<Point> convexHull = computeConvexHull(points);

        // Debug statement to check convex hull points.
        System.out.println("Convex Hull Points:");
        for (Point point : convexHull) {
            System.out.println(point);
        }

        // Draw lines between convex hull points.
        if (convexHull.size() > 1) {
            for (int i = 0; i < convexHull.size(); i++) {
                Point p1 = convexHull.get(i);
                Point p2 = convexHull.get((i + 1) % convexHull.size()); // Wrap around for closing the polygon.
                Line line = new Line(p1.x(), p1.y(), p2.x(), p2.y());
                getChildren().add(line);
                System.out.println("Line drawn from (" + p1.x() + ", " + p1.y() + ") to (" + p2.x() + ", " + p2.y() + ")");
            }
        }
    }

    // Computes the convex hull of a set of points using the Graham scan algorithm.
    private List<Point> computeConvexHull(List<Point> points) {
        // Sort points primarily by x-coordinate, and by y-coordinate if x is the same.
        points.sort((p1, p2) -> {
            if (p1.x() == p2.x()) {
                return Double.compare(p1.y(), p2.y());
            } else {
                return Double.compare(p1.x(), p2.x());
            }
        });

        // Build lower hull.
        List<Point> lowerHull = new ArrayList<>();
        for (Point point : points) {
            while (lowerHull.size() >= 2 &&
                    orientation(lowerHull.get(lowerHull.size() - 2), lowerHull.get(lowerHull.size() - 1), point) <= 0) {
                lowerHull.remove(lowerHull.size() - 1);
            }
            lowerHull.add(point);
        }

        // Build upper hull.
        List<Point> upperHull = new ArrayList<>();
        for (int i = points.size() - 1; i >= 0; i--) {
            Point point = points.get(i);
            while (upperHull.size() >= 2 &&
                    orientation(upperHull.get(upperHull.size() - 2), upperHull.get(upperHull.size() - 1), point) <= 0) {
                upperHull.remove(upperHull.size() - 1);
            }
            upperHull.add(point);
        }

        lowerHull.remove(lowerHull.size() - 1);
        upperHull.remove(upperHull.size() - 1);

        // Combine lower and upper hull to get the full convex hull.
        List<Point> convexHull = new ArrayList<>(lowerHull);
        convexHull.addAll(upperHull);

        return convexHull;
    }

    // Determines the orientation of the ordered triplet (p, q, r).
    private int orientation(Point p, Point q, Point r) {
        double val = (q.y() - p.y()) * (r.x() - q.x()) -
                (q.x() - p.x()) * (r.y() - q.y());
        if (val == 0) return 0; // Collinear
        return (val > 0) ? 1 : -1; // Clockwise or counterclockwise
    }
}