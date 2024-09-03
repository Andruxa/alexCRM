package ru.kabzex.ui.vaadin.pages.dictionary.parts;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.NativeLabel;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractDataPagePart;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractPagePart;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryTypeDTO;

import java.util.Collection;

public class DictionaryHeader extends AbstractDataPagePart<DictionaryTypeDTO> {
    private ComboBox<DictionaryTypeDTO> comboBox;

    public DictionaryHeader() {
        setWidth("100%");
        setFlexDirection(FlexDirection.COLUMN);
        comboBox = new ComboBox<>();
        comboBox.setPlaceholder("Выберите справочник");
        comboBox.setItemLabelGenerator(DictionaryTypeDTO::getName);
        comboBox.setWidth("100%");
        comboBox.setClearButtonVisible(true);
        comboBox.addValueChangeListener(this::comboboxValueChanged);
        comboBox.setClearButtonVisible(true);
        var label = new NativeLabel("Доступные справочники:");
        add(label, comboBox);
    }

    public String getSelectedType() {
        return this.comboBox.getValue() != null ?
                this.comboBox.getValue().getName() : null;
    }

    private void comboboxValueChanged(ComponentEvent<ComboBox<DictionaryTypeDTO>> event) {
        fireEvent(new ComboboxChangedEvent(this, event.getSource().getValue()));
    }

    @Override
    public void setData(Collection<DictionaryTypeDTO> data) {
        comboBox.setItems(data);
    }

    public class HeaderEvent extends PagePartEvent<DictionaryTypeDTO> {

        protected HeaderEvent(AbstractPagePart source, DictionaryTypeDTO entity) {
            super(source, entity);
        }

    }

    public class ComboboxChangedEvent extends HeaderEvent {
        protected ComboboxChangedEvent(DictionaryHeader source, DictionaryTypeDTO value) {
            super(source, value);
        }
    }

}
