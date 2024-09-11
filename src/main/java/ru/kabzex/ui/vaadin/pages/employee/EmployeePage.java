package ru.kabzex.ui.vaadin.pages.employee;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.kabzex.server.enums.Dictionary;
import ru.kabzex.server.exception.NoRoleExistsException;
import ru.kabzex.server.security.Roles;
import ru.kabzex.server.service.DictionaryValueService;
import ru.kabzex.server.service.EmployeeService;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractPage;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeFilter;
import ru.kabzex.ui.vaadin.pages.employee.dialog.EmployeeDialog;
import ru.kabzex.ui.vaadin.pages.employee.parts.EmployeeBody;
import ru.kabzex.ui.vaadin.pages.employee.parts.EmployeeFooter;
import ru.kabzex.ui.vaadin.pages.employee.parts.EmployeeHeader;

import java.util.ArrayList;
import java.util.UUID;

@PageTitle("Сотрудники")
@Route(value = "employee", layout = MainLayout.class)
@RolesAllowed(Roles.ADMIN)
public class EmployeePage extends AbstractPage<EmployeeHeader, EmployeeBody, EmployeeFooter> {
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
    protected EmployeeFooter initFooter() {

        var header = new EmployeeFooter();
        header.addListener(EmployeeFooter.NewValueEvent.class, this::handle);
        return header;
    }

    @Override
    protected EmployeeBody initBody() {
        EmployeeBody body = new EmployeeBody();
//        body.addListener(EmployeeBody.FilterChangedEvent.class, this::handle);
//        body.addListener(EmployeeBody.RecordEditEvent.class, this::handle);
//        body.addListener(EmployeeBody.RecordCreateEvent.class, this::handle);
//        body.addListener(EmployeeBody.RecordDeleteEvent.class, this::handle);
        return body;
    }

    @Override
    protected EmployeeHeader initHeader() {
        return null;
    }

    private void handle(EmployeeFooter.NewValueEvent event) {
        var dialog = getDialog(new EmployeeDto());
        dialog.open();
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
    private void confirmedDeleteSelectedEmployee(UUID id) {
        employeeService.deleteById(id);
        getBody().refresh();
    }

    private void handle(EmployeeDialog.ItemConfirmedEvent event) {
        employeeService.saveFromDto(event.getEntity());
        getBody().refresh();
    }

/*    private void handle(EmployeeBody.RecordEditEvent event) {
        var dto = employeeService.getAndMap(event.getEntity().getId(), EmployeeDto.class);
        var dialog = getDialog(dto);
        dialog.open();
    }*/

    private EmployeeDialog getDialog(EmployeeDto value) {
        EmployeeDialog dialog = new EmployeeDialog();
        dialog.addListener(EmployeeDialog.ItemConfirmedEvent.class, this::handle);
        dialog.setWidth("40%");
        dialog.setHeight("65%");
        dialog.setTitle(String.format("%s записи", (value != null && value.getId() != null) ? "Редактирование" : "Создание"));
        var positions = dictionaryValueService.getAllByCodeAndMap(Dictionary.EMPLOYEE_ROLES, DictionaryValueDTO.class);
        if (positions.isEmpty()) {
            throw new NoRoleExistsException(String.format("Нет записи в справочнике %s. Создайте перед заведением сотрудников", Dictionary.EMPLOYEE_ROLES.getDescription()));
        }
        dialog.setPosition(positions);
        if (value != null) {
            dialog.setItem(value);
        }
        return dialog;
    }

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
