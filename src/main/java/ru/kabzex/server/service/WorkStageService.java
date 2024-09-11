package ru.kabzex.server.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kabzex.server.entity.work.WorkStage;
import ru.kabzex.server.repository.WorkStageRepository;
import ru.kabzex.ui.vaadin.dto.DTOFilter;


@Slf4j
@Service
public class WorkStageService extends AbstractService<WorkStage, WorkStageRepository> {
    public WorkStageService(WorkStageRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected WorkStage getEmptyEntity() {
        return new WorkStage();
    }

    @Override
    protected Specification<WorkStage> parseFilter(Specification<WorkStage> specification, DTOFilter filter) {
        return null;
    }


}
