package ru.kabzex.server.entity.dictionary;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kabzex.server.entity.AbstractEntity;
import ru.kabzex.server.enums.Dictionary;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "DICTIONARY_VALUE",
        uniqueConstraints = @UniqueConstraint(name = "uniqueNames", columnNames = {"dictionary", "value", "delete_date"}))
public class DictionaryValue extends AbstractEntity {

    /**
     * Справочник
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private Dictionary dictionary;
    /**
     * Значение
     */
    @NotNull
    @NotEmpty
    private String value;
    /**
     * Описание
     */
    @Column(length = 2000)
    private String description;
}
