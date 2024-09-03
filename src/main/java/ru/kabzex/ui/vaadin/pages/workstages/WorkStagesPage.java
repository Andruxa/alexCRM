package ru.kabzex.ui.vaadin.pages.workstages;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.pages.workstages.parts.WorkStagesBody;

import java.awt.*;

@PageTitle("Работы")
@Route(value = "stages", layout = MainLayout.class)
@RolesAllowed({Roles.ADMIN, Roles.EMPLOYEE})
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
