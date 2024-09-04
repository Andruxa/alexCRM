package ru.kabzex.server.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kabzex.server.entity.target.WorkObject;
import ru.kabzex.server.repository.WorkObjectRepository;
import ru.kabzex.ui.vaadin.dto.DTOFilter;


@Slf4j
@Service
public class WorkObjectService extends AbstractService<WorkObject, WorkObjectRepository> {
    public WorkObjectService(WorkObjectRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected WorkObject getEmptyEntity() {
        return new WorkObject();
    }

    @Override
    protected Specification<WorkObject> parseFilter(Specification<WorkObject> specification, DTOFilter filter) {
        return null;
    }


}
