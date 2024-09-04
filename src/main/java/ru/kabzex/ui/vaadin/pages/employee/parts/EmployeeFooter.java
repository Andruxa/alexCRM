package ru.kabzex.ui.vaadin.pages.employee.parts;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractPagePart;

public class EmployeeFooter extends AbstractPagePart {
    public EmployeeFooter() {
        Button addValueButton = new Button("Добавить сотрудника", this::fireNewValue);
        setWidth("100%");
        setFlexDirection(FlexDirection.ROW);
        add(addValueButton);
    }

    private void fireNewValue(ClickEvent<Button> event) {
        fireEvent(new NewValueEvent(this));
    }

    public class NewValueEvent extends PagePartEvent<String> {
        protected NewValueEvent(EmployeeFooter source) {
            super(source, null);
        }
    }
}
