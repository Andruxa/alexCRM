package ru.kabzex.server.entity.historical;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.kabzex.server.entity.AbstractEntity;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OldVersionsOfEntity extends AbstractEntity {
    private String entityId;
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> value;
}
