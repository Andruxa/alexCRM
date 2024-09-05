package ru.kabzex.ui.vaadin.pages.workobjects.parts;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.grid.editor.EditorCancelEvent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Getter;
import ru.kabzex.server.entity.target.WorkObject_;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractEditableGridPagePart;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractPagePart;
import ru.kabzex.ui.vaadin.dto.AbstractDTO;
import ru.kabzex.ui.vaadin.dto.document.ContractDto;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectDto;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.vaadin.flow.component.icon.VaadinIcon.CHECK;
import static com.vaadin.flow.component.icon.VaadinIcon.CLOSE;

public class WorkObjectBody extends AbstractEditableGridPagePart<WorkObjectDto, WorkObjectFilter> {
    private WorkObjectFilter filter = new WorkObjectFilter();
    private Collection<String> allowedRoles = List.of(Roles.EMPLOYEE, Roles.ADMIN);
    private Component footer;
    private DataProvider dataProvider;
    private List<WorkObjectDto> tempDataList = new ArrayList<>();

    @Getter
    private Tab tab = new Tab("Объекты");


    @Override
    protected void configurePart() {
        super.configurePart();
        setFlexDirection(FlexDirection.COLUMN);
        footer = configureFooter();
        add(footer);
    }

    private Component configureFooter() {
        var addSingleObservationButton = new Button("Добавить Объект");
        addSingleObservationButton.addClickListener(this::createEvent);
        var ft = new HorizontalLayout();
        ft.setWidth("100%");
        ft.add(addSingleObservationButton);
        return ft;
    }

    @Override
    public void onAttach() {
        if (currentRoles.stream().noneMatch(allowedRoles::contains)) {
            footer.setVisible(false);
        }
        configureGridEditor();
    }

    private void createEvent(ClickEvent<Button> event) {
        if (currentRoles.stream().anyMatch(allowedRoles::contains)) {
            if (grid.getEditor().isOpen()) {
                grid.getEditor().cancel();
            }
            var dto = new WorkObjectDto();
            tempDataList.add(dto);
            grid.setItems(tempDataList);
            grid.getEditor().editItem(dto);
        }
    }

    @Override
    protected void editEvent(WorkObjectDto dto) {
        if (grid.getEditor().isOpen())
            grid.getEditor().cancel();
        grid.getEditor().editItem(dto);
    }

    @Override
    protected void deleteEvent(WorkObjectDto dto) {
        fireEvent(new DeleteEvent(this, dto));
    }

    @Override
    public void setDataProvider(DataProvider<WorkObjectDto, WorkObjectFilter> dataProvider) {
        getGrid().setDataProvider(dataProvider);
        this.dataProvider = dataProvider;
    }

    @Override
    public void refresh() {
        getGrid().getDataProvider().refreshAll();
    }

    @Override
    protected Grid<WorkObjectDto> configureGrid() {
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
        grid.addComponentColumn(this::editDelButtons)
                .setFlexGrow(1)
                .setKey(EDIT_COLUMN)
                .setTextAlign(ColumnTextAlign.END);
        grid.setSizeFull();
        grid.setMultiSort(true);
        grid.addItemDoubleClickListener(this::editItem);
        HeaderRow filterHeader = grid.appendHeaderRow();
        //
        TextField contractFilter = new TextField();
        contractFilter.setMaxLength(255);
        contractFilter.setValueChangeMode(ValueChangeMode.EAGER);
        contractFilter.setClearButtonVisible(true);
        contractFilter.addValueChangeListener(event -> {
                    filter.setObjectContract(event.getValue());
                    filterChanged();
                }
        );
        contractFilter.setSizeFull();
        filterHeader.getCell(grid.getColumnByKey(WorkObject_.OBJECT_CONTRACT)).setComponent(contractFilter);
        //
        TextField nameFilter = new TextField();
        nameFilter.setMaxLength(255);
        nameFilter.setValueChangeMode(ValueChangeMode.EAGER);
        nameFilter.setClearButtonVisible(true);
        nameFilter.addValueChangeListener(event -> {
                    filter.setName(event.getValue());
                    filterChanged();
                }
        );
        nameFilter.setSizeFull();
        filterHeader.getCell(grid.getColumnByKey(WorkObject_.NAME)).setComponent(nameFilter);
        //
        TextField addressFilter = new TextField();
        addressFilter.setMaxLength(255);
        addressFilter.setValueChangeMode(ValueChangeMode.EAGER);
        addressFilter.setClearButtonVisible(true);
        addressFilter.addValueChangeListener(event -> {
                    filter.setAddress(event.getValue());
                    filterChanged();
                }
        );
        addressFilter.setSizeFull();
        filterHeader.getCell(grid.getColumnByKey(WorkObject_.ADDRESS)).setComponent(addressFilter);
        //
        TextField managerFilter = new TextField();
        managerFilter.setMaxLength(255);
        managerFilter.setValueChangeMode(ValueChangeMode.EAGER);
        managerFilter.setClearButtonVisible(true);
        managerFilter.addValueChangeListener(event -> {
                    filter.setEmployee(event.getValue());
                    filterChanged();
                }
        );
        managerFilter.setSizeFull();
        filterHeader.getCell(grid.getColumnByKey(WorkObject_.EMPLOYEE)).setComponent(managerFilter);
        //
        return grid;
    }

