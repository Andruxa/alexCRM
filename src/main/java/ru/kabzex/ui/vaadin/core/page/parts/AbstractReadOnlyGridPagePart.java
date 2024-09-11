package ru.kabzex.ui.vaadin.core.page.parts;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.DataProvider;
import lombok.Getter;
import ru.kabzex.ui.vaadin.dto.DTO;

public abstract class AbstractReadOnlyGridPagePart<D extends DTO, F> extends AbstractDataPagePart<DataProvider<D, F>, D> {

    @Getter
    private final Grid<D> grid;

    protected AbstractReadOnlyGridPagePart() {
        this.grid = initGrid();
        this.grid.setSizeFull();
        add(grid);
    }

    protected abstract Grid<D> initGrid();

    @Override
    public void setData(DataProvider<D, F> data) {
        getGrid().setDataProvider(data);
    }

    public void refresh() {
        getGrid().getDataProvider().refreshAll();
    }


}
