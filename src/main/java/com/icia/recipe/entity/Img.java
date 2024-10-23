package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "img")
public class Img {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "i_num")
    private int img_num;

    @Column(name = "i_path", nullable = false, length = 500)
    private String img_path;

    @Column(name = "i_sys_name", nullable = false, length = 250)
    private String img_sys_name;

    @Column(name = "i_original_name", nullable = false, length = 250)
    private String img_origin_name;

    @Column(name = "f_num", columnDefinition = "VARCHAR(50) DEFAULT NULL")
    private String f_num;

    @Column(name = "r_num", unique = true, columnDefinition = "TINYINT DEFAULT NULL")
    private String r_num;

    @Column(name = "m_id", columnDefinition = "VARCHAR(20) DEFAULT NULL")
    private String m_id;

    @Column(name = "i_filesize", nullable = false, length = 20)
    private String img_filesize;

    @Column(name = "i_register_name", nullable = false, length = 30, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date img_register_name;












}
