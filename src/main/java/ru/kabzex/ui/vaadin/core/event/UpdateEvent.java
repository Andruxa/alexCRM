package ru.kabzex.ui.vaadin.core.event;

import com.vaadin.flow.component.Component;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractDataPagePart;
import ru.kabzex.ui.vaadin.dto.DTO;

public class UpdateEvent<D extends DTO> extends AbstractCRUDEvent<D> {
    public UpdateEvent(AbstractDataPagePart source, D item) {
        super(source, item);
    }
}
