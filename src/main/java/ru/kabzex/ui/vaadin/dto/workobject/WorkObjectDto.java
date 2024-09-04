package ru.kabzex.ui.vaadin.dto.workobject;

import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.documents.Contract;
import ru.kabzex.server.entity.employee.Employee;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;
import ru.kabzex.ui.vaadin.dto.document.ContractDto;
import ru.kabzex.ui.vaadin.dto.employee.EmployeeDto;

@Getter
@Setter
public class WorkObjectDto extends AbstractUpdatableDTO<Employee> {
    private String name;
    private String address;
    private EmployeeDto employee;
    private ContractDto objectContract;
}
