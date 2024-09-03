package ru.kabzex.ui.vaadin.core.page.parts;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public abstract class AbstractTab extends VerticalLayout implements TabView {

    protected AbstractTab() {
        setSizeFull();
        addClassName("list-view");
    }
}
