package ru.kabzex.ui.vaadin.pages.workobjects.parts;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Getter;
import ru.kabzex.server.entity.target.WorkObject_;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.vaadin.core.page.parts_v2.AbstractEditableGridPagePart;
import ru.kabzex.ui.vaadin.dto.document.ContractDto;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectDto;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectFilter;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class WorkObjectBody extends AbstractEditableGridPagePart<WorkObjectDto, WorkObjectFilter> {
    private static final List<String> ALLOWED = List.of(Roles.EMPLOYEE, Roles.ADMIN);
    private final WorkObjectFilter filter = new WorkObjectFilter();
    @Getter
    private final Tab tab = new Tab("Объекты");

    @Override
    protected Collection<String> getAllowedRoles() {
        return ALLOWED;
    }

    @Override
    protected void configureFilters(HeaderRow headerRow) {
        HeaderRow filterHeader = headerRow;
        //
        TextField contractFilter = new TextField();
        contractFilter.setMaxLength(255);
        contractFilter.setValueChangeMode(ValueChangeMode.EAGER);
        contractFilter.setClearButtonVisible(true);
        contractFilter.addValueChangeListener(event -> {
                    filter.setObjectContract(event.getValue());
                    filterChanged(filter);
                }
        );
        contractFilter.setSizeFull();
        filterHeader.getCell(getGrid().getColumnByKey(WorkObject_.OBJECT_CONTRACT)).setComponent(contractFilter);
        //
        TextField nameFilter = new TextField();
        nameFilter.setMaxLength(255);
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilter.setClearButtonVisible(true);
        nameFilter.addValueChangeListener(event -> {
                    filter.setName(event.getValue());
                    filterChanged(filter);
                }
        );
        nameFilter.setSizeFull();
        filterHeader.getCell(getGrid().getColumnByKey(WorkObject_.NAME)).setComponent(nameFilter);
        //
        TextField addressFilter = new TextField();
        addressFilter.setMaxLength(255);
        addressFilter.setValueChangeMode(ValueChangeMode.EAGER);
        addressFilter.setClearButtonVisible(true);
        addressFilter.addValueChangeListener(event -> {
                    filter.setAddress(event.getValue());
                    filterChanged(filter);
                }
        );
        addressFilter.setSizeFull();
        filterHeader.getCell(getGrid().getColumnByKey(WorkObject_.ADDRESS)).setComponent(addressFilter);
        //
        TextField managerFilter = new TextField();
        managerFilter.setMaxLength(255);
        managerFilter.setValueChangeMode(ValueChangeMode.EAGER);
        managerFilter.setClearButtonVisible(true);
        managerFilter.addValueChangeListener(event -> {
                    filter.setEmployee(event.getValue());
                    filterChanged(filter);
                }
        );
        managerFilter.setSizeFull();
        filterHeader.getCell(getGrid().getColumnByKey(WorkObject_.EMPLOYEE)).setComponent(managerFilter);
    }

    @Override
    protected void configureEditor() {
        Editor<WorkObjectDto> editor = getGrid().getEditor();
        Binder<WorkObjectDto> binder = new Binder<>(WorkObjectDto.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        var employee = new ComboBox<EmployeeDto>();
        employee.setWidthFull();
        employee.setItemLabelGenerator(EmployeeDto::getName);
        binder.forField(employee)
                .bind(WorkObjectDto::getEmployee, WorkObjectDto::setEmployee);
        getGrid().getColumnByKey(WorkObject_.EMPLOYEE).setEditorComponent(employee);

        var contract = new ComboBox<ContractDto>();
        contract.setWidthFull();
        contract.setItemLabelGenerator(ContractDto::getFullDescription);
        binder.forField(contract)
                .bind(WorkObjectDto::getObjectContract, WorkObjectDto::setObjectContract);
        getGrid().getColumnByKey(WorkObject_.OBJECT_CONTRACT).setEditorComponent(contract);

        var name = new TextField();
        name.setMaxLength(255);
        name.setRequired(true);
        name.setMinLength(1);
        name.setWidthFull();
        binder.forField(name)
                .asRequired("Обязательное значение")
                .bind(WorkObjectDto::getName, WorkObjectDto::setName);
        getGrid().getColumnByKey(WorkObject_.NAME).setEditorComponent(name);

        var address = new TextArea();
        address.setMaxLength(512);
        address.setWidthFull();
        binder.forField(address)
                .bind(WorkObjectDto::getAddress, WorkObjectDto::setAddress);
        getGrid().getColumnByKey(WorkObject_.ADDRESS).setEditorComponent(address);
    }

    @Override
    protected WorkObjectDto getEmptyDto() {
        return new WorkObjectDto();
    }

    @Override
    protected Grid<WorkObjectDto> initGrid() {
        var grid = new Grid<>(WorkObjectDto.class, false);
//        grid.setEmptyStateText("No employees found.");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(this::parseContract)
                .setHeader("Договор")
                .setSortProperty(WorkObject_.objectContract.getName())
                .setKey(WorkObject_.OBJECT_CONTRACT)
                .setFlexGrow(3);
        grid.addColumn(WorkObjectDto::getName)
                .setKey(WorkObject_.NAME)
                .setSortProperty(WorkObject_.name.getName())
                .setHeader("Название")
                .setFlexGrow(3);
        grid.addColumn(WorkObjectDto::getAddress)
                .setHeader("Адрес")
                .setSortProperty(WorkObject_.address.getName())
                .setKey(WorkObject_.ADDRESS)
                .setFlexGrow(3);
        grid.addColumn(this::parseEmployee)
                .setHeader("Менеджер")
                .setSortProperty(WorkObject_.employee.getName())
                .setKey(WorkObject_.EMPLOYEE)
                .setFlexGrow(3);
        grid.setSizeFull();
        grid.setMultiSort(true);
        grid.addSelectionListener(this::selected);
        return grid;
    }

    private void selected(SelectionEvent<Grid<WorkObjectDto>, WorkObjectDto> event) {
        fireEvent(new SelectedEvent(this, event.getFirstSelectedItem().orElse(null)));
    }

    private String parseEmployee(WorkObjectDto workObjectDto) {
        return Optional.of(workObjectDto)
                .map(WorkObjectDto::getEmployee)
                .map(EmployeeDto::getName).orElse("Отсутствует Менеджер");
    }

    private String parseContract(WorkObjectDto workObjectDto) {
        return Optional.of(workObjectDto)
                .map(WorkObjectDto::getObjectContract)
                .map(ContractDto::getFullDescription).orElse("Отсутствует договор");
    }

    public class SelectedEvent extends ComponentEvent<WorkObjectBody> {

        WorkObjectDto selected;

        protected SelectedEvent(WorkObjectBody source, WorkObjectDto dto) {
            super(source, false);
            this.selected = dto;
        }
    }
}
