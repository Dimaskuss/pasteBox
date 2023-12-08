package com.example.pasteBox.repository;

import com.example.pasteBox.exception.NotFoundEntityException;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class PasteBoxRepositoryImplMap implements PasteBoxRepository{

    private final Map<String,PasteBoxEntity> rep = new ConcurrentHashMap<>();
    @Override
    public PasteBoxEntity getByHash(String hash) {
        PasteBoxEntity pasteBoxEntity = rep.get(hash);

        if(pasteBoxEntity==null){
            throw new NotFoundEntityException("PasteBox not found with hash = "+hash);
        }
        return pasteBoxEntity;
    }

    @Override
    public List<PasteBoxEntity> getListOfPublicAndAlive(int amount) {
        LocalDateTime now = LocalDateTime.now();


        return rep.values().stream()
                .filter(PasteBoxEntity::isPublic)
                .filter(pasteBoxEntity -> pasteBoxEntity.getLifeTime().isAfter(now))
                .sorted(Comparator.comparing(PasteBoxEntity::getId).reversed())
                .limit(amount)
                .collect(Collectors.toList());

    }

    @Override
    public void add(PasteBoxEntity pasteBoxEntity) {
        rep.put(pasteBoxEntity.getHash(), pasteBoxEntity);
    }
}
