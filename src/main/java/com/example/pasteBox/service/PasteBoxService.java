package com.example.pasteBox.service;

import com.example.pasteBox.api.request.PasteBoxRequest;
import com.example.pasteBox.api.response.PasteBoxResponse;
import com.example.pasteBox.api.response.PasteBoxUrlResponse;

import java.util.List;

public interface PasteBoxService {
    PasteBoxResponse getByHash(String hash);
    List<PasteBoxResponse> getFirstPublicPasteBox();
    PasteBoxUrlResponse create(PasteBoxRequest request);
}
