package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_num")
    private Integer notice_num;

    @Column(name = "title", nullable = false, length = 100)
    private String notice_title;

    @Column(name = "contents", columnDefinition = "VARCHAR(3000) DEFAULT NULL", length = 3000)
    private String notice_contents;

    @Column(name = "m_id", length = 50, nullable = false)
    private Integer memberId_FK_notice;


}
