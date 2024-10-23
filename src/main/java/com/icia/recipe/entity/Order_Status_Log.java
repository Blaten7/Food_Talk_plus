package com.icia.recipe.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "order1_status_log")
public class Order_Status_Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int OSL_id;

    @Column(name = "o_num", columnDefinition = "INT DEFAULT NULL")
    private int OSL_num;

    @Column(name = "old_status", columnDefinition = "INT DEFAULT NULL")
    private int OSL_old_status;

    @Column(name = "new_status", columnDefinition = "INT DEFAULT NULL")
    private int OSL_new_status;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date OSL_updated_at;


}
