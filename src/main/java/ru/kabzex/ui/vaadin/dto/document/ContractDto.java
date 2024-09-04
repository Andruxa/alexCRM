package ru.kabzex.ui.vaadin.dto.document;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.documents.Contract;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;
import ru.kabzex.ui.vaadin.dto.client.PersonClientDto;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class ContractDto extends AbstractUpdatableDTO<Contract> {
    private String contractNumber;
    private LocalDate contractDate;
    private PersonClientDto contractor;
    private Set<EmployeeDto> linkedEmployees;
}
