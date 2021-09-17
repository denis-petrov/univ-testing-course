package com.app.model;

import java.util.Objects;

import static com.app.util.MessageUtil.*;

public class Triangle {

    private final Integer first;
    private final Integer second;
    private final Integer third;

    public Triangle(Integer first, Integer second, Integer third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public String getTriangleType() {
        return (first.equals(second) && second.equals(third) && first.equals(third)) ? EQUILATERAL_TRIANGLE
                : (first.equals(second) || second.equals(third) || first.equals(third)) ? ISOSCELES_TRIANGLE
                : DEFAULT_TRIANGLE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return Objects.equals(first, triangle.first)
                && Objects.equals(second, triangle.second)
                && Objects.equals(third, triangle.third);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }
}
