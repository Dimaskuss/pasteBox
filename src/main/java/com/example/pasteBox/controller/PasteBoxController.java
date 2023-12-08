package com.example.pasteBox.controller;

import com.example.pasteBox.api.request.PasteBoxRequest;
import com.example.pasteBox.api.response.PasteBoxResponse;
import com.example.pasteBox.api.response.PasteBoxUrlResponse;
import com.example.pasteBox.service.PasteBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PasteBoxController {

    private final PasteBoxService pasteBoxService;

    @GetMapping("/")
    public List<PasteBoxResponse> getPublicPasteList() {
        return pasteBoxService.getFirstPublicPasteBox();
    }

    @GetMapping("/{hash}")
    public PasteBoxResponse getByHash(@PathVariable String hash) {
        return pasteBoxService.getByHash(hash);
    }
    @PostMapping("/")
    public PasteBoxUrlResponse add(@RequestBody PasteBoxRequest request){
        return pasteBoxService.create(request);
    }
}