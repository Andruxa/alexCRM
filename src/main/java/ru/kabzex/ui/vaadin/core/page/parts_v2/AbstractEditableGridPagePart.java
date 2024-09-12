package ru.kabzex.ui.vaadin.core.page.parts_v2;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.shared.Registration;
import lombok.Getter;
import ru.kabzex.ui.vaadin.core.dialog.ConfirmDialog;
import ru.kabzex.ui.vaadin.core.event.CreateEvent;
import ru.kabzex.ui.vaadin.core.event.DeleteEvent;
import ru.kabzex.ui.vaadin.core.event.FilterChangedEvent;
import ru.kabzex.ui.vaadin.core.event.UpdateEvent;
import ru.kabzex.ui.vaadin.dto.AbstractDTO;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.utils.NotificationUtils;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static com.vaadin.flow.component.icon.VaadinIcon.*;

/*
D - DTO
F - Filter
 */
public abstract class AbstractEditableGridPagePart<D extends AbstractDTO, F extends DTOFilter> extends AbstractDataPagePart<DataProvider<D, F>> {

    public static final String EDIT_COLUMN = "EDIT";

    protected Button addButton;
    protected Collection<String> currentRoles;
    @Getter
    private final Grid<D> grid;
    private DataProvider dataProvider;
    private final Set<ComponentEventListener<CreateEvent<D>>> createListeners = new LinkedHashSet<>();

    protected abstract Collection<String> getAllowedRoles();

    protected abstract void configureFilters(HeaderRow headerRow);

    protected abstract void configureEditor();

    protected abstract D getEmptyDto();

    protected abstract Grid<D> initGrid();

    protected AbstractEditableGridPagePart() {
        setSizeFull();
        this.grid = initGrid();
        this.grid.setSizeFull();
        add(grid);
    }

    public void setCurrentRoles(Collection<String> cr) {
        this.currentRoles = cr;
        if (currentRoles != null &&
                currentRoles.stream().anyMatch(getAllowedRoles()::contains)) {
            getGrid().addComponentColumn(this::gridEditDelButtons).setKey(EDIT_COLUMN);
            getGrid().addItemDoubleClickListener(e -> editEvent(e.getItem()));
            getGrid().getColumnByKey(EDIT_COLUMN).setEditorComponent(this::editorEditDelButtons);
            configureEditor();
            configureFilters(getGrid().appendHeaderRow());
            configureAddButton(getGrid().appendFooterRow());
            registerHandlers();
        }
    }

    @Override
    public void setData(DataProvider<D, F> data) {
        getGrid().setDataProvider(data);
    }

    public void refresh() {
        getGrid().getDataProvider().refreshAll();
    }

    private Component gridEditDelButtons(D dto) {
        Button delete = new Button();
        delete.setIcon(new Icon(CLOSE));
        delete.addClickListener(e -> deleteEvent(dto));
        Button edit = new Button();
        edit.setIcon(new Icon(EDIT));
        edit.addClickListener(e -> editEvent(dto));
        return new HorizontalLayout(edit, delete);
    }

    private Component editorEditDelButtons(D d) {
        Button saveButton = new Button(CHECK.create(), e -> editorSave(getGrid().getEditor()));
        Button cancelButton = new Button(CLOSE.create(), e -> editorCanceled(getGrid().getEditor()));
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout actions = new HorizontalLayout(saveButton,
                cancelButton);
        actions.setPadding(false);
        return actions;
    }

    private void deleteEvent(D dto) {
        final var event = new DeleteEvent<>(this, dto);
        var confirmationDialog = new ConfirmDialog("Удаление",
                "После нажатия создание запись будет удалена",
                e -> ComponentUtil.fireEvent(this, event));
        confirmationDialog.open();
    }

    private void editEvent(D dto) {
        if (getGrid().getEditor().isOpen())
            getGrid().getEditor().cancel();
        getGrid().getEditor().editItem(dto);
    }

    private void editorSave(Editor<D> editor) {
        var item = Optional.ofNullable(editor.getItem());
        if (editor.getBinder().isValid() && item.isPresent()) {
            if (item.map(AbstractDTO::getId).isEmpty()) {
                ConfirmDialog confirmationDialog = new ConfirmDialog("Сохранение записи",
                        "После нажатия запись будет сохранена",
                        e -> {
                            if (dataProvider != null) {
                                getGrid().setDataProvider(dataProvider);
                            } else {
                                getGrid().setDataProvider(DataProvider.ofItems());
                            }
                            var createEvent = new CreateEvent<>(this, item.get());
                            ComponentUtil.fireEvent(this, createEvent);
                        });
                confirmationDialog.open();
            } else {
                ConfirmDialog confirmationDialog = new ConfirmDialog("Обновление записи",
                        "После нажатия запись будет сохранена",
                        e -> ComponentUtil.fireEvent(this, new UpdateEvent<>(this, item.get())));
                confirmationDialog.open();
            }
        }
    }

    private void editorCanceled(Editor<D> editor) {
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

    private void configureAddButton(FooterRow footerRow) {
        addButton = new Button("Добавить");
        addButton.addClickListener(this::createEvent);
        setFlexDirection(FlexDirection.COLUMN);
        add(new HorizontalLayout(addButton));
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
        var fe = new FilterChangedEvent<>(this, filter);
        ComponentUtil.fireEvent(this, fe);
    }

    private void registerHandlers() {
        ComponentUtil.addListener(this, CreateEvent.class,
                (ComponentEventListener) ((ComponentEventListener<CreateEvent<D>>) e ->
                        createListeners.forEach(listener -> listener
                                .onComponentEvent(e))
                ));
    }

    public Registration addCreateEventListener(
            ComponentEventListener<CreateEvent<D>> listener) {
        createListeners.add(listener);
        return () -> createListeners.remove(listener);
    }
}

