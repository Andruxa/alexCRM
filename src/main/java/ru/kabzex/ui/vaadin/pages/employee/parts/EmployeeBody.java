package ru.kabzex.ui.vaadin.pages.employee.parts;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Getter;
import ru.kabzex.server.entity.employee.Employee_;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractEditableGridPagePart;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractPagePart;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeSimpleFilter;

public class EmployeeBody extends AbstractEditableGridPagePart<EmployeeDto, EmployeeSimpleFilter> {

    private EmployeeSimpleFilter currentFilter = new EmployeeSimpleFilter();


    private void filterChanged() {
        fireEvent(new FilterChangedEvent(this, currentFilter));
    }

    @Override
    public void setDataProvider(DataProvider<EmployeeDto, EmployeeSimpleFilter> dataProvider) {
        grid.setDataProvider(dataProvider);
    }

    @Override
    public void refresh() {
        this.grid.getDataProvider().refreshAll();
    }

    @Override
    protected Grid<EmployeeDto> configureGrid() {
        Grid<EmployeeDto> grid = new Grid<>(EmployeeDto.class, false);
        grid.addColumn(EmployeeDto::getName)
                .setHeader("Сотрудник")
                .setSortable(true)
                .setKey(Employee_.NAME);
        grid.addColumn(EmployeeDto::getPositionValue)
                .setHeader("Должность")
                .setSortable(true)
                .setKey(Employee_.ROLE)
                .setFlexGrow(1);
        grid.addComponentColumn(this::editDelButtons)
                .setFlexGrow(1)
                .setTextAlign(ColumnTextAlign.END);
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        HeaderRow filterHeader = grid.appendHeaderRow();
        //
        TextField name = new TextField();
        name.setMaxLength(255);
        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.setClearButtonVisible(true);
        name.addValueChangeListener(event -> {
                    currentFilter.setName(event.getValue());
                    filterChanged();
                }
        );
        name.setSizeFull();
        filterHeader.getCell(grid.getColumnByKey(Employee_.NAME)).setComponent(name);
        //
        TextField position = new TextField();
        position.setMaxLength(255);
        position.setValueChangeMode(ValueChangeMode.EAGER);
        position.setClearButtonVisible(true);
        position.addValueChangeListener(event -> {
                    currentFilter.setPosition(event.getValue());
                    filterChanged();
                }
        );
        position.setSizeFull();
        filterHeader.getCell(grid.getColumnByKey(Employee_.ROLE)).setComponent(position);
        return grid;
    }

    @Override
    protected void editEvent(EmployeeDto dto) {
        fireEvent(new RecordEditEvent(this, dto));
    }

    @Override
    protected void deleteEvent(EmployeeDto dto) {
        fireEvent(new RecordDeleteEvent(this, dto));
    }

    public class BodyEvent extends PagePartEvent<EmployeeDto> {

        protected BodyEvent(AbstractPagePart source, EmployeeDto entity) {
            super(source, entity);
        }

    }

    public class FilterChangedEvent extends BodyEvent {
        @Getter
        EmployeeSimpleFilter filter;

        protected FilterChangedEvent(EmployeeBody source, EmployeeSimpleFilter value) {
            super(source, null);
            this.filter = value;
        }
    }

    public class RecordEditEvent extends BodyEvent {
        protected RecordEditEvent(EmployeeBody source, EmployeeDto value) {
            super(source, value);
        }
    }

    public class RecordDeleteEvent extends BodyEvent {
        protected RecordDeleteEvent(EmployeeBody source, EmployeeDto value) {
            super(source, value);
        }
    }

    public class RecordCreateEvent extends BodyEvent {
        protected RecordCreateEvent(EmployeeBody source, EmployeeDto value) {
            super(source, value);
        }
    }
}
