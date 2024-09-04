package ru.kabzex.server.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kabzex.server.entity.documents.Contract;
import ru.kabzex.server.repository.ContractRepository;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.dto.document.ContractFilter;


@Slf4j
@Service
public class ContractService extends AbstractService<Contract, ContractRepository> {
    public ContractService(ContractRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected Contract getEmptyEntity() {
        return new Contract();
    }

    @Override
    protected  Specification<Contract> parseFilter(Specification<Contract> specification, DTOFilter filter) {
        return null;
    }

}
