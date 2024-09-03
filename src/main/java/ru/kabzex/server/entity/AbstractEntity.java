package ru.kabzex.server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "bigint default 1")
    private Long version;

    @Column(name = "create_author")
    private String createAuthor;

    @Column(name = "delete_author")
    private String deleteAuthor;

    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AbstractEntity other = (AbstractEntity) obj;
        if (getId() == null || other.getId() == null) {
            return false;
        }
        return getId().equals(other.getId());
    }
}
