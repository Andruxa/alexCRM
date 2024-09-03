package ru.kabzex.server.entity.target;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kabzex.server.entity.AbstractEntity;
import ru.kabzex.server.entity.employee.Employee;

//Объект работ
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkObject extends AbstractEntity {
    private String name;
    private String address;
    @ManyToOne
    private Employee employee;

}
