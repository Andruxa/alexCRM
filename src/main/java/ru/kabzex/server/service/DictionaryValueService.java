package ru.kabzex.server.service;

import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kabzex.server.entity.dictionary.DictionaryValue;
import ru.kabzex.server.entity.dictionary.DictionaryValue_;
import ru.kabzex.server.enums.Dictionary;
import ru.kabzex.server.repository.DictionaryValueRepository;
import ru.kabzex.server.security.Roles;
import ru.kabzex.ui.vaadin.dto.AbstractDTO;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueFilter;

import java.util.List;

import static ru.kabzex.server.utils.StringUtils.likeInUpperCase;

@Slf4j
@Service
@RolesAllowed(Roles.ADMIN)
public class DictionaryValueService extends AbstractService<DictionaryValue, DictionaryValueRepository> {
    public DictionaryValueService(DictionaryValueRepository repository, ModelMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected DictionaryValue getEmptyEntity() {
        return new DictionaryValue();
    }

    @Transactional
    public <D extends AbstractDTO> List<D> getAllByCodeAndMap(Dictionary code, Class<D> dtoClass) {
        var filter = new DictionaryValueFilter();
        filter.setType(code.getDescription());
        return getAllByFilterAndMap(filter, dtoClass);
    }

    static Specification<DictionaryValue> dictionaryType(Dictionary type) {
        return (wd, cq, cb) -> cb.equal(wd.get(DictionaryValue_.dictionary.getName()), type);
    }

    @Override
    protected Specification<DictionaryValue> parseFilter(Specification<DictionaryValue> specification, DTOFilter filter) {
        return specification
                .and(dictionaryType((DictionaryValueFilter) filter))
                .and(valueLike((DictionaryValueFilter) filter))
                .and(descLike((DictionaryValueFilter) filter));
    }

    static Specification<DictionaryValue> dictionaryType(DictionaryValueFilter type) {
        if (type == null || type.getType() == null || type.getType().isEmpty()) return null;
        return dictionaryType(Dictionary.getByValue(type.getType()));
    }

    static Specification<DictionaryValue> valueLike(DictionaryValueFilter filter) {
        if (filter == null || filter.getValue() == null || filter.getValue().isEmpty()) return null;
        return (wd, cq, cb) ->
                cb.like(cb.upper(wd.get(DictionaryValue_.value.getName())),
                        likeInUpperCase(filter.getValue()));
    }

    static Specification<DictionaryValue> descLike(DictionaryValueFilter filter) {
        if (filter == null || filter.getDescription() == null || filter.getDescription().isEmpty()) return null;
        return (wd, cq, cb) ->
                cb.like(cb.upper(wd.get(DictionaryValue_.description.getName())),
                        likeInUpperCase(filter.getDescription()));
    }
}
