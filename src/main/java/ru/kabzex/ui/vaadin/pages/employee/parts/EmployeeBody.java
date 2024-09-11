package ru.kabzex.ui.vaadin.pages.employee.parts;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Getter;
import ru.kabzex.server.entity.employee.Employee_;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractEditableGridPagePart;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractPagePart;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeFilter;

import java.util.Collection;
import java.util.List;

public class EmployeeBody extends AbstractEditableGridPagePart<EmployeeDto, EmployeeFilter> {
    private static final Collection<String> ALLOWED = List.of(Roles.ADMIN);
    private EmployeeFilter filter = new EmployeeFilter();

    @Override
    protected Grid<EmployeeDto> initGrid() {
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
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);
        return grid;
    }

    @Override
    protected Collection<String> getAllowedRoles() {
        return ALLOWED;
    }

    @Override
    protected void configureFilters(HeaderRow headerRow) {
        HeaderRow filterHeader = headerRow;
        //
        TextField name = new TextField();
        name.setMaxLength(255);
        name.setValueChangeMode(ValueChangeMode.EAGER);
        name.setClearButtonVisible(true);
        name.addValueChangeListener(event -> {
                    filter.setName(event.getValue());
                    filterChanged(filter);
                }
        );
        name.setSizeFull();
        filterHeader.getCell(getGrid().getColumnByKey(Employee_.NAME)).setComponent(name);
        //
        TextField position = new TextField();
        position.setMaxLength(255);
        position.setValueChangeMode(ValueChangeMode.EAGER);
        position.setClearButtonVisible(true);
        position.addValueChangeListener(event -> {
                    filter.setPosition(event.getValue());
                    filterChanged(filter);
                }
        );
        position.setSizeFull();
        filterHeader.getCell(getGrid().getColumnByKey(Employee_.ROLE)).setComponent(position);
    }

    @Override
    protected void configureEditor() {
        Editor<EmployeeDto> editor = getGrid().getEditor();
        Binder<EmployeeDto> binder = new Binder<>(EmployeeDto.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        var position = new ComboBox<DictionaryValueDTO>();
        position.setWidthFull();
        position.setItemLabelGenerator(DictionaryValueDTO::getValue);
        binder.forField(position)
                .bind(EmployeeDto::getPosition, EmployeeDto::setPosition);
        getGrid().getColumnByKey(Employee_.ROLE).setEditorComponent(position);

        var name = new TextField();
        name.setWidthFull();
        name.setRequired(true);
        name.setMaxLength(255);
        binder.forField(name)
                .asRequired("Обязательное значение")
                .bind(EmployeeDto::getName, EmployeeDto::setName);
        getGrid().getColumnByKey(Employee_.NAME).setEditorComponent(name);
    }

    @Override
    protected EmployeeDto getEmptyDto() {
        return new EmployeeDto();
    }

    @Override
    protected AbstractEditableGridPagePart<EmployeeDto, EmployeeFilter>.EditEvent getEditEvent(EmployeeDto item) {
        return null;
    }

    @Override
    protected AbstractEditableGridPagePart<EmployeeDto, EmployeeFilter>.SaveEvent getSaveEvent(EmployeeDto item) {
        return null;
    }

    @Override
    protected AbstractEditableGridPagePart<EmployeeDto, EmployeeFilter>.DeleteEvent getDeleteEvent(EmployeeDto item) {
        return null;
    }

    @Override
    protected AbstractEditableGridPagePart<EmployeeDto, EmployeeFilter>.FilterChangedEvent getFilterChanged(EmployeeFilter filter) {
        return null;
    }


}
