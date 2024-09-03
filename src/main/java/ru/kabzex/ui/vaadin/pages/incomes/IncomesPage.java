package ru.kabzex.ui.vaadin.pages.incomes;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.pages.incomes.parts.IncomesBody;

import java.awt.*;

@PageTitle("Платежи")
@Route(value = "incomes", layout = MainLayout.class)
@RolesAllowed({Roles.ADMIN, Roles.EMPLOYEE})
public class IncomesPage extends AbstractSimplePage<Component, IncomesBody, Component> {
    @Override
    protected Component initFooter() {
        return null;
    }

    @Override
    protected IncomesBody initBody() {
        return new IncomesBody();
    }

    @Override
    protected Component initHeader() {
        return null;
    }
}
