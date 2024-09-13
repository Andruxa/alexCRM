package ru.kabzex.ui.vaadin.core.page.parts;

import java.util.Collection;

public abstract class AbstractDataPagePart<D> extends AbstractPagePart {

    protected Collection<String> currentRoles;

    public abstract void setData(D dataContainer);

    public abstract void refresh();

    public void setCurrentRoles(Collection<String> cr) {
        this.currentRoles = cr;
    }
}
