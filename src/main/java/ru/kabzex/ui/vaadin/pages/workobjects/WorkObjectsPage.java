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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import ru.kabzex.server.entity.target.WorkObject;
import ru.kabzex.server.security.Roles;
import ru.kabzex.server.service.WorkObjectService;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.event.DeleteEvent;
import ru.kabzex.ui.vaadin.core.event.FilterChangedEvent;
import ru.kabzex.ui.vaadin.core.event.UpdateEvent;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.core.page.parts.TabBuilder;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectDto;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectFilter;
import ru.kabzex.ui.vaadin.pages.workobjects.parts.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Optional;


@PageTitle("Объекты")
@Route(value = "", layout = MainLayout.class)
@RolesAllowed({Roles.ADMIN, Roles.EMPLOYEE, Roles.USER})
@RequiredArgsConstructor
public class WorkObjectsPage extends AbstractSimplePage<WorkObjectHeader, Component, Component> {
    private final WorkObjectService workObjectService;
    private final AuthenticationContext authenticationContext;

    private LinkedHashMap<Tab, Component> tabsMap;
    private Tabs tabs;
    private Tab mainTab;

    @Override
    protected Component initFooter() {
        return null;
    }

    @Override
    protected Component initBody() {
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
        objectInfo.addAttachListener(this::handle);
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
        return component;
    }

    private void handle(UpdateEvent<WorkObjectDto> event) {
        workObjectService.saveFromDto(event.getItem());
        event.getSource().refresh();
    }

    private void handle(DeleteEvent<WorkObjectDto> event) {
        workObjectService.deleteById(event.getItem().getId());
        ((WorkObjectBody) event.getSource()).refresh();
    }

    private void handle(FilterChangedEvent<WorkObjectFilter> event) {
        ((WorkObjectBody) event.getSource()).setData(getLazyObjectListDataProvider(event.getFilter()));
        ((WorkObjectBody) event.getSource()).refresh();
    }

    private void handle(AttachEvent attachEvent) {
        if (attachEvent.getSource() instanceof WorkObjectBody wob) {
            wob.setCurrentRoles(authenticationContext.getGrantedRoles());
            wob.setData(getLazyObjectListDataProvider(null));
        }
    }

    private void handle(WorkObjectBody.SelectedEvent event) {
        var selected = Optional.ofNullable(event.getSelected());
        selected.ifPresentOrElse(this::selected, this::deselected);
    }

    private void deselected() {
        getHeader().setLabel(null);
        disableTabs();
    }

    private void selected(WorkObjectDto dto) {
        getHeader().setLabel(dto.getName());
        tabsMap.forEach(((tab, component) -> tab.setEnabled(true)));
    }

    @Override
    protected WorkObjectHeader initHeader() {
        return new WorkObjectHeader();
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

    private DataProvider<WorkObjectDto, WorkObjectFilter> getLazyObjectListDataProvider(WorkObjectFilter filter) {
        return DataProvider.fromFilteringCallbacks(query -> {
            int offset = query.getOffset();
            int limit = query.getLimit();
            var vaadinSortOrders = query.getSortOrders();
            var springSortOrders = new ArrayList<Sort.Order>();
            for (QuerySortOrder so : vaadinSortOrders) {
                String colKey = so.getSorted();
                if (so.getDirection() == SortDirection.ASCENDING) {
                    springSortOrders.add(Sort.Order.asc(colKey));
                } else {
                    springSortOrders.add(Sort.Order.desc(colKey));
                }
            }
            var page = workObjectService.getAllByFilterAndMap(filter, offset, limit, Sort.by(springSortOrders), WorkObjectDto.class);
            return page.stream();
        }, countQuery -> Math.toIntExact(workObjectService.countByFilter(filter)));
    }


}
