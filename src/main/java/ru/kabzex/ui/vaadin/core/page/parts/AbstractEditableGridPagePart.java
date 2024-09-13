package ru.kabzex.ui.vaadin.core.page.parts;

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
import ru.kabzex.ui.vaadin.core.event.*;
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
    @Getter
    protected F filter = initFilter();

    protected abstract F initFilter();

    protected Button addButton;
    @Getter
    private final Grid<D> grid;
    private DataProvider dataProvider;
    private final Set<ComponentEventListener<CreateEvent<D>>> createListeners = new LinkedHashSet<>();
    private final Set<ComponentEventListener<UpdateEvent<D>>> updateListeners = new LinkedHashSet<>();
    private final Set<ComponentEventListener<DeleteEvent<D>>> deleteListeners = new LinkedHashSet<>();
    private final Set<ComponentEventListener<RequestDataProviderEvent>> dataRequiredListeners = new LinkedHashSet<>();
    private final Set<ComponentEventListener<FilterChangedEvent>> filterListeners = new LinkedHashSet<>();

    protected abstract Collection<String> getAllowedRoles();

    protected abstract void configureFilters(HeaderRow headerRow);

    protected abstract void configureEditor();

    public abstract D getEmptyDto();

    protected abstract Grid<D> initGrid();

    protected AbstractEditableGridPagePart() {
        setSizeFull();
        this.grid = initGrid();
        if (grid != null) {
            this.grid.setSizeFull();
            add(grid);
        }
    }

    @Override
    public void setCurrentRoles(Collection<String> cr) {
        super.setCurrentRoles(cr);
        if (currentRoles != null &&
                currentRoles.stream().anyMatch(getAllowedRoles()::contains)
                && getGrid() != null) {
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
        getGrid().getEditor().setBuffered(true);
        getGrid().getEditor().editItem(dto);
    }

    private void editorSave(Editor<D> editor) {
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
                            ComponentUtil.fireEvent(this, new CreateEvent<>(this, item.get()));
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
                getGrid().getEditor().setBuffered(true);
                getGrid().getEditor().editItem(dto);
            }
        }
    }

    private void registerHandlers() {
        ComponentUtil.addListener(this, CreateEvent.class,
                (ComponentEventListener) ((ComponentEventListener<CreateEvent<D>>) e ->
                        createListeners.forEach(listener -> listener
                                .onComponentEvent(e))
                ));
        ComponentUtil.addListener(this, UpdateEvent.class,
                (ComponentEventListener) ((ComponentEventListener<UpdateEvent<D>>) e ->
                        updateListeners.forEach(listener -> listener
                                .onComponentEvent(e))
                ));
        ComponentUtil.addListener(this, DeleteEvent.class,
                (ComponentEventListener) ((ComponentEventListener<DeleteEvent<D>>) e ->
                        deleteListeners.forEach(listener -> listener
                                .onComponentEvent(e))
                ));
        ComponentUtil.addListener(this, FilterChangedEvent.class,
                (ComponentEventListener) ((ComponentEventListener<FilterChangedEvent>) e ->
                        filterListeners.forEach(listener -> listener
                                .onComponentEvent(e))
                ));
        ComponentUtil.addListener(this, RequestDataProviderEvent.class,
                (ComponentEventListener) ((ComponentEventListener<RequestDataProviderEvent>) e ->
                        dataRequiredListeners.forEach(listener -> listener
                                .onComponentEvent(e))
                ));
    }

    public Registration addCreateEventListener(
            ComponentEventListener<CreateEvent<D>> listener) {
        createListeners.add(listener);
        return () -> createListeners.remove(listener);
    }

    public Registration addDataRequiredEventListener(
            ComponentEventListener<RequestDataProviderEvent> listener) {
        dataRequiredListeners.add(listener);
        return () -> dataRequiredListeners.remove(listener);
    }

    public Registration addUpdateEventListener(
            ComponentEventListener<UpdateEvent<D>> listener) {
        updateListeners.add(listener);
        return () -> updateListeners.remove(listener);
    }

    public Registration addDeleteEventListener(
            ComponentEventListener<DeleteEvent<D>> listener) {
        deleteListeners.add(listener);
        return () -> deleteListeners.remove(listener);
    }

    public Registration addFilterChangedEventListener(
            ComponentEventListener<FilterChangedEvent> listener) {
        filterListeners.add(listener);
        return () -> filterListeners.remove(listener);
    }
}

