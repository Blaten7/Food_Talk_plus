package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "notice")
public class Notice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_num")
    private Long notice_num;

    @Column(name = "title", nullable = false, length = 100)
    private String notice_title;

    @Column(name = "contents", columnDefinition = "VARCHAR(3000) DEFAULT NULL", length = 3000)
    private String notice_contents;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "m_id", insertable = false, updatable = false)
    private Member member;

}
