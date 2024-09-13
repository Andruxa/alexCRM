package ru.kabzex.ui.vaadin.core.page.parts;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

public abstract class AbstractPagePart extends FlexLayout {

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        setWidthFull();
    }
}
