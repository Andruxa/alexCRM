package ru.kabzex.ui.vaadin.pages.workobjects.parts;

import com.vaadin.flow.component.tabs.Tab;
import lombok.Getter;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractDataPagePart;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectDto;

import java.util.Collection;

public class WorkObjectAgregateInfoBody extends AbstractDataPagePart<Collection<WorkObjectDto>> {
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
