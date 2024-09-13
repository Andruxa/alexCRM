package ru.kabzex.ui.vaadin.pages.employee;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.RolesAllowed;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractDataPage;
import ru.kabzex.ui.vaadin.pages.employee.parts.EmployeeBody;

@PageTitle("Сотрудники")
@Route(value = "employee", layout = MainLayout.class)
@RolesAllowed(Roles.ADMIN)
public class EmployeePage extends AbstractDataPage {

    protected EmployeePage(AuthenticationContext authenticationContext) {
        super(authenticationContext);
        add(initBody());
    }

    protected EmployeeBody initBody() {
        EmployeeBody body = new EmployeeBody();
        body.addCreateEventListener(this::handle);
        body.addUpdateEventListener(this::handle);
        body.addDeleteEventListener(this::handle);
        body.addFilterChangedEventListener(this::handle);
        return body;
    }

}
