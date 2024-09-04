package ru.kabzex.server.entity.documents;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kabzex.server.entity.AbstractEntity;
import ru.kabzex.server.entity.clients.PersonClient;
import ru.kabzex.server.entity.target.WorkObject;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contract extends AbstractEntity {
    private String contractNumber;
    private LocalDate contractDate;
    @ManyToOne
    private PersonClient contractor;
    @OneToMany
    private List<WorkObject> workObjects;
}
