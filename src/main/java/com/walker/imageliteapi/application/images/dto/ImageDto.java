package com.walker.imageliteapi.application.images.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ImageDto {
    private String url;
    private String name;
    private String extension;
    private Long size;
    private LocalDate uploadDate;
}
