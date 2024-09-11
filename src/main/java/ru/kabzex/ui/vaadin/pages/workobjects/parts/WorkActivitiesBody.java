package ru.kabzex.ui.vaadin.pages.workobjects.parts;

import com.vaadin.flow.component.tabs.Tab;
import lombok.Getter;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractPagePart;

public class WorkActivitiesBody extends AbstractPagePart {
    @Getter
    private final Tab tab = new Tab("Работы");

    public WorkActivitiesBody() {
    }
}
