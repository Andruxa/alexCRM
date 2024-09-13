package ru.kabzex.ui.vaadin.core.page;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.security.AuthenticationContext;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.kabzex.server.entity.AbstractEntity;
import ru.kabzex.server.exception.NoHandlerException;
import ru.kabzex.server.exception.handler.CustomExceptionHandler;
import ru.kabzex.server.service.AbstractService;
import ru.kabzex.ui.vaadin.core.event.CreateEvent;
import ru.kabzex.ui.vaadin.core.event.DeleteEvent;
import ru.kabzex.ui.vaadin.core.event.FilterChangedEvent;
import ru.kabzex.ui.vaadin.core.event.UpdateEvent;
import ru.kabzex.ui.vaadin.core.page.parts.AbstractEditableGridPagePart;
import ru.kabzex.ui.vaadin.dto.AbstractDTO;
import ru.kabzex.ui.vaadin.dto.DTOFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class AbstractDataPage extends VerticalLayout {

    private final AuthenticationContext authenticationContext;
    private Map<Class, AbstractService> serviceMap;


    protected AbstractDataPage(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
        setSizeFull();
    }

    @Autowired
    private void setServiceMap(AbstractService... services) {
        serviceMap = new HashMap<>();
        Arrays.stream(services).forEach(s -> serviceMap.put(s.getEntityClass(), s));
    }

    protected AbstractService getService(Class type) {
        return serviceMap.get(type);
    }


    public <T extends ComponentEvent<?>> void handle(T t) {
        throw new NoHandlerException("Сценарий не реализован");
    }

    public <E extends AbstractEntity, D extends AbstractDTO<E>> void handle(CreateEvent<D> event) {
        var item = event.getItem();
        var service = getService(item.getEntityClass());
        service.saveFromDto(item);
        event.getSource().refresh();
    }

    public <E extends AbstractEntity, D extends AbstractDTO<E>> void handle(UpdateEvent<D> event) {
        var item = event.getItem();
        var service = getService(item.getEntityClass());
        service.saveFromDto(item);
        event.getSource().refresh();
    }

    public <E extends AbstractEntity, D extends AbstractDTO<E>> void handle(DeleteEvent<D> event) {
        var item = event.getItem();
        var service = getService(item.getEntityClass());
        service.deleteById(event.getItem().getId());
        event.getSource().refresh();
    }

    public void handle(FilterChangedEvent event) {
        var source = event.getSource();
        var dto = source.getEmptyDto();
        source.setData(getLazyObjectListDataProvider(dto.getEntityClass(), dto.getClass(), source.getFilter()));
    }

    public void handle(AttachEvent attachEvent) {
        if (attachEvent.getSource() instanceof AbstractEditableGridPagePart source) {
            source.setCurrentRoles(getAuthenticationContext().getGrantedRoles());
            var dto = source.getEmptyDto();
            source.setData(getLazyObjectListDataProvider(dto.getEntityClass(), dto.getClass(), source.getFilter()));
        } else {
            throw new NoHandlerException("Инициализирующий сценарий не реализован");
        }
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        VaadinSession.getCurrent().setErrorHandler(new CustomExceptionHandler());
    }

    private <E extends AbstractEntity, D extends AbstractDTO<E>, F extends DTOFilter> DataProvider<D, F> getLazyObjectListDataProvider(Class<E> entityClass, Class<D> dtoClass, F filter) {
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
            var page = getService(entityClass).getAllByFilterAndMap(filter, offset, limit, Sort.by(springSortOrders), dtoClass);
            return page.stream();
        }, countQuery -> Math.toIntExact(getService(entityClass).countByFilter(filter)));
    }
}
