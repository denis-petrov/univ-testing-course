package com.app.model;

import com.app.exception.NotTriangleException;

import java.math.BigInteger;

public final class TriangleFacade {

    private TriangleFacade() {
    }

    public static Triangle buildTriangle(String aIn, String bIn, String cIn) throws NumberFormatException, NotTriangleException {
        BigInteger a = BigInteger.valueOf(Long.parseLong(aIn));
        BigInteger b = BigInteger.valueOf(Long.parseLong(bIn));
        BigInteger c = BigInteger.valueOf(Long.parseLong(cIn));
        if (!isInputParamsValid(a, b, c) || !isTriangleValid(a, b, c)) {
            throw new NotTriangleException();
        }
        return new Triangle(a, b, c);
    }

    private static boolean isInputParamsValid(BigInteger a, BigInteger b, BigInteger c) {
        return a.compareTo(BigInteger.ZERO) > 0 &&
                b.compareTo(BigInteger.ZERO) > 0 &&
                c.compareTo(BigInteger.ZERO) > 0;
    }

    private static boolean isTriangleValid(BigInteger a, BigInteger b, BigInteger c) {
        return a.add(b).compareTo(c) > 0 && a.add(c).compareTo(b) > 0 && b.add(c).compareTo(a) > 0;
    }
}
