package top.mnsx.take_out.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 菜品
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //菜品名称
    private String name;


    //菜品分类id
    private Long categoryId;


    //菜品价格
    private BigDecimal price;


    //商品码
    private String code;


    //图片
    private String image;


    //描述信息
    private String description;


    //0 停售 1 起售
    private Integer status;


    //顺序
    private Integer sort;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    //是否删除
    private Integer isDeleted;

    public Dish(Dish dish) {
        this.id = dish.getId();
        this.name = dish.getName();
        this.categoryId = dish.getCategoryId();
        this.price = dish.getPrice();
        this.code = dish.getCode();
        this.image = dish.getImage();
        this.description = dish.getDescription();
        this.status = dish.getStatus();
        this.sort = dish.getSort();
        this.createTime = dish.getCreateTime();
        this.updateTime = dish.getUpdateTime();
        this.createUser = dish.getCreateUser();
        this.updateUser = dish.getUpdateUser();
        this.isDeleted = dish.getIsDeleted();
    }
}
