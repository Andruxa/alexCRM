package ru.kabzex.ui.vaadin.core.page;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import lombok.Getter;
import ru.kabzex.server.exception.NoHandlerException;
import ru.kabzex.server.exception.handler.CustomExceptionHandler;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractPagePart;

import java.util.Objects;
import java.util.stream.Stream;

@Getter
public abstract class AbstractPage<H extends AbstractPagePart,
        B extends AbstractPagePart,
        F extends AbstractPagePart> extends VerticalLayout {

    private final H header;
    private final B body;
    private final F footer;

    protected AbstractPage() {
        setSizeFull();
        header = initHeader();
        body = initBody();
        footer = initFooter();
        Stream.of(header, body, footer)
                .filter(Objects::nonNull)
                .forEach(this::add);
    }


    protected abstract F initFooter();

    protected abstract B initBody();

    protected abstract H initHeader();

    public <T extends ComponentEvent<?>> void handle(T t) {
        throw new NoHandlerException("Сценарий не реализован");
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        VaadinSession.getCurrent().setErrorHandler(new CustomExceptionHandler());
    }
}
