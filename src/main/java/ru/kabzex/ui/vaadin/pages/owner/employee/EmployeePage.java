package ru.kabzex.ui.vaadin.pages.owner.employee;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.pages.owner.employee.parts.EmployeeBody;

import java.awt.*;

@PageTitle("Сотрудники")
@Route(value = "employee", layout = MainLayout.class)
public class EmployeePage extends AbstractSimplePage<Component, EmployeeBody, Component> {
    @Override
    protected Component initFooter() {
        return null;
    }

    @Override
    protected EmployeeBody initBody() {
        return new EmployeeBody();
    }

    @Override
    protected Component initHeader() {
        return null;
    }
}
