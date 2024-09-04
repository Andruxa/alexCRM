package ru.kabzex.server.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kabzex.server.entity.money.Outcome;
import ru.kabzex.server.repository.OutcomeRepository;
import ru.kabzex.ui.vaadin.dto.DTOFilter;


@Slf4j
@Service
public class OutcomeService extends AbstractService<Outcome, OutcomeRepository> {
    public OutcomeService(OutcomeRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected Outcome getEmptyEntity() {
        return new Outcome();
    }

    @Override
    protected Specification<Outcome> parseFilter(Specification<Outcome> specification, DTOFilter filter) {
        return null;
    }


}
