package ru.kabzex.server.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kabzex.server.entity.employee.Employee;
import ru.kabzex.server.entity.employee.Employee_;
import ru.kabzex.server.repository.EmployeeRepository;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeFilter;

import java.util.Optional;


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
                .and(stringLike(Employee_.name, optional.map(EmployeeFilter::getName)))
                .and(dictionaryValueLike(Employee_.position, optional.map(EmployeeFilter::getPosition)));
    }
}
