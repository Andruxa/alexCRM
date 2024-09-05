package ru.kabzex.ui.vaadin.pages.workobjects;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import ru.kabzex.server.security.Roles;
import ru.kabzex.server.service.WorkObjectService;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.core.page.parts.TabBuilder;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectDto;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectFilter;
import ru.kabzex.ui.vaadin.pages.workobjects.parts.*;

import java.util.LinkedHashMap;


@PageTitle("Объекты")
@Route(value = "", layout = MainLayout.class)
@RolesAllowed({Roles.ADMIN, Roles.EMPLOYEE, Roles.USER})
@RequiredArgsConstructor
public class WorkObjectsPage extends AbstractSimplePage<Component, Component, Component> {
    private final WorkObjectService workObjectService;
    private final AuthenticationContext authenticationContext;

    private LinkedHashMap<Tab, Component> tabsMap;
    private Tabs tabs;
    private WorkObjectDto selected;
    WorkObjectBody objectList;
    WorkObjectAgregateInfoBody objectInfo;
    ContractBody contractInfo;
    IncomeBody incomeInfo;
    WorkActivitiesBody activitiesInfo;
    WorkStuffBody stuffInfo;
    WorkActivitiesSpecialBody specialInfo;

    @Override
    protected Component initFooter() {
        return null;
    }

    @Override
    protected Component initBody() {
        objectList = new WorkObjectBody();
        objectInfo = new WorkObjectAgregateInfoBody();
        contractInfo = new ContractBody();
        incomeInfo = new IncomeBody();
        activitiesInfo = new WorkActivitiesBody();
        stuffInfo = new WorkStuffBody();
        specialInfo = new WorkActivitiesSpecialBody();
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
        return component;
    }

    @Override
    protected Component initHeader() {
        return new WorkObjectHeader();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        objectList.setCurrentRoles(authenticationContext.getGrantedRoles());
        objectList.onAttach();
        clearBody();
    }

    private void clearBody() {
        disableTabs();
    }

    private void disableTabs() {
        tabs.setSelectedTab(objectList.getTab());
        tabsMap.forEach((tab, component) -> component.setVisible(objectList.getTab().equals(tab)));
    }

}
