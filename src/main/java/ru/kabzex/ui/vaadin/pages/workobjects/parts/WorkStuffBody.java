package ru.kabzex.ui.vaadin.pages.workobjects.parts;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.tabs.Tab;
import lombok.Getter;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractEditableGridPagePart;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractPagePart;
import ru.kabzex.ui.vaadin.dto.work.WorkStageDto;
import ru.kabzex.ui.vaadin.dto.work.WorkStuffDto;
import ru.kabzex.ui.vaadin.dto.work.WorkStuffFilter;

import java.util.Collection;
import java.util.List;

public class WorkStuffBody extends AbstractEditableGridPagePart<WorkStuffDto, WorkStuffFilter> {
    private static final List<String> ALLOWED = List.of(Roles.EMPLOYEE, Roles.ADMIN);
    @Getter
    private final Tab tab = new Tab("Материалы");

    @Override
    protected WorkStuffFilter initFilter() {
        return null;
    }

    @Override
    protected Collection<String> getAllowedRoles() {
        return ALLOWED;
    }

    @Override
    protected void configureFilters(HeaderRow headerRow) {

    }

    @Override
    protected void configureEditor() {

    }

    @Override
    public WorkStuffDto getEmptyDto() {
        return new WorkStuffDto();
    }

    @Override
    protected Grid<WorkStuffDto> initGrid() {
        return null;
    }
}
