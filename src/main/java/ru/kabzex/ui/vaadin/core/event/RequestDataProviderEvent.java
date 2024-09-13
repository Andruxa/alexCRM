package ru.kabzex.ui.vaadin.core.event;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.combobox.ComboBox;
import lombok.Getter;
import ru.kabzex.ui.vaadin.dto.DTOFilter;

@Getter
public class RequestDataProviderEvent extends ComponentEvent<ComboBox> {

    Class dataClass;
    Class entityClass;
    DTOFilter filter;

    public RequestDataProviderEvent(ComboBox source, Class dataClass, Class entityClass, DTOFilter filter) {
        super(source, false);
        this.dataClass = dataClass;
        this.entityClass = entityClass;
        this.filter = filter;
    }
}
