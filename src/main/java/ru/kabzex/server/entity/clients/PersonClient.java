package ru.kabzex.server.entity.clients;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kabzex.server.entity.AbstractEntity;
import ru.kabzex.server.entity.documents.Contract;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PersonClient extends AbstractEntity {
    private String name;
    private String phoneNumber;
    @OneToMany
    private List<Contract> contracts;
}
