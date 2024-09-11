package ru.kabzex.ui.vaadin.pages.dictionary.parts;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.NativeLabel;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractDataPagePart;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractPagePart;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryTypeDTO;

import java.util.Collection;

public class DictionaryHeader extends AbstractDataPagePart<Collection<DictionaryTypeDTO>, DictionaryTypeDTO> {
    private final ComboBox<DictionaryTypeDTO> comboBox;

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

    private void comboboxValueChanged(ComponentEvent<ComboBox<DictionaryTypeDTO>> event) {
        fireEvent(new ComboboxChangedEvent(this, event.getSource().getValue()));
    }

    @Override
    public void setData(Collection<DictionaryTypeDTO> data) {
        comboBox.setItems(data);
    }

    @Override
    protected AttachedEvent getOnAttachEvent() {
        return new AttachedEvent(this);
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

    public class AttachedEvent extends AbstractDataPagePart.AttachedEvent {

        protected AttachedEvent(AbstractPagePart source) {
            super(source);
        }

    }
}
