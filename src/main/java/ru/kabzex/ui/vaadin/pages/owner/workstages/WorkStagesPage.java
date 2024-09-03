package ru.kabzex.ui.vaadin.pages.owner.workstages;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.pages.owner.workstages.parts.WorkStagesBody;

import java.awt.*;

@PageTitle("Работы")
@Route(value = "stages", layout = MainLayout.class)
public class WorkStagesPage extends AbstractSimplePage<Component, WorkStagesBody, Component> {
    @Override
    protected Component initFooter() {
        return null;
    }

    @Override
    protected WorkStagesBody initBody() {
        return new WorkStagesBody();
    }

    @Override
    protected Component initHeader() {
        return null;
    }
}
