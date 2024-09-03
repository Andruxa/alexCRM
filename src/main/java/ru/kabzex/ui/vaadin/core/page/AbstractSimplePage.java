package ru.kabzex.ui.vaadin.core.page;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import lombok.Getter;
import ru.kabzex.server.exception.NoHandlerException;
import ru.kabzex.server.exception.handler.CustomExceptionHandler;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
public abstract class AbstractSimplePage<H, B, F> extends VerticalLayout {

    private final H header;
    private final B body;
    private final F footer;

    protected AbstractSimplePage() {
        setSizeFull();
        header = initHeader();
        body = initBody();
        footer = initFooter();
        Stream.of(header, body, footer)
                .filter(Objects::nonNull)
                .map(Component.class::cast)
                .forEach(this::add);
    }

    protected abstract F initFooter();

    protected abstract B initBody();

    protected abstract H initHeader();

    public <T extends ComponentEvent<?>> void handle(T t) {
        throw new NoHandlerException("unknown event");
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        VaadinSession.getCurrent().setErrorHandler(new CustomExceptionHandler());
    }
}
