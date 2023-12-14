package com.walker.imageliteapi.application.images.controller;

import com.walker.imageliteapi.application.images.ImageMapper;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImagesController {

    private final ImageService imageService;
    private final ImageMapper imageMapper;

    @PostMapping
    public ResponseEntity save(@RequestParam("file") MultipartFile multipartFile, @RequestParam("name") String name, @RequestParam("tags") List<String> tags) throws IOException {
        log.info("Imagem recebida: name: {}, size: {}", multipartFile.getOriginalFilename(), multipartFile.getSize());
        log.info("Nome definido para a imagem: {}", name);
        log.info("Tags: {}",tags);
        log.info("Content Type: {} ",multipartFile.getContentType());
        log.info("Media Type: {} ", MediaType.valueOf(multipartFile.getContentType()));

        Image image = imageMapper.mapToImage(multipartFile,name,tags);
        Image savedImage = imageService.save(image);
        URI imageUri = buildImageURL(savedImage);

        return ResponseEntity.created(imageUri).build();
    }


    private URI buildImageURL(Image image){  //Retornando a URL da imagem
        String imagePath = "/" + image.getId();
        return ServletUriComponentsBuilder.fromCurrentRequest().path(imagePath).build().toUri();
    }

}
