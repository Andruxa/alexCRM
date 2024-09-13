package ru.kabzex.ui.vaadin.dto.work;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.work.WorkActivitySpecial;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;

import java.time.LocalDate;

//Спецмонтаж
@Getter
@Setter
public class WorkActivitySpecialDto extends AbstractUpdatableDTO<WorkActivitySpecial> {
    private WorkStageDto workStageDto;
    private String name;
    private LocalDate specialActivityDate;
    private String amount;
    private String singleCost;
}
