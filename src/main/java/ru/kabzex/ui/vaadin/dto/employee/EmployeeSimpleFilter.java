package ru.kabzex.ui.vaadin.dto.employee;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.ui.vaadin.dto.DTOFilter;

@Getter
@Setter
public class EmployeeSimpleFilter implements DTOFilter {
    private String name;
    private String position;

}
