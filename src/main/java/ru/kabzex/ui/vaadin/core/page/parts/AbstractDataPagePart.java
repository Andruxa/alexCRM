package ru.kabzex.ui.vaadin.core.page.parts;


import ru.kabzex.ui.vaadin.dto.DTO;

import java.util.Collection;

public abstract class AbstractDataPagePart<D extends DTO> extends AbstractPagePart {
    public abstract void setData(Collection<D> data);

}
