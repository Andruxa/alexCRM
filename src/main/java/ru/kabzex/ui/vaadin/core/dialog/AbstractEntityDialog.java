package ru.kabzex.ui.vaadin.core.dialog;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;
import ru.kabzex.server.exception.EntityDialogException;
import ru.kabzex.server.exception.EntityDialogRequiredFieldsException;
import ru.kabzex.ui.vaadin.dto.DTO;
import ru.kabzex.ui.vaadin.utils.NotificationUtils;

import java.util.List;

public abstract class AbstractEntityDialog<D extends DTO> extends Dialog {
    private D item;
    private NativeLabel title;
    @Getter
    Button confirm;

    protected AbstractEntityDialog() {
        removeAll();
        getItem();
        add(new VerticalLayout(createHeader(),
                createBody(),
                createFooter()));
        getBinder().bindInstanceFields(this);
        setWidth("40%");
        setCloseOnEsc(false);
        setCloseOnOutsideClick(false);
    }

    protected D getItem() {
        if (this.item == null) {
            this.item = constructEmptyItem();
        }
        return this.item;
    }

    protected abstract D constructEmptyItem();

    public void setItem(D item) {
        getBinder().readBean(item);
        this.item = item;
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

    protected abstract Component createBody();

    protected abstract Binder<D> getBinder();

    protected Component createFooter() {
        Button cancel = new Button("Отмена");
        cancel.addClickListener(this::canceled);
        confirm = new Button("Сохранить");
        confirm.addClickListener(this::confirmed);
        HorizontalLayout footer = new HorizontalLayout();
        footer.add(confirm, cancel);
        footer.setWidthFull();
        footer.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        return footer;
    }

    protected void canceled(ClickEvent<Button> event) {
        new ConfirmDialog("Отмена изменений", "После нажатия изменения будут отменены",
                ev -> close())
                .open();
    }

    protected void writeBean() throws ValidationException {
        getBinder().writeBean(getItem());
    }

    private void confirmed(ClickEvent<Button> event) {
        try {
            validateFields();
            writeBean();
            openConfirmDialog(item);
        } catch (ValidationException e) {
            throw new EntityDialogException(e);
        } catch (EntityDialogRequiredFieldsException e) {
            NotificationUtils.showMessage("Обязательные поля не заполнены либо заполнены некорректно!");
        }
    }

    protected void validateFields() throws EntityDialogRequiredFieldsException {
        long count = getRequiredFields().stream()
                .filter(AbstractField::isEmpty)
                .map(HasValidation.class::cast)
                .peek(hv -> hv.setInvalid(true))
                .count();
        if (count > 0) throw new EntityDialogRequiredFieldsException(String.format("%d полей не заполнено", count));
    }

    protected abstract List<AbstractField> getRequiredFields();

    protected abstract void fireConfirm(D item);

    private void openConfirmDialog(D item) {
        ConfirmDialog confirmationDialog = new ConfirmDialog("Сохранение изменений",
                "Подтверждаете изменения,",
                e -> {
                    fireConfirm(item);
                    this.close();
                });
        confirmationDialog.open();
    }

    @Override
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    public class PageDialogEvent extends ComponentEvent<AbstractEntityDialog> {
        @Getter
        private D entity;

        protected PageDialogEvent(AbstractEntityDialog source, D entity) {
            super(source, false);
            this.entity = entity;
        }

        protected PageDialogEvent(AbstractEntityDialog source, boolean fromClient) {
            super(source, fromClient);
        }

        public PageDialogEvent(AbstractEntityDialog source) {
            super(source, false);
        }
    }

    protected void emptyHandler(Object o1, Object o2) {
    }

    public void setSaveButtonEnabled(boolean value) {
        confirm.setEnabled(value);
    }
}
