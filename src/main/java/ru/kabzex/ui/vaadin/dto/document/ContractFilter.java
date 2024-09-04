package ru.kabzex.ui.vaadin.dto.document;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.dto.client.PersonClientDto;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class ContractFilter implements DTOFilter {
    private String contractNumber;
    private LocalDate contractDateFrom;
    private LocalDate contractDateTo;
    private PersonClientDto contractor;
    private Set<EmployeeDto> linkedEmployees;
}
