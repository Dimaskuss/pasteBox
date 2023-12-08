package com.example.pasteBox.service;

import com.example.pasteBox.api.request.PasteBoxRequest;
import com.example.pasteBox.api.request.PublicStatus;
import com.example.pasteBox.api.response.PasteBoxResponse;
import com.example.pasteBox.api.response.PasteBoxUrlResponse;
import com.example.pasteBox.repository.PasteBoxEntity;
import com.example.pasteBox.repository.PasteBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor

@ConfigurationProperties(prefix = "app")
public class PasteBoxServiceImpl implements PasteBoxService {

private String host;
private int publicListSize;
    private final PasteBoxRepository repository;
    private final AtomicInteger generateId = new AtomicInteger(0);

    @Override
    public PasteBoxResponse getByHash(String hash) {
        PasteBoxEntity pasteBoxEntity = repository.getByHash(hash);
        return new PasteBoxResponse(pasteBoxEntity.getData(), pasteBoxEntity.isPublic());
    }

    @Override
    public List<PasteBoxResponse> getFirstPublicPasteBox() {
//        return repository.getListOfPublicAndAlive(publicListSize);
        return null;
    }

    @Override
    public PasteBoxUrlResponse create(PasteBoxRequest request) {
        int hash = generateId();
        PasteBoxEntity pasteBoxEntity = new PasteBoxEntity();
        pasteBoxEntity.setData(request.getData());
        pasteBoxEntity.setId(hash);
        pasteBoxEntity.setHash(Integer.toHexString(hash));
        pasteBoxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);
        repository.add(pasteBoxEntity);

        return new PasteBoxUrlResponse(host+"/"+pasteBoxEntity.getHash());
    }

    private int generateId() {
        return generateId.getAndIncrement();
    }
}
