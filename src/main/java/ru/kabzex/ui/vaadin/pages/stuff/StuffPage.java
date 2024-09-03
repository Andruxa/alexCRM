package ru.kabzex.ui.vaadin.pages.stuff;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.pages.stuff.parts.StuffBody;

import java.awt.*;

@PageTitle("Материалы")
@Route(value = "stuff", layout = MainLayout.class)
@RolesAllowed({Roles.ADMIN, Roles.EMPLOYEE})
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
