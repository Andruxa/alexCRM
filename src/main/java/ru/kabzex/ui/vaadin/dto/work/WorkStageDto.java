package ru.kabzex.ui.vaadin.dto.work;

import lombok.Getter;
import lombok.Setter;
import ru.kabzex.server.entity.work.WorkStage;
import ru.kabzex.ui.vaadin.dto.AbstractUpdatableDTO;
import ru.kabzex.ui.vaadin.dto.workobject.WorkObjectDto;

//Этапы
@Getter
@Setter
public class WorkStageDto extends AbstractUpdatableDTO<WorkStage> {

    private WorkObjectDto workObject;
    private String serialNumber;
    private String name;
}
