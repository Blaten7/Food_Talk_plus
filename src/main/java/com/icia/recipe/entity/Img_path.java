package com.icia.recipe.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "i_path")
public class Img_path implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long img_path_id;

    @Column(name = "image_name", nullable = false, length = 255)
    private String img_path_name;

    @Column(name = "image_url", nullable = false, length = 255)
    private String img_path_url;

}
