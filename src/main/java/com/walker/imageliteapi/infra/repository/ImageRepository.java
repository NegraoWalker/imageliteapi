package com.walker.imageliteapi.infra.repository;

import com.walker.imageliteapi.domain.entity.Image;
import com.walker.imageliteapi.domain.enums.ImageExtension;
import com.walker.imageliteapi.infra.repository.specs.GenericSpecs;
import com.walker.imageliteapi.infra.repository.specs.ImageSpecs;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,String>, JpaSpecificationExecutor<Image> {
    default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension,String query){
        // SELECT * FROM IMAGE WHERE 1 = 1
        Specification<Image> specification = Specification.where(GenericSpecs.conjunction());

        if (extension != null){
            // AND EXTENSION = 'PNG'
            specification = specification.and(ImageSpecs.extensionEqual(extension));
        }

        if (StringUtils.hasText(query)){
            // AND (NAME LIKE 'QUERY' OR TAGS LIKE 'QUERY')
            specification = specification.and(Specification.anyOf(ImageSpecs.nameLike(query),ImageSpecs.tagsLike(query)));
        }
        return findAll(specification);
    }
}
