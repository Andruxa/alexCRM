package ru.kabzex.ui.vaadin.core.dialog;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractForm;
import ru.kabzex.ui.vaadin.dto.AbstractDTO;

public abstract class AbstractFormBasedDialog<D extends AbstractDTO> extends Dialog {
    @Getter
    private AbstractForm<D> form;
    private NativeLabel title;

    protected AbstractFormBasedDialog() {
        form = createForm();
        add(createHeader(), form);
        setWidth("40%");
    }

    public abstract AbstractForm<D> createForm();

    public void setItem(D item) {
        form.setEntity(item);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    protected Component createHeader() {
        this.title = new NativeLabel();
        HorizontalLayout header = new HorizontalLayout();
        header.add(this.title);
        header.setFlexGrow(1, this.title);
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        return header;
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
