package ru.kabzex.server.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kabzex.server.entity.work.WorkActivity;
import ru.kabzex.server.repository.WorkActivityRepository;
import ru.kabzex.ui.vaadin.dto.DTOFilter;


@Slf4j
@Service
public class WorkActivityService extends AbstractService<WorkActivity, WorkActivityRepository> {
    public WorkActivityService(WorkActivityRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected WorkActivity getEmptyEntity() {
        return new WorkActivity();
    }

    @Override
    protected Specification<WorkActivity> parseFilter(Specification<WorkActivity> specification, DTOFilter filter) {
        return null;
    }


}
