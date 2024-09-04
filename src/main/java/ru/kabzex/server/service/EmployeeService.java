package ru.kabzex.server.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kabzex.server.entity.dictionary.DictionaryValue_;
import ru.kabzex.server.entity.employee.Employee;
import ru.kabzex.server.entity.employee.Employee_;
import ru.kabzex.server.repository.EmployeeRepository;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeFilter;

import java.util.Optional;

import static ru.kabzex.server.utils.StringUtils.likeInUpperCase;


@Slf4j
@Service
public class EmployeeService extends AbstractService<Employee, EmployeeRepository> {
    public EmployeeService(EmployeeRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected Employee getEmptyEntity() {
        return new Employee();
    }

    @Override
    protected Specification<Employee> parseFilter(Specification<Employee> eSpecification, DTOFilter filter) {
        Optional<EmployeeFilter> optional = Optional.ofNullable((EmployeeFilter) filter);
        return eSpecification
                .and(nameLike(optional))
                .and(positionIs(optional));
    }

    private Specification<Employee> nameLike(Optional<EmployeeFilter> filter) {
        var val = filter.map(EmployeeFilter::getName).orElse(null);
        if (val == null) {
            return null;
        } else {
            return (wd, cq, cb) ->
                    cb.like(cb.upper(wd.get(Employee_.name)),
                            likeInUpperCase(val));
        }
    }

    private Specification<Employee> positionIs(Optional<EmployeeFilter> filter) {
        var val = filter.map(EmployeeFilter::getPosition).orElse(null);
        if (val == null) {
            return null;
        } else {
            return (wd, cq, cb) ->
                    cb.like(cb.upper(wd.get(Employee_.role).get(DictionaryValue_.value)),
                            likeInUpperCase(val));
        }
    }
}
