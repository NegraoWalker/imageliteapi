package com.walker.imageliteapi.infra.repository;

import com.walker.imageliteapi.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image,String> {
}
