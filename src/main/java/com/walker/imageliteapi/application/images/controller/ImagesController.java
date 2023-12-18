package com.walker.imageliteapi.application.images.controller;

import com.walker.imageliteapi.application.images.ImageMapper;
import com.walker.imageliteapi.application.images.dto.ImageDto;
import com.walker.imageliteapi.domain.entity.Image;
import com.walker.imageliteapi.domain.enums.ImageExtension;
import com.walker.imageliteapi.domain.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id){
        var possibleImage = imageService.getById(id);
        if (possibleImage.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var image = possibleImage.get();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(image.getExtension().getMediaType());
        httpHeaders.setContentLength(image.getSize());
        httpHeaders.setContentDispositionFormData("inline; filename=\"" + image.getFileName() + "\"",image.getFileName());
        return new ResponseEntity<>(image.getFile(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ImageDto>> search(@RequestParam(value = "extension", required = false, defaultValue = "") String extension, @RequestParam(value = "query", required = false) String query){
        var result = imageService.search(ImageExtension.ofName(extension),query);
        var images = result.stream().map(image -> {
            var url = buildImageURL(image);
            return imageMapper.imageToDto(image,url.toString());
        }).collect(Collectors.toList());
        return ResponseEntity.ok(images);
    }







    private URI buildImageURL(Image image){  //Retornando a URL da imagem
        String imagePath = "/" + image.getId();
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path(imagePath).build().toUri();
    }

}
