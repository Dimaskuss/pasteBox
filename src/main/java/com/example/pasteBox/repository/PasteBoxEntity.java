package com.example.pasteBox.repository;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PasteBoxEntity {
    private String data;
    private String hash;
    private LocalDateTime lifeTime;
    private boolean isPublic;
}
