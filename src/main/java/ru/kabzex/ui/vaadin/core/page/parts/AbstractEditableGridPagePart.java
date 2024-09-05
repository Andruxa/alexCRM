package ru.kabzex.ui.vaadin.core.page.parts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import ru.kabzex.ui.vaadin.dto.DTO;

import static com.vaadin.flow.component.icon.VaadinIcon.CLOSE;
import static com.vaadin.flow.component.icon.VaadinIcon.EDIT;

/*
D - DTO
F - Filter
 */
public abstract class AbstractEditableGridPagePart<D extends DTO, F> extends AbstractReadOnlyGridPagePart<D, F> {

    public static final String EDIT_COLUMN = "EDIT";

    protected Component editDelButtons(D dto) {
        Button delete = new Button();
        delete.setIcon(new Icon(CLOSE));
        delete.addClickListener(e -> deleteEvent(dto));
        Button edit = new Button();
        edit.setIcon(new Icon(EDIT));
        edit.addClickListener(e -> editEvent(dto));
        return new HorizontalLayout(edit, delete);
    }

    protected abstract void editEvent(D dto);

    protected abstract void deleteEvent(D dto);

}
