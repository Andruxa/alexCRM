package ru.kabzex.ui.vaadin.pages.dictionary.parts;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import ru.kabzex.server.entity.dictionary.DictionaryValue_;
import ru.kabzex.server.enums.Dictionary;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractEditableGridPagePart;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueFilter;

import java.util.Collection;
import java.util.List;

public class DictionaryBody extends AbstractEditableGridPagePart<DictionaryValueDTO, DictionaryValueFilter> {

    private static final List<String> ALLOWED = List.of(Roles.EMPLOYEE, Roles.ADMIN);
    private final DictionaryValueFilter filter = new DictionaryValueFilter();

    @Override
    protected Grid<DictionaryValueDTO> initGrid() {
        var grid = new Grid<>(DictionaryValueDTO.class, false);
        grid.addColumn(this::parseType)
                .setKey(DictionaryValue_.DICTIONARY)
                .setSortProperty(DictionaryValue_.dictionary.getName())
                .setHeader("Справочник")
                .setFlexGrow(3);
        grid.addColumn(DictionaryValueDTO::getValue)
                .setKey(DictionaryValue_.VALUE)
                .setSortProperty(DictionaryValue_.value.getName())
                .setHeader("Значение")
                .setFlexGrow(3);
        grid.addColumn(DictionaryValueDTO::getDescription)
                .setHeader("Описание")
                .setSortProperty(DictionaryValue_.description.getName())
                .setKey(DictionaryValue_.DESCRIPTION)
                .setFlexGrow(3);
        grid.setSizeFull();
        grid.setMultiSort(true);
        grid.addItemDoubleClickListener(this::editItem);
        return grid;
    }

    private String parseType(DictionaryValueDTO dictionaryValueDTO) {
        return dictionaryValueDTO.getType().getDescription();
    }

    @Override
    protected Collection<String> getAllowedRoles() {
        return ALLOWED;
    }

    @Override
    protected void configureFilters(HeaderRow headerRow) {
        HeaderRow filterHeader = headerRow;
        //
        var typeFilter = new ComboBox<Dictionary>();
        typeFilter.setItemLabelGenerator(Dictionary::getDescription);
        typeFilter.setClearButtonVisible(true);
        typeFilter.setItems(Dictionary.values());
        typeFilter.addValueChangeListener(event -> {
                    filter.setType(event.getValue());
                    filterChanged(filter);
                }
        );
        typeFilter.setSizeFull();
        filterHeader.getCell(getGrid().getColumnByKey(DictionaryValue_.DICTIONARY)).setComponent(typeFilter);
        //
        TextField valFilter = new TextField();
        valFilter.setMaxLength(255);
        valFilter.setValueChangeMode(ValueChangeMode.EAGER);
        valFilter.setClearButtonVisible(true);
        valFilter.addValueChangeListener(event -> {
                    filter.setValue(event.getValue());
                    filterChanged(filter);
                }
        );
        valFilter.setSizeFull();
        filterHeader.getCell(getGrid().getColumnByKey(DictionaryValue_.VALUE)).setComponent(valFilter);
        //
        TextField descFilter = new TextField();
        descFilter.setMaxLength(255);
        descFilter.setValueChangeMode(ValueChangeMode.EAGER);
        descFilter.setClearButtonVisible(true);
        descFilter.addValueChangeListener(event -> {
                    filter.setDescription(event.getValue());
                    filterChanged(filter);
                }
        );
        descFilter.setSizeFull();
        filterHeader.getCell(getGrid().getColumnByKey(DictionaryValue_.DESCRIPTION)).setComponent(descFilter);
    }

    @Override
    protected void configureEditor() {
        Editor<DictionaryValueDTO> editor = getGrid().getEditor();
        Binder<DictionaryValueDTO> binder = new Binder<>(DictionaryValueDTO.class);
        editor.setBinder(binder);
        editor.setBuffered(true);

        var type = new ComboBox<Dictionary>();
        type.setWidthFull();
        type.setItemLabelGenerator(Dictionary::getDescription);
        type.setItems(Dictionary.values());
        binder.forField(type)
                .bind(DictionaryValueDTO::getType, DictionaryValueDTO::setType);
        getGrid().getColumnByKey(DictionaryValue_.DICTIONARY).setEditorComponent(type);

        var value = new TextField();
        value.setWidthFull();
        value.setRequired(true);
        value.setMaxLength(255);
        binder.forField(value)
                .asRequired("Обязательное значение")
                .bind(DictionaryValueDTO::getValue, DictionaryValueDTO::setValue);
        getGrid().getColumnByKey(DictionaryValue_.VALUE).setEditorComponent(value);

        var description = new TextArea();
        description.setMaxLength(512);
        binder.forField(description)
                .bind(DictionaryValueDTO::getDescription, DictionaryValueDTO::setDescription);
        getGrid().getColumnByKey(DictionaryValue_.DESCRIPTION).setEditorComponent(description);
    }

    @Override
    protected DictionaryValueDTO getEmptyDto() {
        return new DictionaryValueDTO();
    }

    @Override
    protected EditEvent getEditEvent(DictionaryValueDTO item) {
        return new EditEvent(this, item);
    }

    @Override
    protected SaveEvent getSaveEvent(DictionaryValueDTO item) {
        return new SaveEvent(this, item);
    }

    @Override
    protected DeleteEvent getDeleteEvent(DictionaryValueDTO item) {
        return new DeleteEvent(this, item);
    }

    @Override
    protected FilterChangedEvent getFilterChanged(DictionaryValueFilter filter) {
        return new FilterChangedEvent(this, filter);
    }

    public class EditEvent extends AbstractEditableGridPagePart<DictionaryValueDTO, DictionaryValueFilter>.EditEvent {

        protected EditEvent(DictionaryBody source, DictionaryValueDTO dto) {
            super(source, dto);
        }
    }

    public class DeleteEvent extends AbstractEditableGridPagePart<DictionaryValueDTO, DictionaryValueFilter>.DeleteEvent {

        protected DeleteEvent(DictionaryBody source, DictionaryValueDTO dto) {
            super(source, dto);
        }
    }

    public class SaveEvent extends AbstractEditableGridPagePart<DictionaryValueDTO, DictionaryValueFilter>.SaveEvent {

        protected SaveEvent(DictionaryBody source, DictionaryValueDTO dto) {
            super(source, dto);
        }
    }

    public class FilterChangedEvent extends AbstractEditableGridPagePart<DictionaryValueDTO, DictionaryValueFilter>.FilterChangedEvent {

        protected FilterChangedEvent(DictionaryBody source, DictionaryValueFilter filter) {
            super(source, filter);
        }
    }

}
