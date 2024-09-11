package ru.kabzex.server.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@UtilityClass
@Slf4j
public class StringUtils {
    public static boolean isEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean notEmpty(String value) {
        return !isEmpty(value);
    }

    public static String emptyOrValue(String value) {
        return emptyOrValueWithPostfix(value, "");
    }

    public static String emptyOrValueWithPostfix(String value, String postfix) {
        return isEmpty(value) ? "" : String.format("%s%s", value, postfix);
    }

    public static String emptyOrValueWithPrefix(String prefix, String value) {
        return isEmpty(value) ? "" : String.format("%s%s", prefix, value);
    }

    public static String formatByRule(double value) {
        return String.format(NumberUtils.formatByRule(value), value);
    }

    public static String formatByRule(BigDecimal value) {
        return formatByRule(value.doubleValue());
    }

    public static String normalise(String fileName) {
        return fileName.replaceAll("[\\\\/:*?\"<>|]", "").trim();
    }

    public static String likeInUpperCase(String value) {
        return String.format("%%%s%%", value).toUpperCase();
    }

    public static String replaceEndZero(String text) {
        while (text.length() > 0 && text.lastIndexOf("0") == text.length() - 1) {
            text = text.replaceFirst("(?s)(.*)0", "$1");
        }
        return text;
    }
}
