package ru.kabzex.ui.vaadin.pages.workobjects;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.pages.workobjects.parts.WorkObjectBody;


@PageTitle("Работы")
@Route(value = "stages", layout = MainLayout.class)
@RolesAllowed({Roles.ADMIN, Roles.EMPLOYEE})
public class WorkObjectsPage extends AbstractSimplePage<Component, WorkObjectBody, Component> {
    @Override
    protected Component initFooter() {
        return null;
    }

    @Override
    protected WorkObjectBody initBody() {
        return new WorkObjectBody();
    }

    @Override
    protected Component initHeader() {
        return null;
    }
}
