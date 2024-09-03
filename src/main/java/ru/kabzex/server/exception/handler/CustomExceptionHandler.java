package ru.kabzex.server.exception.handler;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.ErrorEvent;
import com.vaadin.flow.server.ErrorHandler;
import lombok.extern.slf4j.Slf4j;
import ru.kabzex.ui.vaadin.utils.NotificationUtils;

@Slf4j
public class CustomExceptionHandler implements ErrorHandler {

    @Override
    public void error(ErrorEvent errorEvent) {
        log.error("Неожиданная ошибка", errorEvent.getThrowable());
        if (UI.getCurrent() != null) {
            UI.getCurrent().access(() ->
                    NotificationUtils.showError("", errorEvent.getThrowable())
            );
        }
    }
}