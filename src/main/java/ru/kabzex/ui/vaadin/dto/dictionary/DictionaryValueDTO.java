package ru.kabzex.ui.vaadin.dto.dictionary;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.dictionary.DictionaryValue;
import ru.kabzex.server.enums.Dictionary;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;

@Getter
@Setter
public class DictionaryValueDTO extends AbstractUpdatableDTO<DictionaryValue> {
    private Dictionary type;
    private String value;
    private String description;
}
