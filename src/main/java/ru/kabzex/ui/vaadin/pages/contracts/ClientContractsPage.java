package ru.kabzex.ui.vaadin.pages.contracts;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractDataPage;
import ru.kabzex.ui.vaadin.pages.contracts.parts.ClientContractBody;

@PageTitle("Сметы")
@Route(value = "contracts", layout = MainLayout.class)
@PermitAll
public class ClientContractsPage extends AbstractDataPage {

    protected ClientContractsPage(AuthenticationContext authenticationContext) {
        super(authenticationContext);
        add(initBody());
    }

    protected ClientContractBody initBody() {
        return new ClientContractBody();
    }

}
