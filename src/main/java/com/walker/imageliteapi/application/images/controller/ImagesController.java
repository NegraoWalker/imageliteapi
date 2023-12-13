package com.walker.imageliteapi.application.images.controller;

import com.walker.imageliteapi.domain.entity.Image;
import com.walker.imageliteapi.domain.enums.ImageExtension;
import com.walker.imageliteapi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImagesController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity save(@RequestParam("file") MultipartFile multipartFile, @RequestParam("name") String name, @RequestParam("tags") List<String> tags) throws IOException {
        log.info("Imagem recebida: name: {}, size: {}", multipartFile.getOriginalFilename(), multipartFile.getSize());
        log.info("Nome definido para a imagem: {}", name);
        log.info("Tags: {}",tags);
        log.info("Content Type: {} ",multipartFile.getContentType());
        log.info("Media Type: {} ", MediaType.valueOf(multipartFile.getContentType()));
        Image image = Image.builder().name(name).tags(String.join(",",tags)).extension(ImageExtension.valueOf(multipartFile.getContentType())).file(multipartFile.getBytes()).build();
        imageService.save(image);
        return ResponseEntity.ok().build();
    }



}
