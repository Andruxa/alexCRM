package ru.kabzex.server.service;

import jakarta.persistence.criteria.JoinType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kabzex.server.entity.clients.PersonClient;
import ru.kabzex.server.entity.clients.PersonClient_;
import ru.kabzex.server.entity.documents.Contract_;
import ru.kabzex.server.entity.employee.Employee_;
import ru.kabzex.server.entity.target.WorkObject_;
import ru.kabzex.server.repository.PersonClientRepository;
import ru.kabzex.ui.vaadin.dto.AbstractDTO;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.dto.client.PersonClientFilter;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.kabzex.server.utils.StringUtils.likeInUpperCase;


@Slf4j
@Service
public class PersonClientService extends AbstractService<PersonClient, PersonClientRepository> {


    public PersonClientService(PersonClientRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected PersonClient getEmptyEntity() {
        return new PersonClient();
    }

    @Override
    protected Specification<PersonClient> parseFilter(Specification<PersonClient> eSpecification, DTOFilter filter) {
        Optional<PersonClientFilter> optional = Optional.ofNullable((PersonClientFilter) filter);
        return eSpecification
                .and(nameLike(optional))
                .and(phoneNumberLike(optional))
                .and(employeeLinkedIs(optional));
    }

    private static Specification<PersonClient> nameLike(Optional<PersonClientFilter> filter) {
        var val = filter.map(PersonClientFilter::getName);
        return val.<Specification<PersonClient>>map(s -> (wd, cq, cb) ->
                cb.like(cb.upper(wd.get(PersonClient_.name)),
                        likeInUpperCase(s))).orElse(null);
    }

    private static Specification<PersonClient> phoneNumberLike(Optional<PersonClientFilter> filter) {
        var val = filter.map(PersonClientFilter::getPhoneNumber);
        return val.<Specification<PersonClient>>map(s -> (wd, cq, cb) ->
                cb.like(cb.upper(wd.get(PersonClient_.phoneNumber)),
                        likeInUpperCase(s))).orElse(null);
    }

    private static Specification<PersonClient> employeeLinkedIs(Optional<PersonClientFilter> filter) {
        var val = filter.map(PersonClientFilter::getLinkedEmployees);
        if (val.isPresent()) {
            return (wd, cq, cb) -> {
                var joinEmployee = wd
                        .join(PersonClient_.CONTRACTS, JoinType.LEFT)
                        .join(Contract_.WORK_OBJECTS, JoinType.LEFT)
                        .join(WorkObject_.EMPLOYEE, JoinType.LEFT);
                var employeeIds = val.stream()
                        .flatMap(Collection::stream)
                        .map(AbstractDTO::getId)
                        .collect(Collectors.toSet());
                return joinEmployee.get(Employee_.ID).in(employeeIds);
            };
        } else {
            return null;
        }
    }
}
