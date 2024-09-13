package ru.kabzex.ui.vaadin.pages.workobjects.parts;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.tabs.Tab;
import lombok.Getter;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractEditableGridPagePart;
import ru.kabzex.ui.vaadin.dto.work.WorkActivityDto;
import ru.kabzex.ui.vaadin.dto.work.WorkActivityFilter;

import java.util.Collection;
import java.util.List;

public class WorkActivitiesBody extends AbstractEditableGridPagePart<WorkActivityDto, WorkActivityFilter> {
    private static final List<String> ALLOWED = List.of(Roles.EMPLOYEE, Roles.ADMIN);
    @Getter
    private final Tab tab = new Tab("Работы");

    @Override
    protected WorkActivityFilter initFilter() {
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
    public WorkActivityDto getEmptyDto() {
        return new WorkActivityDto();
    }

    @Override
    protected Grid<WorkActivityDto> initGrid() {
        return null;
    }
}
