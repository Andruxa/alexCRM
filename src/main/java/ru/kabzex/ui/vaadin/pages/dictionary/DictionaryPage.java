package ru.kabzex.ui.vaadin.pages.dictionary;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.kabzex.server.enums.Dictionary;
import ru.kabzex.server.security.Roles;
import ru.kabzex.server.service.DictionaryValueService;
import ru.kabzex.ui.MainLayout;
import ru.kabzex.ui.vaadin.core.page.AbstractSimplePage;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryTypeDTO;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueFilter;
import ru.kabzex.ui.vaadin.pages.dictionary.dialog.DictionaryItemDialog;
import ru.kabzex.ui.vaadin.pages.dictionary.parts.DictionaryBody;

import java.util.ArrayList;
import java.util.Arrays;

@PageTitle("Справочники")
@Route(value = "dict", layout = MainLayout.class)
@RolesAllowed({Roles.ADMIN})
public class DictionaryPage extends AbstractSimplePage<Component, DictionaryBody, Component> {
    @Autowired
    private DictionaryValueService dictionaryValueService;
    @Autowired
    private ModelMapper modelMapper;

    private final DictionaryValueFilter currentFilter = new DictionaryValueFilter();

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        getBody().setData(getLazyBodyDataProvider(null));
        getBody().setCurrentRoles(authenticationContext.getGrantedRoles());
    }


    @Override
    protected Component initFooter() {
        return null;
    }

    @Override
    protected DictionaryBody initBody() {
        DictionaryBody body = new DictionaryBody();
        body.addCreateEventListener(this::handle);
        body.addUpdateEventListener(this::handle);
        body.addDeleteEventListener(this::handle);
        body.addFilterChangedEventListener(this::handle);
        body.addAttachListener(this::handle);
        return body;
    }

    @Override
    protected Component initHeader() {
        return null;
    }

/*    private void handle(DictionaryBody.FilterChangedEvent event) {
        currentFilter.setValue(event.getFilter().getValue());
        currentFilter.setDescription(event.getFilter().getDescription());
        getBody().setDataProvider(getLazyBodyDataProvider(currentFilter));
    }



    private void handle(DictionaryBody.RecordDeleteEvent event) {
        ConfirmDialog confirmationDialog = new ConfirmDialog("Удаление записи", String.format("После нажатия запись \"%s\" будет удалена", event.getEntity().getValue()), e -> {
            dictionaryValueService.deleteById(event.getEntity().getId());
            getBody().refresh();
        });
        confirmationDialog.open();
    }

    private void handle(DictionaryBody.RecordEditEvent event) {
        var dictionaryItemDialog = getDialog(event.getEntity());
        dictionaryItemDialog.open();
    }*/

    private DictionaryItemDialog getDialog(DictionaryValueDTO value) {
        DictionaryItemDialog dictionaryItemDialog = new DictionaryItemDialog();
        dictionaryItemDialog.addListener(DictionaryItemDialog.DictionaryValueConfirmedEvent.class, this::handle);
        dictionaryItemDialog.setWidth("40%");
        dictionaryItemDialog.setHeight("65%");
        dictionaryItemDialog.setTitle(String.format("%s записи справочника", value == null ? "Редактирование" : "Создание"));
        var types = Arrays.stream(Dictionary.values()).map(t -> modelMapper.map(t, DictionaryTypeDTO.class)).toList();
        dictionaryItemDialog.setTypeItems(types);
        if (value != null) {
            dictionaryItemDialog.setItem(value);
        }
        return dictionaryItemDialog;
    }

    private void handle(DictionaryItemDialog.DictionaryValueConfirmedEvent event) {
        dictionaryValueService.saveFromDto(event.getEntity());
        getBody().refresh();
    }

    private DataProvider<DictionaryValueDTO, DictionaryValueFilter> getLazyBodyDataProvider(DictionaryValueFilter filter) {
        return DataProvider.fromFilteringCallbacks(query -> {
            int offset = query.getOffset();
            int limit = query.getLimit();
            var vaadinSortOrders = query.getSortOrders();
            var springSortOrders = new ArrayList<Sort.Order>();
            for (QuerySortOrder so : vaadinSortOrders) {
                String colKey = so.getSorted();
                if (so.getDirection() == SortDirection.ASCENDING) {
                    springSortOrders.add(Sort.Order.asc(colKey));
                } else {
                    springSortOrders.add(Sort.Order.desc(colKey));
                }
            }
            var page = dictionaryValueService.getAllByFilterAndMap(filter, offset, limit, Sort.by(springSortOrders), DictionaryValueDTO.class);
            return page.stream();
        }, countQuery -> Math.toIntExact(dictionaryValueService.countByFilter(filter)));
    }

}
