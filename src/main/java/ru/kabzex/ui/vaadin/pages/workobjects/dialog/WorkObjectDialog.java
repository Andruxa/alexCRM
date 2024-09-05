package ru.kabzex.ui.vaadin.pages.workobjects.dialog;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import ru.kabzex.ui.vaadin.core.dialog.AbstractEntityDialog;
import ru.kabzex.ui.vaadin.dto.document.ContractDto;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectDto;

import java.util.List;

public class WorkObjectDialog extends AbstractEntityDialog<WorkObjectDto> {
    private TextField name;
    private TextArea address;
    private ComboBox<EmployeeDto> employee;
    private ComboBox<ContractDto> objectContract;

    private Binder<WorkObjectDto> binder;

    @Override
    protected WorkObjectDto constructEmptyItem() {
        return new WorkObjectDto();
    }

    @Override
    protected Component createBody() {
        name = new TextField("Название");
        name.setWidthFull();
        name.setMaxLength(255);
        address = new TextArea("Описание");
        address.setWidthFull();
        address.setMaxLength(512);
        objectContract = new ComboBox<>("Договор");
        objectContract.setItemLabelGenerator(ContractDto::getFullDescription);
        objectContract.setWidthFull();
        employee = new ComboBox<>("Менеджер");
        employee.setItemLabelGenerator(EmployeeDto::getName);
        employee.setWidthFull();
        VerticalLayout body = new VerticalLayout(name, address, objectContract, employee);
        body.setWidthFull();
        return body;
    }

    @Override
    protected Binder<WorkObjectDto> getBinder() {
        if (this.binder == null) {
            this.binder = new Binder<>(WorkObjectDto.class);
        }
        return this.binder;
    }

    @Override
    protected List<AbstractField> getRequiredFields() {
        return null;
    }

    @Override
    protected void fireConfirm(WorkObjectDto item) {

    }
}


