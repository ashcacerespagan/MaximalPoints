// Ashley Cáceres Pagán, MaximalPoints, 06-13-2024
// Used to represent points in a 2D space for graphical applications, particularly to determine and visualize maximal
// points.

package com.ashleycacerespagan.maximalpoints.maximalpoints;

// Represents a point in 2D space and implements Comparable to allow sorting by x-coordinate.
public record Point(double x, double y) implements Comparable<Point> {

    // Compares two points primarily by their x-coordinate.
    @Override
    public int compareTo(Point other) {
        return Double.compare(this.x, other.x);
    }

    // Returns a string representation of the point.
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}