package ru.kabzex.server.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

import static java.lang.Math.pow;

@UtilityClass
public class NumberUtils {
    public static final String MEASUREMENT_VALUE_PATTERN = "^([<>+-]?\\d*\\.?\\d*)$";
    public static final String SIMPLE_VALUE_PATTERN = "[<>+\\d*\\.,-]*";
    public static final String SIMPLE_VALUE_PATTERN_EXT = "[±<>+\\d*\\.,-]*";
    public static final String SIMPLE_VALUE_PATTERN_TITLE = "Допустим ввод чисел и ,.+-<>";
    public static final String SIMPLE_VALUE_PATTERN_TITLE_EXT = "Допустим ввод чисел и ±,.+-<>";
    public static final String SIMPLE_VALUE_PATTERN_CHARS = "[,.+-<>0-9]";
    public static final String PERCENT_VALUE_PATTERN = "[%\\d*]*";
    public static final String PERCENT_VALUE_PATTERN_TITLE = "Допустим ввод чисел и знака %";
    public static final String PERCENT_VALUE_PATTERN_CHARS = "[0-9%]";

    public static Double randomDouble(double min, double max) {
        return round(ThreadLocalRandom.current().nextDouble(min, max),
                countAfterDot(max - min) + 1);
    }

    public static Long randomLong(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    public static int randomInt(int min, int max) {
        return min == max ? min : ThreadLocalRandom.current().nextInt(min, max);
    }

    public static int countAfterDot(double value) {
        int i = 0;
        while (value * pow(10, i) % 1 != 0) {
            i++;
        }
        return i;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static Optional<Float> parseNumericValue(String input) {
        return Optional.ofNullable(input)
                .map(e -> e.replaceAll("[<>%]", ""))
                .map(e -> e.replace(",", "."))
                .filter(Predicate.not(String::isEmpty))
                .map(Float::parseFloat);
    }


    public static String valueOrEmpty(Integer val) {
        return val == null ? "" : val.toString();
    }

    public static String valueOrDefault(Integer val, int def) {
        return val == null ? String.valueOf(def) : val.toString();
    }

    public static String getRoundFormat(Float value) {
        if (value > 10) {
            return "%0.f";
        } else {
            return "%1.f";
        }
    }

    public static String formatByRule(double value) {
        if (value >= 10) {
            return "%.0f";
        } else {
            String format = "%." + getTwoDigitsCount(value) + "f";
            String r = String.format(format, value);
            if (parseDouble(r) >= 10) return "%.0f";
            else return format;
        }
    }

    public static String formatByRule(BigDecimal value) {
        return formatByRule(value.doubleValue());
    }

    public static int getTwoDigitsCount(double value) {
        if (value >= 1d) {
            return 1;
        } else {
            int i = 0;
            while (value * pow(10, i) <= 1) {
                i++;
            }
            i++;
            return i;
        }
    }

    public static double parseDouble(String s) {
        return Optional.ofNullable(s)
                .map(e -> e.replaceAll("[<>%]", ""))
                .map(e -> e.replaceAll(",", "."))
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .map(Double::parseDouble).orElse(0d);
    }

    public static BigDecimal parseBigDecimal(String s) {
        return Optional.ofNullable(s)
                .map(e -> e.replaceAll("[<>%]", ""))
                .map(e -> e.replace(",", "."))
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .map(BigDecimal::new)
                .orElse(BigDecimal.ZERO);
    }
}
