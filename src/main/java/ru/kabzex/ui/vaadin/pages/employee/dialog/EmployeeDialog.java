package ru.kabzex.ui.vaadin.pages.employee.dialog;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import ru.kabzex.ui.vaadin.core.dialog.AbstractEntityDialog;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;

import java.util.Arrays;
import java.util.List;

public class EmployeeDialog extends AbstractEntityDialog<EmployeeDto> {
    TextField name;
    ComboBox<DictionaryValueDTO> position;
    private Binder<EmployeeDto> binder;

    public void setPosition(List<DictionaryValueDTO> dtos) {
        position.setItems(dtos);
    }

    @Override
    protected EmployeeDto constructEmptyItem() {
        return new EmployeeDto();
    }

    @Override
    protected Component createBody() {
        position = new ComboBox<>("Должность");
        name = new TextField("ФИО сотрудника");
        name.setMaxLength(255);
        name.setSizeFull();
        position.setItemLabelGenerator(DictionaryValueDTO::getValue);
        position.setSizeFull();
        position.setRequiredIndicatorVisible(true);
        VerticalLayout body = new VerticalLayout(name,
                position);
        body.setWidthFull();
        return body;
    }

    @Override
    protected Binder<EmployeeDto> getBinder() {
        if (this.binder == null) {
            this.binder = new Binder<>(EmployeeDto.class);
        }
        return this.binder;
    }

    @Override
    protected List<AbstractField> getRequiredFields() {
        return Arrays.asList(name, position);
    }

    @Override
    protected void fireConfirm(EmployeeDto item) {
        fireEvent(new ItemConfirmedEvent(this, item));
    }

    public class ItemConfirmedEvent extends PageDialogEvent {

        protected ItemConfirmedEvent(AbstractEntityDialog source, EmployeeDto entity) {
            super(source, entity);
        }
    }
}
