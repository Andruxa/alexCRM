package ru.kabzex.ui.vaadin.pages.workobjects.parts;

import com.vaadin.flow.component.tabs.Tab;
import lombok.Getter;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractPagePart;

public class IncomeBody extends AbstractPagePart {
    @Getter
    private Tab tab = new Tab("Платежи");

    public IncomeBody() {
    }
}
