package ru.kabzex.server.entity.money;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.kabzex.server.entity.AbstractEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Outcome extends AbstractEntity {
    private LocalDateTime incomeDate;
    private String amount;
}
