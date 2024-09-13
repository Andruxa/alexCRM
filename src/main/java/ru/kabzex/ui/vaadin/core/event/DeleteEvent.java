package ru.kabzex.ui.vaadin.core.event;

import ru.kabzex.ui.vaadin.core.page.parts.AbstractDataPagePart;
import ru.kabzex.ui.vaadin.dto.DTO;

public class DeleteEvent<D extends DTO> extends AbstractCRUDEvent<D> {
    public DeleteEvent(AbstractDataPagePart source, D item) {
        super(source, item);
    }
}
