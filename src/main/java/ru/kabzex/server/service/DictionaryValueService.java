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
import java.util.Optional;

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
        filter.setType(code);
        return getAllByFilterAndMap(filter, dtoClass);
    }


    @Override
    protected Specification<DictionaryValue> parseFilter(Specification<DictionaryValue> specification, DTOFilter filter) {
        Optional<DictionaryValueFilter> optional = Optional.ofNullable((DictionaryValueFilter) filter);
        return specification
                .and(equality(DictionaryValue_.dictionary, optional.map(DictionaryValueFilter::getType)))
                .and(stringLike(DictionaryValue_.value, optional.map(DictionaryValueFilter::getValue)))
                .and(stringLike(DictionaryValue_.description, optional.map(DictionaryValueFilter::getDescription)));
    }
}
