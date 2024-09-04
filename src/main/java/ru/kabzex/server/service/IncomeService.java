package ru.kabzex.server.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kabzex.server.entity.money.Income;
import ru.kabzex.server.repository.IncomeRepository;
import ru.kabzex.ui.vaadin.dto.DTOFilter;


@Slf4j
@Service
public class IncomeService extends AbstractService<Income, IncomeRepository> {
    public IncomeService(IncomeRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected Income getEmptyEntity() {
        return new Income();
    }

    @Override
    protected Specification<Income> parseFilter(Specification<Income> specification, DTOFilter filter) {
        return null;
    }


}
