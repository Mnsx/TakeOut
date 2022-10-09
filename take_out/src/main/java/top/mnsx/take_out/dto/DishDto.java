package top.mnsx.take_out.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.mnsx.take_out.entity.Dish;
import top.mnsx.take_out.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: take_out
 * @User: Mnsx_x
 * @CreateTime: 2022/10/9 21:25
 * @Description:
 */
public class DishDto extends Dish {
    public List<DishFlavor> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<DishFlavor> flavors) {
        this.flavors = flavors;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    private List<DishFlavor> flavors = new ArrayList<>();
    private String categoryName;
    private Integer copies;

    public DishDto(Dish dish, String categoryName) {
        super(dish);
        this.categoryName = categoryName;
    }

    public DishDto() {
        super();

    }
}
