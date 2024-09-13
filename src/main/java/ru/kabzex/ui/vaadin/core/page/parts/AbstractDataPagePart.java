package ru.kabzex.ui.vaadin.core.page.parts;

import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.combobox.ComboBox;
import ru.kabzex.server.entity.AbstractEntity;
import ru.kabzex.ui.vaadin.core.event.RequestDataProviderEvent;
import ru.kabzex.ui.vaadin.dto.AbstractDTO;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueFilter;

import java.util.Collection;

public abstract class AbstractDataPagePart<D> extends AbstractPagePart {

    protected Collection<String> currentRoles;

    public abstract void setData(D dataContainer);

    public abstract void refresh();

    public void setCurrentRoles(Collection<String> cr) {
        this.currentRoles = cr;
    }

    protected void dataProviderRequired(ComboBox source,
                                        Class<? extends AbstractDTO> dtoClass,
                                        Class<? extends AbstractEntity> entityClass, DictionaryValueFilter dFilter) {
        ComponentUtil.fireEvent(this, new RequestDataProviderEvent(source, dtoClass, entityClass, dFilter));
    }
}
