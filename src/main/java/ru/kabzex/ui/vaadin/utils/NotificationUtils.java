package ru.kabzex.ui.vaadin.utils;

import com.vaadin.flow.component.notification.Notification;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import static com.vaadin.flow.component.notification.Notification.Position.BOTTOM_CENTER;

@UtilityClass
@Slf4j
public class NotificationUtils {
    public static void showError(String desc, Throwable e) {
        showError(desc + " " + e.getMessage(), 6000, e);
    }

    public static void showError(String desc, int duration, Throwable e) {
        showMessage(
                String.format("Ошибка: %s %n (%s)", desc, e.getCause() == null ? "" : e.getCause()),
                duration);
        log.error(desc, e);
    }

    public static void showMessage(String message, int duration) {
        new Notification(
                message,
                duration,
                BOTTOM_CENTER).open();
        log.info(message);
    }

    public static void showMessage(String message) {
        showMessage(message, 6000);
    }
}
