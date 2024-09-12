package ru.kabzex.ui.vaadin.core.event;

import com.vaadin.flow.component.Component;
import ru.kabzex.ui.vaadin.dto.DTO;

public class DeleteEvent<D extends DTO> extends AbstractCRUDEvent<D> {
    public DeleteEvent(Component source, D item) {
        super(source, item);
    }
}
