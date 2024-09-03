package ru.kabzex.server.entity.work;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kabzex.server.entity.AbstractEntity;
import ru.kabzex.server.entity.target.WorkObject;

//Этапы
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkStage extends AbstractEntity {

    @ManyToOne
    private WorkObject workObject;
    private String serialNumber;
    private String name;
}
