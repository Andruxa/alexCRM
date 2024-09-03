package ru.kabzex.ui.vaadin.pages.employee.stuff;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.pages.employee.stuff.parts.StuffBody;

import java.awt.*;

@PageTitle("Материалы")
@Route(value = "stuff", layout = MainLayout.class)
public class StuffPage extends AbstractSimplePage<Component, StuffBody, Component> {
    @Override
    protected Component initFooter() {
        return null;
    }

    @Override
    protected StuffBody initBody() {
        return new StuffBody();
    }

    @Override
    protected Component initHeader() {
        return null;
    }
}
