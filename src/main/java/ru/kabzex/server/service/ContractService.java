package ru.kabzex.server.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kabzex.server.entity.clients.PersonClient_;
import ru.kabzex.server.entity.documents.Contract;
import ru.kabzex.server.entity.documents.Contract_;
import ru.kabzex.server.entity.employee.Employee_;
import ru.kabzex.server.entity.target.WorkObject_;
import ru.kabzex.server.repository.ContractRepository;
import ru.kabzex.ui.vaadin.dto.AbstractDTO;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.dto.document.ContractFilter;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.kabzex.server.utils.StringUtils.likeInUpperCase;


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

    //    private String contractNumber;
//    private LocalDate contractDate;
//    private PersonClientDto contractor;
//    private Set<EmployeeDto> linkedEmployees;
    @Override
    protected Specification<Contract> parseFilter(Specification<Contract> specification, DTOFilter filter) {
        var f = Optional.ofNullable((ContractFilter) filter);
        return specification
                .and(numberLike(f))
                .and(dateBetween(f))
                .and(contractorIs(f))
                .and(employeesIn(f));
    }

    private static Specification<Contract> employeesIn(Optional<ContractFilter> f) {
        var val = f.map(ContractFilter::getLinkedEmployees);
        return val.<Specification<Contract>>map(s -> (wd, cq, cb) ->
                wd.join(Contract_.WORK_OBJECTS)
                        .join(WorkObject_.EMPLOYEE)
                        .get(Employee_.ID)
                        .in(s.stream().map(AbstractDTO::getId).collect(Collectors.toSet()))).orElse(null);
    }

    private static Specification<Contract> contractorIs(Optional<ContractFilter> f) {
        var val = f.map(ContractFilter::getContractor);
        return val.<Specification<Contract>>map(s -> (wd, cq, cb) ->
                cb.equal(wd.get(Contract_.contractor).get(PersonClient_.id), s.getId())
        ).orElse(null);
    }

    private static Specification<Contract> dateBetween(Optional<ContractFilter> f) {
        var from = f.map(ContractFilter::getContractDateFrom);
        var to = f.map(ContractFilter::getContractDateTo);
        if (from.isPresent() || to.isPresent()) {
            return (wd, cq, cb) ->
                    cb.between(wd.get(Contract_.contractDate),
                            from.map(ld -> ld.minusDays(1L)).orElse(LocalDate.MIN),
                            to.map(ld -> ld.plusDays(1L)).orElse(LocalDate.MAX));
        } else {
            return null;
        }
    }

    private static Specification<Contract> numberLike(Optional<ContractFilter> filter) {
        var val = filter.map(ContractFilter::getContractNumber);
        return val.<Specification<Contract>>map(s -> (wd, cq, cb) ->
                cb.like(cb.upper(wd.get(Contract_.contractNumber)),
                        likeInUpperCase(s))).orElse(null);
    }

}
