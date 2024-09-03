package ru.kabzex.ui.vaadin.core.page.parts;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.DataProvider;
import lombok.Getter;
import ru.kabzex.ui.vaadin.dto.DTO;

public abstract class AbstractReadOnlyGridPagePart<D extends DTO, F> extends AbstractPagePart {

    @Getter
    protected Grid<D> grid;

    public abstract void setDataProvider(DataProvider<D, F> dataProvider);

    public abstract void refresh();

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        setSizeFull();
        configurePart();
    }

    protected void configurePart() {
        grid = configureGrid();
        grid.setSizeFull();
        grid.addClassName("value-grid");
        add(grid);
    }

    protected abstract Grid<D> configureGrid();

}
