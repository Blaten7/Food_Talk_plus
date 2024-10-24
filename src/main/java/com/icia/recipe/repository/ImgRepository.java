package com.icia.recipe.repository;

import com.icia.recipe.entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgRepository extends JpaRepository<Img, Long> {
}
