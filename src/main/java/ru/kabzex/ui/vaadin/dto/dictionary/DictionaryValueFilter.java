package ru.kabzex.ui.vaadin.dto.dictionary;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.enums.Dictionary;
import ru.kabzex.ui.vaadin.dto.DTOFilter;

@Getter
@Setter
public class DictionaryValueFilter implements DTOFilter {
    private Dictionary type;
    private String value;
    private String description;
}
