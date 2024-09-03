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

//Спецмонтаж
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkActivitySpecial extends AbstractEntity {
    @ManyToOne
    private WorkStage workStage;
    private String name;
    private LocalDate specialActivityDate;
    private String amount;
    private String singleCost;
}
