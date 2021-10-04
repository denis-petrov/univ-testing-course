package com.app;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Triangle {

    enum Side {FIRST, SECOND, THIRD}

    private final Double first;
    private final Double second;
    private final Double third;

    public Triangle(Double first, Double second, Double third) {
        if (!isTriangleValid(first, second, third)) {
            throw new IllegalArgumentException("Not valid triangle sides");
        }
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public Double getPerimeter() {
        return first + second + third;
    }

    public Double getArea() {
        Double halfPerimeter = getPerimeter() / 2;
        Double area = Math.sqrt(halfPerimeter * (halfPerimeter - first) * (halfPerimeter - second) * (halfPerimeter - third));
        return roundDoubleToScaleTwo(area);
    }

    public String getTriangleType() {
        if (first.equals(second) && second.equals(third)) {
            return "Equilateral triangle";
        }
        if (first.equals(second) || second.equals(third) || first.equals(third)) {
            return "Isosceles triangle";
        }

        return "Default triangle";
    }

    public Double getHeightToSide(Side side) {
        switch (side) {
            case FIRST:
                return roundDoubleToScaleTwo(2 * getArea() / first);
            case SECOND:
                return roundDoubleToScaleTwo(2 * getArea() / second);
            default:
                return roundDoubleToScaleTwo(2 * getArea() / third);
        }
    }

    public Double getMedianToSide(Side side) {
        switch (side) {
            case FIRST:
                return roundDoubleToScaleTwo(Math.sqrt(2 * second * second + 2 * third * third - first * first) / 2);
            case SECOND:
                return roundDoubleToScaleTwo(Math.sqrt(2 * first * first + 2 * third * third - second * second) / 2);
            default:
                return roundDoubleToScaleTwo(Math.sqrt(2 * first * first + 2 * second * second - third * third) / 2);
        }
    }

    private Double roundDoubleToScaleTwo(Double value) {
        return new BigDecimal(value.toString())
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private boolean isTriangleValid(Double first, Double second, Double third) {
        BigDecimal firstBig = BigDecimal.valueOf(first);
        BigDecimal secondBig = BigDecimal.valueOf(second);
        BigDecimal thirdBig = BigDecimal.valueOf(third);
        return firstBig.add(secondBig).compareTo(thirdBig) > 0
                && firstBig.add(thirdBig).compareTo(secondBig) > 0
                && secondBig.add(thirdBig).compareTo(firstBig) > 0;
    }
}
