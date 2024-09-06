package ru.kabzex.ui.vaadin.core.page.parts;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.data.provider.DataProvider;
import lombok.Getter;
import ru.kabzex.ui.vaadin.dto.DTO;

public abstract class AbstractReadOnlyGridPagePart<D extends DTO, F> extends AbstractPagePart {

    @Getter
    private final Grid<D> grid;

    protected AbstractReadOnlyGridPagePart() {
        this.grid = initGrid();
        this.grid.setSizeFull();
        add(grid);
    }

    protected abstract Grid<D> initGrid();

    public void setDataProvider(DataProvider<D, F> dataProvider) {
        getGrid().setDataProvider(dataProvider);
    }

    public void refresh() {
        getGrid().getDataProvider().refreshAll();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        setSizeFull();
    }


}
