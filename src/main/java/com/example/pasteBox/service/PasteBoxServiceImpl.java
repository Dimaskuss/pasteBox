package com.example.pasteBox.service;

import com.example.pasteBox.api.request.PasteBoxRequest;
import com.example.pasteBox.api.response.PasteBoxResponse;
import com.example.pasteBox.api.response.PasteBoxUrlResponse;

import java.util.List;

public class PasteBoxServiceImpl implements PasteBoxService{
    @Override
    public PasteBoxResponse getByHash(String hash) {
        return null;
    }

    @Override
    public List<PasteBoxResponse> getFirstPublicPasteBox(int amount) {
        return null;
    }

    @Override
    public PasteBoxUrlResponse create(PasteBoxRequest request) {
        return null;
    }
}
