package ru.kabzex.ui.vaadin.pages.workobjects.parts;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Getter;
import ru.kabzex.server.entity.dictionary.DictionaryValue_;
import ru.kabzex.server.entity.target.WorkObject_;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractEditableGridPagePart;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractPagePart;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectDto;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectFilter;

public class WorkObjectBody extends AbstractEditableGridPagePart<WorkObjectDto, WorkObjectFilter> {
    private WorkObjectFilter filter = new WorkObjectFilter();

    @Getter
    private Tab tab = new Tab("Объекты");

    public WorkObjectBody() {
    }

    @Override
    protected void editEvent(WorkObjectDto dto) {
        fireEvent(new EditEvent(this, dto));
    }

    @Override
    protected void deleteEvent(WorkObjectDto dto) {
        fireEvent(new DeleteEvent(this, dto));
    }

    @Override
    public void setDataProvider(DataProvider<WorkObjectDto, WorkObjectFilter> dataProvider) {
        getGrid().setDataProvider(dataProvider);
    }

    @Override
    public void refresh() {
        getGrid().getDataProvider().refreshAll();
    }

    @Override
    protected Grid<WorkObjectDto> configureGrid() {
        var grid = new Grid<>(WorkObjectDto.class, false);
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

    private String parseEmployee(WorkObjectDto workObjectDto) {
        return workObjectDto.getEmployee().getName();
    }

    private String parseContract(WorkObjectDto workObjectDto) {
        return workObjectDto.getObjectContract().getFullDescription();
    }

    private void editItem(ItemDoubleClickEvent<WorkObjectDto> workObjectDtoItemDoubleClickEvent) {
        editEvent(grid.getSelectionModel().getFirstSelectedItem().orElse(null));
    }

    private void filterChanged() {
        fireEvent(new FilterChangedEvent(this, filter));
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
