package ru.kabzex.ui.vaadin.pages.incomes;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.RolesAllowed;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractDataPage;
import ru.kabzex.ui.vaadin.pages.incomes.parts.IncomesBody;

@PageTitle("Платежи")
@Route(value = "incomes", layout = MainLayout.class)
@RolesAllowed({Roles.ADMIN, Roles.EMPLOYEE})
public class IncomesPage extends AbstractDataPage {

    protected IncomesPage(AuthenticationContext authenticationContext) {
        super(authenticationContext);
        add(initBody());
    }

    protected IncomesBody initBody() {
        return new IncomesBody();
    }

}
