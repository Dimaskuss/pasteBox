package com.example.pasteBox.service;

import com.example.pasteBox.api.request.PasteBoxRequest;
import com.example.pasteBox.api.request.PublicStatus;
import com.example.pasteBox.api.response.PasteBoxResponse;
import com.example.pasteBox.api.response.PasteBoxUrlResponse;
import com.example.pasteBox.repository.PasteBoxEntity;
import com.example.pasteBox.repository.PasteBoxRepository;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class PasteBoxServiceImpl implements PasteBoxService {

    private final String host = "http://asc.ru";
    private final int publicListSize = 5;
    private final PasteBoxRepository repository;
    private final AtomicInteger generateId = new AtomicInteger(1);
//    private List<PasteBoxEntity> list = new ArrayList<>();

    @Override
    public PasteBoxResponse getByHash(String hash) {
        PasteBoxEntity pasteBoxEntity = repository.getByHash(hash);
        return new PasteBoxResponse(pasteBoxEntity.getData(), pasteBoxEntity.isPublic());
    }

    public List<PasteBoxResponse> getFirstPublicPasteBox() {
        try {
            List<PasteBoxEntity> list = repository.getListOfPublicAndAlive(publicListSize);

            System.out.println("List from repository: " + list); // добавьте эту строку

            return list.stream().map(pasteBoxEntity ->
                            new PasteBoxResponse(pasteBoxEntity.getData(), pasteBoxEntity.isPublic()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to get first public paste box", e);
        }
    }

    @Override
    public PasteBoxUrlResponse create(PasteBoxRequest request) {
        int hash = generateId();
        PasteBoxEntity pasteBoxEntity = new PasteBoxEntity();
        pasteBoxEntity.setData(request.getData());
        pasteBoxEntity.setId(hash);
        pasteBoxEntity.setHash(Integer.toHexString(hash));
        pasteBoxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);
        pasteBoxEntity.setLifeTime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));
        repository.add(pasteBoxEntity);

        return new PasteBoxUrlResponse(host + "/" + pasteBoxEntity.getHash());
    }

    private int generateId() {
        return generateId.getAndIncrement();
    }
}
