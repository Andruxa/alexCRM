package ru.kabzex.ui.vaadin.dto.client;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;

import java.util.Set;

@Getter
@Setter
public class PersonClientFilter implements DTOFilter {
    private String name;
    private String phoneNumber;
    private Set<EmployeeDto> linkedEmployees;
}
