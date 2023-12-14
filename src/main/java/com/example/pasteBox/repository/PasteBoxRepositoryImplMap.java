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
        System.out.println(now);

        List<PasteBoxEntity> resultList = rep.values().stream()
                .peek(pasteBoxEntity -> System.out.println("Before filter: " + pasteBoxEntity))
                .filter(PasteBoxEntity::isPublic)
                .peek(pasteBoxEntity -> System.out.println("After public filter: " + pasteBoxEntity))
                .filter(pasteBoxEntity -> pasteBoxEntity.getLifeTime().isAfter(now))
                .peek(pasteBoxEntity -> System.out.println("After time filter: " + pasteBoxEntity))
                .sorted(Comparator.comparing(PasteBoxEntity::getId).reversed())
                .peek(pasteBoxEntity -> System.out.println("After sorting: " + pasteBoxEntity))
                .limit(amount)
                .collect(Collectors.toList());

        System.out.println("Filtered and sorted list: " + resultList);
        return resultList;

    }

    @Override
    public void add(PasteBoxEntity pasteBoxEntity) {
        rep.put(pasteBoxEntity.getHash(), pasteBoxEntity);
    }
}
