package ru.kabzex.ui.vaadin.pages.workobjects;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.data.domain.Sort;
import ru.kabzex.server.entity.target.WorkObject;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSmartPage;
import ru.kabzex.ui.vaadin.core.page.parts.TabBuilder;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectDto;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectFilter;
import ru.kabzex.ui.vaadin.pages.workobjects.parts.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;


@PageTitle("Объекты2")
@Route(value = "2", layout = MainLayout.class)
@RolesAllowed({Roles.ADMIN, Roles.EMPLOYEE, Roles.USER})

public class WorkObjectsPage2 extends AbstractSmartPage {

    private LinkedHashMap<Tab, Component> tabsMap;
    private Tabs tabs;
    private Tab mainTab;
    private WorkObjectHeader header;

    protected WorkObjectsPage2(AuthenticationContext authenticationContext) {
        super(authenticationContext);
        initBody();
    }

    protected void initBody() {
        var objectList = new WorkObjectBody();
        mainTab = objectList.getTab();
        objectList.addCreateEventListener(this::handle);
        objectList.addUpdateEventListener(this::handle);
        objectList.addDeleteEventListener(this::handle);
        objectList.addFilterChangedEventListener(this::handle);
        objectList.addAttachListener(this::handle);
        ComponentUtil.addListener(objectList, WorkObjectBody.SelectedEvent.class, this::handle);
        //
        var objectInfo = new WorkObjectAgregateInfoBody();
//        objectInfo.addAttachListener(this::handle);
        var contractInfo = new ContractBody();
        var incomeInfo = new IncomeBody();
        var activitiesInfo = new WorkActivitiesBody();
        var stuffInfo = new WorkStuffBody();
        var specialInfo = new WorkActivitiesSpecialBody();
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
