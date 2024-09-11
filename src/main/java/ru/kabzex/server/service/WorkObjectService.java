package ru.kabzex.server.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kabzex.server.entity.documents.Contract_;
import ru.kabzex.server.entity.employee.Employee_;
import ru.kabzex.server.entity.target.WorkObject;
import ru.kabzex.server.entity.target.WorkObject_;
import ru.kabzex.server.repository.WorkObjectRepository;
import ru.kabzex.server.utils.StringUtils;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectFilter;

import java.util.Optional;

import static ru.kabzex.server.utils.StringUtils.likeInUpperCase;


@Slf4j
@Service
public class WorkObjectService extends AbstractService<WorkObject, WorkObjectRepository> {
    public WorkObjectService(WorkObjectRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected WorkObject getEmptyEntity() {
        return new WorkObject();
    }

    @Override
    protected Specification<WorkObject> parseFilter(Specification<WorkObject> specification, DTOFilter filter) {
        var f = Optional.ofNullable((WorkObjectFilter) filter);
        return specification
                .and(stringLike(WorkObject_.address, f.map(WorkObjectFilter::getAddress)))
                .and(stringLike(WorkObject_.name, f.map(WorkObjectFilter::getName)))
                .and(contractValueLike(f.map(WorkObjectFilter::getObjectContract)))
                .and(employeeValueLike(f.map(WorkObjectFilter::getEmployee)));
    }

    private static Specification<WorkObject> contractValueLike(Optional<String> value) {
        return value.filter(StringUtils::notEmpty).<Specification<WorkObject>>map(s -> (wd, cq, cb) ->
                cb.like(cb.upper(wd.get(WorkObject_.objectContract).get(Contract_.contractNumber)),
                        likeInUpperCase(s))).orElse(null);
    }

    private static Specification<WorkObject> employeeValueLike(Optional<String> value) {
        return value.filter(StringUtils::notEmpty).<Specification<WorkObject>>map(s -> (wd, cq, cb) ->
                cb.like(cb.upper(wd.get(WorkObject_.employee).get(Employee_.name)),
                        likeInUpperCase(s))).orElse(null);
    }

}
