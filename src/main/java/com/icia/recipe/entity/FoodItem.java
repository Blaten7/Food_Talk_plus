package com.icia.recipe.entity;

import com.icia.recipe.dto.manageDto.FoodItemDto;
import jakarta.persistence.*;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "fooditem")
@NoArgsConstructor
@Entity
@SqlResultSetMapping(
        name = "FoodItemDtoMapping",
        classes = @ConstructorResult(
                targetClass = FoodItemDto.class,
                columns = {
                        @ColumnResult(name = "f_num", type = Integer.class),
                        @ColumnResult(name = "f_title", type = String.class),
                        @ColumnResult(name = "f_price", type = String.class),
                        @ColumnResult(name = "f_views", type = Integer.class),
                        @ColumnResult(name = "f_code", type = String.class),
                        @ColumnResult(name = "f_volume", type = String.class),
                        @ColumnResult(name = "f_origin", type = String.class),
                        @ColumnResult(name = "f_cal", type = String.class),
                        @ColumnResult(name = "f_save", type = String.class),
                        @ColumnResult(name = "i_path", type = String.class),
                        @ColumnResult(name = "i_sys_name", type = String.class),
                        @ColumnResult(name = "i_original_name", type = String.class)
                }
        )
)
public class FoodItem {

    @Id
    @Column(name = "f_num", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fooditem_num;

    @Column(name = "c_num2", nullable = true, length = 20)
    private String midCgNum;

    @Column(name = "f_title", nullable = false)
    private String foodItem_Title;

    @Column(name = "f_contents")
    private String foodItem_Contents;

    @Column(name = "f_price")
    private int foodItem_price;

    @Column(name = "f_count")
    private String foodItem_Count;

    @Column(name = "f_date")
    private Date foodItem_Date;

    @Column(name = "f_edate")
    private Date foodItem_Expire_Date;

    @Column(name = "f_views")
    private int foodItem_Views;

    @Column(name = "f_code", nullable = true)
    private String foodItem_Code;

    @Column(name = "f_volume")
    private String foodItem_Volume;

    @Column(name = "f_origin")
    private String foodItem_Origin;

    @Column(name = "f_cal")
    private String foodItem_Cal;

    @Column(name = "f_save")
    private String foodItem_Save;

    @Column(columnDefinition = "TINYINT DEFAULT '1'")
    private int status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "c_num", nullable = false, insertable = false, updatable = false)
    private Category bigCgNum;

    @ManyToMany(mappedBy = "foodList")
    private List<Cart> cartList = new ArrayList<>();

}
