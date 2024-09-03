package ru.kabzex.ui.vaadin.pages.contracts;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.pages.contracts.parts.ClientContractBody;

import java.awt.*;

@PageTitle("Сметы")
@Route(value = "contracts", layout = MainLayout.class)
@PermitAll
public class ClientContractsPage extends AbstractSimplePage<Component, ClientContractBody, Component> {
    @Override
    protected Component initFooter() {
        return null;
    }

    @Override
    protected ClientContractBody initBody() {
        return new ClientContractBody();
    }

    @Override
    protected Component initHeader() {
        return null;
    }
}
