package ru.kabzex.ui.vaadin.dto.dictionary;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.ui.vaadin.dto.DTOFilter;

@Getter
@Setter
public class DictionaryFilterDTO implements DTOFilter {
    private String value;
    private String description;
}
