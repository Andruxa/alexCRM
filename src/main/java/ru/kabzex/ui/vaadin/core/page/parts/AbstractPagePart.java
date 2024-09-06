package ru.kabzex.ui.vaadin.core.page.parts;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;

import java.util.Collection;

public abstract class AbstractPagePart extends FlexLayout {

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public class PagePartEvent<E> extends ComponentEvent<AbstractPagePart> {
        @Getter
        private E entity;

        protected PagePartEvent(AbstractPagePart source, E entity) {
            super(source, false);
            this.entity = entity;
        }

        protected PagePartEvent(AbstractPagePart source, boolean fromClient) {
            super(source, fromClient);
        }

        public PagePartEvent(AbstractPagePart source) {
            super(source, false);
        }
    }

}
