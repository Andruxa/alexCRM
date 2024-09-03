package ru.kabzex.ui.vaadin.pages.owner.dictionary.dialog;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import ru.kabzex.ui.vaadin.core.dialog.AbstractEntityDialog;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryTypeDTO;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;

import java.util.Arrays;
import java.util.List;

public class DictionaryItemDialog extends AbstractEntityDialog<DictionaryValueDTO> {

    ComboBox<DictionaryTypeDTO> type;
    TextField value;
    TextArea description;
    private Binder<DictionaryValueDTO> binder;

    @Override
    protected DictionaryValueDTO constructEmptyItem() {
        return new DictionaryValueDTO();
    }

    @Override
    protected Component createBody() {
        type = new ComboBox<>("Справочник");
        type.setItemLabelGenerator(DictionaryTypeDTO::getName);
        type.setRequired(true);
        type.setWidthFull();
        value = new TextField("Значение");
        value.setRequired(true);
        value.setWidthFull();
        value.setMaxLength(255);
        description = new TextArea("Описание");
        description.setWidthFull();
        description.setMaxLength(512);
        VerticalLayout body = new VerticalLayout(type, value, description);
        body.setWidthFull();
        return body;
    }

    public void setTypeItems(List<DictionaryTypeDTO> dtos) {
        this.type.setItems(dtos);
    }

    @Override
    protected Binder<DictionaryValueDTO> getBinder() {
        if (this.binder == null) {
            this.binder = new Binder<>(DictionaryValueDTO.class);
        }
        return this.binder;
    }

    @Override
    protected List<AbstractField> getRequiredFields() {
        return Arrays.asList(type, value);
    }

    @Override
    protected void fireConfirm(DictionaryValueDTO item) {
        fireEvent(new DictionaryValueConfirmedEvent(this, item));
    }

    public class DictionaryValueConfirmedEvent extends PageDialogEvent {

        protected DictionaryValueConfirmedEvent(AbstractEntityDialog source, DictionaryValueDTO entity) {
            super(source, entity);
        }
    }
}
