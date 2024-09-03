package ru.kabzex.server.entity.work;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kabzex.server.entity.AbstractEntity;
import ru.kabzex.server.entity.dictionary.DictionaryValue;

import java.time.LocalDate;

//Выполненные работы
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkActivity extends AbstractEntity {
    @ManyToOne
    private WorkStage workStage;
    private String name;
    private LocalDate activityDate;
    @ManyToOne
    private DictionaryValue activityType;
    @ManyToOne
    private DictionaryValue measureType;
    private String amount;
    private String singleCost;
    @Size(max = 100)
    private Integer progress;


}
