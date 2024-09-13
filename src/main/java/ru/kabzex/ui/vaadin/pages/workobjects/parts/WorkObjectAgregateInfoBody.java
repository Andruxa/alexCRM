package ru.kabzex.ui.vaadin.pages.workobjects.parts;

import com.vaadin.flow.component.tabs.Tab;
import lombok.Getter;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractDataPagePart;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectDto;

import java.util.Collection;
import java.util.List;

public class WorkObjectAgregateInfoBody extends AbstractDataPagePart<Collection<WorkObjectDto>> {
    private static final List<String> ALLOWED = List.of(Roles.EMPLOYEE, Roles.ADMIN);
    @Getter
    private final Tab tab = new Tab("Инфо");

    public WorkObjectAgregateInfoBody() {
    }

    @Override
    public void setData(Collection<WorkObjectDto> data) {

    }

    @Override
    public void refresh() {

    }
}
