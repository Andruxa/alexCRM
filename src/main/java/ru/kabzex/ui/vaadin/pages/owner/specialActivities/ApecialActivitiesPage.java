package ru.kabzex.ui.vaadin.pages.owner.specialActivities;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.pages.owner.specialActivities.parts.SpecialActivitiesBody;

import java.awt.*;

@PageTitle("Спецмонтаж")
@Route(value = "special", layout = MainLayout.class)
public class ApecialActivitiesPage extends AbstractSimplePage<Component, SpecialActivitiesBody, Component> {
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
