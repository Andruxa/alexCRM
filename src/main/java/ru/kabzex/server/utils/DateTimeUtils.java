package ru.kabzex.server.utils;

import com.vaadin.flow.component.datepicker.DatePicker;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import ru.kabzex.ui.vaadin.utils.NotificationUtils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

@UtilityClass
@Slf4j
public class DateTimeUtils {

    public static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";
    public static final String DATE_PATTERN = "dd.MM.yyyy";
    public static final String TIME_PATTERN = "HH:mm";
    public static final LocalDate DEFAULT_DATE = LocalDate.now();
    public static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.now();


    public static boolean isBetween(LocalTime start, LocalTime end, LocalTime time) {
        if (start.isAfter(end)) {
            return !time.isBefore(start) || !time.isAfter(end);
        } else {
            return !time.isBefore(start) && !time.isAfter(end);
        }
    }

    public static boolean isNationalHoliday(LocalDate date) {
        //after new year
        switch (date.getMonth()) {
            case JANUARY:
                return date.getDayOfMonth() < 9;
            case FEBRUARY:
                return date.getDayOfMonth() == 23;
            case MARCH:
                return date.getDayOfMonth() == 8;
            case MAY:
                return date.getDayOfMonth() == 1
                        || date.getDayOfMonth() == 9;
            case JUNE:
                return date.getDayOfMonth() == 12;
            case NOVEMBER:
                return date.getDayOfMonth() == 4;
            case DECEMBER:
                return date.getDayOfMonth() == 31;
            default:
                return false;
        }
    }

    public static LocalDate parseLocalDateOrNull(String input, String format) {
        try {
            return StringUtils.hasText(input) ? LocalDate.parse(input, DateTimeFormatter.ofPattern(format)) : null;
        } catch (DateTimeParseException e) {
            log.error("Error while date parsing", e);
            NotificationUtils.showError(String.format("Не удалось преобразовать строку в дату:%s", input), e);
            return null;
        }
    }

    public static String getAsSimpleDateString(LocalDate input) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return input == null ? "" : input.format(df);
    }

    public static String getAsSimpleTimeString(LocalTime input) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(TIME_PATTERN);
        return input == null ? "" : input.format(df);
    }

    public static String getAsSimpleDateTimeString(LocalDateTime input) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        return input == null ? "" : input.format(df);
    }

    public static DatePicker.DatePickerI18n getRussianDateFormat() {
        DatePicker.DatePickerI18n i18n = new DatePicker.DatePickerI18n();
        i18n.setFirstDayOfWeek(1);
        i18n.setMonthNames(Arrays.asList("Январь",
                "Февраль",
                "Март",
                "Апрель",
                "Май",
                "Июнь",
                "Июль",
                "Август",
                "Сентябрь",
                "Октябрь",
                "Ноябрь",
                "Декабрь"
        ));
        i18n.setWeekdaysShort(Arrays.asList("вс.",
                "пн.",
                "вт.",
                "ср.",
                "чт.",
                "пт.",
                "сб."));
        i18n.setWeekdays(Arrays.asList("воскресенье",
                "понедельник",
                "вторник",
                "среда",
                "четверг",
                "пятница",
                "суббота"));
        i18n.setDateFormat(DATE_PATTERN);
        return i18n;
    }

    public static boolean isWeekendDate(LocalDate date) {
        return DayOfWeek.SUNDAY.equals(date.getDayOfWeek()) || DayOfWeek.SATURDAY.equals(date.getDayOfWeek());
    }

    public static boolean isWorkTime(LocalTime time) {
        return LocalTime.of(8, 59).isBefore(time) && LocalTime.of(18, 1).isAfter(time);
    }

    public static List<LocalDate> getHolidays(int year) {
        return List.of(LocalDate.of(year, Month.JANUARY, 1),
                LocalDate.of(year, Month.JANUARY, 2),
                LocalDate.of(year, Month.JANUARY, 3),
                LocalDate.of(year, Month.JANUARY, 4),
                LocalDate.of(year, Month.JANUARY, 5),
                LocalDate.of(year, Month.JANUARY, 6),
                LocalDate.of(year, Month.JANUARY, 7),
                LocalDate.of(year, Month.JANUARY, 8),
                LocalDate.of(year, Month.FEBRUARY, 23),
                LocalDate.of(year, Month.MARCH, 8),
                LocalDate.of(year, Month.MAY, 1),
                LocalDate.of(year, Month.MAY, 9),
                LocalDate.of(year, Month.JUNE, 12),
                LocalDate.of(year, Month.NOVEMBER, 4),
                LocalDate.of(year, Month.DECEMBER, 31));
    }

    public static List<String> getHolidaysAsDDMM() {
        return List.of("0101",
                "0201",
                "0301",
                "0401",
                "0501",
                "0601",
                "0701",
                "0801",
                "2302",
                "0803",
                "0105",
                "0905",
                "1206",
                "0411",
                "3112");
    }
}
