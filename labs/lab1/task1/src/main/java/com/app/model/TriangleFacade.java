package com.app.model;

import com.app.exception.NotTriangleException;
import com.app.exception.UnsupportedNegativeNumInPowException;
import com.app.util.MessageUtil;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TriangleFacade {

    private static final String POWER_NUMBER = "(-?\\d*)\\^(-?\\d*)";

    private TriangleFacade() {
    }

    public static Triangle buildTriangle(String firstIn, String secondIn, String thirdIn)
            throws NumberFormatException, NotTriangleException {
        Integer first = parseInt(firstIn);
        Integer second = parseInt(secondIn);
        Integer third = parseInt(thirdIn);
        if (!isInputParamsValid(first, second, third) || !isTriangleValid(first, second, third)) {
            throw new NotTriangleException(MessageUtil.NOT_TRIANGLE);
        }
        return new Triangle(first, second, third);
    }

    private static Integer parseInt(String data) {
        Pattern patternPower = Pattern.compile(POWER_NUMBER);
        Matcher matcherPower = patternPower.matcher(data);
        if (matcherPower.find()) {
            var num = Integer.parseInt(matcherPower.group(1));
            var power = Integer.parseInt(matcherPower.group(2));
            if (num < 0 || power < 0)
                throw new UnsupportedNegativeNumInPowException(MessageUtil.NEGATIVE_NUMBER_NOT_SUPPORTED_IN_POW);
            var res = Math.pow(num, power);
            if (res > Integer.MAX_VALUE)
                throw new NumberFormatException();
            return (int) res;
        }
        return Integer.parseInt(data);
    }

    private static boolean isInputParamsValid(Integer first, Integer second, Integer third) {
        return first > 0 && second > 0 && third > 0;
    }

    private static boolean isTriangleValid(Integer first, Integer second, Integer third) {
        BigInteger firstBig = BigInteger.valueOf(first.longValue());
        BigInteger secondBig = BigInteger.valueOf(second.longValue());
        BigInteger thirdBig = BigInteger.valueOf(third.longValue());
        return firstBig.add(secondBig).compareTo(thirdBig) > 0
                && firstBig.add(thirdBig).compareTo(secondBig) > 0
                && secondBig.add(thirdBig).compareTo(firstBig) > 0;
    }
}
