package ru.kabzex.ui.vaadin.pages.clients.contracts;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.pages.clients.contracts.parts.ClientContractBody;

import java.awt.*;

@PageTitle("Сметы")
@Route(value = "contracts", layout = MainLayout.class)
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
