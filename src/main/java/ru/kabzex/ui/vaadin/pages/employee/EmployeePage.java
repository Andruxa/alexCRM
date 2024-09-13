package ru.kabzex.ui.vaadin.pages.employee;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.kabzex.server.security.Roles;
import ru.kabzex.server.service.DictionaryValueService;
import ru.kabzex.server.service.EmployeeService;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeFilter;
import ru.kabzex.ui.vaadin.pages.employee.parts.EmployeeBody;

import java.util.ArrayList;

@PageTitle("Сотрудники")
@Route(value = "employee", layout = MainLayout.class)
@RolesAllowed(Roles.ADMIN)
public class EmployeePage extends AbstractSimplePage<Component, EmployeeBody, Component> {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    DictionaryValueService dictionaryValueService;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        getBody().setData(getLazyBodyDataProvider(null));
    }

    @Override
    protected Component initFooter() {
        return null;
    }

    @Override
    protected EmployeeBody initBody() {
        EmployeeBody body = new EmployeeBody();
        body.addCreateEventListener(this::handle);
        body.addUpdateEventListener(this::handle);
        body.addDeleteEventListener(this::handle);
        body.addFilterChangedEventListener(this::handle);
        return body;
    }

    @Override
    protected Component initHeader() {
        return null;
    }

 /*   private void handle(EmployeeBody.FilterChangedEvent event) {
        getBody().setDataProvider(getLazyBodyDataProvider(event.getFilter()));
    }*/

    /*private void handle(EmployeeBody.RecordDeleteEvent event) {
        ConfirmDialog confirmationDialog = new ConfirmDialog("Удаление записи",
                String.format("После нажатия будет удалена запись %s", event.getEntity().getName()),
                e -> confirmedDeleteSelectedEmployee(event.getEntity().getId()));
        confirmationDialog.open();
    }
*/

/*    private void handle(EmployeeBody.RecordEditEvent event) {
        var dto = employeeService.getAndMap(event.getEntity().getId(), EmployeeDto.class);
        var dialog = getDialog(dto);
        dialog.open();
    }*/

    protected DataProvider<EmployeeDto, EmployeeFilter> getLazyBodyDataProvider(EmployeeFilter filter) {
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
                    var page
                            = employeeService.getAllByFilterAndMap(filter,
                            offset,
                            limit,
                            Sort.by(springSortOrders),
                            EmployeeDto.class);
                    return page.stream();
                },
                countQuery -> Math.toIntExact(employeeService.countByFilter(filter)));
    }
}
