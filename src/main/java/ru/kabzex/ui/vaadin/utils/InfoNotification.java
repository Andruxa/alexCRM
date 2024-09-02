package ru.kabzex.ui.vaadin.utils;

import com.vaadin.flow.component.notification.Notification;
import lombok.experimental.UtilityClass;

import static com.vaadin.flow.component.notification.Notification.Position.BOTTOM_CENTER;

@UtilityClass
public class InfoNotification {
    public static void showInfo(String desc) {
        new Notification(
                String.format("Внимание: %s", desc),
                6000,
                BOTTOM_CENTER).open();
    }
}
