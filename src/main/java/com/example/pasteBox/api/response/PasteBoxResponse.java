package com.example.pasteBox.api.response;

import com.example.pasteBox.api.request.PublicStatus;
import lombok.Data;

@Data
public class PasteBoxResponse {
    private String data;
    private PublicStatus status;
}
