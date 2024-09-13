package ru.kabzex.ui.vaadin.core.event;

import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractDataPagePart;
import ru.kabzex.ui.vaadin.dto.DTO;

@Getter
public class AbstractCRUDEvent<D extends DTO> extends ComponentEvent<AbstractDataPagePart> {
    private final D item;


    public AbstractCRUDEvent(AbstractDataPagePart source, D item) {
        super(source, false);
        this.item = item;
    }
}
