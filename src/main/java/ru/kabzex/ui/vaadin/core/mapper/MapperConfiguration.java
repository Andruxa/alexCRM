package ru.kabzex.ui.vaadin.core.mapper;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kabzex.server.entity.dictionary.DictionaryValue;
import ru.kabzex.server.enums.Dictionary;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryTypeDTO;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;

@Configuration
public class MapperConfiguration {
    @Bean
    public ModelMapper getMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPreferNestedProperties(false);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        configureDictionaryMapping(mapper);
        return mapper;
    }

    private void configureDictionaryMapping(ModelMapper mapper) {
        Converter<Dictionary, DictionaryTypeDTO> dictConverter = new AbstractConverter<>() {
            protected DictionaryTypeDTO convert(Dictionary source) {
                DictionaryTypeDTO dto = new DictionaryTypeDTO();
                dto.setName(source.getDescription());
                return dto;
            }
        };
        Converter<DictionaryTypeDTO, Dictionary> dtoToDictConverter = new AbstractConverter<>() {
            protected Dictionary convert(DictionaryTypeDTO source) {
                return Dictionary.getByValue(source.getName());
            }
        };
        mapper.addConverter(dictConverter);
        mapper.addConverter(dtoToDictConverter);
        mapper.createTypeMap(DictionaryValueDTO.class, DictionaryValue.class)
                .addMappings(m -> m.map(DictionaryValueDTO::getType, DictionaryValue::setDictionary));
        mapper.createTypeMap(DictionaryValue.class, DictionaryValueDTO.class)
                .addMappings(m -> m.map(DictionaryValue::getDictionary, DictionaryValueDTO::setType));
    }

}
