package ru.kabzex.ui.vaadin.pages.specialActivities;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.pages.specialActivities.parts.SpecialActivitiesBody;

import java.awt.*;

@PageTitle("Спецмонтаж")
@Route(value = "special", layout = MainLayout.class)
@RolesAllowed({Roles.ADMIN, Roles.EMPLOYEE})
public class SpecialActivitiesPage extends AbstractSimplePage<Component, SpecialActivitiesBody, Component> {
    @Override
    protected Component initFooter() {
        return null;
    }

    @Override
    protected SpecialActivitiesBody initBody() {
        return new SpecialActivitiesBody();
    }

    @Override
    protected Component initHeader() {
        return null;
    }
}
