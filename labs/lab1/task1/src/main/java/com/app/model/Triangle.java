package com.app.model;

import java.math.BigInteger;
import java.util.Objects;

import static com.app.util.MessageUtil.*;

public class Triangle {

    private final BigInteger a;
    private final BigInteger b;
    private final BigInteger c;

    public Triangle(BigInteger a, BigInteger b, BigInteger c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public String getTriangleType() {
        return (a.equals(b) && b.equals(c) && a.equals(c)) ? EQUILATERAL_TRIANGLE
                : (a.equals(b) || b.equals(c) || a.equals(c)) ? ISOSCELES_TRIANGLE
                : DEFAULT_TRIANGLE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return Objects.equals(a, triangle.a) && Objects.equals(b, triangle.b) && Objects.equals(c, triangle.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }
}
