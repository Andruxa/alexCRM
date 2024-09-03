package ru.kabzex.ui.vaadin.core.page.parts;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import lombok.Getter;
import ru.kabzex.ui.vaadin.dto.DTO;

/*
D - DTO
F - Filter
 */
public abstract class AbstractGridAndFormPagePart<D extends DTO, F> extends AbstractReadOnlyGridPagePart<D, F> {

    @Getter
    protected AbstractForm form;

    protected AbstractGridAndFormPagePart() {
        setSizeFull();
        addClassName("list-view");
    }

    @Override
    protected void configurePart() {
        form = configureForm();
        grid = configureGrid();
        grid.setSizeFull();
        grid.addClassName("value-grid");
        if (form != null) {
            form.setVisible(false);
            grid.asSingleSelect().addValueChangeListener(this::showForm);
            HorizontalLayout content = new HorizontalLayout(grid, form);
            content.setFlexGrow(2, grid);
            content.setFlexGrow(1, form);
            content.addClassName("content");
            content.setSizeFull();
            add(content);
        } else {
            add(grid);
        }
    }

    protected abstract void showForm(AbstractField.ComponentValueChangeEvent<Grid<D>, D> event);

    protected abstract AbstractForm configureForm();

}
