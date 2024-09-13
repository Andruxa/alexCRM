package ru.kabzex.ui.vaadin.dto.work;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.work.WorkActivity;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;
import ru.kabzex.ui.vaadin.dto.dictionary.DictionaryValueDTO;

import java.time.LocalDate;

//Выполненные работы
@Getter
@Setter
public class WorkActivityDto extends AbstractUpdatableDTO<WorkActivity> {
    private WorkStageDto workStageDto;
    private String name;
    private LocalDate activityDate;
    private DictionaryValueDTO activityType;
    private DictionaryValueDTO measureType;
    private String amount;
    private String singleCost;
    private Integer progress;


}
