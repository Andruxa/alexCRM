package ru.kabzex.server.entity.work;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kabzex.server.entity.AbstractEntity;

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
    @Column(length = 2000)
    private String name;
    private LocalDate specialActivityDate;
    private String amount;
    private String singleCost;
}
