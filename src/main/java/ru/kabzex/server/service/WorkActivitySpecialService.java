package ru.kabzex.server.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kabzex.server.entity.work.WorkActivity;
import ru.kabzex.server.entity.work.WorkActivitySpecial;
import ru.kabzex.server.repository.WorkActivityRepository;
import ru.kabzex.server.repository.WorkActivitySpecialRepository;
import ru.kabzex.ui.vaadin.dto.DTOFilter;


@Slf4j
@Service
public class WorkActivitySpecialService extends AbstractService<WorkActivitySpecial, WorkActivitySpecialRepository> {
    public WorkActivitySpecialService(WorkActivitySpecialRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected WorkActivitySpecial getEmptyEntity() {
        return new WorkActivitySpecial();
    }

    @Override
    protected Specification<WorkActivitySpecial> parseFilter(Specification<WorkActivitySpecial> specification, DTOFilter filter) {
        return null;
    }


}
