package ru.kabzex.server.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kabzex.server.entity.AbstractEntity;
import ru.kabzex.server.entity.historical.OldVersionsOfEntity;
import ru.kabzex.server.repository.ArchiveRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ArchiveService {
    private final ArchiveRepository repository;
    private final ObjectMapper mapper;

    public ArchiveService(ArchiveRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public void archiveEntity(AbstractEntity entity, String author) {
        var currentDT = LocalDateTime.now();
        var archive = new OldVersionsOfEntity();
        archive.setCreateDate(currentDT);
        archive.setCreatedBy(author);
        archive.setEntityId(entity.getId().toString());
        var r = mapper.convertValue(entity, Map.class);
        archive.setValue(r);
        repository.save(archive);
    }

}
