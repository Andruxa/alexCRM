package ru.kabzex.ui.vaadin.core.page.parts;


import com.vaadin.flow.component.AttachEvent;

public abstract class AbstractDataPagePart<DataContainer, D> extends AbstractPagePart {
    public abstract void setData(DataContainer data);

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        fireEvent(getOnAttachEvent());
    }

    protected abstract AttachedEvent getOnAttachEvent();

    public class AttachedEvent extends PagePartEvent<D> {

        protected AttachedEvent(AbstractPagePart source) {
            super(source);
        }
    }
}
