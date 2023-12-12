package com.walker.imageliteapi.application.images.service;

import com.walker.imageliteapi.domain.entity.Image;
import com.walker.imageliteapi.domain.service.ImageService;
import com.walker.imageliteapi.infra.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ImageServiceImplentation implements ImageService {

    private final ImageRepository imageRepository;
    @Override
    @Transactional
    public Image save(Image image) {
        return imageRepository.save(image);
    }
}
