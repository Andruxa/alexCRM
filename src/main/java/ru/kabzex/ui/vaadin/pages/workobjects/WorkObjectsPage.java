package ru.kabzex.ui.vaadin.pages.workobjects;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.RolesAllowed;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractDataPage;
import ru.kabzex.ui.vaadin.core.page.parts.TabBuilder;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectDto;
import ru.kabzex.ui.vaadin.pages.workobjects.parts.*;

import java.util.LinkedHashMap;
import java.util.Optional;

@PageTitle("Объекты")
@Route(value = "", layout = MainLayout.class)
@RolesAllowed({Roles.ADMIN, Roles.EMPLOYEE, Roles.USER})
public class WorkObjectsPage extends AbstractDataPage {

    private LinkedHashMap<Tab, Component> tabsMap;
    private Tabs tabs;
    private Tab mainTab;
    private WorkObjectHeader header;

    protected WorkObjectsPage(AuthenticationContext authenticationContext) {
        super(authenticationContext);
        initBody();
    }

    protected void initBody() {
        var objectList = new WorkObjectBody();
        mainTab = objectList.getTab();
        ComponentUtil.addListener(objectList, WorkObjectBody.SelectedEvent.class, this::handle);
        registerDataComponent(objectList);
        //
        var objectInfo = new WorkObjectAgregateInfoBody();
        registerDataComponent(objectInfo);
        var contractInfo = new ContractBody();
        registerDataComponent(contractInfo);
        var incomeInfo = new IncomeBody();
        registerDataComponent(incomeInfo);
        var activitiesInfo = new WorkActivitiesBody();
        registerDataComponent(activitiesInfo);
        var stuffInfo = new WorkStuffBody();
        registerDataComponent(stuffInfo);
        var specialInfo = new WorkActivitiesSpecialBody();
        registerDataComponent(specialInfo);
        TabBuilder tabBuilder = new TabBuilder();
        tabBuilder.addNextPage(objectList.getTab(), objectList, true);
        tabBuilder.addNextPage(objectInfo.getTab(), objectInfo, false);
        tabBuilder.addNextPage(contractInfo.getTab(), contractInfo, false);
        tabBuilder.addNextPage(incomeInfo.getTab(), incomeInfo, false);
        tabBuilder.addNextPage(activitiesInfo.getTab(), activitiesInfo, false);
        tabBuilder.addNextPage(stuffInfo.getTab(), stuffInfo, false);
        tabBuilder.addNextPage(specialInfo.getTab(), specialInfo, false);
        tabsMap = tabBuilder.getTabsToPages();
        var component = tabBuilder.build();
        tabs = tabBuilder.getTabs();
        header = new WorkObjectHeader();
        add(header, component);
    }

    private void handle(WorkObjectBody.SelectedEvent event) {
        var selected = Optional.ofNullable(event.getSelected());
        selected.ifPresentOrElse(this::selected, this::deselected);
    }

    private void deselected() {
        header.setLabel(null);
        disableTabs();
    }

    private void selected(WorkObjectDto dto) {
        header.setLabel(dto.getName());
        tabsMap.forEach(((tab, component) -> tab.setEnabled(true)));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        clearBody();
    }

    private void clearBody() {
        disableTabs();
    }

    private void disableTabs() {
        tabs.setSelectedTab(mainTab);
        tabsMap.forEach((tab, component) -> {
            component.setVisible(mainTab.equals(tab));
            tab.setEnabled(mainTab.equals(tab));
        });
    }

}
