package ru.kabzex.server.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kabzex.server.entity.target.WorkObject;
import ru.kabzex.server.entity.work.WorkStuff;
import ru.kabzex.server.repository.WorkObjectRepository;
import ru.kabzex.server.repository.WorkStuffRepository;
import ru.kabzex.ui.vaadin.dto.DTOFilter;


@Slf4j
@Service
public class WorkStuffService extends AbstractService<WorkStuff, WorkStuffRepository> {
    public WorkStuffService(WorkStuffRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected WorkStuff getEmptyEntity() {
        return new WorkStuff();
    }

    @Override
    protected Specification<WorkStuff> parseFilter(Specification<WorkStuff> specification, DTOFilter filter) {
        return null;
    }


}
