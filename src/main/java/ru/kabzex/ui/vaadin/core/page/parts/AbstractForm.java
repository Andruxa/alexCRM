package ru.kabzex.ui.vaadin.core.page.parts;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import ru.kabzex.server.exception.EntityDialogRequiredFieldsException;
import ru.kabzex.ui.vaadin.dto.AbstractDTO;
import ru.kabzex.ui.vaadin.utils.NotificationUtils;

import java.util.List;


public abstract class AbstractForm<E extends AbstractDTO> extends AbstractPagePart {

    private E entity;
    private Button delete;
    private Button save;

    protected AbstractForm() {
        add(createBody(),
                createButtonsLayout());
        getBinder().bindInstanceFields(this);
    }

    public void setEntity(E entity) {
        getBinder().readBean(entity);
        this.entity = entity;
    }

    public abstract Component createBody();

    public abstract Binder<E> getBinder();

    private HorizontalLayout createButtonsLayout() {
        save = new Button("Сохранить");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.addClickListener(event -> validateAndSave());
        delete = new Button("Удалить");
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.addClickListener(event -> deleteButtonEvent());
        Button close = new Button("Отмена");
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        close.addClickListener(event -> this.close());
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            validateFields();
            saveButtonEvent();
        } catch (EntityDialogRequiredFieldsException e) {
            NotificationUtils.showMessage("Обязательные поля не заполнены либо заполнены некорректно!");
        }
    }

    public void hideDeletion() {
        this.delete.setVisible(false);
    }

    public void setSaveButtonEnabled(boolean value) {
        save.setEnabled(value);
    }

    public void close() {
        setVisible(false);
        setEntity(null);
        removeClassName("editing");
        closeButtonEvent();
    }

    public void show(E entity) {
        if (entity == null) {
            close();
        } else {
            setEntity(entity);
            getRequiredFields().forEach(f -> f.setRequiredIndicatorVisible(true));
            setVisible(true);
            addClassName("editing");
        }
    }

    protected abstract void closeButtonEvent();

    protected abstract void deleteButtonEvent();

    protected abstract void saveButtonEvent();

    protected abstract E createEmptyEntity();

    public E getEntity() {
        if (entity == null) {
            return createEmptyEntity();
        } else return entity;
    }

    protected abstract List<AbstractField> getRequiredFields();

    protected void validateFields() throws EntityDialogRequiredFieldsException {
        long count = getRequiredFields().stream()
                .filter(AbstractField::isEmpty)
                .map(HasValidation.class::cast)
                .peek(hv -> hv.setInvalid(true))
                .count();
        if (count > 0) throw new EntityDialogRequiredFieldsException(String.format("%d полей не заполнено", count));
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
