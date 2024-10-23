package com.icia.recipe.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "m_id", nullable = false, length = 50)
    private String member_id;

    @Column(name = "m_pw", columnDefinition = "VARCHAR(150) DEFAULT NULL", length = 150)
    private String member_pw;

    @Column(name = "m_name", nullable = false, length = 10)
    private String member_name;

    @Column(name = "m_address", nullable = false, length = 50)
    private String member_address;

    @Column(name = "m_phone", nullable = false, length = 13)
    private String member_phone;

    @Column(name = "m_block", length = 1, columnDefinition = "CHAR(1) DEFAULT 0")
    private String member_block;

    @Column(name = "role", length = 5, columnDefinition = "VARCHAR(5) DEFAULT 'USER'")
    private String member_role;
}
