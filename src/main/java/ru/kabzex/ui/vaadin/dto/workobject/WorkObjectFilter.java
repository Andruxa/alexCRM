package ru.kabzex.ui.vaadin.dto.workobject;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.ui.vaadin.dto.DTOFilter;
import ru.kabzex.ui.vaadin.dto.document.ContractDto;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;

@Getter
@Setter
public class WorkObjectFilter implements DTOFilter {
    private String name;
    private String address;
    private String employee;
    private String objectContract;
}
