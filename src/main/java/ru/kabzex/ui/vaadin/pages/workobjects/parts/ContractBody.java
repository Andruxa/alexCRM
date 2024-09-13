package ru.kabzex.ui.vaadin.pages.workobjects.parts;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.tabs.Tab;
import lombok.Getter;
import ru.kabzex.server.entity.documents.Contract_;
import ru.kabzex.server.entity.target.WorkObject;
import ru.kabzex.server.entity.target.WorkObject_;
import ru.kabzex.server.security.Roles;
import ru.kabzex.server.utils.DateTimeUtils;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractEditableGridPagePart;
import ru.kabzex.ui.vaadin.dto.client.PersonClientDto;
import ru.kabzex.ui.vaadin.dto.document.ContractDto;
import ru.kabzex.ui.vaadin.dto.document.ContractFilter;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ContractBody extends AbstractEditableGridPagePart<ContractDto, ContractFilter> {
    private static final List<String> ALLOWED = List.of(Roles.EMPLOYEE, Roles.ADMIN);
    @Getter
    private final Tab tab = new Tab("Договор");

    @Override
    protected ContractFilter initFilter() {
        return null;
    }

    @Override
    protected Collection<String> getAllowedRoles() {
        return ALLOWED;
    }

    @Override
    protected void configureFilters(HeaderRow headerRow) {

    }

    @Override
    protected void configureEditor() {

    }

    @Override
    public ContractDto getEmptyDto() {
        return new ContractDto();
    }

    @Override
    protected Grid<ContractDto> initGrid() {
        var grid = new Grid<>(ContractDto.class, false);
//        grid.setEmptyStateText("No employees found.");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(ContractDto::getContractNumber)
                .setHeader("№")
                .setSortProperty(Contract_.contractNumber.getName())
                .setKey(Contract_.CONTRACT_NUMBER)
                .setFlexGrow(1);
        grid.addColumn(this::parseDate)
                .setKey(Contract_.CONTRACT_DATE)
                .setSortProperty(Contract_.contractDate.getName())
                .setHeader("Дата")
                .setFlexGrow(3);
        grid.addColumn(this::parseClient)
                .setHeader("Клиент")
                .setSortProperty(Contract_.contractor.getName())
                .setKey(Contract_.CONTRACTOR)
                .setFlexGrow(3);
        grid.addColumn(this::parseWorkObject)
                .setHeader("Объекты")
                .setSortProperty(Contract_.workObjects.getName())
                .setKey(Contract_.WORK_OBJECTS)
                .setFlexGrow(3);
        grid.addColumn(this::parseEmployees)
                .setHeader("Менеджеры")
                .setKey(WorkObject_.EMPLOYEE)
                .setFlexGrow(3);
        grid.setSizeFull();
        grid.setMultiSort(true);
        return grid;
    }

    private String parseEmployees(ContractDto contractDto) {
        return Optional.ofNullable(contractDto)
                .map(ContractDto::getLinkedEmployees)
                .stream()
                .flatMap(List::stream)
                .map(EmployeeDto::getName)
                .collect(Collectors.joining(","));
    }

    private String parseWorkObject(ContractDto contractDto) {
        return Optional.ofNullable(contractDto)
                .map(ContractDto::getWorkObjects)
                .stream()
                .flatMap(List::stream)
                .map(WorkObject::getName)
                .collect(Collectors.joining(","));
    }

    private String parseClient(ContractDto contractDto) {
        return Optional.ofNullable(contractDto).map(ContractDto::getContractor)
                .map(PersonClientDto::getShortInfo).orElse("-");
    }

    private String parseDate(ContractDto contractDto) {
        return Optional.ofNullable(contractDto).map(ContractDto::getContractDate)
                .map(DateTimeUtils::getAsSimpleDateString)
                .orElse("-");
    }
}
