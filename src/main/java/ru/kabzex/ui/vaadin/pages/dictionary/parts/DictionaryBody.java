package ru.kabzex.ui.vaadin.pages.dictionary.parts;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import lombok.Getter;
import ru.kabzex.server.entity.dictionary.DictionaryValue_;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractEditableGridPagePart;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractPagePart;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueFilter;

public class DictionaryBody extends AbstractEditableGridPagePart<DictionaryValueDTO, DictionaryValueFilter> {

    private DictionaryValueFilter filter = new DictionaryValueFilter();

    private void editItem(ItemDoubleClickEvent<DictionaryValueDTO> event) {
        editEvent(event.getItem());
    }

    @Override
    public void setDataProvider(DataProvider<DictionaryValueDTO, DictionaryValueFilter> dataProvider) {
        grid.setDataProvider(dataProvider);
    }

    @Override
    public void refresh() {
        this.grid.getDataProvider().refreshAll();
    }

    @Override
    protected Grid<DictionaryValueDTO> configureGrid() {
        var grid = new Grid<>(DictionaryValueDTO.class, false);
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
        grid.addComponentColumn(this::editDelButtons)
                .setFlexGrow(1)
                .setTextAlign(ColumnTextAlign.END);
        grid.setSizeFull();
        grid.setMultiSort(true);
        grid.addItemDoubleClickListener(this::editItem);
        HeaderRow filterHeader = grid.appendHeaderRow();
        //
        TextField valFilter = new TextField();
        valFilter.setMaxLength(255);
        valFilter.setValueChangeMode(ValueChangeMode.EAGER);
        valFilter.setClearButtonVisible(true);
        valFilter.addValueChangeListener(event -> {
                    filter.setValue(event.getValue());
                    filterChanged();
                }
        );
        valFilter.setSizeFull();
        filterHeader.getCell(grid.getColumnByKey(DictionaryValue_.VALUE)).setComponent(valFilter);
        //
        TextField descFilter = new TextField();
        descFilter.setMaxLength(255);
        descFilter.setValueChangeMode(ValueChangeMode.EAGER);
        descFilter.setClearButtonVisible(true);
        descFilter.addValueChangeListener(event -> {
                    filter.setDescription(event.getValue());
                    filterChanged();
                }
        );
        descFilter.setSizeFull();
        filterHeader.getCell(grid.getColumnByKey(DictionaryValue_.DESCRIPTION)).setComponent(descFilter);
        //
        return grid;
    }

    @Override
    protected void editEvent(DictionaryValueDTO valueDTO) {
        fireEvent(new RecordEditEvent(this, valueDTO));
    }

    @Override
    protected void deleteEvent(DictionaryValueDTO valueDTO) {
        fireEvent(new RecordDeleteEvent(this, valueDTO));
    }

    private void filterChanged() {
        fireEvent(new FilterChangedEvent(this, filter));
    }

    public class FilterChangedEvent extends BodyEvent {
        @Getter
        private DictionaryValueFilter filter;

        protected FilterChangedEvent(DictionaryBody source, DictionaryValueFilter value) {
            super(source, null);
            this.filter = value;
        }
    }

    public class BodyEvent extends PagePartEvent<DictionaryValueDTO> {

        protected BodyEvent(AbstractPagePart source, DictionaryValueDTO entity) {
            super(source, entity);
        }

    }

    public class RecordEditEvent extends BodyEvent {
        protected RecordEditEvent(DictionaryBody source, DictionaryValueDTO value) {
            super(source, value);
        }
    }

    public class RecordDeleteEvent extends BodyEvent {
        protected RecordDeleteEvent(DictionaryBody source, DictionaryValueDTO value) {
            super(source, value);
        }
    }
}
