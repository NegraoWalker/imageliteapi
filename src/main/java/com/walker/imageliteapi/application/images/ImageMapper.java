package com.walker.imageliteapi.application.images;

import com.walker.imageliteapi.domain.entity.Image;
import com.walker.imageliteapi.domain.enums.ImageExtension;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
public class ImageMapper {

    public Image mapToImage(MultipartFile multipartFile, String name, List<String> tags) throws IOException {
        return  Image.builder()
                .name(name)
                .tags(String.join(",",tags))
                .size(multipartFile.getSize())
                .extension(ImageExtension.valueOf(MediaType.valueOf(multipartFile.getContentType())))
                .file(multipartFile.getBytes())
                .build();
    }
}
