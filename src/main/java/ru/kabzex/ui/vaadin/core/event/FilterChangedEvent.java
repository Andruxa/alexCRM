package ru.kabzex.ui.vaadin.core.event;

import com.vaadin.flow.component.ComponentEvent;
import lombok.Getter;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractEditableGridPagePart;

@Getter
public class FilterChangedEvent extends ComponentEvent<AbstractEditableGridPagePart> {

    public FilterChangedEvent(AbstractEditableGridPagePart source) {
        super(source, false);
    }
}
