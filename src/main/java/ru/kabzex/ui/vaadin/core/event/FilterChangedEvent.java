package ru.kabzex.ui.vaadin.core.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;
import ru.kabzex.ui.vaadin.dto.DTOFilter;

@Getter
public class FilterChangedEvent<F extends DTOFilter> extends ComponentEvent<Component> {
    private final F filter;


    public FilterChangedEvent(Component source, F filter) {
        super(source, false);
        this.filter = filter;
    }
}