    private void configureGridEditor() {
        Editor<WorkObjectDto> editor = grid.getEditor();
        Binder<WorkObjectDto> binder = new Binder<>(WorkObjectDto.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        var employee = new ComboBox<EmployeeDto>();
        employee.setWidthFull();
        employee.setItemLabelGenerator(EmployeeDto::getName);
        binder.forField(employee)
                .bind(WorkObjectDto::getEmployee, WorkObjectDto::setEmployee);
        grid.getColumnByKey(WorkObject_.EMPLOYEE).setEditorComponent(employee);

        var contract = new ComboBox<ContractDto>();
        contract.setWidthFull();
        contract.setItemLabelGenerator(ContractDto::getFullDescription);
        binder.forField(contract)
                .bind(WorkObjectDto::getObjectContract, WorkObjectDto::setObjectContract);
        grid.getColumnByKey(WorkObject_.OBJECT_CONTRACT).setEditorComponent(contract);

        var name = new TextField();
        name.setMaxLength(255);
        name.setMinLength(1);
        name.setWidthFull();
        binder.forField(name)
                .asRequired("Обязательное значение")
                .bind(WorkObjectDto::getName, WorkObjectDto::setName);
        grid.getColumnByKey(WorkObject_.NAME).setEditorComponent(name);

        var address = new TextArea();
        address.setMaxLength(512);
        address.setWidthFull();
        binder.forField(address)
                .bind(WorkObjectDto::getAddress, WorkObjectDto::setAddress);
        grid.getColumnByKey(WorkObject_.ADDRESS).setEditorComponent(address);

        Button saveButton = new Button(CHECK.create(), e -> editor.save());
        Button cancelButton = new Button(CLOSE.create(),
                e -> {
                    editCanceled(editor);
                    editor.cancel();
                });
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,
                cancelButton);
        actions.setPadding(false);
        grid.getColumnByKey(EDIT_COLUMN).setEditorComponent(actions);
    }

    private void editCanceled(Editor<WorkObjectDto> editor) {
        var item = Optional.ofNullable(editor.getItem());
        if (item.map(AbstractDTO::getId).isEmpty()) {
            if (dataProvider == null) {
                tempDataList.clear();
                getGrid().getDataProvider().refreshAll();
            } else {
                getGrid().setDataProvider(dataProvider);
            }
        }
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

    private void editItem(ItemDoubleClickEvent<WorkObjectDto> workObjectDtoItemDoubleClickEvent) {
        editEvent(grid.getSelectionModel().getFirstSelectedItem().orElse(null));
    }

    private void filterChanged() {
        fireEvent(new FilterChangedEvent(this, filter));
    }

    public class CreateEvent extends PagePartEvent {

        protected CreateEvent(AbstractPagePart source) {
            super(source, null);
        }
    }

    public class EditEvent extends PagePartEvent<WorkObjectDto> {

        protected EditEvent(AbstractPagePart source, WorkObjectDto dto) {
            super(source, dto);
        }
    }

    public class DeleteEvent extends PagePartEvent<WorkObjectDto> {

        protected DeleteEvent(AbstractPagePart source, WorkObjectDto dto) {
            super(source, dto);
        }
    }

    public class FilterChangedEvent extends PagePartEvent<WorkObjectFilter> {

        protected FilterChangedEvent(AbstractPagePart source, WorkObjectFilter filter) {
            super(source, filter);
        }
    }
}
