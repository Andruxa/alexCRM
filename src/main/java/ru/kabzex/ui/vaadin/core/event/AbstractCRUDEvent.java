package ru.kabzex.ui.vaadin.core.event;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;
import ru.kabzex.ui.vaadin.dto.DTO;

@Getter
public class AbstractCRUDEvent<D extends DTO> extends ComponentEvent<Component> {
    private final D item;


    public AbstractCRUDEvent(Component source, D item) {
        super(source, false);
        this.item = item;
    }
}
