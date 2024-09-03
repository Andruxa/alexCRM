package ru.kabzex.ui.vaadin.core.page.parts;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.shared.Registration;

public abstract class AbstractHeader extends FlexLayout {

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public class HeaderEvent extends ComponentEvent<AbstractHeader> {

        protected HeaderEvent(AbstractHeader source) {
            super(source, false);
        }

        protected HeaderEvent(AbstractHeader source, boolean fromClient) {
            super(source, fromClient);
        }

    }

}
