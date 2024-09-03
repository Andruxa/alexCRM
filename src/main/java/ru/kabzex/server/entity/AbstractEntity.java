package ru.kabzex.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@MappedSuperclass
@Getter
public abstract class AbstractEntity implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;
    @Setter
    @Column(name = "create_date")
    private LocalDate createDate;
    @Setter
    @Column(name = "delete_date")
    private LocalDate deleteDate;
    @Setter
    @Column(name = "version")
    private Long version;
    @Setter
    @Column(name = "create_author")
    private UUID createAuthor;
    @Setter
    @Column(name = "delete_author")
    private UUID deleteAuthor;

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
