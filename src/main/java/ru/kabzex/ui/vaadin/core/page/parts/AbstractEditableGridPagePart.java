package ru.kabzex.ui.vaadin.core.page.parts;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.ItemDoubleClickEvent;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import ru.kabzex.ui.vaadin.core.dialog.ConfirmDialog;
import ru.kabzex.ui.vaadin.dto.AbstractDTO;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;
import ru.kabzex.ui.vaadin.utils.NotificationUtils;

import java.util.Collection;
import java.util.Optional;

import static com.vaadin.flow.component.icon.VaadinIcon.*;

/*
D - DTO
F - Filter
 */
public abstract class AbstractEditableGridPagePart<D extends AbstractDTO, F> extends AbstractReadOnlyGridPagePart<D, F> {

    public static final String EDIT_COLUMN = "EDIT";
    protected Button addButton;

    protected abstract Collection<String> getAllowedRoles();

    protected abstract void configureFilters(HeaderRow headerRow);

    protected abstract void configureEditor();

    protected abstract D getEmptyDto();

    private DataProvider dataProvider;

    protected Component editDelButtons(D dto) {
        Button delete = new Button();
        delete.setIcon(new Icon(CLOSE));
        delete.addClickListener(e -> deleteEvent(dto));
        Button edit = new Button();
        edit.setIcon(new Icon(EDIT));
        edit.addClickListener(e -> editEvent(dto));
        return new HorizontalLayout(edit, delete);
    }

    private void deleteEvent(D dto) {
        ConfirmDialog confirmationDialog = new ConfirmDialog("Удаление",
                "После нажатия создание запись будет удалена",
                e -> fireEvent(new DeleteEvent(this, dto)));
        confirmationDialog.open();
    }

    protected void editEvent(D dto) {
        if (getGrid().getEditor().isOpen())
            getGrid().getEditor().cancel();
        getGrid().getEditor().editItem(dto);
    }

    protected void editorSave(Editor<D> editor) {
        var item = Optional.ofNullable(editor.getItem());
        if (editor.save() && item.isPresent()) {
            if (item.map(AbstractDTO::getId).isEmpty()) {
                ConfirmDialog confirmationDialog = new ConfirmDialog("Сохранение записи",
                        "После нажатия запись будет сохранена",
                        e -> {
                            if (dataProvider != null) {
                                getGrid().setDataProvider(dataProvider);
                            } else {
                                getGrid().setDataProvider(DataProvider.ofItems());
                            }
                            fireEvent(new SaveEvent(this, item.get()));
                        });
                confirmationDialog.open();
            } else {
                ConfirmDialog confirmationDialog = new ConfirmDialog("Обновление записи",
                        "После нажатия запись будет сохранена",
                        e -> fireEvent(new EditEvent(this, item.get())));
                confirmationDialog.open();
            }
        }
    }

    protected void editorCanceled(Editor<D> editor) {
        var item = Optional.ofNullable(editor.getItem());
        if (item.map(AbstractDTO::getId).isEmpty()) {
            ConfirmDialog confirmationDialog = new ConfirmDialog("Отмена создания",
                    "После нажатия создание записи будет отменено",
                    e -> {
                        if (dataProvider != null) {
                            getGrid().setDataProvider(dataProvider);
                        } else {
                            getGrid().setDataProvider(DataProvider.ofItems());
                        }
                        editor.cancel();
                    });
            confirmationDialog.open();
        } else {
            ConfirmDialog confirmationDialog = new ConfirmDialog("Отмена изменений",
                    "После нажатия изменения будут отменены",
                    e -> editor.cancel());
            confirmationDialog.open();
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        if (currentRoles != null &&
                currentRoles.stream().anyMatch(getAllowedRoles()::contains)) {
            getGrid().addComponentColumn(this::editDelButtons);
            getGrid().getColumnByKey(EDIT_COLUMN).setEditorComponent(this::editorButtons);
            configureEditor();
            configureFilters(getGrid().appendHeaderRow());
            configureAddButton(getGrid().appendFooterRow());
        }
    }

    private Component editorButtons(D d) {
        Button saveButton = new Button(CHECK.create(), e -> editorSave(getGrid().getEditor()));
        Button cancelButton = new Button(CLOSE.create(), e -> editorCanceled(getGrid().getEditor()));
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,
                cancelButton);
        actions.setPadding(false);
        return actions;
    }

    private void configureAddButton(FooterRow footerRow) {
        addButton = new Button("Добавить");
        addButton.addClickListener(this::createEvent);
        var fcell = footerRow.join(footerRow.getCells());
        fcell.setComponent(addButton);
    }

    private void createEvent(ClickEvent<Button> event) {
        if (currentRoles.stream().anyMatch(getAllowedRoles()::contains)) {
            if (getGrid().getEditor().isOpen()) {
                NotificationUtils.showMessage("Запись уже редактируется");
            } else {
                dataProvider = getGrid().getDataProvider();
                var dto = getEmptyDto();
                getGrid().setDataProvider(DataProvider.ofItems(dto));
                getGrid().getEditor().editItem(dto);
            }
        }
    }

    protected void filterChanged(F filter) {
        fireEvent(new FilterChangedEvent(this, filter));
    }

    protected void editItem(ItemDoubleClickEvent<D> event) {
        editEvent(event.getItem());
    }

    public class SaveEvent extends PagePartEvent<D> {

        protected SaveEvent(AbstractPagePart source, D dto) {
            super(source, dto);
        }
    }

    public class EditEvent extends PagePartEvent<D> {

        protected EditEvent(AbstractPagePart source, D dto) {
            super(source, dto);
        }
    }

    public class DeleteEvent extends PagePartEvent<D> {

        protected DeleteEvent(AbstractPagePart source, D dto) {
            super(source, dto);
        }
    }

    public class FilterChangedEvent extends PagePartEvent<F> {

        protected FilterChangedEvent(AbstractPagePart source, F filter) {
            super(source, filter);
        }
    }
}
