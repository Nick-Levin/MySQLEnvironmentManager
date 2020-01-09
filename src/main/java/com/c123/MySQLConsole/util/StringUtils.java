package com.c123.MySQLConsole.util;

import java.util.Random;
import java.util.function.Function;

public final class StringUtils {

    private StringUtils() {}

    public static final Function<String, Character> getRandomChar =
            str -> str.charAt(new Random().nextInt(str.length()));

}
