package com.app;

import org.junit.jupiter.api.Test;

import static java.lang.Math.round;
import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {

    @Test
    void buildTriangle_NotValidSides_ThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Triangle(0.0, 0.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(-10.0, 2.0, 4.0));
        assertThrows(IllegalArgumentException.class, () -> new Triangle(3.0, 3.0, 40.0));
    }

    @Test
    void getPerimeter() {
        var triangle = new Triangle(1.0, 1.0, 1.0);
        assertEquals(3.0, triangle.getPerimeter());
    }

    @Test
    void getArea() {
        var triangle = new Triangle(1.0, 1.0, 1.0);
        assertEquals(0.43, triangle.getArea());
    }

    @Test
    void getTriangleType() {
        var triangle1 = new Triangle(1.0, 1.0, 1.0);
        assertEquals("Equilateral triangle", triangle1.getTriangleType());

        var triangle2 = new Triangle(2.0, 2.0, 1.0);
        assertEquals("Isosceles triangle", triangle2.getTriangleType());

        var triangle3 = new Triangle(2.0, 2.2, 1.0);
        assertEquals("Default triangle", triangle3.getTriangleType());
    }

    @Test
    void getHeightToSide() {
        var triangle1 = new Triangle(1.0, 1.0, 1.0);
        assertEquals(0.86, triangle1.getHeightToSide(Triangle.Side.FIRST));
        assertEquals(0.86, triangle1.getHeightToSide(Triangle.Side.SECOND));
        assertEquals(0.86, triangle1.getHeightToSide(Triangle.Side.THIRD));

        var triangle2 = new Triangle(2.0, 1.5, 1.5);
        assertEquals(1.12, triangle2.getHeightToSide(Triangle.Side.FIRST));
        assertEquals(1.49, triangle2.getHeightToSide(Triangle.Side.SECOND));
        assertEquals(1.49, triangle2.getHeightToSide(Triangle.Side.THIRD));

        var triangle3 = new Triangle(2.0, 1.8, 1.5);
        assertEquals(1.3, triangle3.getHeightToSide(Triangle.Side.FIRST));
        assertEquals(1.44, triangle3.getHeightToSide(Triangle.Side.SECOND));
        assertEquals(1.73, triangle3.getHeightToSide(Triangle.Side.THIRD));
    }

    @Test
    void getMedianToSide() {
        var triangle1 = new Triangle(1.0, 1.0, 1.0);
        assertEquals(0.87, triangle1.getMedianToSide(Triangle.Side.FIRST));
        assertEquals(0.87, triangle1.getMedianToSide(Triangle.Side.SECOND));
        assertEquals(0.87, triangle1.getMedianToSide(Triangle.Side.THIRD));

        var triangle2 = new Triangle(2.0, 1.5, 1.5);
        assertEquals(1.12, triangle2.getMedianToSide(Triangle.Side.FIRST));
        assertEquals(1.6, triangle2.getMedianToSide(Triangle.Side.SECOND));
        assertEquals(1.6, triangle2.getMedianToSide(Triangle.Side.THIRD));

        var triangle3 = new Triangle(2.0, 1.8, 1.5);
        assertEquals(1.32, triangle3.getMedianToSide(Triangle.Side.FIRST));
        assertEquals(1.52, triangle3.getMedianToSide(Triangle.Side.SECOND));
        assertEquals(1.75, triangle3.getMedianToSide(Triangle.Side.THIRD));
    }
}